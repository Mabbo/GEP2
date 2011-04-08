package evolver;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import base.*;
import framework.*;

public class Evolver {

	Random _rand = new Random();
	
	private Boolean killswitch = false;
	private boolean isKilled() {
		boolean retVal;
		synchronized ( killswitch ) {
			retVal = killswitch;
		}
		return retVal;
	}
	public void Kill() {
		synchronized ( killswitch ) {
			killswitch = true;
		}
	}
	
	public void Evolve(String configFile) {

		Config conf = new Config();
		try{
			conf.LoadConfigurationFile(configFile);
		}catch(Exception ex){
			System.err.println("Error loading config file: ");
			ex.printStackTrace();
			return;
		}
		Evolve(conf);
	}
	
	public void Evolve(String configFile, PrintWriter pw) {

		Config conf = new Config();
		try{
			conf.LoadConfigurationFile(configFile);
			conf.setOut(pw);
		}catch(Exception ex){
			System.err.println("Error loading config file: ");
			ex.printStackTrace();
			return;
		}
		Evolve(conf);
	}
	
	private void Evolve(Config conf){	
		
		//Load the DataSetLoader
		DataSetLoader dsl = conf.getDataSetLoader();

		//Get the DataSet
		DataSet trainSet = new DataSet();
		DataSet testSet = new DataSet();
		String datafile = conf.getDataSetFile();
		dsl.Load(datafile, trainSet, conf);

		//split into training set and testing set
		trainSet.SplitDataSet(testSet, 1.0-conf.getTrainingPercentage());

		//Create Population
		ArrayList<Unit> population = new ArrayList<Unit>();
		
		//Create EvolverState object 
		EvolverState es = new EvolverState(population, trainSet, testSet, conf);

		for( int i = 0; i < conf.getStartProcess().size(); ++i){
			EvolverStateProcess esp = conf.getStartProcess().get(i);
			String params = conf.getStartProcessParameter().get(i);
			esp.Initialize(params);
			esp.Process(es);
		}

		for( int i = 0; i < conf.getBeforeRunProcess().size(); ++i){
			EvolverStateProcess esp = conf.getBeforeRunProcess().get(i);
			String params = conf.getBeforeRunProcessParameter().get(i);
			esp.Initialize(params);
		}
		for( int i = 0; i < conf.getEndOfRunProcess().size(); ++i){
			EvolverStateProcess esp = conf.getEndOfRunProcess().get(i);
			String params = conf.getEndOfRunProcessParameter().get(i);
			esp.Initialize(params);
		}

		//For each run
		for( int r = 0; r < conf.getNumberOfRuns(); ++r){

			es.ResetGeneration();
			population.clear();
			for( int i = 0; i < conf.getPopulationSize(); ++i){
				Genome genome = new Genome(conf);
				genome.InitializeRandom();
				Unit unit = new Unit(conf, genome);
				population.add(unit);
			}
			
			for( EvolverStateProcess e : conf.getBeforeRunProcess()){
				e.Process(es);
			}				

			for( int i = 0; i < conf.getBeforeGenerationProcess().size(); ++i){
				EvolverStateProcess esp = conf.getBeforeGenerationProcess().get(i);
				String params = conf.getBeforeGenerationProcessParameter().get(i);
				esp.Initialize(params);
			}
			for( int i = 0; i < conf.getEndOfGenerationProcess().size(); ++i){
				EvolverStateProcess esp = conf.getEndOfGenerationProcess().get(i);
				String params = conf.getEndOfGenerationProcessParameter().get(i);
				esp.Initialize(params);
			}

			//For each generation
			for( int g = 0; g < conf.getNumberOfGenerations(); ++g ) {

				if(isKilled()) return;
				for( EvolverStateProcess e : conf.getBeforeGenerationProcess()){
					e.Process(es);
				}
				if(isKilled()) return;
				//Fitness test for each population member
				conf.getFitnessProcess().Process(es);
				if(isKilled()) return;
				
				//Sort by fitness
				Collections.sort(population);
				
				//Select using selection method
				SelectionMethod sel = conf.getSelectionMethod();
				sel.RemovePopulation(es);
				if(isKilled()) return;
				//Breed and Mutate
				int popSize = population.size();
				while( population.size() < conf.getPopulationSize() ) {
					Crossover cross = conf.getModificationSet().getCrossover();
					Unit A = population.get(_rand.nextInt(popSize));
					Unit B = population.get(_rand.nextInt(popSize));
					Unit newUnit = cross.Cross(A, B, conf);
					population.add(newUnit);
				}
				if(isKilled()) return;
				for( int m = 0; m < conf.getMutationRate() * population.size(); ++m) {
					Mutator mut = conf.getModificationSet().getMutator();
					Unit u = population.get(_rand.nextInt(population.size()-1)+1);
					mut.Mutate(u, conf);
					u.Initialize();						
				}
				if(isKilled()) return;
				for( EvolverStateProcess e : conf.getEndOfGenerationProcess()){
					e.Process(es);
				}
				if(isKilled()) return;
				es.IncrementGeneration();
			}
			//For each Run EvolverStateProcess e
			for( EvolverStateProcess e : conf.getEndOfRunProcess()){
				e.Process(es);
			}
			if(isKilled()) return;
			es.IncrementRun();
		}

		for( int i = 0; i < conf.getEndProcess().size(); ++i){
			EvolverStateProcess esp = conf.getEndProcess().get(i);
			String params = conf.getEndProcessParameter().get(i);
			esp.Initialize(params);
			esp.Process(es);
		}
	}	
}

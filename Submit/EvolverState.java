import java.io.PrintWriter;
import java.util.ArrayList;

public class EvolverState {
	
	private ArrayList<Unit> population;
	private Config config;
	private DataSet trainSet;
	private DataSet testSet;
	private int 	runNumber;
	private int 	genNumber;
	
	public EvolverState(ArrayList<Unit> pop, DataSet train, DataSet test, Config conf) {
		population = pop;
		trainSet = train;
		testSet = test;
		config = conf;
		runNumber = 0;
		genNumber = 0;
	}
	
	public ArrayList<Unit> getPopulation() {
		return population;
	}
	public void setPopulation(ArrayList<Unit> pop){
		population = pop;
	}
	public Config getConfig() {
		return config;
	}
	public DataSet getTrainingSet(){
		return trainSet;
	}
	public DataSet getTestingSet() {
		return testSet;
	}
	
	public void IncrementGeneration(){
		genNumber++;
	}
	public void IncrementRun(){
		runNumber++;
	}

	public int getGenerationNumber(){
		return genNumber;
	}
	public int getRunNumber() {
		return runNumber;
	}

	public void ResetGeneration(){
		genNumber = 0;
	}
	public void ResetRuns(){
		runNumber = 0;
	}
	
	public PrintWriter out() {
		return config.out();
	}
	
}

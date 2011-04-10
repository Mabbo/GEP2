package builtin;

import java.util.HashMap;

import base.EvolverState;
import base.Instance;
import base.Unit;
import framework.FitnessProcess;

public class RareHitsBonus implements FitnessProcess {

	public boolean isFitnessProcess() {
		return true;
	}
	public void Initialize(String parameters) {
	
	}
	public void Process(EvolverState es) {
		//We build a hashset, and store in it the number of times each 
		//Instance gets correctly identified by a Unit. Then, we run 
		//again, and give more points to those Instances least identified
		HashMap<Instance, Integer> values = new HashMap<Instance, Integer>();
		
		//For each member of the population
		for( Unit u : es.getPopulation() ){
			//For each test in the training set
			for( Instance inst : es.getTrainingSet().getInstances() ){
				//If the member correctly identifies class, add one to fitness
				if( !values.containsKey(inst) ) values.put(inst, es.getPopulation().size() + 1);
				int guess = u.Classify(inst);
				if( guess == inst.getClassValue() ) {
					values.put(inst, values.get(inst) - 1);
//					if( values.get(inst) <= 0 )
//						es.out().println("Thats odd, it reached 0, hashmap size: " + values.size());
				}
			}
		}
		
		//For each member of the population
		for( Unit u : es.getPopulation() ){
			u.setFitnessScore(0);
			u.setNumberCorrect(0);
			//For each test in the training set
			for( Instance inst : es.getTrainingSet().getInstances() ){
				//If the member correctly identifies class, add one to fitness
				int guess = u.Classify(inst);
				if( guess == inst.getClassValue() ) {
					int FitnessValue = (int)(Math.floor(Math.pow(values.get(inst), 0.25)));
					u.incrementFitness( FitnessValue );
					u.incrementNumberCorrect(1);					
				}
			}
		}
	}

	public void Terminate() {

	}

}

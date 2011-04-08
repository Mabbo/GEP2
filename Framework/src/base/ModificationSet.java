package base;

import java.util.ArrayList;
import java.util.Random;

import framework.*;

public class ModificationSet {

	Random _rand = new Random();
	
	ArrayList<Mutator> mutators = new ArrayList<Mutator>();
	ArrayList<Crossover> crossovers = new ArrayList<Crossover>();
	ArrayList<Integer> mutator_weights = new ArrayList<Integer>();
	ArrayList<Integer> crossover_weights = new ArrayList<Integer>();
	int totalMutatorWeight = 0;
	int totalCrossoverWeight = 0;
	
	public void addMutator(Mutator mut, int weight){
		mutators.add(mut);
		mutator_weights.add(weight);
		totalMutatorWeight += weight;
	}
	
	public void addCrossover(Crossover cross, int weight){
		crossovers.add(cross);
		crossover_weights.add(weight);
		totalCrossoverWeight += weight;
	}
	
	public int getMutatorCount(){
		return mutators.size();
	}
	
	public int getCrossoverCount() {
		return crossovers.size();
	}
	
	public Mutator getMutator(int index){
		return mutators.get(index);
	}
	public Crossover getCrossover(int index){
		return crossovers.get(index);
	}
	public Mutator getMutator(){
		int choice = _rand.nextInt(totalMutatorWeight);
		for( int i = 0; i < mutators.size(); ++i){
			choice -= mutator_weights.get(i);
			if( choice < 0 ) {
				return mutators.get(i);
			}
		}
		return null;
	}
	public Crossover getCrossover(){
		int choice = _rand.nextInt(totalCrossoverWeight);
		for( int i = 0; i < crossovers.size(); ++i){
			choice -= crossover_weights.get(i);
			if( choice < 0 ) {
				return crossovers.get(i);
			}
		}
		return null;
	}
	
	
}

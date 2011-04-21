import java.util.HashMap;

public class DupeDetectAddOnCorrect implements FitnessProcess {

	
	private HashMap<String, Integer> lastScores;
	
	public boolean isFitnessProcess() {
		return true;
	}

	public void Initialize(String parameters) {
		lastScores = new HashMap<String, Integer>();
	}

	public void Process(EvolverState es) {
		//For each member of the population
		HashMap<String, Integer> newScores = new HashMap<String, Integer>(); 
		int dupecount = 0;
		for( Unit u : es.getPopulation() ){
		//set fitness to 0
			
			String dna = u.getGenome().getDNAString();
			if( lastScores.containsKey(dna)) {
				int score = lastScores.get(dna);
				newScores.put(dna, score);
				dupecount++;
			} else {
				u.setFitnessScore(0);
				for( Instance inst : es.getTrainingSet().getInstances() ){
					int guess = u.Classify(inst);
					if( guess == inst.getClassValue() ) {
						u.incrementFitness(1);
					}
				}
				newScores.put(dna, u.getFitnessScore());
			}
		}
		es.getConfig().out().println("Dupes: " + dupecount);
		lastScores = newScores;
	}

	@Override
	public void Terminate() {
		// TODO Auto-generated method stub
		
	}

}

package base;

public class Unit implements Comparable<Unit>{

	Genome genome;
	Phenome phenome;
	Config config;
	int fitnessScore = 0;
	boolean initialized = false;
	
	public Unit(Config conf, Genome genome) {
		config = conf; this.genome = genome;
	}
	
	public Unit(Unit other){
		config = other.getConfig(); 
		genome = new Genome(other.genome);
		fitnessScore = other.getFitnessScore();
	}
	
	public void Initialize(){
		phenome = new Phenome(genome, config);
		phenome.Initialize();
		initialized = true;
	}
	
	public int Classify(Instance instance){// For a given instance from a dataset, return classification #
		if( !initialized ) Initialize();
		double[] inputs = instance.getValue();
		return phenome.Classify(inputs);
	}
	
	public Genome getGenome() {
		return genome;
	}

	public void setGenome(Genome genome) {
		this.genome = genome;
	}

	public Phenome getPhenome() {
		return phenome;
	}
	
	public int getFitnessScore() {
		return fitnessScore;
	}

	public void setFitnessScore(int fitnessScore) {
		this.fitnessScore = fitnessScore;
	}

	public Config getConfig() {
		return config;
	}

	public int compareTo(Unit o) {
		if( o.fitnessScore < this.fitnessScore )
			return -1;
		else return 1;
	}

	public void incrementFitness(int i) {
		fitnessScore += i;
	}

}

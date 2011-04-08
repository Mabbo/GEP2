package base;

public class Phenome {
	boolean initialized = false;
	Genome genome;
	Phene[][] phenes;
	Config conf;
	
	public Phenome(Genome genome, Config conf){
		this.genome = genome;
		this.conf = conf;
	}
	
	public void Initialize(){
		if( initialized ) return;
		
		//Need to create Phenes for each Gene, arrange them correctly
		//Create phene layers
		phenes = new Phene[conf.getNumberLayers()][];
		for( int layer = 0; layer < conf.getNumberLayers(); ++layer) {
			phenes[layer] = new Phene[conf.getNodesInLayer(layer)];
			for( int index = 0; index < conf.getNodesInLayer(layer); ++index) {
				phenes[layer][index] = new Phene(genome.getGene(layer, index), conf);
			}
		}
		initialized = true;		
	}
	
	
	public int Classify(double[] inputVec) {
		if(!initialized) return -1;

		double[] inputs = inputVec;
		double[] outputs = null;
		//for each layer
		for( int i = 0; i < conf.getNumberLayers(); ++i) {
			//for each phene
			outputs = new double[conf.getNodesInLayer(i)];
			for( int p = 0; p < conf.getNodesInLayer(i); ++p){
				//give it the current inputs
				outputs[p] = phenes[i][p].getOutput(inputs);
				//put it's result into an output layer
			}
			//set the input layer to output
			inputs = outputs;
		}
		//find which class has highest value, return that class
		int maxClass = -1;
		double maxScore = -999999999.9;
		for( int i = 0; i < conf.getNumberOfClasses(); ++i){
			if( outputs[i] > maxScore ) {
				maxClass = i; maxScore = outputs[i];
			}
		}
		return maxClass;
	}

	public Phene getPhene(int layer, int index){
		return phenes[layer][index];
	}
	
}

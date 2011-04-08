package base;

public class Genome {

	private Config _conf;
	private Gene[][] _genes;
	
	public Genome(Config conf) {
		_conf = conf;	
	}
	
	public Genome(Genome genome) {
		_conf = genome._conf;
		_genes = new Gene[_conf.getNumberLayers()][];
		//For each layer
		for( int layer = 0; layer < _conf.getNumberLayers(); ++layer) {
		//For each node in the layer
			_genes[layer] = new Gene[_conf.getNodesInLayer(layer)];
			for( int node = 0; node < _conf.getNodesInLayer(layer); ++node) {
				_genes[layer][node] = new Gene(genome._genes[layer][node]);
			}
		}
	}
	
	public void InitializeRandom(){
		//create a full random dna string to initialize with
		_genes = new Gene[_conf.getNumberLayers()][];
		//For each layer
		for( int layer = 0; layer < _conf.getNumberLayers(); ++layer) {
		//For each node in the layer
			_genes[layer] = new Gene[_conf.getNodesInLayer(layer)];
			for( int node = 0; node < _conf.getNodesInLayer(layer); ++node) {
				_genes[layer][node] = Gene.makeRandomGene(_conf, layer);
			}
		}
	}
		
	public Gene getGene(int layer, int index){
		return _genes[layer][index];
	}
	
	public void setGene(int layer, int index, Gene g){
		_genes[layer][index] = g;
	}

}

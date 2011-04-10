package base;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;

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

	
	//returns a byte[] representing the entire genome, every gene
	public Byte[] getDNA() {
		
		ArrayList<Byte> built = new ArrayList<Byte>();
		for( int i = 0; i < _genes.length; ++i){
			for( int j = 0; j < _genes[i].length; ++j){
				byte[] dna = _genes[i][j].getDNA();
				for( int x = 0; x < dna.length; ++x)
					built.add(dna[x]);
			}
		}
		return built.toArray(new Byte[0]);
	}
	public String getDNAString() {
		String output = "";
		for( int i = 0; i < _genes.length; ++i){
			for( int j = 0; j < _genes[i].length; ++j){
				byte[] dna = _genes[i][j].getDNA();
				for( int x = 0; x < dna.length; ++x)
					output += "" + dna[x];
				for( int y = 0; y < _genes[i][j].getRNC().length; ++y) {
					output += "" + _genes[i][j].getRNC(y);
				}
			}
		}
		return output;		
	}
	
}

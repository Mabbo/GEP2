import java.util.Random;

import base.Config;
import base.Gene;
import base.Unit;
import framework.Function;
import framework.Mutator;


public class Complexify implements Mutator {

	Random _rand = new Random();
	public void Mutate(Unit u, Config conf) {
		//For a random gene
		//Take a random piece of the head, and replace it with a function
		
		int layer = _rand.nextInt(conf.getNumberLayers());
		int genIndex = _rand.nextInt(conf.getNodesInLayer(layer));
		
		Function func = conf.getNodeFunctionSet().getRandomFunction();
		
		Gene gene = u.getGenome().getGene(layer, genIndex);

		int index = _rand.nextInt(conf.getNodeHeadSize()); 
		byte[] dna = gene.getDNA();
		dna[index] = func.getSymbol();
		gene.setDNA(dna);	
	}	

}

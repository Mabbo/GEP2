package builtin;

import java.util.Random;

import base.Config;
import base.Gene;
import base.Unit;
import framework.Mutator;

public class RandomReplacement implements Mutator {

	private Random _rand = new Random();
	public void Mutate(Unit u, Config conf) {
		//pick a random layer
		int layer = _rand.nextInt(conf.getNumberLayers());

		int g = _rand.nextInt(conf.getNodesInLayer(layer));
		
		Gene gene = u.getGenome().getGene(layer, g);
		
		byte[] dna = gene.getDNA();
		int pick = _rand.nextInt(dna.length);
		
		boolean isHead = pick < conf.getNodeHeadSize();
		byte[] possibleOptions = (isHead? conf.getHeadValues(layer) :
										  conf.getTailValues(layer));
		dna[pick] = possibleOptions[_rand.nextInt(possibleOptions.length)];
		
		gene.setDNA(dna);
		
	}

}

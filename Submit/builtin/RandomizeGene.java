import java.util.Random;

public class RandomizeGene implements Mutator {

	private Random _rand = new Random();
	public void Mutate(Unit u, Config conf) {
		int layer = _rand.nextInt(conf.getNumberLayers());
		int g = _rand.nextInt(conf.getNodesInLayer(layer));
		
		Gene gene = Gene.makeRandomGene(conf, layer);
		u.getGenome().setGene(layer, g, gene);
	}

}

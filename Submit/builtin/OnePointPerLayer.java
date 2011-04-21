package builtin;

import java.util.Random;

import base.Config;
import base.Genome;
import base.Unit;
import framework.Crossover;

public class OnePointPerLayer implements Crossover {

	private Random _rand = new Random();
	public Unit Cross(Unit pA, Unit pB, Config conf) {
		
		Unit parentA;
		Unit parentB;
		if( _rand.nextBoolean() ){
			parentA = pA;
			parentB = pB;
		} else{
			parentB = pA;
			parentA = pB;
		}
		
		Genome child = new Genome(parentA.getGenome());
		//For each layer
		for( int layer = 0; layer < conf.getNumberLayers(); ++layer){
			//pick pivot 
			int pivot = _rand.nextInt(conf.getNodesInLayer(layer));
			for( int i = 0; i < pivot; ++i) {
				child.setGene(layer, i, parentA.getGenome().getGene(layer, i));
			}
			for( int i = pivot; i < conf.getNodesInLayer(layer); ++i) {
				child.setGene(layer, i, parentB.getGenome().getGene(layer, i));
			}
			if( _rand.nextBoolean()) {
				Unit temp = parentA;
				parentA = parentB;
				parentB = temp;
			}
		}
		Unit childUnit = new Unit(conf, child);
		return childUnit;
	}

}

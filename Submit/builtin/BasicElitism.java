package builtin;

import base.EvolverState;
import framework.SelectionMethod;

public class BasicElitism implements SelectionMethod {

	private double keepFraction = 0.5;
	
	public void RemovePopulation(EvolverState es) {
		int toKeep = (int) (keepFraction * es.getConfig().getPopulationSize());
		while( es.getPopulation().size() > toKeep ) {
			es.getPopulation().remove(es.getPopulation().size()-1);
		}
	}

	public void Initialize(String[] args) {
		if( args.length == 0 ) return;
		
		String sKeep = args[0];
		if( sKeep.indexOf("keep=") < 0) return;
		
		String sKeepCut = sKeep.replace("keep=", "");
		double newVal = Double.parseDouble(sKeepCut);
		if (newVal > 0 ) {
			keepFraction = newVal;
		}
	}

}

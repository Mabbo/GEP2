package builtin;

import java.io.PrintWriter;

import base.EvolverState;
import framework.EvolverStateProcess;

public class OutputBestScore implements EvolverStateProcess {

	public void Process(EvolverState es) {
		PrintWriter out = es.getConfig().out();
		out.println("" + es.getGenerationNumber()
					+ ": " + es.getPopulation().get(0).getFitnessScore()
					+ " / " + es.getTrainingSet().size() );
	}

	@Override
	public void Initialize(String parameters) {
		// TODO Auto-generated method stub
		
	}

}

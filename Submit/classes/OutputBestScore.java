import java.io.PrintWriter;

public class OutputBestScore implements EvolverStateProcess {

	public void Process(EvolverState es) {
		PrintWriter out = es.getConfig().out();
		out.println("" + es.getGenerationNumber()
					+ ": " + es.getPopulation().get(0).getFitnessScore()
					+ ", " + es.getPopulation().get(0).getNumberCorrect()
					+ " / " + es.getTrainingSet().size() );
	}

	@Override
	public void Initialize(String parameters) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void Terminate() {
		// TODO Auto-generated method stub
		
	}

}

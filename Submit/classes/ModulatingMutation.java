import java.io.PrintWriter;

public class ModulatingMutation implements EvolverStateProcess {

	
	int bestScore = -100000;
	int timeSinceLastIncrease = 0;
	double minMutation = 0.25;
	double maxMutation = 10.0;
	int maxTime = 50;
	
	public void Process(EvolverState es) {
		PrintWriter out = es.getConfig().out();
		
		int best = es.getPopulation().get(0).getFitnessScore();
		if( best > bestScore ){
			bestScore = best;
			timeSinceLastIncrease = 0;
		} else {
			timeSinceLastIncrease++;
		}
		
		double mutRate = minMutation + (((maxMutation-minMutation) / maxTime) * timeSinceLastIncrease);
		
		if( mutRate > maxMutation){
			timeSinceLastIncrease = 0;
		}
		es.getConfig().setMutationRate(mutRate);
		out.println("Mutation Rate: " + mutRate);
		
	}

	public void Initialize(String parameters) {
		String[] paramList = parameters.split(" ");
		for( int i = 0; i < paramList.length; ++i){
			if( paramList[i].startsWith("min=")){
				minMutation = Double.parseDouble(paramList[i].replace("min=", ""));
			} else if( paramList[i].startsWith("max=")){
				maxMutation = Double.parseDouble(paramList[i].replace("max=", ""));
			} else if (paramList[i].startsWith("period=")){
				maxTime = Integer.parseInt(paramList[i].replace("period=", ""));
			}
		}
	}

	@Override
	public void Terminate() {
		// TODO Auto-generated method stub
		
	}

}

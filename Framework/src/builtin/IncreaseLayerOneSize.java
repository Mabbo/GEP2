package builtin;
import base.EvolverState;
import framework.EvolverStateProcess;


public class IncreaseLayerOneSize implements EvolverStateProcess {

	public int[] values = {30, 45, 67, 100, 1};
	public int size = 5;
	private int count = 0;
	
	public void Initialize(String parameters) {
		try{			
			
		}catch(Exception ex)
		{
			ex.printStackTrace();
		}

	}

	public void Process(EvolverState es) {
		int[] nodesInLayer = es.getConfig().getNodesInLayer();
		nodesInLayer[0] = values[count];
		es.out().println("Set layer 1 to size " + values[count]);
		count++;		
	}

	public void Terminate() {
		// TODO Auto-generated method stub

	}

}

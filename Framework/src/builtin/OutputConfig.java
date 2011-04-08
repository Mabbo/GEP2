package builtin;

import java.io.PrintWriter;

import base.EvolverState;
import base.FunctionSet;
import base.ModificationSet;
import framework.EvolverStateProcess;

public class OutputConfig implements EvolverStateProcess {

	public void Process(EvolverState es) {

		PrintWriter out = es.getConfig().out();
		//What functiosn exist?	
		FunctionSet fs = es.getConfig().getNodeFunctionSet();
		byte[] funcBytes = es.getConfig().getFunctionValues();
		for( int i = 0; i < fs.size(); ++i){
			out.println("Function " 
					+ i + ": " + fs.getFunction(funcBytes[i]).getFunctionName());
		}
		
		ModificationSet ms = es.getConfig().getModificationSet();
		for( int i = 0; i < ms.getCrossoverCount(); ++i){
			out.println("Crossover "
					+ i + ": " + ms.getCrossover(i).getClass().getSimpleName() );
		}
		for( int i = 0; i < ms.getMutatorCount(); ++i){
			out.println("Mutator "
					+ i + ": " + ms.getMutator(i).getClass().getSimpleName() );
		}
	}

	@Override
	public void Initialize(String parameters) {
		// TODO Auto-generated method stub
		
	}
}

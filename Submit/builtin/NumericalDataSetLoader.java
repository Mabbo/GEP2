package builtin;

import java.io.BufferedReader;
import java.io.FileReader;
import base.Config;
import base.DataSet;
import base.Instance;
import framework.DataSetLoader;

public class NumericalDataSetLoader implements DataSetLoader {

	
	public int[] ignoredColumns = null;
	public int classIndex = 0;
	
	public void Load(String filename, DataSet trainingSet, Config conf) {
		
		//NEED TO LOOK AT PARAMETERS GIVEN DURING INITIALIZATION
		
		int numInputs = conf.getNumberOfInputs();

		try {
			FileReader fr = new FileReader(filename);
			BufferedReader br = new BufferedReader(fr);
			String tmp;
			tmp = br.readLine();
			while( tmp != null && !tmp.isEmpty()) {
				String[] split = tmp.split(",");
				double[] data = new double[numInputs];
				int x = 0;
				for( int i = 0; i < numInputs; ++i ) {
					if( i == classIndex ) {
					} else if (arrayContains(ignoredColumns, i)) {
					} else {
						data[x] = Double.parseDouble(split[i]);
						x++;
					}
				}
				int classNum = trainingSet.getClassNumber(split[classIndex]); 
				Instance inst = new Instance(data, classNum);
				trainingSet.addInstance(inst);
				tmp = br.readLine();
			}
			br.close();			
		} catch (Exception ex) {
			System.err.println(ex.getLocalizedMessage());
		}
	}
	private static boolean arrayContains(int[] array, int number) {
		for( int i = 0; i < array.length; ++i){
			if( array[i] == number) return true;
		}
		return false;
	}
	
	
	public void Initialize(String params) {
		
		classIndex = 0;
		ignoredColumns = new int[0];
		//put ignored columns and class index into correct place
		
		String[] paramsplit = params.split(" ");
		//Format: class=5 ignored=1,4,5
		
		if( paramsplit.length > 0) 
		{
			String classInd = paramsplit[0];
			classInd = classInd.replace("classIndex=", "");
			classIndex = Integer.parseInt(classInd); 
		}
		
		
	}

}


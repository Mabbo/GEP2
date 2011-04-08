package evolver;

import framework.DataSetLoader;
import base.Config;
import base.DataSet;
import base.Genome;
import base.Instance;
import base.Unit;

public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try{		
			Config config = new Config();
			config.LoadConfigurationFile("gep.config");

			Genome genome = new Genome(config);

			genome.InitializeRandom();
			Unit u = new Unit(config, genome);
			u.Initialize();
		
			//Load the DataSetLoader
			DataSetLoader dsl = config.getDataSetLoader();

			//Get the DataSet
			DataSet trainSet = new DataSet();
			DataSet testSet = new DataSet();
			String datafile = config.getDataSetFile();
			dsl.Load(datafile, trainSet, config);

			//split into training set and testing set
			trainSet.SplitDataSet(testSet, 1.0-config.getTrainingPercentage());

			Instance instance = trainSet.getInstance(0);
			int classification = u.Classify(instance);
			
			System.out.println("Classified as: " + classification);
		
			System.out.println("done");
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

}

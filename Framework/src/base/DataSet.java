package base;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataSet {

	private ArrayList<Instance> instances;
	ArrayList<String> classes;
	
	public DataSet(){
		instances = new ArrayList<Instance>();
	}
	
	public DataSet(int size){
		instances = new ArrayList<Instance>(size);
	}
	
	public void addInstance(Instance inst){
		instances.add(inst);
	}
	
	public Instance getInstance(int index){
		return instances.get(index);
	}
	
	public int getClassNumber(String classname) {
		if(classes == null) classes = new ArrayList<String>();
		//Do we already have an instance of this class?
		//if not, add it, and return that index
		for( int i = 0; i < classes.size(); ++i) {
			if( classes.get(i).equals(classname)) return i;
		}
		classes.add(classname);
		return classes.size()-1;
	}

	public int size() {
		return instances.size();
	}
	
	private static Random _rand = null;
	public void SplitDataSet(DataSet destination, double fraction) {
		//Takes a fraction of this dataset, and puts it into another
		if( _rand == null ) _rand = new Random();
		assert(fraction >= 0.0 && fraction <= 1.0);
		int numToMove = (int) (instances.size() * fraction);
		for( int i = 0; i < numToMove; ++i){
			//pick random instance
			int pick = _rand.nextInt(instances.size());
			destination.addInstance(instances.get(pick));
			instances.remove(pick);
		}
	}

	public List<Instance> getInstances() {
		return instances;
	}
	
	
}

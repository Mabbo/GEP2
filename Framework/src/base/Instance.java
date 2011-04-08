package base;

public class Instance {

	double[] _values;
	int _classValue;
	
	public Instance(double[] values, int classValue) {
		_values = values;
		_classValue = classValue;
	}
	public int size() { return _values.length; }
	public double[] getValue(){
		return _values;
	}
	public double getValue(int index){
		return _values[index];
	}
	public int getClassValue() {
		return _classValue;
	}
}

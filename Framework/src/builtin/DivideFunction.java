package builtin;

import framework.Function;

public class DivideFunction implements Function {

	static final double epsilon = 0.0001;
	private byte symbol;

	public int getNumArgs() {
		return 2;
	}

	public double ApplyFunction(double[] args) {
		assert(args.length == 2);
		if( args[1] == 0)
			return args[0] / epsilon;
		else return args[0] / args[1];
	}

	public void setSymbol(byte sym) {
		symbol = sym;
	}

	public byte getSymbol() {
		return symbol;
	}
	public String getFunctionName() {
		return "Divide";
	}	
	
}

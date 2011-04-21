package builtin;

import framework.Function;

public abstract class MathFunction implements Function {
	
	protected abstract double applyFunction(double a, double b);
	protected byte symbol;
	public double ApplyFunction(double[] args) {
		assert(args.length == getNumArgs());
		return applyFunction(args[0], args[1]);
	}
	
	public int getNumArgs() {
		return 2;
	}
	public byte getSymbol() {
		return symbol;
	}
	public void setSymbol(byte b){
		symbol = b;
	}
	
	
}


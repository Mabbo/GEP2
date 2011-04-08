import framework.Function;


public class IfLessThan implements Function {

	byte symbol;
	public double ApplyFunction(double[] arguments) {
		assert(arguments.length == 4);
		if( arguments[0] < arguments[1] )
			return arguments[2];
		else 
			return arguments[3];
	}

	public String getFunctionName() {
		return "If-Less-Than";
	}
	public int getNumArgs() {
		return 4;
	}
	public byte getSymbol() {
		return symbol;
	}
	public void setSymbol(byte sym) {
		symbol = sym;
	}

}

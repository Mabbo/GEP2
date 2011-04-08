import framework.Function;


public class SineFunction implements Function {

	private byte symbol;
	
	public double ApplyFunction(double[] arguments) {
		return Math.sin(arguments[0]);
	}

	public String getFunctionName() {
		return "Sine";
	}

	public int getNumArgs() {
		return 1;
	}

	public byte getSymbol() {
		return symbol;
	}

	public void setSymbol(byte sym) {
		symbol = sym;
	}

}

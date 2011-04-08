import framework.Function;


public class Sigmoid implements Function {

	byte symbol = 0;
	public double ApplyFunction(double[] arguments) {
		return 1.0 / (1.0 + Math.exp(-1.0 * arguments[0]));
	}
	public String getFunctionName() {
		return "Sigmoid";
	}
	public int getNumArgs() {
		return 1;
	}
	public byte getSymbol() {
		return symbol;
	}

	@Override
	public void setSymbol(byte sym) {
		symbol = sym;
	}

}

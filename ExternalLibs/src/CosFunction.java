import framework.Function;


public class CosFunction implements Function {
	private byte symbol;
	
	public double ApplyFunction(double[] arguments) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getFunctionName() {
		return "Cos";
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

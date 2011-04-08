package framework;

public interface Function {
	 double ApplyFunction(double[] arguments);
	 int getNumArgs();
	 byte getSymbol();
	 void setSymbol(byte sym);
	 String getFunctionName();
}


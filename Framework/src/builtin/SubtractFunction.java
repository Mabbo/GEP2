package builtin;

public class SubtractFunction extends MathFunction {
	public double applyFunction(double a, double b) {
		return a - b;
	}
	public String getFunctionName() {
		return "Subtract";
	}
}


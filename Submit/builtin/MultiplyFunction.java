package builtin;

public class MultiplyFunction extends MathFunction {
	protected double applyFunction(double a, double b) {
		return a * b;
	}
	public String getFunctionName() {
		return "Multiply";
	}
}

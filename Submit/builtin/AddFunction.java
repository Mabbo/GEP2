package builtin;

public class AddFunction extends MathFunction {
	public double applyFunction(double a, double b) {
		return a + b;
	}
	public String getFunctionName() {
		return "Add";
	}
}
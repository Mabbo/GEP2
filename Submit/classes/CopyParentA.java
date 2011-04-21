
public class CopyParentA implements Crossover {

	public Unit Cross(Unit parentA, Unit parentB, Config conf) {
		Unit child = new Unit(parentA);
		return child;
	}

}

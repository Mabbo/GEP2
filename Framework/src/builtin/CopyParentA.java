package builtin;

import base.Config;
import base.Unit;
import framework.Crossover;

public class CopyParentA implements Crossover {

	public Unit Cross(Unit parentA, Unit parentB, Config conf) {
		Unit child = new Unit(parentA);
		return child;
	}

}

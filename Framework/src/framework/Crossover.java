package framework;

import base.Unit;
import base.Config;

public interface Crossover {
	Unit Cross(Unit parentA, Unit parentB, Config conf);
}

package framework;

import base.Config;
import base.Unit;

public interface Mutator {
	void Mutate(Unit u, Config conf);
}

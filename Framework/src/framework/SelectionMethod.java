package framework;

import base.EvolverState;


public interface SelectionMethod {
	void Initialize(String[] args);
	void RemovePopulation(EvolverState es);
}

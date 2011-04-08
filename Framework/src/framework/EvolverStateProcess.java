package framework;

import base.EvolverState;


public interface EvolverStateProcess {
	void Initialize(String parameters);
	void Process(EvolverState es);
}

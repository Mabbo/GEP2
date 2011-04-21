
public interface EvolverStateProcess {
	void Initialize(String parameters);
	void Process(EvolverState es);
	void Terminate();
}

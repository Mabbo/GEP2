package framework;

import base.*;

public interface DataSetLoader {
	void Initialize(String params);
	void Load(String filename, DataSet ds, Config conf);
}

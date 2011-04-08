package base;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import framework.*;

public class Config {

	//---------------------------------------------------//

	private String Title = "";
	private String DataSetFile = null;
	private String DataSetLoaderLocation = null;
	private String DataSetLoaderFilename = null;
	private DataSetLoader DataSetLoader = null;
	private String DataSetLoaderParameterString = null;
	private int NumberOfInputs = 1;
	private int NumberOfClasses = 1;
	private double TrainingPercentage = 1.0;
	private int NumberOfRuns = 1;
	private int NumberOfGenerations = 1;
	private int PopulationSize = 10;
	
	//---------------------------------------------------//
	
	private int NodeHeadSize = 2;
	private int NodeTailSize = 0;
	private FunctionSet NodeFunctionSet = null;
	private int NumberRNC = 0;
	private int NumberLayers = 0;
	private int[] NodesInLayer = null;
	
	//---------------------------------------------------//
	
	private EvolverStateProcess FitnessProcess = null;
	private double KeepPercentage = 0.75;
	private SelectionMethod SelectionMethod = null;
	private ModificationSet ModificationSet = null;
	private double MutationRate = 0.1;

	//---------------------------------------------------//
	
	private ArrayList<EvolverStateProcess> StartProcess = null;
	private ArrayList<String> StartProcessParameter = null;
	private ArrayList<EvolverStateProcess> BeforeRunProcess = null;
	private ArrayList<String> BeforeRunProcessParameter = null;
	private ArrayList<EvolverStateProcess> BeforeGenerationProcess = null;
	private ArrayList<String> BeforeGenerationProcessParameter = null;
	private ArrayList<EvolverStateProcess> EndOfGenerationProcess = null;
	private ArrayList<String> EndOfGenerationProcessParameter = null;
	private ArrayList<EvolverStateProcess> EndOfRunProcess = null;
	private ArrayList<String> EndOfRunProcessParameter = null;
	private ArrayList<EvolverStateProcess> EndProcess = null;
	private ArrayList<String> EndProcessParameter = null;
	
	public enum LetterType{
		FUNCTION, TERMINAL, RNC,
		UNKNOWN;
	};
	
	
	public Config() {
		Title = "";
		DataSetFile = "";
		DataSetLoaderLocation = "";
		DataSetLoaderFilename = "";
		DataSetLoaderParameterString = "";	
		
		NodeFunctionSet = new FunctionSet();
	
		this.DataSetLoader = null;
		DataSetLoaderParameterString = "";
		NumberOfInputs = 1;
		NumberOfClasses = 1;
		TrainingPercentage = 1.0;
		NumberOfRuns = 1;
		NumberOfGenerations = 1;
		PopulationSize = 10;
		
		NodeHeadSize = 2;
		NodeTailSize = 0;
		NodeFunctionSet = new FunctionSet();
		NumberRNC = 5;
		NumberLayers = 0;
		NodesInLayer = new int[NumberLayers];

		//EvolverStateProcess FitnessProcess = null;
		KeepPercentage = 0.75;
		this.SelectionMethod = null;
		ModificationSet = new ModificationSet();
		MutationRate = 0.1;

		
		StartProcess = new ArrayList<EvolverStateProcess>();
		StartProcessParameter = new ArrayList<String>();
		BeforeRunProcess = new ArrayList<EvolverStateProcess>();
		BeforeRunProcessParameter = new ArrayList<String>();
		BeforeGenerationProcess = new ArrayList<EvolverStateProcess>();
		BeforeGenerationProcessParameter = new ArrayList<String>();
		EndOfGenerationProcess = new ArrayList<EvolverStateProcess>();
		EndOfGenerationProcessParameter = new ArrayList<String>();
		EndOfRunProcess = new ArrayList<EvolverStateProcess>();
		EndOfRunProcessParameter = new ArrayList<String>();
		EndProcess = new ArrayList<EvolverStateProcess>();
		EndProcessParameter = new ArrayList<String>();
		
	}
	
	//---------------Getters and Setters-----------------//
	
	public String getTitle(){
		return Title;
	}
	public void setTitle(String value) {
		Title = value;
	}
	public String getDataSetFile() {
		return DataSetFile;
	}
	public void setDataSetFile(String dataSetLocation) {
		DataSetFile = dataSetLocation;
	}
	public String getDataSetLoaderLocation() {
		return DataSetLoaderLocation;
	}
	public void setDataSetLoaderLocation(String dataSetLoaderLocation) {
		DataSetLoaderLocation = dataSetLoaderLocation;
	}
	public String getDataSetLoaderFilename() {
		return DataSetLoaderFilename;
	}
	public void setDataSetLoaderFilename(String dataSetLoaderFilename) {
		DataSetLoaderFilename = dataSetLoaderFilename;
	}
	public DataSetLoader getDataSetLoader() {
		return this.DataSetLoader;
	}
	public String getDataSetLoaderParameterString() {
		return DataSetLoaderParameterString;
	}
	public void setDataSetLoaderParameterString(String dataSetLoaderParameterString) {
		DataSetLoaderParameterString = dataSetLoaderParameterString;
	}
	public int getNumberOfClasses(){
		return NumberOfClasses;
	}
	public void setNumberOfClasses(int value){
		NumberOfClasses = value;
	}
	public void setNumberOfInputs(int numberOfInputs) {
		NumberOfInputs = numberOfInputs;
	}
	public int getNumberOfInputs() {
		return NumberOfInputs;
	}
	public double getTrainingPercentage() {
		return TrainingPercentage;
	}
	public void setTrainingPercentage(double trainingPercentage) {
		TrainingPercentage = trainingPercentage;
	}
	public int getNumberOfRuns() {
		return NumberOfRuns;
	}
	public void setNumberOfRuns(int numberOfRuns) {
		NumberOfRuns = numberOfRuns;
	}
	public int getNumberOfGenerations() {
		return NumberOfGenerations;
	}
	public void setNumberOfGenerations(int numberOfGenerations) {
		NumberOfGenerations = numberOfGenerations;
	}
	public int getPopulationSize() {
		return PopulationSize;
	}
	public void setPopulationSize(int value) {
		PopulationSize = value;
	}
	public int getNodeHeadSize() {
		return NodeHeadSize;
	}
	public int getNodeTailSize() {
		return NodeTailSize;
	}
	public void setNodeHeadSize(int nodeHeadSize) {
		NodeHeadSize = nodeHeadSize;
	}
	public void setNodeTailSize(){
		NodeTailSize = (getNodeHeadSize() * (NodeFunctionSet.getMaxArgs()-1)) + 1;
	}
	public FunctionSet getNodeFunctionSet() {
		return NodeFunctionSet;
	}
	public void setNodeFunctionSet(FunctionSet nodeFunctionSet) {
		NodeFunctionSet = nodeFunctionSet;
	}
	public int getNumberRNC() {
		return NumberRNC;
	}
	public void setNumberRNC(int numberRNC) {
		NumberRNC = numberRNC;
	}
	public int getNumberLayers() {
		return NumberLayers;
	}
	public void setNumberLayers(int numberLayers) {
		NumberLayers = numberLayers;
	}
	public int[] getNodesInLayer() {
		return NodesInLayer;
	}
	public void setNodesInLayer(int[] nodesInLayer) {
		NodesInLayer = nodesInLayer;
	}
	public EvolverStateProcess getFitnessProcess() {
		return FitnessProcess;
	}
	public void setFitnessProcess(EvolverStateProcess fitnessProcess) {
		FitnessProcess = fitnessProcess;
	}
	public double getKeepPercentage() {
		return KeepPercentage;
	}
	public void setKeepPercentage(double value){
		KeepPercentage = value;
	}
	public SelectionMethod getSelectionMethod() {
		return SelectionMethod;
	}
	public void setSelectionMethod(SelectionMethod selectionMethod) {
		SelectionMethod = selectionMethod;
	}
	public ModificationSet getModificationSet() {
		return ModificationSet;
	}
	public void setModificationSet(ModificationSet modificationSet) {
		ModificationSet = modificationSet;
	}
	public double getMutationRate() {
		return MutationRate;
	}
	public void setMutationRate(double mutationRate) {
		MutationRate = mutationRate;
	}
	public ArrayList<EvolverStateProcess> getStartProcess() {
		return StartProcess;
	}
	public void setStartProcess(ArrayList<EvolverStateProcess> startProcess) {
		StartProcess = startProcess;
	}
	public ArrayList<String> getStartProcessParameter() {
		return StartProcessParameter;
	}
	public void setStartProcessParameter(ArrayList<String> startProcessParameter) {
		StartProcessParameter = startProcessParameter;
	}
	public ArrayList<EvolverStateProcess> getBeforeRunProcess() {
		return BeforeRunProcess;
	}
	public void setBeforeRunProcess(ArrayList<EvolverStateProcess> beforeRunProcess) {
		BeforeRunProcess = beforeRunProcess;
	}
	public ArrayList<String> getBeforeRunProcessParameter() {
		return BeforeRunProcessParameter;
	}
	public void setBeforeRunProcessParameter(
			ArrayList<String> beforeRunProcessParameter) {
		BeforeRunProcessParameter = beforeRunProcessParameter;
	}
	public ArrayList<EvolverStateProcess> getBeforeGenerationProcess() {
		return BeforeGenerationProcess;
	}
	public void setBeforeGenerationProcess(
			ArrayList<EvolverStateProcess> beforeGenerationProcess) {
		BeforeGenerationProcess = beforeGenerationProcess;
	}
	public ArrayList<String> getBeforeGenerationProcessParameter() {
		return BeforeGenerationProcessParameter;
	}
	public void setBeforeGenerationProcessParameter(
			ArrayList<String> beforeGenerationProcessParameter) {
		BeforeGenerationProcessParameter = beforeGenerationProcessParameter;
	}
	public ArrayList<EvolverStateProcess> getEndOfGenerationProcess() {
		return EndOfGenerationProcess;
	}
	public void setEndOfGenerationProcess(
			ArrayList<EvolverStateProcess> endOfGenerationProcess) {
		EndOfGenerationProcess = endOfGenerationProcess;
	}
	public ArrayList<String> getEndOfGenerationProcessParameter() {
		return EndOfGenerationProcessParameter;
	}
	public void setEndOfGenerationProcessParameter(
			ArrayList<String> endOfGenerationProcessParameter) {
		EndOfGenerationProcessParameter = endOfGenerationProcessParameter;
	}
	public ArrayList<EvolverStateProcess> getEndOfRunProcess() {
		return EndOfRunProcess;
	}
	public void setEndOfRunProcess(ArrayList<EvolverStateProcess> endOfRunProcess) {
		EndOfRunProcess = endOfRunProcess;
	}
	public ArrayList<String> getEndOfRunProcessParameter() {
		return EndOfRunProcessParameter;
	}
	public void setEndOfRunProcessParameter(
			ArrayList<String> endOfRunProcessParameter) {
		EndOfRunProcessParameter = endOfRunProcessParameter;
	}
	public ArrayList<EvolverStateProcess> getEndProcess() {
		return EndProcess;
	}
	public void setEndProcess(ArrayList<EvolverStateProcess> endProcess) {
		EndProcess = endProcess;
	}
	public ArrayList<String> getEndProcessParameter() {
		return EndProcessParameter;
	}
	public void setEndProcessParameter(ArrayList<String> endProcessParameter) {
		EndProcessParameter = endProcessParameter;
	}
	
	//---------------------------------------------------//

	byte functionIndexEnd = 0;
	byte terminalIndexEnd = 0;
	byte rncIndexEnd = 0;
	
	//---------------------------------------------------//
	
	public void AddFunction(String funcClassDir, String funcClassName, byte b ){
		
		Class<?> funcClass = getClassFromFile(funcClassDir, funcClassName);
		//Instantiate the function
		Function function = (Function)createInstanceOf(funcClass);
		function.setSymbol((byte) b);
		//Add to the functionset
		NodeFunctionSet.addFunction(function);
	}
	
	//---------------------------------------------------//
	
	private DocumentBuilderFactory domFactory;
	private DocumentBuilder builder;
	private Document doc;
	private XPathFactory factory;
	XPath xpath;
	private boolean xpathinitialized = false;
	
	private void InitXPath(String filename) throws ParserConfigurationException, SAXException, IOException {
		if( xpathinitialized ) return;
		domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true); // never forget this!
	    builder = domFactory.newDocumentBuilder();
	    doc = builder.parse(filename);

	    factory = XPathFactory.newInstance();
	    xpath = factory.newXPath();
	    xpathinitialized = true;
	}
	private NodeList getNodes(String expression) throws XPathExpressionException{
		//For each part of the config, load it with an XPathExpression object
	    XPathExpression expr = xpath.compile(expression);

	    Object result = expr.evaluate(doc, XPathConstants.NODESET);
	    NodeList nodes = (NodeList) result;
	    return nodes;
	}
	private Node getNode(String expression) throws XPathExpressionException{
		return getNodes(expression).item(0);
	}
	private String getStringValue(String expression) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()");
	    for (int i = 0; i < nodes.getLength();) {
	        return nodes.item(i).getNodeValue(); 
	    }	
	    return "";
	}
	private double getDoubleValue(String expression) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()");
	    for (int i = 0; i < nodes.getLength();) {
	        return Double.parseDouble(nodes.item(i).getNodeValue()); 
	    }	
	    return 0.0;
	}
	private int getIntValue(String expression) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()");
	    for (int i = 0; i < nodes.getLength();) {
	        return Integer.parseInt(nodes.item(i).getNodeValue()); 
	    }	
	    return 0;
	}
	private void getESPList(String expression, ArrayList<EvolverStateProcess> procList, 
			ArrayList<String> paraList) throws XPathExpressionException{
		NodeList ProcNodes = getNodes(expression);
		for( int i = 0; i < ProcNodes.getLength(); ++i){
			//For each crossover node
			Node ProcNode = ProcNodes.item(i);
			//Read it's name, and location
	
			ClassInformation cinfo = getClassInformation(ProcNode);
			String ProcClassDir = cinfo.dir;
			String ProcClassName = cinfo.file;
			String params = cinfo.param;
			
			Class<?> ProcClass = getClassFromFile(ProcClassDir, ProcClassName);
			//Instantiate the function
			EvolverStateProcess Proc = (EvolverStateProcess) createInstanceOf(ProcClass);	
			
			//Add to the modification set
			procList.add(Proc);
			paraList.add(params);
		}
	}
	
	private ClassInformation getClassInformation(String Path) throws XPathExpressionException{
		Node node = getNode(Path);
		return getClassInformation(node);
	}
	
	private ClassInformation getClassInformation(Node node) throws XPathExpressionException{
		NamedNodeMap attrib = node.getAttributes();
		ClassInformation cinfo = new ClassInformation();
		cinfo.isBuiltIn = (attrib.getNamedItem("builtin") != null);
		String sfilename = "";
		String slocation = "";
		String sparams = "";
		if( !cinfo.isBuiltIn ) {
			sfilename = attrib.getNamedItem("classfile").getNodeValue();
			slocation = 
				(attrib.getNamedItem("location") == null? "bin/" : 
			     attrib.getNamedItem("location").getNodeValue());
		} else {
			sfilename = "builtin." + attrib.getNamedItem("builtin").getNodeValue();
			slocation = "bin/";
		}
		sparams = 
			(attrib.getNamedItem("parameters") == null? "" : 
			     attrib.getNamedItem("parameters").getNodeValue());
		cinfo.file=  sfilename;
		cinfo.dir = slocation;
		cinfo.param = sparams;
		return cinfo;
	}
	
	//---------------------------------------------------//
	
	
	private class ClassInformation{
		public String file;
		public String dir;
		public String param;
		public boolean isBuiltIn;
		public ClassInformation(){
			file = dir = param = "";
			isBuiltIn = false;
		}
	}
	
	private void LoadDataSetInformation() throws XPathExpressionException{
		Title = getStringValue("//Title");
		DataSetFile = getStringValue("//DataSet/File");
		TrainingPercentage = getDoubleValue("//DataSet/TrainPercentage");
				
		
		ClassInformation cinfo = getClassInformation("//DataSet/DataSetLoader");
		DataSetLoaderFilename = cinfo.file;
		DataSetLoaderLocation = cinfo.dir;
		DataSetLoaderParameterString = cinfo.param;
		Class<?> dslClass = getClassFromFile(
				DataSetLoaderLocation, DataSetLoaderFilename);
		
		this.DataSetLoader = (framework.DataSetLoader) 
								createInstanceOf(dslClass);
		this.DataSetLoader.Initialize(DataSetLoaderParameterString);
		NumberOfClasses = getIntValue("//DataSet/NumberOfClasses");
		NumberOfInputs = getIntValue("//DataSet/NumberOfInputs");
	}
	
	private void LoadAlgorithmInformation() throws XPathExpressionException{
		NumberOfRuns = getIntValue("//Runs");
		NumberOfGenerations = getIntValue("//Generations");
		PopulationSize = getIntValue("//PopulationSize");		
		ClassInformation cinfo = getClassInformation("//Fitness");
		String fitnessClassName = cinfo.file;
		String fitnessClassDir = cinfo.dir;
		Class<?> fitnessClass = getClassFromFile(fitnessClassDir, fitnessClassName);
		this.FitnessProcess = (EvolverStateProcess) createInstanceOf(fitnessClass);
		
		cinfo = getClassInformation("//Selection");
		String selectionClassName = cinfo.file;
		String selectionClassDir = cinfo.dir;
		String selectionParameters = cinfo.param;
	
		Class<?> selectionClass = getClassFromFile(selectionClassDir, selectionClassName); 
		this.SelectionMethod = (SelectionMethod) createInstanceOf(selectionClass);
		this.SelectionMethod.Initialize(selectionParameters.split(" "));

		this.ModificationSet = new ModificationSet();
		
		NodeList crossoversNodes = getNodes("//Crossovers/Crossover");
		for( int i = 0; i < crossoversNodes.getLength(); ++i){
			//For each crossover node
			Node crossNode = crossoversNodes.item(i);
	
			cinfo = getClassInformation(crossNode);
			String crossName = cinfo.file;
			String crossLocation = cinfo.dir;
			
			Class<?> crossClass = getClassFromFile(crossLocation, crossName);
			//Instantiate the function
			Crossover cross = (Crossover) createInstanceOf(crossClass);
			
			int crossWeight = Integer.parseInt(crossNode.getAttributes().getNamedItem("weight").getNodeValue());
			
			//Add to the modification set
			this.ModificationSet.addCrossover(cross, crossWeight);		
		}
	
		this.MutationRate = getDoubleValue("//MutationRate");
		
		NodeList mutatorNodes = getNodes("//Mutators/Mutator");
		for( int i = 0; i < mutatorNodes.getLength(); ++i){
			//For each crossover node
			Node mutatorNode = mutatorNodes.item(i);
			//Read it's name, and location
			/*String mutatorClassName = mutatorNode.getAttributes()
				.getNamedItem("classfile").getNodeValue();
			
			Node mutatorLocationNode = mutatorNode.getAttributes()
				.getNamedItem("location");
			String mutatorClassDir = "bin/";
			if( mutatorLocationNode != null ) {
				mutatorClassDir = mutatorLocationNode.getNodeValue();
			}*/
			//Get the class from the file
			cinfo = getClassInformation(mutatorNode);
			String mutatorClassDir = cinfo.dir;
			String mutatorClassName = cinfo.file;
			
			Class<?> mutatorClass = getClassFromFile(mutatorClassDir, mutatorClassName);
			//Instantiate the function
			Mutator mutator = (Mutator) createInstanceOf(mutatorClass);
			
			int mutatorWeight = Integer.parseInt(mutatorNode.getAttributes().getNamedItem("weight").getNodeValue());
			
			//Add to the modification set
			this.ModificationSet.addMutator(mutator, mutatorWeight);		
		}
	}
	
	public void LoadNodeInformation() throws XPathExpressionException {
		setNodeHeadSize( getIntValue("//NodeDescription/Head") );
		NodeFunctionSet = new FunctionSet();
		
		NodeList functionNodes = getNodes("//NodeDescription/FunctionSet/*");
		for( int i = 0; i < functionNodes.getLength(); ++i){
		//For each function node
			Node funcNode = functionNodes.item(i);
			//Read it's name, and location
			ClassInformation cinfo = getClassInformation(funcNode);
			String funcClassDir = cinfo.dir;
			String funcClassName = cinfo.file;
			//Get the class from the file
			AddFunction(funcClassDir, funcClassName, (byte)i);
		}
		
		setNodeTailSize();
		
		NumberRNC = getIntValue("//NodeDescription/RNC");
		NodeList layerNodes = getNodes("//NodeLayers/Layer/Nodes/text()");
		NumberLayers = layerNodes.getLength() + 1;
		this.NodesInLayer = new int[NumberLayers];
		int maxTerminals = NumberOfClasses;
		for(int i = 0; i < layerNodes.getLength(); ++i){
			NodesInLayer[i] = Integer.parseInt(layerNodes.item(i).getNodeValue());
			if( NodesInLayer[i] > maxTerminals )
				maxTerminals = NodesInLayer[i];
		}
		NodesInLayer[NumberLayers-1] = NumberOfClasses;
		
		
		functionIndexEnd = (byte) NodeFunctionSet.size();
		terminalIndexEnd = (byte) (functionIndexEnd + maxTerminals);
		rncIndexEnd = (byte) (terminalIndexEnd + NumberRNC);
	}
	
	private void LoadEvolverStateProcesses() throws XPathExpressionException{
		
		StartProcess = new ArrayList<EvolverStateProcess>();
		StartProcessParameter = new ArrayList<String>();
		BeforeRunProcess = new ArrayList<EvolverStateProcess>();
		BeforeRunProcessParameter = new ArrayList<String>();
		BeforeGenerationProcess = new ArrayList<EvolverStateProcess>();
		BeforeGenerationProcessParameter = new ArrayList<String>();
		EndOfGenerationProcess = new ArrayList<EvolverStateProcess>();
		EndOfGenerationProcessParameter = new ArrayList<String>();
		EndOfRunProcess = new ArrayList<EvolverStateProcess>();
		EndOfRunProcessParameter = new ArrayList<String>();
		EndProcess = new ArrayList<EvolverStateProcess>();
		EndProcessParameter = new ArrayList<String>();
		
		getESPList("//Processes/StartProcesses/Process", StartProcess, StartProcessParameter);
		getESPList("//Processes/EndProcesses/Process", EndProcess, EndProcessParameter);
		
		getESPList("//Processes/BeforeRunProcesses/Process", BeforeRunProcess, BeforeRunProcessParameter);
		getESPList("//Processes/EndRunProcesses/Process", EndOfRunProcess, EndOfRunProcessParameter);
		
		getESPList("//Processes/BeforeGenerationProcesses/Process", BeforeGenerationProcess, BeforeGenerationProcessParameter);
		getESPList("//Processes/EndGenerationProcesses/Process", EndOfGenerationProcess, EndOfGenerationProcessParameter);		
	}
	
	public void LoadConfigurationFile(String filename) 
	throws ParserConfigurationException, SAXException, 
		   IOException, XPathExpressionException {
	
		InitXPath(filename);
		LoadDataSetInformation();
		LoadAlgorithmInformation();
		LoadNodeInformation();
		LoadEvolverStateProcesses();
		

	}
	
	
	//---------------------------------------------------//
	
	private static Class<?> getClassFromFile(String dir, String className){
		// Create a File object on the root of the directory containing the class file
		File file = new File(dir);

		try {
		    URL url = file.toURI().toURL();
		    URL[] urls = new URL[]{url};
		    ClassLoader cl = new URLClassLoader(urls);
		    
		    Class<?> cls = cl.loadClass(className);
		    		    
		    return cls;
		} catch (MalformedURLException e) {
			System.err.println("1: Error loading class '" + className + "'.");
			return null;
		} catch (ClassNotFoundException e) {
			System.err.println("2: Error loading class '" + className + "'.");
			return null;
		}
	
	}
	
	private static Object createInstanceOf(Class<?> c) {
		Constructor<?> ct = c.getConstructors()[0];
		Object obj = null;
		try {
			obj = ct.newInstance(new Object[]{});
		} catch (Exception e) {
			System.err.println("Could not instantiate instance of type '" + c.getCanonicalName() + "'.");
			return null;
		}	
		return obj;
	}
	
	
	//---------------------------------------------------//
	
	public int getNodeLength(){
		return getNodeHeadSize() + getNodeTailSize();
	}
	
	public int getNodesInLayer(int layer){
		return NodesInLayer[layer];
	}
	
	
	private byte[] _funcValues = null;
	public byte[] getFunctionValues() {
		if( _funcValues == null) {
			_funcValues = new byte[functionIndexEnd];
			for( byte i = 0; i < functionIndexEnd; ++i){
				_funcValues[i] = i;
			}
		}
		return _funcValues;		
	}
	
	public byte[] getTerminalValues(int layerNum) {	
		int numTerminals = (layerNum == 0? NumberOfInputs : NodesInLayer[layerNum-1]);
		byte[] terminalValues = new byte[numTerminals];
		for( byte i = 0; i < numTerminals; ++i){
			terminalValues[i] = (byte) (functionIndexEnd + i);
		}
		return terminalValues;		
	}
	
	private byte[] _rncValues = null;
	public byte[] getRNCValues() {
		if( _rncValues == null) {
			_rncValues = new byte[rncIndexEnd - terminalIndexEnd];
			for( byte i = 0; i < rncIndexEnd-terminalIndexEnd; ++i){
				_rncValues[i] = (byte) (terminalIndexEnd + i);
			}
		}
		return _rncValues;
	}
	
	private byte[][] layerHeadValues = null;
	public byte[] getHeadValues(int layerNum) {
		if( layerHeadValues == null ) layerHeadValues = new byte[NumberLayers][];
		
		if( layerHeadValues[layerNum] == null) {
			byte[] functionValues = getFunctionValues();
			byte[] terminalValues = getTerminalValues(layerNum);
			byte[] rncValues = getRNCValues();
			layerHeadValues[layerNum] = new byte[functionValues.length
			                                   + terminalValues.length
			                                   + rncValues.length];
			int itt = 0;
			for( byte b : functionValues ){
				layerHeadValues[layerNum][itt] = b;
				itt++;
			}
			for( byte b : terminalValues ){
				layerHeadValues[layerNum][itt] = b;
				itt++;
			}
			for( byte b : rncValues ){
				layerHeadValues[layerNum][itt] = b;
				itt++;
			}
		}
		return layerHeadValues[layerNum];
	}
	
	private byte[][] layerTailValues = null;
	public byte[] getTailValues(int layerNum) {
		if( layerTailValues == null ) layerTailValues = new byte[NumberLayers][];
		
		if( layerTailValues[layerNum] == null) {
			byte[] terminalValues = getTerminalValues(layerNum);
			byte[] rncValues = getRNCValues();
			layerTailValues[layerNum] = new byte[terminalValues.length
			                                   + rncValues.length];
			int itt = 0;
			for( byte b : terminalValues ){
				layerTailValues[layerNum][itt] = b;
				itt++;
			}
			for( byte b : rncValues ){
				layerTailValues[layerNum][itt] = b;
				itt++;
			}
		}
		return layerTailValues[layerNum];
	}
	
	
	public LetterType getTypeFor(byte input) {
		//For a given input, is this a Function, terminal, RNC, or none
		//0<= i < functionIndexEnd => Function
		//functionIndexEnd <= i < terminalIndexEnd => Terminal
		//terminalIndexEnd <= i < rncIndexEnd => RNC
		//else, unknown
		if( input >= 0 && input < functionIndexEnd )
			return LetterType.FUNCTION;
		else if (input >= functionIndexEnd && input < terminalIndexEnd)
			return LetterType.TERMINAL;
		else if (input >= terminalIndexEnd && input < rncIndexEnd)
			return LetterType.RNC;
		else 
			return LetterType.UNKNOWN;
	}
	
	public int getIndexForTerminal(byte terminal) {
		return terminal - functionIndexEnd;
	}
	public int getIndexForRNC(byte rnc){
		return rnc - terminalIndexEnd;
	}	
	
	//--------------------------------------------------------//
	
	private PrintWriter out = new PrintWriter(new SysOutPrintWriter()); 
	public PrintWriter out() { return out; }
	public void setOut(PrintWriter pw) {out = pw;}
	private class SysOutPrintWriter extends Writer{
		public void close() throws IOException {}
		public void flush() throws IOException {}
		public void write(char[] cbuf, int off, int len) throws IOException {
			System.out.println(new String(cbuf,off, len));
		}
	}
	
	//--------------------------------------------------------//	

	
}

package base;
import framework.*;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;

import javax.xml.parsers.*;
import javax.xml.xpath.*;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class OldUselessConfig {
	
private boolean configured = false;

	ArrayList<Class<Function> > functionClasses;
	Class<SelectionMethod> selectionClass;
	ArrayList<Class<EvolverStateProcess> > evolverStateProcesses;
	
	private String 	title = "";
	private String 	datafilelocation = "";
	private String 	datafilename = "";
	private DataSetLoader datasetloader = null;
	private double 	trainpercentage = 0.7;
	private int    	numclasses = 0;
	private int		numinputs = 0;
	private int 	classindex = 0;
	private int[]	ignorecolumns = null;
	
	private int 	numruns = 0;
	private int 	numgenerations = 0;
	private long 	maxruntime = 0;
	private int		populationsize = 0;
	private int 	headlength = 0;
	private int		taillength = -1;
	private int 	numRNC = 0;
	private FunctionSet functionset = null;
	
	private int 	numnodelayers = 1;
	private int[]	nodesperlayer = null;

	private double	mutationrate = 0;
	private ModificationSet modifiers = null;	
	private SelectionMethod selectionMethod = null;
	private double	keeppercentage = 0.0;
	
	private byte 	functionIndexEnd = 0;
	private byte	terminalIndexEnd = 0;
	private byte	rncIndexEnd = 0;
	
	public enum LetterType{
		FUNCTION, TERMINAL, RNC,
		UNKNOWN;
	};
	
	private ArrayList<EvolverStateProcess> generationProcesses 
		= new ArrayList<EvolverStateProcess>();
	private ArrayList<EvolverStateProcess> runProcesses 
		= new ArrayList<EvolverStateProcess>(); 
	private ArrayList<EvolverStateProcess> endProcesses
	 	= new ArrayList<EvolverStateProcess>();
	private ArrayList<EvolverStateProcess> beforeRunProcesses
		= new ArrayList<EvolverStateProcess>();
	
	
	private EvolverStateProcess fitnessTest;
	
	public ArrayList<EvolverStateProcess> getGenerationProcesses() {
		return generationProcesses;
	}
	public ArrayList<EvolverStateProcess> getRunProcesses() {
		return runProcesses;
	}
	public void setMutationrate(double mutationrate) {
		this.mutationrate = mutationrate;
	}
	public ArrayList<EvolverStateProcess> getEndProcesses() {
		return endProcesses;
	}
	public ArrayList<EvolverStateProcess> getBeforeRunProcesses() {
		return beforeRunProcesses;
	}	
	public boolean isConfigured() {
		return configured;
	}
	public String getTitle() {
		return title;
	}
	public String getDatafilelocation() {
		return datafilelocation;
	}
	public String getDatafilename() {
		return datafilename;
	}
	public double getTrainpercentage() {
		return trainpercentage;
	}
	public int getNumclasses() {
		return numclasses;
	}
	public int getNuminputs() {
		return numinputs;
	}
	public int getClassIndex() {
		return classindex;
	}
	public int[] getIgnoreColumns() {
		return ignorecolumns;
	}
	public int getNumruns() {
		return numruns;
	}
	public int getNumgenerations() {
		return numgenerations;
	}
	public long getMaxruntime() {
		return maxruntime;
	}
	public int getPopulationsize() {
		return populationsize;
	}
	public int getHeadlength() {
		return headlength;
	}
	public int getTaillength() {
		return taillength;
	}
	public int getNodelength() {
		return getHeadlength() + getTaillength();
	}
	public int getNumRNC() {
		return numRNC;
	}
	public FunctionSet getNodefunctionset() {
		return functionset;
	}
	public int getNumNodeLayers() {
		return numnodelayers;
	}
	public int getNodesInLayer(int layer) {
		return nodesperlayer[layer];
	}
	public double getMutationrate() {
		return mutationrate;
	}
	public ModificationSet getModifiers() {
		return modifiers;
	}
	public SelectionMethod getSelectionMethod() {
		return selectionMethod;
	}
	public double getKeeppercentage() {
		return keeppercentage;
	}
	
	public void setFitnessTest(EvolverStateProcess fitnessTest) {
		this.fitnessTest = fitnessTest;
	}
	public EvolverStateProcess getFitnessTest() {
		return fitnessTest;
	}
	public DataSetLoader getDataSetLoader()  {
		return datasetloader;		
	}
	
	//-----------------------------------------------------//
	
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
		int numTerminals = (layerNum == 0? numinputs : nodesperlayer[layerNum-1]);
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
		if( layerHeadValues == null ) layerHeadValues = new byte[numnodelayers][];
		
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
		if( layerTailValues == null ) layerTailValues = new byte[numnodelayers][];
		
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

	
	
	//-----------XML PARSING--------------//
	
	private DocumentBuilderFactory domFactory;
	private DocumentBuilder builder;
	private Document doc;
	private XPathFactory factory; 
	private XPath xpath; 

	public void LoadConfigurationFile(String filename) 
		throws ParserConfigurationException, SAXException, 
			   IOException, XPathExpressionException {
		
		InitXPath(filename);

		title = getStringValue("//Title");
		datafilelocation = getStringValue("//DataSet/FileLocation");
		datafilename = getStringValue("//DataSet/FileName");
		trainpercentage = getDoubleValue("//DataSet/TrainPercentage");
		numclasses = getIntValue("//DataSet/Description/NumberOfClasses");
		numinputs = getIntValue("//DataSet/Description/NumberOfInputs");
		classindex = getIntValue("//DataSet/Description/ClassIndex");
		//get the ignored columns
		NodeList ignoreInputs = getNodes("//DataSet/Description/Ignore/text()");
		ignorecolumns = new int[ignoreInputs.getLength()];
		for( int i = 0; i < ignoreInputs.getLength(); ++i){
			ignorecolumns[i] = Integer.parseInt(ignoreInputs.item(i).getNodeValue());
		}
		
		Node dslnode = getNodes("//DataSet/DataSetLoader").item(0);
		
		String dslClassName = dslnode.getAttributes()
		.getNamedItem("classfile").getNodeValue();
		
		Node locationNode = dslnode.getAttributes()
			.getNamedItem("location");
		String dslClassDir = "bin/";
		if( locationNode != null ) {
			dslClassDir = locationNode.getNodeValue();
		}
		
		Class<?> dslClass = getClassFromFile(dslClassDir, dslClassName); 
		datasetloader = (DataSetLoader)createObjectOfClass(dslClass);

		//--------------------//
		
		numruns = getIntValue("//Runs");
		numgenerations = getIntValue("//Generations");
		maxruntime = getIntValue("//MaxRunTime");
		populationsize = getIntValue("//PopulationSize");
		
		headlength = getIntValue("//NodeDescription/Head");

		//-----Functions-----//
		
		functionset = new FunctionSet();
		NodeList functionNodes = getNodes("//NodeDescription/FunctionSet/*");
		for( int i = 0; i < functionNodes.getLength(); ++i){
		//For each function node
			Node funcNode = functionNodes.item(i);
			//Read it's name, and location
			String funcClassName = funcNode.getAttributes()
			.getNamedItem("classfile").getNodeValue();
			
			Node funcLocationNode = funcNode.getAttributes()
				.getNamedItem("location");
			String funcClassDir = "bin/";
			if( funcLocationNode != null ) {
				funcClassDir = funcLocationNode.getNodeValue();
			}
			//Get the class from the file
			Class<?> funcClass = getClassFromFile(funcClassDir, funcClassName);
			//Instantiate the function
			Function function = (Function)createObjectOfClass(funcClass);
			function.setSymbol((byte) i);
			//Add to the functionset
			functionset.addFunction(function);		
		}
		
		//Set tail length
		//t = h*(MaxArg-1)+1
		taillength = (getHeadlength() * (functionset.getMaxArgs()-1)) + 1;
		
		//get number of RNCs
		
		numRNC = getIntValue("//NodeDescription/RNC");		
		
		//------------Read Layer descriptions-------------//
		
		NodeList layerNodes = getNodes("//NodeLayers/Layer/Nodes/text()");
		numnodelayers = layerNodes.getLength() + 1;
		this.nodesperlayer = new int[numnodelayers];
		int maxTerminals = numclasses;
		for(int i = 0; i < layerNodes.getLength(); ++i){
			nodesperlayer[i] = Integer.parseInt(layerNodes.item(i).getNodeValue());
			if( nodesperlayer[i] > maxTerminals )
				maxTerminals = nodesperlayer[i];
		}
		nodesperlayer[numnodelayers-1] = numclasses;
		
		
		functionIndexEnd = (byte) functionset.size();
		terminalIndexEnd = (byte) (functionIndexEnd + maxTerminals);
		rncIndexEnd = (byte) (terminalIndexEnd + numRNC);
		
		//----------Fitness-----------//
		
		
		Node fitnessNode = getNode("//Fitness");
		
		String fitnessClassName = fitnessNode.getAttributes()
			.getNamedItem("classfile").getNodeValue();
		
		Node fitnessLocationNode = fitnessNode.getAttributes()
			.getNamedItem("location");
		
		String fitnessClassDir = "bin/";
		if( fitnessLocationNode != null ) {
			fitnessClassDir = fitnessLocationNode.getNodeValue();
		}
		
		Class<?> fitnessClass = getClassFromFile(fitnessClassDir, fitnessClassName); 
		fitnessTest = (EvolverStateProcess) createObjectOfClass(fitnessClass);
		
		//----------Selection Method-----------//
		
		Node selectionNode = getNode("//Selection");
		
		String selectionClassName = selectionNode.getAttributes()
			.getNamedItem("classfile").getNodeValue();
		
		Node selectionLocationNode = selectionNode.getAttributes()
			.getNamedItem("location");
		
		String selectionClassDir = "bin/";
		if( selectionLocationNode != null ) {
			selectionClassDir = selectionLocationNode.getNodeValue();
		}
		
		Class<?> selectionClass = getClassFromFile(selectionClassDir, selectionClassName); 
		selectionMethod = (SelectionMethod) createObjectOfClass(selectionClass);
		
		keeppercentage = Double.parseDouble(selectionNode.getAttributes().getNamedItem("keep").getNodeValue());
		
		//-------------Crossovers---------------//
		
		modifiers = new ModificationSet();
		
		NodeList crossoversNodes = getNodes("//Crossovers/Crossover");
		for( int i = 0; i < crossoversNodes.getLength(); ++i){
			//For each crossover node
			Node crossNode = crossoversNodes.item(i);
			//Read it's name, and location
			String crossClassName = crossNode.getAttributes()
				.getNamedItem("classfile").getNodeValue();
			
			Node crossLocationNode = crossNode.getAttributes()
				.getNamedItem("location");
			String crossClassDir = "bin/";
			if( crossLocationNode != null ) {
				crossClassDir = crossLocationNode.getNodeValue();
			}
			//Get the class from the file
			Class<?> crossClass = getClassFromFile(crossClassDir, crossClassName);
			//Instantiate the function
			Crossover cross = (Crossover) createObjectOfClass(crossClass);
			
			int crossWeight = Integer.parseInt(crossNode.getAttributes().getNamedItem("weight").getNodeValue());
		
			System.out.println("THIS SHOULD NEVER HAPPEN");
			
			
			//Add to the modification set
			modifiers.addCrossover(cross, crossWeight);		
		}
	
		mutationrate = getDoubleValue("//MutationRate");
		
		NodeList mutatorNodes = getNodes("//Mutators/Mutator");
		for( int i = 0; i < mutatorNodes.getLength(); ++i){
			//For each crossover node
			Node mutatorNode = mutatorNodes.item(i);
			//Read it's name, and location
			String mutatorClassName = mutatorNode.getAttributes()
				.getNamedItem("classfile").getNodeValue();
			
			Node mutatorLocationNode = mutatorNode.getAttributes()
				.getNamedItem("location");
			String mutatorClassDir = "bin/";
			if( mutatorLocationNode != null ) {
				mutatorClassDir = mutatorLocationNode.getNodeValue();
			}
			//Get the class from the file
			Class<?> mutatorClass = getClassFromFile(mutatorClassDir, mutatorClassName);
			//Instantiate the function
			Mutator mutator = (Mutator) createObjectOfClass(mutatorClass);
			
			int mutatorWeight = Integer.parseInt(mutatorNode.getAttributes().getNamedItem("weight").getNodeValue());
			
			//Add to the modification set
			modifiers.addMutator(mutator, mutatorWeight);		
		}
		
		//-------Generation Processes-------//
		
		NodeList genProcNodes = getNodes("//GenerationProcesses/GenerationProcess");
		for( int i = 0; i < genProcNodes.getLength(); ++i){
			//For each crossover node
			Node genProcNode = genProcNodes.item(i);
			//Read it's name, and location
			String genProcClassName = genProcNode.getAttributes()
				.getNamedItem("classfile").getNodeValue();
			
			Node genProcLocationNode = genProcNode.getAttributes()
				.getNamedItem("location");
			String genProcClassDir = "bin/";
			if( genProcLocationNode != null ) {
				genProcClassDir = genProcLocationNode.getNodeValue();
			}
			//Get the class from the file
			Class<?> genProcClass = getClassFromFile(genProcClassDir, genProcClassName);
			//Instantiate the function
			EvolverStateProcess genProc = (EvolverStateProcess) createObjectOfClass(genProcClass);	
			
			//Add to the modification set
			generationProcesses.add(genProc);
		}

		NodeList runProcNodes = getNodes("//RunProcesses/RunProcess");
		for( int i = 0; i < runProcNodes.getLength(); ++i){
			//For each crossover node
			Node runProcNode = runProcNodes.item(i);
			//Read it's name, and location
			String runProcClassName = runProcNode.getAttributes()
				.getNamedItem("classfile").getNodeValue();
			
			Node runProcLocationNode = runProcNode.getAttributes()
				.getNamedItem("location");
			String runProcClassDir = "bin/";
			if( runProcLocationNode != null ) {
				runProcClassDir = runProcLocationNode.getNodeValue();
			}
			//Get the class from the file
			Class<?> runProcClass = getClassFromFile(runProcClassDir, runProcClassName);
			//Instantiate the function
			EvolverStateProcess runProc = (EvolverStateProcess) createObjectOfClass(runProcClass);	
			
			//Add to the modification set
			runProcesses.add(runProc);
		}

		NodeList brProcNodes = getNodes("//BeforeRunProcesses/BeforeRunProcess");
		for( int i = 0; i < brProcNodes.getLength(); ++i){
			//For each crossover node
			Node brProcNode = brProcNodes.item(i);
			//Read it's name, and location
			String brProcClassName = brProcNode.getAttributes()
				.getNamedItem("classfile").getNodeValue();
			
			Node brProcLocationNode = brProcNode.getAttributes()
				.getNamedItem("location");
			String brProcClassDir = "bin/";
			if( brProcLocationNode != null ) {
				brProcClassDir = brProcLocationNode.getNodeValue();
			}
			//Get the class from the file
			Class<?> brProcClass = getClassFromFile(brProcClassDir, brProcClassName);
			//Instantiate the function
			EvolverStateProcess brProc = (EvolverStateProcess) createObjectOfClass(brProcClass);	
			
			//Add to the modification set
			beforeRunProcesses.add(brProc);
		}

	}
	
	private void InitXPath(String filename) throws ParserConfigurationException, SAXException, IOException {
		domFactory = DocumentBuilderFactory.newInstance();
	    domFactory.setNamespaceAware(true); // never forget this!
	    builder = domFactory.newDocumentBuilder();
	    doc = builder.parse(filename);

	    factory = XPathFactory.newInstance();
	    xpath = factory.newXPath();
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
	
	private static Object createObjectOfClass(Class<?> c) {
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
	
	
	
}













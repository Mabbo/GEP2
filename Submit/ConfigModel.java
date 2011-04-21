import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ConfigModel {

	private boolean isNew = false;
	private boolean hasChanged = false;
	
	private String ConfigFileName = "";

	private String Title = "";
	private String DataSetFile = null;
	private ClassInformation DataSetLoader = null;
	private int NumberOfInputs = 1;
	private int NumberOfClasses = 1;
	private double TrainingPercentage = 1.0;
	private int NumberOfRuns = 1;
	private int NumberOfGenerations = 1;
	private int PopulationSize = 10;

	//---------------------------------------------------//

	private int NodeHeadSize = 2;
	private ArrayList<ClassInformation> Functions;
	private int NumberRNC = 0;
	private int NumberLayers = 0;
	private int[] NodesInLayer = null;

	//---------------------------------------------------//

	private ClassInformation FitnessProcess;
	private ClassInformation SelectionMethod;
	private ArrayList<ModifierClassInformation> Mutators;
	private ArrayList<ModifierClassInformation> Crossovers;
	private double MutationRate = 0.1;

	//---------------------------------------------------//
	
	private ArrayList<ClassInformation> StartProcesses = null;
	private ArrayList<ClassInformation> EndProcesses = null;
	private ArrayList<ClassInformation> BeforeRunProcesses = null;
	private ArrayList<ClassInformation> EndOfRunProcesses = null;
	private ArrayList<ClassInformation> BeforeGenerationProcesses = null;
	private ArrayList<ClassInformation> EndOfGenerationProcesses = null;
	

	public ConfigModel(String filename){
		init();
		ConfigFileName = filename;
	}

	public ConfigModel(){
		init();
	}

	private void init(){	

		isNew = true;
		hasChanged = false;
		ConfigFileName = "";
		Title = "";
		DataSetFile = "";
		DataSetLoader = new ClassInformation();
		NumberOfInputs = 1;
		NumberOfClasses = 1;
		TrainingPercentage = 1.0;
		NumberOfRuns = 1;
		NumberOfGenerations = 1;
		PopulationSize = 10;

		NodeHeadSize = 2;
		Functions = new ArrayList<ClassInformation>();

		NumberRNC = 0;
		NumberLayers = 0;
		NodesInLayer = new int[0];

		//---------------------------------------------------//

		FitnessProcess = new ClassInformation();
		SelectionMethod = new ClassInformation();
		Mutators = new ArrayList<ModifierClassInformation>();
		Crossovers = new ArrayList<ModifierClassInformation>();
		
		MutationRate = 0.1;

		//---------------------------------------------------//

		StartProcesses = new ArrayList<ClassInformation>();
		EndProcesses = new ArrayList<ClassInformation>();
		BeforeRunProcesses = new ArrayList<ClassInformation>();
		EndOfRunProcesses = new ArrayList<ClassInformation>();
		BeforeGenerationProcesses = new ArrayList<ClassInformation>();
		EndOfGenerationProcesses = new ArrayList<ClassInformation>();
		
	}

	public void setConfigFileName(String configFileName) {
		hasChanged = true;
		ConfigFileName = configFileName;
	}

	public String getConfigFileName() {
		return ConfigFileName;
	}

	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		hasChanged = true;
		Title = title;
	}
	public String getDataSetFile() {
		return DataSetFile;
	}
	public void setDataSetFile(String dataSetFile) {
		hasChanged = true;
		DataSetFile = dataSetFile;
	}
	public ClassInformation getDataSetLoader() {
		return DataSetLoader;
	}
	public void setDataSetLoader(ClassInformation ci){
		hasChanged = true;
		DataSetLoader = ci;
	}
	public void setDataSetLoaderParameters(String text) {
		hasChanged = true;
		DataSetLoader.param = text;
	}
	public int getNumberOfInputs() {
		return NumberOfInputs;
	}
	public void setNumberOfInputs(int numberOfInputs) {
		hasChanged = true;
		NumberOfInputs = numberOfInputs;
	}
	public int getNumberOfClasses() {
		return NumberOfClasses;
	}
	public void setNumberOfClasses(int numberOfClasses) {
		hasChanged = true;
		NumberOfClasses = numberOfClasses;
	}
	public double getTrainingPercentage() {
		return TrainingPercentage;
	}
	public void setTrainingPercentage(double trainingPercentage) {
		hasChanged = true;
		TrainingPercentage = trainingPercentage;
	}
	public int getNumberOfRuns() {
		return NumberOfRuns;
	}
	public void setNumberOfRuns(int numberOfRuns) {
		hasChanged = true;
		NumberOfRuns = numberOfRuns;
	}
	public int getNumberOfGenerations() {
		return NumberOfGenerations;
	}
	
	public ArrayList<ClassInformation> getStartProcesses() {
		return StartProcesses;
	}

	public ArrayList<ClassInformation> getEndProcesses() {
		return EndProcesses;
	}

	public ArrayList<ClassInformation> getBeforeRunProcesses() {
		return BeforeRunProcesses;
	}

	public ArrayList<ClassInformation> getEndOfRunProcesses() {
		return EndOfRunProcesses;
	}

	public ArrayList<ClassInformation> getBeforeGenerationProcesses() {
		return BeforeGenerationProcesses;
	}

	public ArrayList<ClassInformation> getEndOfGenerationProcesses() {
		return EndOfGenerationProcesses;
	}

	public void setNumberOfGenerations(int numberOfGenerations) {
		hasChanged = true;
		NumberOfGenerations = numberOfGenerations;
	}
	public int getPopulationSize() {
		return PopulationSize;
	}
	public void setPopulationSize(int populationSize) {
		hasChanged = true;
		PopulationSize = populationSize;
	}
	public int getNodeHeadSize() {
		return NodeHeadSize;
	}
	public void setNodeHeadSize(int nodeHeadSize) {
		hasChanged = true;
		NodeHeadSize = nodeHeadSize;
	}
	public int getNumberRNC() {
		return NumberRNC;
	}
	public void setNumberRNC(int numberRNC) {
		hasChanged = true;
		this.NumberRNC = numberRNC;
	}
	public int getNumberLayers() {
		return NumberLayers;
	}
	public void setNumberLayers(int numberLayers) {
		hasChanged = true;
		NumberLayers = numberLayers;
	}
	public int[] getNodesInLayer() {
		return NodesInLayer;
	}
	public void setNodesInLayer(int[] nodesInLayer) {
		hasChanged = true;
		NodesInLayer = nodesInLayer;
	}
	public double getMutationRate() {
		return MutationRate;
	}
	public void setMutationRate(double mutationRate) {
		hasChanged = true;
		MutationRate = mutationRate;
	}
	public ClassInformation getFitnessProcess() {
		return FitnessProcess;
	}
	public ClassInformation getSelectionMethod() {
		return SelectionMethod;
	}
	public void setFitnessProcess(ClassInformation fitnessProcess) {
		hasChanged = true;
		FitnessProcess = fitnessProcess;
	}
	public void setFitnessProcessParameters(String text) {
		hasChanged = true;
		FitnessProcess.param = text;
	}
	public void setSelectionMethod(ClassInformation selectionMethod) {
		hasChanged = true;
		SelectionMethod = selectionMethod;
	}
	public void setSelectionMethodParameters(String text) {
		hasChanged = true;
		SelectionMethod.param = text;
	}
	public ArrayList<ModifierClassInformation> getMutators() {
		return Mutators;
	}
	public ArrayList<ModifierClassInformation> getCrossovers() {
		return Crossovers;
	}
	
	public void Changed(){
		hasChanged = true;
	}
	
	public boolean needsToSave() {
		return isNew || hasChanged;
	}
	
	public boolean getIsNew(){
		return isNew;
	}

	//Reads a config model from an xml file
	public static ConfigModel OpenConfig(String filename) throws ParserConfigurationException, SAXException, IOException, XPathExpressionException {
		ConfigModel conf = new ConfigModel(filename);
		DocumentBuilderFactory domFactory;
		DocumentBuilder builder;
		Document doc;
		XPathFactory factory;
		XPath path;

		domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);
		builder = domFactory.newDocumentBuilder();
		doc = builder.parse(filename);

		factory = XPathFactory.newInstance();
		path = factory.newXPath();

		//Read each item from the XPath values

		conf.setTitle( getStringValue("//Title", path, doc) );
		conf.setDataSetFile(getStringValue("//DataSet/File", path, doc));

		ClassInformation cinfo = getClassInformation("//DataSet/DataSetLoader", path, doc);

		conf.setDataSetLoader(cinfo);
		conf.setNumberOfInputs(getIntValue("//DataSet/NumberOfInputs", path, doc));
		conf.setNumberOfClasses( getIntValue("//DataSet/NumberOfClasses", path, doc) );
		conf.setTrainingPercentage( getDoubleValue("//DataSet/TrainPercentage", path, doc) );

		conf.setNumberOfRuns( getIntValue("//Runs", path, doc) );
		conf.setNumberOfGenerations( getIntValue("//Generations", path, doc) );
		conf.setPopulationSize( getIntValue("//PopulationSize", path, doc)  );

		conf.setNodeHeadSize(getIntValue("//NodeDescription/Head",path,doc));
		getFunctionList("//NodeDescription/FunctionSet/Function", conf.getFunctions(), path, doc);

		conf.setNumberRNC(getIntValue("//NodeDescription/RNC",path, doc));
		int layers = getNodes("//NodeLayers/Layer",path,doc).getLength();
		conf.setNumberLayers(layers);
		int[] NodesInLayer = new int[layers];
		conf.setNodesInLayer(NodesInLayer);
		LoadLayers("//NodeLayers/Layer/Nodes/text()", conf.getNodesInLayer(), path, doc);
		//---------------------------------------------------//

		cinfo = getClassInformation("//Fitness", path, doc);

		conf.setFitnessProcess(cinfo);

		cinfo = getClassInformation("//Selection",path,doc);
		conf.setSelectionMethod(cinfo);

		getModifierList("//Mutators/Mutator", conf.getMutators(), path, doc);		
		getModifierList("//Crossovers/Crossover", conf.getCrossovers(), path, doc);

		conf.setMutationRate( getDoubleValue("//MutationRate", path, doc));

		//---------------------------------------------------//

		getClassList("//Processes/StartProcesses/Process", conf.getStartProcesses(), path, doc);
		getClassList("//Processes/BeforeRunProcesses/Process", conf.getBeforeRunProcesses(), path, doc);
		getClassList("//Processes/BeforeGenerationProcesses/Process", conf.getBeforeGenerationProcesses(), path, doc);
		getClassList("//Processes/EndGenerationProcesses/Process", conf.getEndOfGenerationProcesses(), path, doc);
		getClassList("//Processes/EndRunProcesses/Process", conf.getEndOfRunProcesses(), path, doc);
		getClassList("//Processes/EndProcesses/Process", conf.getEndProcesses(), path, doc);

		conf.isNew = false;
		conf.hasChanged = false;
		return conf;
	}

	public ArrayList<ClassInformation> getFunctions() {
		return Functions;
	}

	private static NodeList getNodes(String expression, XPath xpath, Document doc) throws XPathExpressionException{
		//For each part of the config, load it with an XPathExpression object
		XPathExpression expr = xpath.compile(expression);

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return nodes;
	}
	private static Node getNode(String expression, XPath path, Document doc) throws XPathExpressionException{
		return getNodes(expression, path, doc).item(0);
	}
	private static String getStringValue(String expression, XPath path, Document doc) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()", path, doc);
		for (int i = 0; i < nodes.getLength();) {
			return nodes.item(i).getNodeValue(); 
		}	
		return "";
	}
	private static double getDoubleValue(String expression, XPath path, Document doc) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()", path, doc);
		for (int i = 0; i < nodes.getLength();) {
			return Double.parseDouble(nodes.item(i).getNodeValue()); 
		}	
		return 0.0;
	}
	private static int getIntValue(String expression, XPath path, Document doc) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()", path, doc);
		for (int i = 0; i < nodes.getLength();) {
			return Integer.parseInt(nodes.item(i).getNodeValue()); 
		}	
		return 0;
	}
	private static void getModifierList(String expression, ArrayList<ModifierClassInformation> ALinfo, XPath path, Document doc) throws XPathExpressionException{
		NodeList ProcNodes = getNodes(expression, path, doc);
		for( int i = 0; i < ProcNodes.getLength(); ++i){
			Node ProcNode = ProcNodes.item(i);
			ModifierClassInformation cinfo = getModifierInformation(ProcNode);
			ALinfo.add(cinfo);
		}
	}
	private static void getClassList(String expression, ArrayList<ClassInformation> ALinfo, XPath path, Document doc) throws XPathExpressionException{
		NodeList ProcNodes = getNodes(expression, path, doc);
		for( int i = 0; i < ProcNodes.getLength(); ++i){
			Node ProcNode = ProcNodes.item(i);
			ClassInformation cinfo = getClassInformation(ProcNode);
			ALinfo.add(cinfo);
		}		
	}
	private static void LoadLayers(String expression, int[] layers, XPath path, Document doc) throws XPathExpressionException{
		NodeList layernodes = getNodes(expression, path, doc);
		for( int i = 0; i < layernodes.getLength(); ++i){
			Node node = layernodes.item(i);
			int x = Integer.parseInt(node.getNodeValue());
			layers[i] = x;
		}
	}



	private static void getFunctionList(String expression, ArrayList<ClassInformation> funcs, XPath path, Document doc) throws XPathExpressionException{
		NodeList functions = getNodes("//NodeDescription/FunctionSet/Function", path, doc);
		for( int i = 0; i < functions.getLength(); ++i) {
			Node node = functions.item(i);
			ClassInformation cinfo = getClassInformation(node);
			funcs.add(cinfo);
		}	
	}

	private static ClassInformation getClassInformation(String Path, XPath xpath, Document doc) throws XPathExpressionException{
		Node node = getNode(Path, xpath, doc);
		return getClassInformation(node);
	}

	private static ClassInformation getClassInformation(Node node) throws XPathExpressionException{
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
			sfilename = attrib.getNamedItem("builtin").getNodeValue();
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

	private static ModifierClassInformation getModifierInformation(Node node) throws XPathExpressionException{
		ClassInformation ci = getClassInformation(node);
		ModifierClassInformation mci = new ModifierClassInformation(ci);
		NamedNodeMap attrib = node.getAttributes();
		String weight = attrib.getNamedItem("weight").getNodeValue();
		mci.weight = Integer.parseInt(weight);		
		return mci;
	}
	
	//---------------------------------------------------//	

	public void SaveConfig(String saveTo){
		ConfigFileName = saveTo;
		SaveConfig();
	}

	public void SaveConfig(){
		try {
			InternalSaveConfig();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void InternalSaveConfig() throws ParserConfigurationException, TransformerException, IOException{
		//Create Document object

		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
		DOMImplementation impl = docBuilder.getDOMImplementation();
		Document doc = impl.createDocument(null, null, null);
		
		Element xmln = doc.createElement("xml");
		doc.appendChild(xmln);
		
		AddBasicInfo(doc,xmln);
		AddDataSetInfo(doc,xmln);	
		AddNodeData(doc,xmln);
		AddGeneticMethods(doc,xmln);
		AddProcesses(doc,xmln);

		//write to disc
		DOMSource domSource = new DOMSource(doc);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		transformer.setOutputProperty(OutputKeys.ENCODING,"ISO-8859-1");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		java.io.StringWriter sw = new java.io.StringWriter();
		StreamResult sr = new StreamResult(sw);
		transformer.transform(domSource, sr);
		String xml = sw.toString();

		File file = new File(ConfigFileName);
		if( file.exists() ) file.delete();
		file.createNewFile();
		FileWriter fw = new FileWriter(file);
		fw.write(xml);
		fw.close();
		hasChanged = false;
		isNew = false;
	}

	
	private static void AddToNode(Document doc, Node node, String name, String data){
		Element elem = doc.createElement(name);
		elem.appendChild(doc.createTextNode(data));
		node.appendChild(elem);
	}
	
	private void AddBasicInfo(Document doc, Node node){
		AddToNode(doc, node, "Title", this.getTitle());
		AddToNode(doc, node, "Runs", ""+this.getNumberOfRuns());
		AddToNode(doc, node, "Generations", "" + this.getNumberOfGenerations());
		AddToNode(doc, node, "PopulationSize", "" + this.getPopulationSize());		
	}

	private void AddDataSetInfo(Document doc, Node node){
		Element DataSet = doc.createElement("DataSet");
	
		AddToNode(doc, DataSet, "File", this.getDataSetFile());
		//If the data set is a built-in function, we must note it as such
		//If not, we must include the location and classfile
		
		Element dsl = doc.createElement("DataSetLoader");
		
		if( DataSetLoader.isBuiltIn ) {
			Attr builtin = doc.createAttribute("builtin");
			builtin.setValue(DataSetLoader.getFileName());
			dsl.getAttributes().setNamedItem(builtin);
		} else {
			Attr location = doc.createAttribute("location");
			location.setValue(DataSetLoader.dir);
			Attr classfile = doc.createAttribute("classfile");
			classfile.setValue(DataSetLoader.file);
		}
		if( !DataSetLoader.param.equals("")) {
			Attr params = doc.createAttribute("parameters");
			params.setValue(DataSetLoader.param);
			dsl.getAttributes().setNamedItem(params);
		}
		DataSet.appendChild(dsl);
		
		AddToNode(doc, DataSet, "TrainPercentage", "" + this.getTrainingPercentage());
		AddToNode(doc, DataSet, "NumberOfInputs", "" + this.getNumberOfInputs());
		AddToNode(doc, DataSet, "NumberOfClasses", "" + this.getNumberOfClasses());
		
		node.appendChild(DataSet);
	}

	private void AddNodeData(Document doc, Node node){
		Element desc = doc.createElement("NodeDescription");
		AddToNode(doc, desc, "Head", "" + this.getNodeHeadSize());
		
		Element func = doc.createElement("FunctionSet");
		for( ClassInformation ci : this.getFunctions() ){
			AddFunction(doc, func, ci);
		}
		desc.appendChild(func);
		AddToNode(doc, desc, "RNC", "" + this.getNumberRNC() );
		
		node.appendChild(desc);
		
		Element layers = doc.createElement("NodeLayers");
		for( int i = 0; i < NumberLayers; ++i) {
			Element layer = doc.createElement("Layer");
			Element nodes = doc.createElement("Nodes");
			nodes.appendChild(doc.createTextNode("" + NodesInLayer[i]));
			layer.appendChild(nodes);
			layers.appendChild(layer);
		}
		node.appendChild(layers);
		
	}

	private static void AddFunction(Document doc, Node node, ClassInformation cinfo){
		Element func = doc.createElement("Function");
		if( cinfo.isBuiltIn ) {
			Attr builtin = doc.createAttribute("builtin");
			builtin.setValue(cinfo.getFileName());
			func.getAttributes().setNamedItem(builtin);
		} else {
			Attr location = doc.createAttribute("location");
			location.setValue(cinfo.dir);
			func.getAttributes().setNamedItem(location);
			Attr classfile = doc.createAttribute("classfile");
			classfile.setValue(cinfo.getFileName());
			func.getAttributes().setNamedItem(classfile);
		}
		node.appendChild(func);
	}
	
	private void AddGeneticMethods(Document doc, Node node){
		
		Element fitness = doc.createElement("Fitness");
		ClassInformation finfo = getFitnessProcess();
		if( finfo.isBuiltIn ) {
			Attr builtin = doc.createAttribute("builtin");
			builtin.setValue(finfo.getFileName());
			fitness.getAttributes().setNamedItem(builtin);
		} else {
			Attr location = doc.createAttribute("location");
			location.setValue(finfo.dir);
			fitness.getAttributes().setNamedItem(location);
			Attr classfile = doc.createAttribute("classfile");
			classfile.setValue(finfo.getFileName());
			fitness.getAttributes().setNamedItem(classfile);
		}
		node.appendChild(fitness);
		
		Element selection = doc.createElement("Selection");
		ClassInformation sinfo = getSelectionMethod();
		if( sinfo.isBuiltIn ) {
			Attr builtin = doc.createAttribute("builtin");
			builtin.setValue(sinfo.getFileName());
			selection.getAttributes().setNamedItem(builtin);
		} else {
			Attr location = doc.createAttribute("location");
			location.setValue(sinfo.dir);
			selection.getAttributes().setNamedItem(location);
			Attr classfile = doc.createAttribute("classfile");
			classfile.setValue(sinfo.getFileName());
			selection.getAttributes().setNamedItem(classfile);
		}
		node.appendChild(selection);		

		AddToNode(doc, node, "MutationRate", ""+ getMutationRate());
		
		Element muts = doc.createElement("Mutators");
		for( ModifierClassInformation mci : Mutators ) {
			AddModifier(doc, muts, "Mutator", mci);
		}
		node.appendChild(muts);
		
		Element crosses = doc.createElement("Crossovers");
		for( ModifierClassInformation mci : Crossovers ) {
			AddModifier(doc, crosses, "Crossover", mci);
		}
		node.appendChild(crosses);
		
	}
	
	private static void AddModifier(Document doc, Node node, String name, ModifierClassInformation modifier){
		Element mut = doc.createElement(name);
		if( modifier.isBuiltIn ) {
			Attr builtin = doc.createAttribute("builtin");
			builtin.setValue(modifier.getFileName());
			mut.getAttributes().setNamedItem(builtin);
		} else {
			Attr location = doc.createAttribute("location");
			location.setValue(modifier.dir);
			mut.getAttributes().setNamedItem(location);
			Attr classfile = doc.createAttribute("classfile");
			classfile.setValue(modifier.getFileName());
			mut.getAttributes().setNamedItem(classfile);
		}
		Attr weight = doc.createAttribute("weight");
		weight.setValue("" + modifier.weight);
		mut.getAttributes().setNamedItem(weight);
		node.appendChild(mut);				
	}
	
	

	private void AddProcesses(Document doc, Node node){
		Element procs = doc.createElement("Processes");
	
		AddESPs(doc, procs, "StartProcesses", StartProcesses);
		AddESPs(doc, procs, "EndProcesses", EndProcesses);
		AddESPs(doc, procs, "BeforeRunProcesses", BeforeRunProcesses);
		AddESPs(doc, procs, "EndRunProcesses", EndOfRunProcesses );
		AddESPs(doc, procs, "BeforeGenerationProcesses", BeforeGenerationProcesses);
		AddESPs(doc, procs, "EndGenerationProcesses", EndOfGenerationProcesses);
		
		node.appendChild(procs);
	}

	private static void AddESPs(Document doc, Node node, String name, ArrayList<ClassInformation> cis){
		Element procs = doc.createElement(name);
		for( ClassInformation ci : cis ) {
			AddESP(doc, procs, "Process", ci);
		}
		node.appendChild(procs);
	}
	
	
	private static void AddESP(Document doc, Node node, String name, ClassInformation ci){
		
		Element esp = doc.createElement(name);
		if( ci.isBuiltIn ) {
			Attr builtin = doc.createAttribute("builtin");
			builtin.setValue(ci.getFileName());
			esp.getAttributes().setNamedItem(builtin);
		} else {
			Attr location = doc.createAttribute("location");
			location.setValue(ci.dir);
			esp.getAttributes().setNamedItem(location);
			Attr classfile = doc.createAttribute("classfile");
			classfile.setValue(ci.getFileName());
			esp.getAttributes().setNamedItem(classfile);
		}
		if( !ci.param.isEmpty() ) {
			Attr params = doc.createAttribute("parameters");
			params.setValue(ci.param);
			esp.getAttributes().setNamedItem(params);
		}
		node.appendChild(esp);
	}




}

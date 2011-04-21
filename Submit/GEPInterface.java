import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/***
 * 
 * Requirements:
 * 
 * For each parameter of the GEP algorithm (and each piece of the XML file)
 * -Must display the current value
 * -Must allow editing of the value
 * -Must allow loading and saving of config files
 * -Must allow adding of more values for parameters that allow it (functions, etc)
 * -Must allow launching of GEP Algorithm
 * 
 * @author mabbo
 *
 */


public class GEPInterface extends JFrame {
	private static final long serialVersionUID = 3544476936851462325L;
	public static String ConfigPath = "config.xml";
	
	private ConfigModel config = null;
	private ConfigPanel configPanel;
	private OutputPanel outputPanel;
	private LaunchPanel launchPanel;
	private GridBagLayout layout = null;
	private GridBagConstraints cons = null;
	
	private String lastSavedConfigFile = "";
	
	public GEPInterface(){
		super("GEP Interface");
		Initialize(ConfigPath);
	}
	
	private void Initialize(String guiconfigfile){
		
		//Does the config file exist? If not, make it.
		GuiConfigState gcs = null;
		File configFile = new File(guiconfigfile);
		if( configFile.exists() ) {
			gcs = LoadGuiConfig(guiconfigfile);	
		}
		else {
			gcs = new GuiConfigState();
			gcs.height = 800;
			gcs.width = 1100;
			gcs.locx = 10;
			gcs.locy = 10;
			gcs.gepConfigFile = "";
			SaveGuiConfigState(gcs);
		}
		
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		setLayout(layout);	
		LoadFile(gcs.gepConfigFile);
		this.setLocation(gcs.locx, gcs.locy);
		setSize(gcs.width, gcs.height);
		InitializeMenu();
		InitializeMainPanel();
		InitializeOutputPanel();
		InitializeLaunchPanel();
		addWindowListener(new WindowListener() {
			public void windowOpened(WindowEvent e) {}
			public void windowIconified(WindowEvent e) {}
			public void windowDeiconified(WindowEvent e) {}
			public void windowDeactivated(WindowEvent e) {}
			public void windowClosing(WindowEvent e) {
				Quit();
			}
			public void windowClosed(WindowEvent e) {}
			public void windowActivated(WindowEvent e) {}
		});
		
		
	}
	
	
	//---------visual stuff-----------------//
	private JScrollPane scrollPane;
	private JMenuBar menubar;
	private JMenu filemenu;
	private JMenuItem quit;
	private JMenuItem open;
	private JMenuItem save;
	private JMenuItem saveas;
	private JMenuItem newconf;
	
	public void InitializeMainPanel(){
		cons.gridx = 0;
		cons.gridy = 0;
		cons.gridheight = 1;
		cons.gridwidth = 1;
		cons.weightx = 0.6;
		cons.weighty = 0.9;
		cons.fill = GridBagConstraints.BOTH;
		configPanel = new ConfigPanel(this, config);
		scrollPane = new JScrollPane(configPanel,
	            ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
	            ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		layout.setConstraints(scrollPane, cons);
		add(scrollPane);
		
	}
	
	public void InitializeOutputPanel(){
		cons.gridx = 1;
		cons.gridy = 0;
		cons.gridheight = 1;
		cons.gridwidth = 1;
		cons.weightx = 0.4;
		cons.weighty = 0.9;
		cons.fill = GridBagConstraints.BOTH;
		outputPanel = new OutputPanel();
		layout.setConstraints(outputPanel, cons);
		add(outputPanel);	
	}
	
	private void InitializeLaunchPanel() {
		cons.gridheight = 1;
		cons.weightx = 1;
		cons.weighty = 0.1;
		cons.gridx = 0;
		cons.gridy = 1;
		cons.gridwidth = 2;
		cons.fill = GridBagConstraints.BOTH;
		launchPanel = new LaunchPanel();
		layout.setConstraints(launchPanel, cons);
		add(launchPanel);
		
		launchPanel.addLaunchActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Launch();
			}
		});
		
		launchPanel.addStopActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Stop(); //Hammer time
			}
		});
	}
	
	public void InitializeMenu(){
		menubar = new JMenuBar();
		filemenu = new JMenu("File");
		quit = new JMenuItem("Quit");
		open = new JMenuItem("Open");
		save = new JMenuItem("Save");
		saveas = new JMenuItem("Save As");
		newconf = new JMenuItem("New");
		filemenu.add(newconf);
		filemenu.add(open);
		filemenu.add(save);
		filemenu.add(saveas);
		filemenu.add(new JSeparator());
		filemenu.add(quit);
		menubar.add(filemenu);
		setJMenuBar(menubar);
		
		quit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Quit();
			}
		});
		
		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveConfigFile();
			}
		});
		
		open.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadConfigFile();
			}
		});
		
		saveas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SaveAsConfigFile();
			}
		});
		
		newconf.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if( SaveIfNeeded() ){
					config = new ConfigModel();
					configPanel.setConfig(config);
				}
			}
		});
		
		
	}
	
	public void LoadConfigFile(){
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new ConfigFilter());
		int retval = chooser.showDialog(this, "Open");
		
		//Load the config file
		if( retval == JFileChooser.APPROVE_OPTION ){
			LoadFile(chooser.getSelectedFile().getAbsolutePath());
		}
	}
	
	private void LoadFile(String filename){
		try{
			config = ConfigModel.OpenConfig(filename);
			if( configPanel != null) {
				configPanel.setConfig(config);
				lastSavedConfigFile = config.getConfigFileName();
				this.repaint();
			}
		}catch(Exception ex){
			JOptionPane.showMessageDialog(this, "Error loading file: " + ex.toString());
		}
	}
	
	public void SaveConfigFile() {
		if( config.getIsNew() )
		{
			SaveAsConfigFile();
		} else {
			config.SaveConfig();
		}
		lastSavedConfigFile = config.getConfigFileName();
	}
	
	public void SaveAsConfigFile() {
		JFileChooser chooser = new JFileChooser();
		int retVal = chooser.showSaveDialog(this);
		
		if( retVal == JFileChooser.APPROVE_OPTION ) {
			File file = chooser.getSelectedFile();
			String filename = file.getAbsolutePath();
			config.setConfigFileName(filename);
			config.SaveConfig();
			lastSavedConfigFile = config.getConfigFileName();
		} 
	}
	
	private boolean SaveIfNeeded(){
		if( config.needsToSave() ) {
			String[] optionText = {"Save", "Save As...", "Discard", "Cancel"};
			int choice = JOptionPane.showOptionDialog(this, "Save File?", "Unsaved", 
													JOptionPane.YES_NO_CANCEL_OPTION, 
													JOptionPane.PLAIN_MESSAGE, null, 
													optionText, "Cancel");
			switch (choice) {
			case 0:
				if( config.getIsNew()) {
					SaveAsConfigFile();
				} else {
					SaveConfigFile();
				}
				break;
			case 1:
				SaveAsConfigFile();
				break;
			case 2: 
				break;
			default: 
				return false;
			}
		}
		return true;
	}
	
	public void Quit(){
		boolean keepQuitting = SaveIfNeeded();
		if( !keepQuitting ) return;
		
		GuiConfigState gcs = new GuiConfigState();
		gcs.gepConfigFile = lastSavedConfigFile;
		gcs.locx = this.getX();
		gcs.locy = this.getY();
		gcs.width = this.getWidth();
		gcs.height = this.getHeight();
		
		if( !gcs.gepConfigFile.equals("")){
			SaveGuiConfigState(gcs);
		}
		
		System.exit(0);
	}
	
	private EvolverThread et = null; 
	
	public void Launch() {
		if( configPanel.getErrorsExist() ){
			JOptionPane.showMessageDialog(this, "Errors exist in the configuration.");
			return;
		}
		
		SaveConfigFile();

		launchPanel.setLaunched();
		et = new EvolverThread(config.getConfigFileName(), outputPanel.getWriter(), 
				new Evolver.EvolverFinishedActionListener() {
					public void EvolverFinished() {
						launchPanel.setStopped();
					}
				});
		Thread thread = new Thread(et);
		thread.start();
		
	}
	
	public void Stop() {
		if(et!=null)
			et.Kill();
		launchPanel.setStopped();
	}
	
	public class EvolverThread implements Runnable{
		
		Evolver ev = null;
		String conf = null;
		PrintWriter out = null;
		
		public EvolverThread(String confloc, PrintWriter out, Evolver.EvolverFinishedActionListener listener){
			ev = new Evolver();
			this.conf = confloc;
			this.out = out;
			ev.SetFinishedActionListener(listener);
		}
		
		public EvolverThread(String conf){
			ev = new Evolver();
			this.conf = conf;
		}
		
		public void run() {
			if( out != null ){
				ev.Evolve(conf, out);
			} else {
				ev.Evolve(conf);
			}
		}
		
		public void Kill(){
			ev.Kill();
			if( out!=null){
				out.println("Ended Evolver");
			}
		}
	}
	
	private class GuiConfigState {
		public String gepConfigFile;
		public int locx, locy;
		public int width, height;
		public GuiConfigState(){
			gepConfigFile = "";
			locx = locy = width = height = 0;
		}
	}
	
	private GuiConfigState LoadGuiConfig(String file){
		GuiConfigState gcs = new GuiConfigState();
		
		
		try {
			DocumentBuilderFactory domFactory;
			DocumentBuilder builder;
			Document doc;
			XPathFactory factory;
			XPath path;
	
			domFactory = DocumentBuilderFactory.newInstance();
			domFactory.setNamespaceAware(true);
			builder = domFactory.newDocumentBuilder();
			doc = builder.parse(file);
	
			factory = XPathFactory.newInstance();
			path = factory.newXPath();

			gcs.gepConfigFile = getStringValue("//LastUsedConfigFile", path, doc);
			gcs.height = getIntValue("//Height", path, doc);
			gcs.width = getIntValue("//Width", path, doc);
			gcs.locx = getIntValue("//Location/X", path, doc);
			gcs.locy = getIntValue("//Location/Y", path, doc);
			
		}catch(Exception ex){
			System.err.println(ex);
			System.exit(-1);
		}
		
		return gcs;
	}
	
	private void SaveGuiConfigState(GuiConfigState gcs){
		
		try{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			DOMImplementation impl = docBuilder.getDOMImplementation();
			Document doc = impl.createDocument(null, null, null);
			
			Element xmln = doc.createElement("xml");
			doc.appendChild(xmln);
			
			AddToNode(doc, xmln, "LastUsedConfigFile", gcs.gepConfigFile);
			Element el = doc.createElement("Location");
			AddToNode(doc, el, "X", gcs.locx+"");
			AddToNode(doc, el, "Y", gcs.locy+"");
			xmln.appendChild(el);
			AddToNode(doc, xmln, "Width", gcs.width+"");
			AddToNode(doc, xmln, "Height", gcs.height+"");
			
			
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
	
			File file = new File(ConfigPath);
			if( file.exists() ) file.delete();
			file.createNewFile();
			FileWriter fw = new FileWriter(file);
			fw.write(xml);
			fw.close();
			
		
		}catch(Exception ex){
			
		}
		
	}
	
	private static void AddToNode(Document doc, Node node, String name, String data){
		Element elem = doc.createElement(name);
		elem.appendChild(doc.createTextNode(data));
		node.appendChild(elem);
	}
	
	private static NodeList getNodes(String expression, XPath xpath, Document doc) throws XPathExpressionException{
		//For each part of the config, load it with an XPathExpression object
		XPathExpression expr = xpath.compile(expression);

		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		return nodes;
	}

	private static String getStringValue(String expression, XPath path, Document doc) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()", path, doc);
		for (int i = 0; i < nodes.getLength();) {
			return nodes.item(i).getNodeValue(); 
		}	
		return "";
	}
	
	private static int getIntValue(String expression, XPath path, Document doc) throws XPathExpressionException {
		NodeList nodes = getNodes(expression + "/text()", path, doc);
		for (int i = 0; i < nodes.getLength();) {
			return Integer.parseInt(nodes.item(i).getNodeValue()); 
		}	
		return 0;
	}

	public String getMainDirectory() {
		return "../";
	}

	
	
	
}










import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import framework.DataSetLoader;
import framework.EvolverStateProcess;

public class ConfigPanel extends JPanel {
	private static final long serialVersionUID = -8900449870756436728L;
	private JLabel lblTitle;
	private JTextField txtTitle;
	
	private JLabel lblDataSet;
	private JTextField txtDataSet;
	private JButton butLoadDataSet;
	
	private JLabel lblDataSetLoader;
	private JTextField txtDataSetLoader;
	private JButton butLoadDataSetLoader;
	
	private JLabel lblDataSetLoaderParameters;
	private JTextField txtDataSetLoaderParameters;
	
	private JLabel lblNumInputs;
	private JTextField txtNumInputs;
	
	private JLabel lblNumClasses;
	private JTextField txtNumClasses;
	
	private JLabel lblTrainPercentage;
	private JTextField txtTrainPercentage;
	
	private JLabel lblNumberOfRuns;
	private JTextField txtNumberOfRuns;
	
	private JLabel lblNumberOfGenerations;
	private JTextField txtNumberOfGenerations;
	
	private JLabel lblPopulationSize;
	private JTextField txtPopulationSize;
	
	//--------------------------------------//
	
	private JLabel lblNodeHeadSize;
	private JTextField txtNodeHeadSize;
	
	private JLabel lblFunctionSet;
	private ClassSetPanel fslblFunctionSet;
	
	private JLabel lblNumberRNC;
	private JTextField txtNumberRNC;	
	
	private JLabel lblNumLayers;
	private JTextField txtNumLayers;
	
	private JLabel lblLayers;
	private LayerControlPanel lcpanel;
	
	//--------------------------------------//
	
	private JLabel lblFitnessProcess;
	private JTextField txtFitnessProcess;
	private JTextField txtFitnessProcessParameters;
	private JButton butFitnessProcess;
	
	
	private JLabel lblSelectionMethod;
	private JTextField txtSelectionProcess;
	private JTextField txtSelectionProcessParamters;
	private JButton butSelectionProcess;
	
	private JLabel lblMutators;
	private ModifierSetPanel cspMutators;
	
	private JLabel lblCrossovers;
	private ModifierSetPanel cspCrossovers;
	
	private JLabel lblMutationRate;
	private JTextField txtMutationRate;

	//--------------------------------------//

	private JLabel lblStartProcess;
	private ClassSetPanel esplpStartProcess;
	
	private JLabel lblEndProcess;
	private ClassSetPanel esplpEndProcess;
	
	private JLabel lblBeforeRunProcess;
	private ClassSetPanel esplpBeforeRunProcess;
	
	private JLabel lblEndOfRunProcess;
	private ClassSetPanel esplpEndOfRunProcess;
	
	private JLabel lblBeforeGenerationProcess;
	private ClassSetPanel esplpBeforeGenerationProcess;
	
	private JLabel lblEndOfGenerationProcess;
	private ClassSetPanel esplpEndOfGenerationProcess;
	
	//--------------------------------------//
	
	private ArrayList<Component> erredList = new ArrayList<Component>();
	private void setIsErred(Component comp, boolean erred){
		if( erred ) {
			if( !erredList.contains(comp)) {
				erredList.add(comp);
				comp.setBackground(Color.RED);
			}
		} else {
			if( erredList.contains(comp)){
				erredList.remove(comp);
				comp.setBackground(Color.WHITE);
			}
		}
	}
	
	public boolean getErrorsExist(){
		return (erredList.size() > 0);
	}
	
	//--------------------------------------//
	
	
	private ConfigModel config = null;
	private GEPInterface owner = null;
	
	public ConfigPanel(GEPInterface owner, ConfigModel config){
		super();
		this.config = config;
		this.owner = owner;
		LoadVisualItems();
		SetupEventHandlers();
		UpdateView();
	}
	
	private final double LeftWeight = 0.4;
	private final double RightWeight = 0.6;
	private final int WeightY = 1;
	private final int TextFieldWidth = 15;
	private int currentY = 0;
	
	GridBagLayout layout = null;
	GridBagConstraints cons = null;
	
	public void setConfig(ConfigModel conf){
		config = conf;
		UpdateView();
	}
	
	public void LoadVisualItems(){
	
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		setLayout(layout);
		
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weighty = WeightY;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.insets = new Insets(10, 5, 10, 5);
		
		//-------
		ChangeMethod modelChanged = new ChangeMethod() {
			public void Change() {
				config.Changed();
			}
		};
		
		//-------
		
		lblTitle = new JLabel("Title");	
		txtTitle = new JTextField(TextFieldWidth);
		AddItem(lblTitle, txtTitle);
		
		lblDataSet = new JLabel("DataSet");
		txtDataSet = new JTextField(TextFieldWidth);
		butLoadDataSet = new JButton("Load DataSet");
		AddItem(lblDataSet, txtDataSet, butLoadDataSet);
		
		lblDataSetLoader = new JLabel("DataSet Loader");
		txtDataSetLoader = new JTextField(TextFieldWidth);
		butLoadDataSetLoader = new JButton("Load DataSetLoader");
		AddItem(lblDataSetLoader, txtDataSetLoader, butLoadDataSetLoader);
	
		lblDataSetLoaderParameters = new JLabel("Parameters");
		txtDataSetLoaderParameters = new JTextField(TextFieldWidth);
		AddItem(lblDataSetLoaderParameters, txtDataSetLoaderParameters);
		
		lblNumInputs = new JLabel("Number of Inputs");
		txtNumInputs = new JTextField(TextFieldWidth);
		AddItem(lblNumInputs, txtNumInputs);
		
		lblNumClasses = new JLabel("Number of Classes");
		txtNumClasses = new JTextField(TextFieldWidth);
		AddItem(lblNumClasses, txtNumClasses);
		
		lblTrainPercentage = new JLabel("Training Percentage");
		txtTrainPercentage = new JTextField(TextFieldWidth);
		AddItem(lblTrainPercentage, txtTrainPercentage);
		
		lblNumberOfRuns = new JLabel("Number of Runs");
		txtNumberOfRuns = new JTextField(TextFieldWidth);
		AddItem(lblNumberOfRuns, txtNumberOfRuns);
		
		lblNumberOfGenerations = new JLabel("Number of Generations");
		txtNumberOfGenerations = new JTextField(TextFieldWidth);
		AddItem(lblNumberOfGenerations, txtNumberOfGenerations);
		
		lblPopulationSize = new JLabel("Population Size");
		txtPopulationSize = new JTextField(TextFieldWidth);
		AddItem(lblPopulationSize, txtPopulationSize);
		
		lblFunctionSet = new JLabel("Functions");
		fslblFunctionSet = new ClassSetPanel(owner, "Function", framework.Function.class);
		fslblFunctionSet.Initialize(config.getFunctions());
		fslblFunctionSet.setOnUpdateMethod(modelChanged);
		AddItem(lblFunctionSet, fslblFunctionSet);

		lblNodeHeadSize = new JLabel("Head Size");
		txtNodeHeadSize = new JTextField(TextFieldWidth);
		AddItem(lblNodeHeadSize, txtNodeHeadSize);
		
		lblNumberRNC = new JLabel("Number of RNCs");
		txtNumberRNC = new JTextField(TextFieldWidth);
		AddItem(lblNumberRNC, txtNumberRNC);
		
		lblNumLayers = new JLabel("Number of Layers");
		txtNumLayers = new JTextField(TextFieldWidth);
		AddItem(lblNumLayers, txtNumLayers);
		
		lblLayers = new JLabel("Layers");
		lcpanel = new LayerControlPanel(config, erredList);
		AddItem(lblLayers, lcpanel);
		
		lblFitnessProcess = new JLabel("Fitness Process");
		txtFitnessProcess = new JTextField(TextFieldWidth);
		txtFitnessProcessParameters = new JTextField(TextFieldWidth);
		butFitnessProcess = new JButton("Load Fitness Process");
		AddItem(lblFitnessProcess, txtFitnessProcess, txtFitnessProcessParameters, butFitnessProcess);
		
		lblSelectionMethod = new JLabel("Selection Process");
		txtSelectionProcess = new JTextField(TextFieldWidth);
		txtSelectionProcessParamters = new JTextField(TextFieldWidth);
		butSelectionProcess = new JButton("Load Selection Process");
		AddItem(lblSelectionMethod, txtSelectionProcess, txtSelectionProcessParamters, butSelectionProcess);
	
		lblMutators = new JLabel("Mutators");
		cspMutators = new ModifierSetPanel(owner, "Mutator", framework.Mutator.class);
		cspMutators.Initialize(config.getMutators());
		AddItem(lblMutators, cspMutators);
		
		lblMutationRate = new JLabel("Mutation Rate");
		txtMutationRate = new JTextField(TextFieldWidth);
		AddItem(lblMutationRate, txtMutationRate);
		
		lblCrossovers = new JLabel("Crossovers");
		cspCrossovers = new ModifierSetPanel(owner, "Crossover", framework.Crossover.class);
		cspCrossovers.Initialize(config.getCrossovers());
		AddItem(lblCrossovers, cspCrossovers);
	
		lblStartProcess = new JLabel("Start Processes");
		esplpStartProcess = new ClassSetPanel(owner, "Start Process", EvolverStateProcess.class);
		esplpStartProcess.Initialize(config.getStartProcesses());
		esplpStartProcess.setOnUpdateMethod(modelChanged);
		AddItem(lblStartProcess, esplpStartProcess);
	
		lblEndProcess = new JLabel("End Processes");
		esplpEndProcess = new ClassSetPanel(owner, "End Process", EvolverStateProcess.class);
		esplpEndProcess.Initialize(config.getEndProcesses());
		esplpEndProcess.setOnUpdateMethod(modelChanged);
		AddItem(lblEndProcess, esplpEndProcess);
		
		lblBeforeRunProcess = new JLabel("Start of Run Processes");
		esplpBeforeRunProcess = new ClassSetPanel(owner, "Before Run Process", EvolverStateProcess.class);
		esplpBeforeRunProcess.Initialize(config.getBeforeRunProcesses());
		esplpBeforeRunProcess.setOnUpdateMethod(modelChanged);
		AddItem(lblBeforeRunProcess, esplpBeforeRunProcess);
		
		lblEndOfRunProcess = new JLabel("End of Run Processes");
		esplpEndOfRunProcess = new ClassSetPanel(owner, "End of Run Process", EvolverStateProcess.class);
		esplpEndOfRunProcess.Initialize(config.getEndOfRunProcesses());
		esplpEndOfRunProcess.setOnUpdateMethod(modelChanged);
		AddItem(lblEndOfRunProcess, esplpEndOfRunProcess);
		
		lblBeforeGenerationProcess = new JLabel("Before Generation Processes");
		esplpBeforeGenerationProcess = new ClassSetPanel(owner, "Before Generation Process", EvolverStateProcess.class);
		esplpBeforeGenerationProcess.Initialize(config.getBeforeGenerationProcesses());
		esplpBeforeGenerationProcess.setOnUpdateMethod(modelChanged);
		AddItem(lblBeforeGenerationProcess, esplpBeforeGenerationProcess);
		
		lblEndOfGenerationProcess = new JLabel("End of Generation Processes");
		esplpEndOfGenerationProcess = new ClassSetPanel(owner, "End of Generation Process", EvolverStateProcess.class);
		esplpEndOfGenerationProcess.Initialize(config.getEndOfGenerationProcesses());
		esplpEndOfGenerationProcess.setOnUpdateMethod(modelChanged);
		AddItem(lblEndOfGenerationProcess, esplpEndOfGenerationProcess);		
	}
	
	public void AddItem(JLabel label, JTextField textField){
		cons.gridx = 0;
		cons.gridy = currentY;
		cons.weightx = LeftWeight;
		layout.setConstraints(label, cons);
		add(label);
		
		cons.gridx = 1;
		textField.setColumns(TextFieldWidth);
		cons.weightx = RightWeight;
		layout.setConstraints(textField, cons);
		add(textField);
		currentY++;
	}
	
	public void AddItem(JLabel label, Component comp){
		cons.gridx = 0;
		cons.gridy = currentY;
		cons.weightx = LeftWeight;
		layout.setConstraints(label, cons);
		add(label);
		
		cons.gridx = 1;
		cons.weightx = RightWeight;
		layout.setConstraints(comp, cons);
		add(comp);
		currentY++;
	}
	
	public void AddItem(JLabel label, JTextField textField, JButton button){
		cons.gridx = 0;
		cons.gridy = currentY;
		cons.weightx = LeftWeight;
		layout.setConstraints(label, cons);
		add(label);
		
		cons.gridx = 1;
		textField.setColumns(TextFieldWidth);
		textField.setEditable(false);
		cons.weightx = RightWeight;
		cons.insets = new Insets(10, 5, 1, 5);
		layout.setConstraints(textField, cons);
		add(textField);
		currentY++;
		
		cons.insets = new Insets(1, 5, 10, 5);
		cons.gridx = 1;
		cons.gridy = currentY;
		cons.weightx = RightWeight;
		layout.setConstraints(button, cons);
		add(button);
		cons.insets = new Insets(10, 5, 10, 5);
		currentY++;		
	}
	
	public void AddItem(JLabel label, JTextField textField, JTextField paramField, JButton button){
		if( label == null || textField == null || paramField == null || cons == null || layout == null){
			System.err.println("An Error has occurred");
		}
		cons.gridx = 0;
		cons.gridy = currentY;
		cons.weightx = LeftWeight;
		layout.setConstraints(label, cons);
		add(label);
		
		cons.gridx = 1;
		textField.setColumns(TextFieldWidth);
		textField.setEditable(false);
		cons.weightx = RightWeight;
		cons.insets = new Insets(10, 5, 1, 5);
		layout.setConstraints(textField, cons);
		add(textField);
		currentY++;
		
		cons.gridx = 1;
		paramField.setColumns(TextFieldWidth);
		paramField.setEditable(true);
		cons.weightx = RightWeight;
		cons.gridy = currentY;
		cons.insets = new Insets(1, 5, 1, 5);
		layout.setConstraints(paramField, cons);
		add(paramField);
		currentY++;
		
		cons.insets = new Insets(1, 5, 10, 5);
		cons.gridx = 1;
		cons.gridy = currentY;
		cons.weightx = RightWeight;
		layout.setConstraints(button, cons);
		add(button);
		cons.insets = new Insets(10, 5, 10, 5);
		currentY++;		
	}
	
	private void AddChangeListener(JTextField text, ChangeMethod cm){
		text.getDocument().addDocumentListener(makeChangeListener(cm));
	}
	
	private DocumentListener makeChangeListener(final ChangeMethod cm){
		return new DocumentListener() {
			public void removeUpdate(DocumentEvent arg0) {Change();}
			public void insertUpdate(DocumentEvent arg0) {Change();}
			public void changedUpdate(DocumentEvent arg0) {Change();}
			private void Change(){
				cm.Change();
			}
		};
	}
	
	private void SetupEventHandlers(){
		AddChangeListener(txtTitle, new ChangeMethod() {
			public void Change() {
				config.setTitle(txtTitle.getText());
			}
		});
		
		butLoadDataSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoadDataSet();
			}
		});
		
		butLoadDataSetLoader.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				LoadDataSetLoader();
			}
		});
		
		
		AddChangeListener(txtDataSetLoaderParameters, new ChangeMethod() {
			public void Change() {
				config.setDataSetLoaderParameters(txtDataSetLoaderParameters.getText());		
			}
		});
		
		AddChangeListener(txtNumInputs, new ChangeMethod() {
			public void Change() {
				int value = 0;
				try{
					value = Integer.parseInt( txtNumInputs.getText() );
				} catch(Exception ex){}
				setIsErred(txtNumInputs, value < 1);
				if( value > 0) {
					config.setNumberOfInputs(value);
				}
			}
		});

		AddChangeListener(txtNumClasses, new ChangeMethod() {
			public void Change() {
				int value = 0;
				try{
					value = Integer.parseInt( txtNumClasses.getText() );
				} catch(Exception ex){}
				setIsErred(txtNumClasses, value < 1);
				if( value > 0) {
					config.setNumberOfClasses(value);
				}
			}
		});	

		AddChangeListener(txtTrainPercentage, new ChangeMethod() {
			public void Change() {
				double value = 0;
				try{
					value = Double.parseDouble( txtTrainPercentage.getText() );
				} catch(Exception ex){}
				setIsErred(txtTrainPercentage, value <= 0);
				if( value > 0) {
					config.setTrainingPercentage(value);
				}
			}
		});
		AddChangeListener(txtNumberOfRuns, new ChangeMethod() {
			public void Change() {
				int value = 0;
				try{
					value = Integer.parseInt( txtNumberOfRuns.getText() );
				} catch(Exception ex){}
				setIsErred(txtNumberOfRuns, value < 1);
				if( value > 0) {
					config.setNumberOfRuns(value);
				}
			}
		});
		AddChangeListener(txtNumberOfGenerations, new ChangeMethod() {
			public void Change() {
				int value = 0;
				try{
					value = Integer.parseInt( txtNumberOfGenerations.getText() );
				} catch(Exception ex){}
				setIsErred(txtNumberOfGenerations, value < 1);
				if( value > 0) {
					config.setNumberOfGenerations(value);
				}
			}
		});
		AddChangeListener(txtPopulationSize, new ChangeMethod() {
			public void Change() {
				int value = 0;
				try{
					String strVal = txtPopulationSize.getText();
					value = Integer.parseInt( txtPopulationSize.getText() );
				} catch(Exception ex){}
				setIsErred(txtPopulationSize, value < 1);
				if( value > 0) {
					config.setPopulationSize(value);
				}
			}
		});
		AddChangeListener(txtNodeHeadSize, new ChangeMethod() {
			public void Change() {
				int value = 0;
				try{
					value = Integer.parseInt( txtNodeHeadSize.getText() );
				} catch(Exception ex){}
				setIsErred(txtNodeHeadSize, value < 1);
				if( value > 0) {
					config.setNodeHeadSize(value);
				}
			}
		});
		
		AddChangeListener(txtNumberRNC, new ChangeMethod(){
			public void Change() {
				int value = -1;
				try{
					String strVal = txtNumberRNC.getText();
					value = Integer.parseInt( txtNumberRNC.getText() );
				} catch(Exception ex){}
				setIsErred(txtNumberRNC, value < 0);
				if( value >= 0) {
					config.setNumberRNC(value);
				}
			}
		});
		
		AddChangeListener(txtNumLayers, new ChangeMethod() {
			public void Change() {
				int value = -1;
				try{
					value = Integer.parseInt( txtNumLayers.getText() );
				} catch(Exception ex){}
				setIsErred(txtNumLayers, value < 0);
				if( value >= 0) {
					config.setNumberLayers(value);
					lcpanel.SetNumberOfLayers(value);
				}
			}
		}); 

		AddChangeListener(txtFitnessProcessParameters, new ChangeMethod(){
			public void Change() {
				config.setFitnessProcessParameters(txtFitnessProcessParameters.getText());
			}
		});
		
		butFitnessProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadFitnessProcess();
			}
		});
		
		
		AddChangeListener( txtSelectionProcessParamters, new ChangeMethod(){
			public void Change() {
				config.setSelectionMethodParameters(txtSelectionProcessParamters.getText());
			}
		});
		
		butSelectionProcess.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LoadSelectionProcess();
			}
		});
				
		AddChangeListener(txtMutationRate, new ChangeMethod() {
			public void Change() {
				double value = -1;
				try{
					value = Double.parseDouble( txtMutationRate.getText() );
				} catch(Exception ex){}
				setIsErred(txtMutationRate, value < 0);
				if( value >= 0) {
					config.setMutationRate(value);
				}
			}
		});

		//esplpStartProcess;
		
		//esplpEndProcess;
		
		//esplpBeforeRunProcess;
		
		//esplpEndOfRunProcess;
		
		//esplpBeforeGenerationProcess;
		
		//esplpEndOfGenerationProcess;
		
	}
	
	private void LoadDataSetLoader(){
		ClassInformation ci = new ClassInformation();
		ClassSelector.getClassOfType(owner, DataSetLoader.class, ci);
		if( ci.file.equals("")) return;
		config.setDataSetLoader(ci);
		UpdateView();
	}
	
	private void LoadDataSet(){
		JFileChooser chooser = new JFileChooser();
		chooser.showOpenDialog(this);
		
		if( chooser.getSelectedFile() != null){
			File f = chooser.getSelectedFile();
			config.setDataSetFile(f.getPath());
			UpdateView();
		}
	}
	
	private void LoadFitnessProcess() {
		ClassInformation ci = new ClassInformation();
		ClassSelector.getClassOfType(owner, framework.FitnessProcess.class, ci);
		if( ci.file.equals("")) return;
		config.setFitnessProcess(ci);
		UpdateView();
	}

	private void LoadSelectionProcess() {
		ClassInformation ci = new ClassInformation();
		ClassSelector.getClassOfType(owner, framework.SelectionMethod.class, ci);
		if( ci.file.equals("")) return;
		config.setSelectionMethod(ci);
		UpdateView();
	}
	
	public void UpdateView(){
		txtTitle.setText(config.getTitle());
		txtDataSet.setText(config.getDataSetFile());
		txtDataSetLoader.setText(config.getDataSetLoader().file);
		txtDataSetLoaderParameters.setText(config.getDataSetLoader().param);
		
		txtNumInputs.setText(config.getNumberOfInputs()+"");
		txtNumClasses.setText(config.getNumberOfClasses() + "");
		txtTrainPercentage.setText(config.getTrainingPercentage()+"");
		txtNumberOfRuns.setText(config.getNumberOfRuns()+"");
		txtNumberOfGenerations.setText(config.getNumberOfGenerations()+"");
		String poptext = config.getPopulationSize() + "";
		txtPopulationSize.setText(config.getPopulationSize()+"");
		
		txtNodeHeadSize.setText(config.getNodeHeadSize()+"");
		
		fslblFunctionSet.Redraw();
		String rnctext = "" + config.getNumberOfRuns();
		txtNumberRNC.setText(""+ config.getNumberRNC());
		txtNumLayers.setText(""+ config.getNumberLayers());
		lcpanel.setConfig(config);
		ArrayList<Integer> alist = new ArrayList<Integer>();
		for(int i = 0; i < config.getNumberLayers();++i){
			alist.add( config.getNodesInLayer()[i] );
		}
		lcpanel.SetLayers(alist);
		
		txtFitnessProcess.setText(config.getFitnessProcess().file);
		
		txtSelectionProcess.setText(config.getSelectionMethod().file);
		
		//load the mutators and crossovers
		cspCrossovers.Redraw();
		cspMutators.Redraw();
		
		txtMutationRate.setText("" + config.getMutationRate());
		
		fslblFunctionSet.Initialize(config.getFunctions());
		cspMutators.Initialize(config.getMutators());
		cspCrossovers.Initialize(config.getCrossovers());
		
		
		
		
		
		
		
		this.revalidate();
		
	}
	
	
	
}

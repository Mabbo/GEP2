import java.awt.Color;
import java.awt.Component;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Type;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ModifierSetPanel extends JPanel {

	private static final long serialVersionUID = -6140559806394687680L;

	private ArrayList<ModifierClassInformation> classes = null;
	private String className = "";
	private GridBagLayout layout;
	private GridBagConstraints cons;
	private GEPInterface owner;
	private Type mytype;
	private ArrayList<Component> erredList = null;
	
	public ModifierSetPanel(GEPInterface owner, String classname, Type t, ArrayList<Component> erred){
		init(owner, classname, t, erred);
	}

	private void init(GEPInterface owner, String classname, Type t, ArrayList<Component> erred){
		this.className = classname;
		this.owner = owner;
		this.mytype = t;
		this.erredList = erred;
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		
		this.setLayout(layout);
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(1, 1, 1, 1);
		
		classes = new ArrayList<ModifierClassInformation>();
		Redraw();		
	}
	
	public void Initialize(ArrayList<ModifierClassInformation> alci) {
		classes = alci;
		Redraw();
	}
	
	public void Redraw() {
		//clear the panel.
		this.removeAll();
		int ItemGridY = 0;	
		//For each item in the arraylist,
		for( ModifierClassInformation ci : classes  ) {
			//make a new textfield, and removable button
			cons.gridy = ItemGridY;
			cons.gridx = 0;
			JTextField text = new JTextField();
			text.setColumns(15);
			text.setText(ci.file);
			text.setEditable(false);
			layout.setConstraints(text, cons);
			add(text);

			JTextField weight = new JTextField();
			weight.setColumns(3);
			weight.setText("" + ci.weight);
			weight.setEditable(true);
			
			final ModifierClassInformation fmci = ci;
			//Change listener here!
			final JTextField fweight = weight;
			AddChangeListener(weight, new ChangeMethod() {
				public void Change() {
					int value = -1;
					try{
						value = Integer.parseInt( fweight.getText() );
					} catch(Exception ex){}
					setIsErred(fweight, value < 0);
					if( value >= 0) {
						fmci.weight = value;
					}	
				}
			});
			
			
			
			cons.gridx = 1;
			layout.setConstraints(weight, cons);
			add(weight);
			
			JButton button = new JButton("X");
			cons.gridx = 2;
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					RemoveClass(fmci);
				}
			});
			layout.setConstraints(button, cons);
			add(button);
			ItemGridY++;
		}
		
		cons.gridy = ItemGridY;
		cons.gridx = 0;
		JButton addButton = new JButton("Add " + className);
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AddNewItem();
			}
		});
		layout.setConstraints(addButton, cons);
		add(addButton);
		this.revalidate();
	}
	
	protected void AddNewItem() {
		ClassInformation ci = new ClassInformation();
		ClassSelector.getClassOfType(owner, mytype, ci);
		if( !ci.file.isEmpty() ){
			if( classes.contains(convertCI(ci))) return;
			AddClass(ci);
			Redraw();
		}
	}

	protected void RemoveClass(ModifierClassInformation fmci) {
		classes.remove(fmci);
		Redraw();
	}

	public void AddClass(ModifierClassInformation mci){
		classes.add(mci);
		Redraw();
	}

	public void AddClass(ClassInformation ci){
		classes.add(convertCI(ci));
		Redraw();
	}
	
	private static ModifierClassInformation convertCI(ClassInformation ci){
		ModifierClassInformation mci = new ModifierClassInformation();
		mci.dir = ci.dir;
		mci.file = ci.file;
		mci.param = ci.param;
		mci.isBuiltIn = ci.isBuiltIn;
		mci.weight = 1;
		return mci;
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
	
}


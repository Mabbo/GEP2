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

public class ClassSetPanel extends JPanel {

	private static final long serialVersionUID = -6140559806394687680L;

	private ArrayList<ClassInformation> classes = null;
	private String className = "";
	private GridBagLayout layout;
	private GridBagConstraints cons;
	private Type mytype;
	private GEPInterface owner;
	private ChangeMethod onUpdate = null;
	
	public ClassSetPanel(GEPInterface owner, String classname, Type t){
		init(owner, classname, t);
	}
	
	private void init(GEPInterface owner, String classname, Type t){
		this.className = classname;
		this.mytype = t;
		this.owner = owner;
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		
		this.setLayout(layout);
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(1, 1, 1, 1);
		
		classes = new ArrayList<ClassInformation>();
		Redraw();		
	}
	
	public void setOnUpdateMethod(ChangeMethod cm){
		onUpdate = cm;
	}
	
	public void Initialize(ArrayList<ClassInformation> alci) {
		classes = alci;
		Redraw();
	}
	
	public void Redraw() {
		//clear the panel.
		this.removeAll();
		int ItemGridY = 0;	
		//For each item in the arraylist,
		for( ClassInformation ci : classes ) {
			//make a new textfield, and removable button
			cons.gridy = ItemGridY;
			cons.gridx = 0;
			JTextField text = new JTextField();
			text.setColumns(20);
			text.setText(ci.file);
			text.setEditable(false);
			layout.setConstraints(text, cons);
			add(text);
			
			final ClassInformation fci = ci;
			JButton button = new JButton("X");
			button.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					RemoveClass(fci);
				}
			});
			
			cons.gridx = 1;
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
	
	private void AddNewItem(){
		ClassInformation ci = new ClassInformation();
		ClassSelector.getClassOfType(owner, mytype, ci);
		if( !ci.file.isEmpty() ){
			if( classes.contains(ci) ) return;
			AddClass(ci);
			Redraw();
		}
	}

	public void RemoveClass(ClassInformation ci){
		classes.remove(ci);
		onUpdate.Change();
		Redraw();
	}
	
	public void AddClass(ClassInformation ci){
		classes.add(ci);
		onUpdate.Change();
		Redraw();
	}

	public ArrayList<ClassInformation> getClasses(){
		return classes;
	}
	
}

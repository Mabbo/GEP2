import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


public class ESPLoaderPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private ArrayList<ClassInformation> classes = null;
	GridBagLayout layout;
	GridBagConstraints cons;

	private final double TITLE_WEIGHT = 0.0;
	private final double TEXT_WEIGHT = 0.80;
	private final double DELETE_WEIGHT = 0.1;
		
	public ESPLoaderPanel(){ 
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		
		this.setLayout(layout);
		
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weighty = 100;
		cons.anchor = GridBagConstraints.NORTHWEST;
		cons.fill = GridBagConstraints.HORIZONTAL;
		cons.insets = new Insets(1, 1, 1, 1);
		
		classes = new ArrayList<ClassInformation>();
		Redraw();
	}
	
	public void Redraw() {
		//clear the panel.
		this.removeAll();
		int ItemGridY = 0;	
		//For each item in the arraylist,
		for( int i = 0; i < classes.size(); ++i ) {
			
			cons.gridy = ItemGridY;
			cons.gridx = 0;
			cons.weightx = TITLE_WEIGHT;
			cons.fill = GridBagConstraints.NONE;
			JLabel lblProc = new JLabel("Process");
			layout.setConstraints(lblProc, cons);
			add(lblProc);
			
			cons.gridx = 1;
			cons.fill = GridBagConstraints.HORIZONTAL;
			cons.weightx = TEXT_WEIGHT;
			JTextField text = new JTextField();
			text.setText( classes.get(i).file );
			text.setEditable(false);
			layout.setConstraints(text, cons);
			add(text);
			
			JButton button = new JButton("X");
			cons.gridx = 2;
			cons.weightx = DELETE_WEIGHT;
			cons.gridheight = 2;
			cons.fill = GridBagConstraints.BOTH;
			layout.setConstraints(button, cons);
			add(button);
			cons.gridheight = 1;
			cons.fill = GridBagConstraints.HORIZONTAL;	
			
			ItemGridY++;

			cons.gridy = ItemGridY;
			cons.gridx = 0;
			cons.weightx = TITLE_WEIGHT;
			cons.fill = GridBagConstraints.NONE;
			JLabel lblparam = new JLabel("Parameters");
			layout.setConstraints(lblparam, cons);
			add(lblparam);
			
			cons.gridx = 1;
			cons.weightx = TEXT_WEIGHT;
			cons.fill = GridBagConstraints.HORIZONTAL;
			JTextField paramsField = new JTextField();
			paramsField.setText(classes.get(i).param);
			layout.setConstraints(paramsField,cons);
			add(paramsField);
			ItemGridY++;	
		}
		
		cons.gridy = ItemGridY;
		cons.gridx = 0;
		cons.weightx = 0;
		
		JButton addButton = new JButton("Add Process");
		layout.setConstraints(addButton, cons);
		add(addButton);
	}
	
	public void AddProcess(ClassInformation ci){
		classes.add(ci);
	}

	public ArrayList<ClassInformation> getESPs(){
		return classes;
	}
	
}

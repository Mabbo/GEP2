import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


/***
 * allows editing of each layer
 * 
 * |--------------
 * |layer1:  ____
 * |layer2:  ____
 * |etc
 * |--------------
 * 
 * 
 * @author mabbo
 *
 */


public class LayerControlPanel extends JPanel {

	private ArrayList<Integer> layers;
	private GridBagLayout layout;
	private GridBagConstraints cons;
	private ArrayList<Component> erredList = null;
	private ConfigModel config = null;
	
	public LayerControlPanel(ConfigModel config, ArrayList<Component> erredList){
		layers = new ArrayList<Integer>();
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		this.erredList = erredList;
		
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weighty = 100;
		cons.anchor = GridBagConstraints.WEST;
		cons.insets = new Insets(1, 3, 1, 3);
		this.setLayout(layout);
		Redraw();
	}
	
	public void SetLayers(ArrayList<Integer> layers){
		this.layers = layers;
		UpdateModel();
		Redraw();
	}
	
	public void SetNumberOfLayers(int layercount){
		while( layercount > layers.size() ){
			layers.add(1);
		}
		while( layercount < layers.size() ){
			layers.remove(layers.size()-1);
		}
		UpdateModel();
		Redraw();
	}
	
	private void UpdateModel(){
		if( config == null) return;
		config.setNumberLayers(layers.size());
		int[] layerAr = new int[layers.size()];
		Integer[] layerOAr = layers.toArray(new Integer[0]);
		for( int i = 0; i < layers.size();++i ){layerAr[i] = layerOAr[i];}
		config.setNodesInLayer(layerAr);
	}
	
	private void ClearPanel(){
		for( Component comp : this.getComponents() ){
			setIsErred(comp, false);
		}
		removeAll();		
	}
	
	public void Redraw(){
		ClearPanel();
		for( int i = 0; i < layers.size(); ++i) {
			JLabel lblLayer = new JLabel("" + (i+1));
			JTextField txtLayer = new JTextField(4);
			txtLayer.setText(""+ layers.get(i) );
			cons.gridy = i;
			cons.gridx = 0;
			cons.weightx = 100;
			layout.setConstraints(lblLayer, cons);
			add(lblLayer);
			cons.gridx = 1;
			layout.setConstraints(txtLayer, cons);
			final JTextField text = txtLayer;
			final int x = i;
			AddChangeListener(txtLayer, new ChangeMethod() {
				public void Change() {
					int value = 0;
					try{
						value = Integer.parseInt( text.getText() );
					} catch(Exception ex){}
					setIsErred(text, value < 1);
					if( value > 0) {
						layers.set(x,value);
					}
					UpdateModel();
				}
			});			
			add(txtLayer);
		}
		this.revalidate();
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

	public void setConfig(ConfigModel config2) {
		config = config2;
		Redraw();
	}
	
}

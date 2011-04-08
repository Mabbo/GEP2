import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Responsibilities:
 * 
 * Give a "Launch" and "Stop" buttons, which 
 * will launch and stop the evolver as needed. 
 * 
 * Needs to handle threading issues. 
 * 
 * @author mabbo
 *
 */

public class LaunchPanel extends JPanel {

	private static final long serialVersionUID = 4404832900532796844L;
	private JButton butLaunch = null;
	private JButton butStop = null;
	GridBagLayout layout = null;
	GridBagConstraints cons = null;
	
	public LaunchPanel(){
		layout = new GridBagLayout();
		cons = new GridBagConstraints();
		
		butLaunch = new JButton("Launch");
		butStop = new JButton("Stop");
		cons.gridwidth = 1;
		cons.gridheight = 1;
		cons.weightx = 0.5;
		cons.weighty = 1;
		cons.gridx = 0;
		cons.gridy = 0;
		layout.setConstraints(butLaunch, cons);
		add(butLaunch);
		cons.gridx = 1;
		layout.setConstraints(butStop, cons);
		add(butStop);
		
		setStopped();
		
	}
	
	public void setStopped(){
		butStop.setEnabled(false);
		butLaunch.setEnabled(true);
	}
	public void setLaunched(){
		butStop.setEnabled(true);
		butLaunch.setEnabled(false);
	}
	
	public void addLaunchActionListener(ActionListener al){
		butLaunch.addActionListener(al);
	}
	public void addStopActionListener(ActionListener al){
		butStop.addActionListener(al);
	}
	
	
}

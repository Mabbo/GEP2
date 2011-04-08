import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;



/**
 * OutputPanel
 * 
 * Responsibilities: Have an output stream that text can be sent to, 
 * display that text in a textarea window, like System.out has. The
 * GEP Evolver will send all messages there instead.
 * 
 * @author mabbo
 *
 */

public class OutputPanel extends JPanel {
	private static final long serialVersionUID = 7402393177851861385L;
	private JTextArea outText = null;
	private JScrollPane scroll = null;
	PrintWriter pw = null;
	
	public OutputPanel(){
		
		outText = new JTextArea();
		outText.setAutoscrolls(true);
		outText.setLineWrap(true);
		outText.setEditable(false);
		scroll = new JScrollPane(outText, 
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, 
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		add(scroll);
		
		this.addComponentListener(new ComponentListener() {
			public void componentShown(ComponentEvent e) {}
			public void componentMoved(ComponentEvent e) {}
			public void componentHidden(ComponentEvent e) {}
			public void componentResized(ComponentEvent e) {
				ResetSize();
			}
		});
		
		ResetSize();
		pw = new PrintWriter(new TextAreaWriter(outText));
		
	}
	
	public void ResetSize(){
		scroll.revalidate();
		scroll.setSize( getWidth(), getHeight()  );
		scroll.setLocation(0,0);
	}
	
	public PrintWriter getWriter(){
		return pw;
	}
	
	
	public final class TextAreaWriter extends Writer {

		private final JTextArea textArea;

		public TextAreaWriter(final JTextArea textArea) {
			this.textArea = textArea;
		}

	    @Override
	    public void flush(){ }
	    
	    @Override
	    public void close(){ }

		@Override
		public void write(char[] cbuf, int off, int len) throws IOException {
			textArea.append(new String(cbuf, off, len));
			textArea.setCaretPosition(textArea.getText().length());
		}
	}
	
	
}

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileFilter;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;



/**
 * ClassSelector:
 * 
 * Given an interface type, it provides the user with a list 
 * of built-in classes to pick from, and offers the option 
 * of loading an external class (which it then verifies the
 * type of, to ensure correct class returns).
 * 
 * @author mabbo
 *
 */



public class ClassSelector {

	
	public static void getClassOfType(GEPInterface owner, Type t, ClassInformation ci){
		ClassLoaderDialog cld = new ClassLoaderDialog(null, ci, owner.getMainDirectory()+ "Framework/bin/builtin", t);
		cld.setVisible(true);
	}
	
	public static class ClassLoaderDialog extends JDialog {
		private static final long serialVersionUID = 4102419443372172465L;
		ClassInformation cinfo;
		String directory;
		Type mytype;
		ArrayList<FileClass> classes;
		JList list = new JList();
		
		public ClassLoaderDialog(Frame owner, ClassInformation ci, String filesDir, Type t){
			super(owner, true);
			cinfo = ci;
			directory = filesDir;
			mytype = t;
			
			LoadClasses();
			LoadVisualComponents();
			
		}
		
		private FileFilter filter = new FileFilter() {
			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".class");
			}
		};
		
		private void LoadClasses() {
			classes = new ArrayList<FileClass>();
			
			//Load directory
			File dir = new File(directory);
			File[] files = dir.listFiles( filter );
			//For each file of type .class,
			for( File f : files ){
				//Load file
				try {
					URL url = f.toURI().toURL();
				    URL[] urls = new URL[]{url};
				    ClassLoader cl = new URLClassLoader(urls);
				    Class<?> cls = cl.loadClass("builtin." + f.getName().replace(".class", ""));
				    ArrayList<Class<?>> interfaces = getAllInterfaces(cls);
				    for( Class<?> c : interfaces ){ 
				    	if( c.equals(mytype) && !Modifier.isAbstract(cls.getModifiers()) ) {
				    		classes.add(new FileClass(f, cls));
				    		break;
				    	}
				    }
				}catch(Exception ex){
					System.err.println("Error loading class file: " + f.getAbsolutePath());
				}
			}
			
		}
		
		private void LoadVisualComponents(){
			this.setLayout(new BorderLayout(2,2));
				
			this.setSize(400,400);
			this.setResizable(false);
			
			list = new JList();
			list.setListData(classes.toArray());
			JScrollPane scroll = new JScrollPane(list, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);			
			add(BorderLayout.CENTER, scroll);
			
			JPanel buttonPanel = new JPanel();
			
			JButton select = new JButton("Load Selected");
			select.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					LoadPressed();
				}
			});
			buttonPanel.add(select);
			
			JButton external = new JButton("Load External");
			external.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					LoadExternalPressed();
				}
			});
			buttonPanel.add(external);
			
			JButton cancel = new JButton("Cancel");
			cancel.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					CancelButtonPressed();
				}
			});	
			buttonPanel.add(cancel);
			add(BorderLayout.SOUTH, buttonPanel);
		}
		
		private void CancelButtonPressed(){
			if( cinfo != null){
				cinfo.file = "";
				cinfo.dir = "";
				cinfo.param = "";
				cinfo.isBuiltIn = false;
			}
			this.setVisible(false);				
		}
		
		private void LoadPressed(){
			if( list.getSelectedIndex() < 0 ) return;
			
			FileClass fc = classes.get(list.getSelectedIndex());
			LoadClass(fc, true);
		}
		
		public void LoadClass(FileClass fc, boolean isBuiltIn){
			if( cinfo != null){
				cinfo.file = fc.f.getName().replace(".class", "");
				cinfo.dir = fc.f.getParent();
				cinfo.param = "";
				cinfo.isBuiltIn = isBuiltIn;
			}
			this.setVisible(false);			
		}
		
		
		private void LoadExternalPressed(){
			
			JFileChooser chooser = new JFileChooser();
			chooser.setFileFilter(new javax.swing.filechooser.FileFilter() {
				public String getDescription() {
					return "Class files";
				}
				public boolean accept(File f) {
					return f.getName().endsWith(".class") || f.isDirectory(); 
				}
			});
			
			chooser.showOpenDialog(this);
			
			File chosen = chooser.getSelectedFile();
			File chosenDir = chooser.getCurrentDirectory();
			
			//System.out.println("Loading class " + chosen.getAbsolutePath());
			//Load file
			FileClass fc = null;
			try {
				URL url = chosenDir.toURI().toURL();
			    URL[] urls = new URL[]{url};
			    ClassLoader cl = new URLClassLoader(urls);
			    Class<?> cls = cl.loadClass(chosen.getName().replace(".class", ""));
			    ArrayList<Class<?>> interfaces = getAllInterfaces(cls);
			    for( Class<?> c : interfaces ){ 
			    	if( c.equals(mytype) && !Modifier.isAbstract(cls.getModifiers()) ) {
			    		fc = new FileClass(chosen, cls);
			    		break;
			    	}
			    }
			}catch(Exception ex){
				System.err.println("Error loading class file: " + chosen.getAbsolutePath());
			}
			if( fc != null ){
				LoadClass(fc, false);
			}
		}
	}
	
	private static ArrayList<Class<?>> getAllInterfaces(Class<?> c){
		ArrayList<Class<?>> allInterfaces = new ArrayList<Class<?>>();
		if( !c.isInterface() && c.getInterfaces().length == 0 ){
			allInterfaces.addAll( getAllInterfaces(c.getSuperclass()) );
		} else{
			allInterfaces.addAll(Arrays.asList(c.getInterfaces()));
		}
		return allInterfaces;
	}
	
	
	private static class FileClass {
		File f = null;
		Class<?> c = null;
		public FileClass(File ff, Class<?> cc){ f =ff; c=cc;}
		public String toString(){ return c.getSimpleName(); }
	}
	
}

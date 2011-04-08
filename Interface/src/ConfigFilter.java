import java.io.File;

import javax.swing.filechooser.FileFilter;


public class ConfigFilter extends FileFilter {

	public boolean accept(File f) {
		return f.isDirectory() || f.getName().endsWith(".config");
	}

	public String getDescription() {
		return "Configs (.config)";
	}

}

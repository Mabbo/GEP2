

public class GEPMain {
	
	public static String defaultConfigPath = "default.gepconfig";
	
	public static void main(String[] args) {
		GEPInterface gepi = null;
		if( args.length > 0){
			gepi = new GEPInterface(args[0]);
		} else {
			gepi = new GEPInterface(defaultConfigPath);
		}
		gepi.setVisible(true);	
	}
}


public class ClassInformation{
	public String file;
	public String dir;
	public String param;
	public boolean isBuiltIn;
	public ClassInformation(){
		file = dir = param = "";
		isBuiltIn = false;
	}
	public String getFileName(){
		if( !isBuiltIn ) return file;
		return file.replace("builtin.", "");
	}
	public boolean equals(Object other){
		if( ! other.getClass().equals(this.getClass())) return false;
		
		ClassInformation cOther = (ClassInformation)other;
		return (cOther.dir.equals(this.dir)) && (cOther.file.equals(this.file));
	}
}

public class ModifierClassInformation extends ClassInformation {
	public int weight;
	public ModifierClassInformation(){
		super();
		weight = 1;
	}
	public ModifierClassInformation(ClassInformation ci){
		this.file = ci.file;
		this.dir = ci.dir;
		this.param = ci.param;
		this.isBuiltIn = ci.isBuiltIn;
		weight = 1;
	}
}
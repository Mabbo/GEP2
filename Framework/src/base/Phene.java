package base;

import java.util.ArrayList;
import java.util.LinkedList;

import framework.*;

public class Phene {

	Gene gene;
	Config conf;
	aNode root = null;
	ArrayList<TerminalNode> terminalNodes;
	ArrayList<RNCNode> rncNodes;
	
	public Phene(Gene g, Config conf) {
		this.gene = g;
		this.conf = conf;
	}
	
	public double getOutput(double[] inputs){
		if( root == null ) BuildTree();
		for( TerminalNode tn : terminalNodes ){
			int index = conf.getIndexForTerminal(tn.getTerminal());
			tn.setValue(inputs[index]);
		}
		return root.getValue();
	}
	
	public void BuildTree(){
		
		LinkedList<FunctionNode> worklist = new LinkedList<FunctionNode>();
		terminalNodes = new ArrayList<TerminalNode>();
		
		int readItt = 0;
		
		do {
			byte nextValue = gene.getDNA(readItt);
			aNode node;
			
			Config.LetterType type = conf.getTypeFor(nextValue);
			if( type == Config.LetterType.FUNCTION ){
				Function f = conf.getNodeFunctionSet().getFunction(nextValue);
				FunctionNode fn = new FunctionNode(f);
				node = fn;
				worklist.add(fn);
			} else if (type == Config.LetterType.TERMINAL ) {
				TerminalNode tn = new TerminalNode(nextValue);
				node = tn;
				terminalNodes.add(tn);
			} else if (type == Config.LetterType.RNC ) {
				int index = conf.getIndexForRNC(nextValue);
				double value = gene.getRNC(index);
				RNCNode rncn = new RNCNode(value);
				node = rncn;
			} else {
				System.err.println("Phene: Error building tree, invalid value '" + nextValue + "'.");
				node = null;
			}
			//if this root is null, set this to the root (first node made)
			if( root == null ) {
				root = node;
			} else{
				//if this isn't the root node, get the first node from the worklist
				//add this node as a child to the worklist function node. If that
				//node has enough children now, remove it from the worklist.
				FunctionNode parent = worklist.getFirst();
				parent.children.add(node);
				if( parent.children.size() >= parent.getNumChildren()){
					worklist.pop();
				}
			}
			readItt++;
		} while(!worklist.isEmpty());
	}
	
		
	private abstract class aNode{
		public abstract double getValue();
	}
	private class FunctionNode extends aNode{
		public ArrayList<aNode> children = new ArrayList<aNode>();
		public Function function;
		public int getNumChildren() { return function.getNumArgs();}
		
		public FunctionNode(Function func){
			function = func;
		}	
		public double getValue() {
			double[] inputs = new double[function.getNumArgs()];
			for( int i = 0; i < children.size(); ++i) {
				inputs[i] = children.get(i).getValue();
			}
			return function.ApplyFunction(inputs);
		}
		
		public String toString(){
			String ret = function.getFunctionName() + "( ";
			for( int i = 0; i < children.size(); ++i){
				ret += (i==0? "" : ", " ) + children.get(i).toString() ;
			}
			ret += " )";
			return ret;
		}
	}
	
	private class TerminalNode extends aNode{
		private byte _terminal;
		public TerminalNode(byte terminal){
			_terminal = terminal;
		}
		public byte getTerminal(){return _terminal;}
		
		public static final double NO_VALUE = -45000001;  
		private double _value = NO_VALUE;
		public void setValue(double value) { _value = value; }
		public double getValue() { return _value;}
		public String toString() {
			return "{" + _terminal + "}";
		}
	}
	
	private class RNCNode extends aNode{
		private double _value;
		public RNCNode(double value){
			_value = value;
		}		  
		public double getValue() { return _value;}
		public String toString() {
			return "" + _value;
		}
	}	
	
	public String toString() {
		return root.toString();
	}
	
	
	
}

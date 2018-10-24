
public class NodeBlock extends Node {
	
	private NodeStmt s;
	private NodeBlock b;
	
	public NodeBlock(NodeStmt statement, NodeBlock block) {
		this.s = statement;
		this.b = block;
	}
	
	public double eval(Environment env) throws EvalException{
		if(b == null) {
			return s.eval(env);
		}else {
			s.eval(env);
			return b.eval(env);
		}
	}

}

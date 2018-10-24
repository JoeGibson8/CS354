public class NodeStmt extends Node {

	private NodeAssn assn;
	private NodeBoolexpr boolexpr;
	private NodeBlock block;
	private NodeExpr expr;
	private NodeStmt stmt1, stmt2;
	private Token ifWhile = new Token("");
	private String s;

    public NodeStmt(NodeAssn assn) {
	this.assn=assn;
    }
    

	public NodeStmt(NodeExpr expr){
		this.expr = expr;
	}
	
	public NodeStmt(String s) {
		this.s = s;
	}
	
	public NodeStmt(Token ifWhile, NodeBoolexpr boolexpr, NodeStmt stmt1,
			NodeStmt stmt2) {
		this.boolexpr = boolexpr;
		this.stmt1 = stmt1;
		this.stmt2 = stmt2;
		this.ifWhile = ifWhile;
	}


	public NodeStmt(Token ifWhile, NodeBoolexpr boolexpr, NodeStmt stmt1) {
		this.boolexpr = boolexpr;
		this.stmt1 = stmt1;
		this.ifWhile = ifWhile;
	}

	public NodeStmt(NodeBlock block) {
		this.block = block;
	}

	public double eval(Environment env) throws EvalException {

		if (assn != null) {
			return assn.eval(env);
		} 
		else if (ifWhile.equals(new Token ("if"))) {
			if (boolexpr.eval(env) == true) {
				return stmt1.eval(env);
			} 
			else if (stmt2 == null) {
				return 0;
			} 
			else {
				return stmt2.eval(env);
			}

		} 
		else if (ifWhile.equals(new Token ("while"))) {
			double result = 0;
			while (boolexpr.eval(env) == true){
				result = stmt1.eval(env);
			}
			return result;
		} 
		else if (block != null) {
			return block.eval(env);
		} 
		else if (expr != null){
			//System.out.println(expr.eval(env));
			return expr.eval(env);
		}else {
			return 0;
		}

	}


}

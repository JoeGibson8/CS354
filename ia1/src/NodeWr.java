public class NodeWr extends NodeStmt {
    private NodeExpr expr;

    public NodeWr(NodeExpr expr)
    {
    	super("");
        this.expr = expr;
    }
    
  
    @Override
    public double eval(Environment env) throws EvalException
    {
        System.out.println(expr.eval(env));
        return 0.0 ;
    }
}
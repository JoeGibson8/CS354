/**
 * 
 * @author Michael Blowers
 *
 */

public class NodeBoolexpr {

    private final NodeExpr expr1;
    private final String o;
    private final NodeExpr expr2;
    
    public NodeBoolexpr(NodeExpr expr1, String relop, NodeExpr expr2)
    {

        this.expr1 = expr1;
        this.o = relop;
        this.expr2 = expr2;
    }
    
    /**
     * Evaluates the boolean expression for the given environment
     * @param env
     * @return - boolean
     * @throws EvalException 
     */
    public boolean eval(Environment env) throws EvalException
    {
        double left = expr1.eval(env);
        double right = expr2.eval(env);
        if (o.equals(">")) return left > right;
    	else if (o.equals("<")) return left < right;
    	else if (o.equals("<=")) return left <= right;
    	else if (o.equals(">=")) return left >= right;
    	else if (o.equals("==")) return left == right;
    	else if (o.equals("<>")) return left != right;
       	else
        throw new EvalException(1,"Boolean operation not found" );
    }



/*public NodeBoolexpr(NodeExpr left, NodeExpr right, NodeRelop operator) {
	this.l = left;
	this.r = right;
	this.o = operator;
}

@SuppressWarnings("unlikely-arg-type")
public boolean eval(Environment env) throws EvalException{
	*
	 * adding these items from ia2
	 * s.add(">");
	s.add("<");
	s.add("<=");
	s.add(">=");
	s.add("==");
	s.add("<>");
	 *
	double left = l.eval(env);
	double right = r.eval(env);
	if (o.equals(">")) return left > right;
	else if (o.equals("<")) return left < right;
	else if (o.equals("<=")) return left <= right;
	else if (o.equals(">=")) return left >= right;
	else if (o.equals("==")) return left == right;
	else if (o.equals("<>")) return left != right;
	else throw new EvalException(0, "Bad operation");
	
}*/
}


public class NodeFactNegative extends NodeFact {

	private NodeFact negate;

	public NodeFactNegative(NodeFact node) {
		this.negate = node;
	}

	public double eval(Environment env) throws EvalException {
		return -negate.eval(env);
	}
}
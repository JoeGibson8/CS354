/**
 * 
 * @author Michael Blowers
 *
 */
public class NodeRelop extends Node {
private String s;

public NodeRelop (String relop, int position) {
	
	this.pos = position;
	this.s = relop;
}

public boolean performOperation(double first, double second) throws EvalException{
	/*
	 * adding these items from ia2
	 * s.add(">");
	s.add("<");
	s.add("<=");
	s.add(">=");
	s.add("==");
	s.add("<>");
	 */
	if(s.equals(">")){
		return first > second;
	}
	else if(s.equals("<")){
		return first < second;
	}
	else if(s.equals("<=")){
		return first <= second;
	}
	else if(s.equals(">=")){
		return first >= second;
	}
	else if(s.equals("==")){
		return first == second;
	}
	else if(s.equals("<>")){
		return first != second;
	}else {
		throw new EvalException(0, "Error: Unrecognized relation");
	}
}
}

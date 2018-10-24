
// This class is a recursive-descent parser,
// modeled after the programming language's grammar.
// It constructs and has-a Scanner for the program
// being parsed.

import java.util.*;

public class Parser {

	private Scanner scanner;

	private void match(String s) throws SyntaxException {
		scanner.match(new Token(s));

	}

	private Token curr() throws SyntaxException {
		return scanner.curr();
	}

	// position of the string / statement
	private int pos() {
		return scanner.pos();
	}

	// found the matching operation now it calls the correct node
	private NodeMulop parseMulop() throws SyntaxException {
		if (curr().equals(new Token("*"))) {
			match("*");
			return new NodeMulop(pos(), "*");
		}
		if (curr().equals(new Token("/"))) {
			match("/");
			return new NodeMulop(pos(), "/");
		}
		return null;
	}

	// found the + / - and now it calls the correct node
	private NodeAddop parseAddop() throws SyntaxException {
		if (curr().equals(new Token("+"))) {
			match("+");
			return new NodeAddop(pos(), "+");
		}
		if (curr().equals(new Token("-"))) {
			match("-");
			return new NodeAddop(pos(), "-");
		}
		return null;
	}

	// implemented logic to negate numbers as well as parse for id facts and ""
	private NodeFact parseFact() throws SyntaxException {
		if (curr().equals(new Token("("))) {
			match("(");
			NodeExpr expr = parseExpr();
			match(")");
			return new NodeFactExpr(expr);
		}
		if (curr().equals(new Token("id"))) {
			Token id = curr();
			match("id");
			return new NodeFactId(pos(), id.lex());
		}

		if (curr().equals(new Token("-"))) {
			match("-");
			NodeFact node = parseFact();
			return new NodeFactNegative(node);
		}

		// checks the curr position
		Token num = curr();
		match("num");
		return new NodeFactNum(num.lex());
	}

	// checks for / and * for factoring
	private NodeTerm parseTerm() throws SyntaxException {
		NodeFact fact = parseFact();
		NodeMulop mulop = parseMulop();
		if (mulop == null)
			return new NodeTerm(fact, null, null);
		NodeTerm term = parseTerm();
		term.append(new NodeTerm(fact, mulop, null));
		return term;
	}

	// checks for + and - for summing and subtracting
	private NodeExpr parseExpr() throws SyntaxException {
		NodeTerm term = parseTerm();
		NodeAddop addop = parseAddop();
		if (addop == null)
			return new NodeExpr(term, null, null);
		NodeExpr expr = parseExpr();
		expr.append(new NodeExpr(term, addop, null));
		return expr;
	}

	// parses for the assignment operation
	private NodeAssn parseAssn() throws SyntaxException {
		Token id = curr();
		match("id");
		match("=");
		NodeExpr expr = parseExpr();
		NodeAssn assn = new NodeAssn(id.lex(), expr);
		return assn;
	}

	// parses for an entire statement
	private NodeStmt parseStmt() throws SyntaxException {
		// if statement that evaluates using "then" and "else"
		if (curr().equals(new Token("rd"))) {
			match("rd");
			Token id = curr();
			match("id");
			System.out.println("Please enter desired integer for" + id.lex() + ": ");

			java.util.Scanner input = new java.util.Scanner(System.in);
			int i = 0;
			double num = 0;
			while (i == 0) {
				try {
					num = input.nextDouble();
					i = 1;
				} catch (NumberFormatException a) {
					System.out.println("You must enter a real number.");
				}
			}

			NodeFactNum n = new NodeFactNum(Double.toString(num));
			NodeTerm t = new NodeTerm(n, null, null);
			NodeExpr e = new NodeExpr(t, null, null);
			NodeAssn a = new NodeAssn(id.lex(), e);

			//input.close();
			return new NodeStmt(a);

		} else if (curr().equals(new Token("wr"))) {
			match("wr");
			
			NodeExpr e = parseExpr();
			
			return new NodeStmt(e);
		}
		if (curr().equals(new Token("if"))) {
			match("if");
			NodeBoolexpr b = parseBoolexpr();
			match("then");
			NodeStmt t = parseStmt();
			if (curr().equals(new Token("else"))) {
				match("else");
				NodeStmt e = parseStmt();
				return new NodeStmt(new Token("if"), b, t, e);
			} else {
				return new NodeStmt(new Token("if"), b, t, null);
			}
		} else if (curr().equals(new Token("while"))) {
			match("while");
			NodeBoolexpr b = parseBoolexpr();
			match("do");
			NodeStmt d = parseStmt();
			return new NodeStmt(new Token("while"), b, d);
		} else if (curr().equals(new Token("begin"))) {
			match("begin");
			NodeBlock b = parseBlock();
			match("end");
			return new NodeStmt(b);
		} else {
			NodeAssn assn = parseAssn();
			// match(";");
			NodeStmt stmt = new NodeStmt(assn);
			return stmt;
		}
	}

	public NodeStmt parseStmtScanner() throws SyntaxException {
		if (curr().equals(new Token("rd"))) {
			match("rd");
			Token id = curr();
			match("id");
			System.out.println("Please enter desired integer for " + id.lex() + ": ");
			java.util.Scanner i = new java.util.Scanner(System.in);
			double v = i.nextDouble();
			NodeFactNum n = new NodeFactNum(Double.toString(v));
			NodeTerm t = new NodeTerm(n, null, null);
			NodeExpr e = new NodeExpr(t, null, null);
			NodeAssn a = new NodeAssn(id.lex(), e);
			return new NodeStmt(a);

		} else if (curr().equals(new Token("wr"))) {
			match("wr");
			NodeExpr expr = parseExpr();
			return new NodeStmt(expr);
		} else
			return null;
	}

	// the initial parse to send it to the parse statement where it then calls next
	public Node parse(String program) throws SyntaxException {
		scanner = new Scanner(program);
		scanner.next();
		return parseBlock();
	}

	private NodeBoolexpr parseBoolexpr() throws SyntaxException {
		NodeExpr left = parseExpr();
		String o = "";
		if (curr().equals(new Token("<"))) {
			match("<");
			o = "<";
		} else if (curr().equals(new Token(">"))) {
			match(">");
			o = ">";
		} else if (curr().equals(new Token("<="))) {
			match("<=");
			o = "<=";
		} else if (curr().equals(new Token(">="))) {
			match(">=");
			o = ">=";
		} else if (curr().equals(new Token("<>"))) {
			match("<>");
			o = "<>";
		} else if (curr().equals(new Token("=="))) {
			match("==");
			o = "==";
		}
		NodeExpr right = parseExpr();
		NodeBoolexpr newNode = new NodeBoolexpr(left, o, right);
		return newNode;
	}

	private NodeBlock parseBlock() throws SyntaxException {
		NodeStmt statement = parseStmt();
		NodeBlock block;
		if (!scanner.done() && !curr().equals(new Token("end"))) {
			match(";");
			block = parseBlock();
			return new NodeBlock(statement, block);
		} else {
			return new NodeBlock(statement, null);
		}
	}

}

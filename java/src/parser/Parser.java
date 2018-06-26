package parser;

import parser.node.BinaryExpressionNode;
import parser.node.ExpressionNode;
import parser.node.IdentifierExpressionNode;
import parser.node.NumberExpressionNode;
import parser.node.TermExpressionNode;
import parser.node.Tree;
import parser.node.UnaryExpressionNode;

public class Parser {
	private TokenReader tr;

	private Tree tree;

	public Tree parse(Token tokens) {
		this.tr = new TokenReader(tokens);

		tree = new Tree();
		ExpressionNode expr = parseExpression();
		tree.addExpression(expr);

		tr.next(Type.EOF);

		return tree;
	}

	/*
	 * <expression> := <unary> { ... };
	 */
	private ExpressionNode parseExpression() {
		ExpressionNode lhs = parseUnaryExpression();
		return parseBinaryExpression(lhs, 0);
	}

	/*
	 * ... { ( '+' | '-' | '*' ) <term> };
	 */
	private ExpressionNode parseBinaryExpression(ExpressionNode lhs, int minPrec) {
		int prec;
		while((prec = getPrec(tr.peek())) >= minPrec) {
			String op = tr.next(Type.Symbol);
			ExpressionNode rhs = parseTermExpression();

			if(getPrec(tr.peek())>prec) rhs = parseBinaryExpression(rhs, prec+1);

			BinaryExpressionNode ben = new BinaryExpressionNode();
			ben.lhs = lhs;
			ben.op = op;
			ben.rhs = rhs;
			lhs = ben;
		}
		return lhs;
	}

	/*
	 * return the precedence of given 'String' operator as 'int'. return -1 if given one is not an operator.
	 */
	private int getPrec(String operator) {
		String[][] a = Constants.OPERATOR_PRECEDENCES;
		for (int i = 0; i < a.length; i++) for (int j = 0; j < a[i].length; j++) {
			if (a[i][j].equals(operator)) return i;
		}
		return -1;
	}

	/*
	 * <unary> := [ ( '+' | '-' ) ] <term>;
	 */
	private ExpressionNode parseUnaryExpression() {
		if(tr.is("+")) tr.next("+");
		else if(tr.is("-")) {
			UnaryExpressionNode uen = new UnaryExpressionNode();
			tr.next("-");
			uen.op = "~";
			uen.rhs = parseTermExpression();
			return uen;
		}
		return parseTermExpression();
	}

	/*
	 * <term> := ( <identifier> | <number> | '(' <expression> ')' ) [ '^' <number> ];
	 */
	private ExpressionNode parseTermExpression() {
		ExpressionNode en = null;

		if(tr.is(Type.Identifier)) en = parseIdentifierExpression();
		else if(tr.is(Type.Number)) en = parseNumberExpression();
		else if(tr.is("(")) en = parseParenExpression();
		else new Exception("unexpected token : " + tr.peek()).printStackTrace();

		if(tr.is("^")) {
			TermExpressionNode ten = new TermExpressionNode();
			ten.lhs = en;
			ten.op = tr.next("^");
			ten.rhs = parseNumberExpression();
			return ten;
		}

		return en;
	}

	/*
	 * <identifier>;
	 */
	private ExpressionNode parseIdentifierExpression() {
		IdentifierExpressionNode ien = new IdentifierExpressionNode();
		ien.identifier = tr.next(Type.Identifier);
		return ien;
	}

	/*
	 * <number>;
	 */
	private ExpressionNode parseNumberExpression() {
		NumberExpressionNode nen = new NumberExpressionNode();
		nen.number = tr.next(Type.Number);
		return nen;
	}

	/*
	 * '(' <expr> ')';
	 */
	private ExpressionNode parseParenExpression() {
		tr.next("(");
		ExpressionNode en = parseExpression();
		tr.next(")");
		return en;
	}

}
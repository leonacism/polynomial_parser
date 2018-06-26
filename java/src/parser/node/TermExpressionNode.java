package parser.node;

public class TermExpressionNode extends ExpressionNode {
	public ExpressionNode lhs;
	public String op;
	public ExpressionNode rhs;

	@Override
	public String dumpRPN() {
		return lhs.dumpRPN() + " " + rhs.dumpRPN() + " " + op;
	}
}

package parser.node;

public class UnaryExpressionNode extends ExpressionNode {
	public String op;
	public ExpressionNode rhs;

	@Override
	public String dumpRPN() {
		return rhs.dumpRPN() + " " + op;
	}
}

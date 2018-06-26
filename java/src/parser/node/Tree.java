package parser.node;

public class Tree implements INode {
	public ExpressionNode expr;

	public void addExpression(ExpressionNode expr) {
		this.expr = expr;
	}

	public String toString() {
		return expr.dumpRPN();
	}
}

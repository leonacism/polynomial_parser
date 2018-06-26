package parser.node;

public class IdentifierExpressionNode extends ExpressionNode {
	public String identifier;

	@Override
	public String dumpRPN() {
		return identifier;
	}

}

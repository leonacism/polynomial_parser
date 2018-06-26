package parser.node;

public class NumberExpressionNode extends ExpressionNode {
	public String number;


	public String dumpRPN() {
		return number;
	}

}

package parser.node;

class MainNode implements INode {
	public var expr:ExpressionNode;

	public function new() {}

	public function toString():String return expr.dumpRPN();
}
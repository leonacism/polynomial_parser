package parser.node;

class UnaryExpressionNode extends ExpressionNode {
	public var op:String;
	public var rhs:ExpressionNode;

	public function new() super();
	
	public override function dumpRPN() return '${rhs.dumpRPN()} $op';
}
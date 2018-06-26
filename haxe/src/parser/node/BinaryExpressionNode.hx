package parser.node;

class BinaryExpressionNode extends ExpressionNode {
	public var lhs:ExpressionNode;
	public var op:String;
	public var rhs:ExpressionNode;

	public function new() super();
	
	public override function dumpRPN() return '${lhs.dumpRPN()} ${rhs.dumpRPN()} $op';
}
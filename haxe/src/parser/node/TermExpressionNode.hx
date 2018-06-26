package parser.node;

class TermExpressionNode extends ExpressionNode {
	public var lhs:ExpressionNode;
	public var op:String;
	public var rhs:ExpressionNode;

	public function new() super();
	
	public override function dumpRPN() return '${lhs.dumpRPN()} ${rhs.dumpRPN()} $op';
}
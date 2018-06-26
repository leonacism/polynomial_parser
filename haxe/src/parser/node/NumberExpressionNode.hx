package parser.node;

class NumberExpressionNode extends ExpressionNode {
	public var number:String;

	public function new() super();
	
	public override function dumpRPN() return number;
}
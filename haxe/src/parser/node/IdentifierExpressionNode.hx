package parser.node;

class IdentifierExpressionNode extends ExpressionNode {
	public var identifier:String;

	public function new() super();
	
	public override function dumpRPN() return identifier;
}
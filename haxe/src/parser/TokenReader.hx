package parser;
import parser.Token.Type;

abstract TokenReader(Token) {
	public inline function new(token:Token) this = token;
	
	@:to
	public inline function to() {
		var i = this;
		return {
			hasNext: function() return i.type != Type.EOF,
			next: function() {var tmp = i; i = i.next; return tmp;},
			peek: function() return i,
		};
	}
}
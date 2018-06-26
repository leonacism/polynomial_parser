package parser;

enum Type {
	Number(data : String);
	Identifier(data : String);
	Keyword(data : String);
	Symbol(data : String);
	EOF;
}
 
class Token {
	public var type(default, null):Type;
	public var next:Token;
	
	public function new(type:Type) this.type = type;
	
	public function toString():String {
		return switch (this.type) {
			case Number(data) : '[ num : $data ]';
			case Keyword(data) : '[ keyword : $data ]';
			case Symbol(data) : '[ symbol : $data ]';
			case Identifier(data) : '[ identifier : $data ]';
			case EOF : '[ EOF ]';
		}
    }
}
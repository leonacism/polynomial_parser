package parser;

@:enum
abstract Scope(String) {
	var Global = 'global';
}

@:enum
abstract BuiltInFunction(String) {
	var Max = 'max';
}

class Constants {
	public static var KEYWORDS(get,never):Array<String>;
	static function get_KEYWORDS() return [
//		"program", "this", "var", "function",
//		"void",
//		"if", "else",
	];
	
	public static var SYMBOLS(get,never):Array<String>;
	static function get_SYMBOLS() return [
		'+', '-', '*', '/', '%', '^', '(', ')',
	];
	
	public static var OPERATOR_PRECEDENCES(get, never):Array<Array<String>>;
	static function get_OPERATOR_PRECEDENCES() return [
		['+', '-'],
		['/', '%'],
		['*'],
	];
}
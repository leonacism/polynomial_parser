package parser;

class Tokenizer {
	private var tokens:Token;
	private var lastToken:Token;

	private var sr:{hasNext:Void -> Bool, next:Void -> String, peek:Void -> String};

	public inline function new() {}
	
	public inline function tokenize(src:String) {
		this.sr = new StringReader(src);
		this.tokens = null;
		
		while (sr.hasNext()) readToken();
		appendToken(new Token(Token.Type.EOF));
		return tokens;
	}
	
	private inline function readToken() {
		while(isSpace(sr.peek())) sr.next();
		
		if (isNumber(sr.peek())) {
			var s = '';
			
			while (isNumber(sr.peek())) s += sr.next();
			
			isLetter(sr.peek())? throw 'invalid number format : ${s+sr.peek()}' : appendToken(new Token(Token.Type.Number(s)));
		}
		else if (isLetter(sr.peek())) {
			var s = '';
			
			while (isNumber(sr.peek())||isLetter(sr.peek())) s += sr.next();
			
			isKeyword(s)? appendToken(new Token(Token.Type.Keyword(s))) : appendToken(new Token(Token.Type.Identifier(s)));
		}
		else {
			var s = '';
			
			var b = true;
			while (b) {
				var tmp = s + sr.peek();
				b = false;
				for (m in Constants.SYMBOLS) if (tmp == m.substr(0, tmp.length)) {s += sr.next(); b = sr.hasNext(); break;}
			}
			
			isSymbol(s)? appendToken(new Token(Token.Type.Symbol(s))) : throw 'invalid symbol format : ${s+sr.next()}';
		}
	}
	
	private inline function appendToken(token:Token) {
		if(tokens!=null) lastToken = lastToken.next = token;
		else lastToken = tokens = token;
	}
	
	private inline function isSpace(s:String) return ~/[\t\r\n ]/.match(s);
	private inline function isNumber(s:String) return ~/[0-9]/.match(s);
	private inline function isLetter(s:String) return ~/[a-zA-Z_\$]/.match(s);
	
	private inline function isKeyword(s:String) {
		var b = false;
		for (k in Constants.KEYWORDS) if (s == k) {b = true; break;}
		return b;
	}
	
	private inline function isSymbol(s:String) {
		var b = false;
		for (m in Constants.SYMBOLS) if (s == m) {b = true; break;}
		return b;
	}
}
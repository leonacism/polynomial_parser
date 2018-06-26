package parser;

public class TokenReader {
	public Token tokens;

	public TokenReader(Token tokens) {
		this.tokens = tokens;
	}

	public boolean hasNext() {
		return tokens.getType() != Type.EOF;
	}

	public String peek() {
		return tokens.getData();
	}

	public String next() {
		String data = tokens.getData();
		if(tokens.getType() == Type.Error) new Exception("unexpected token : " + data).printStackTrace();
		tokens = tokens.next;
		return data;
	}

	public String next(String expected) {
		String data = tokens.getData();
		if(!this.is(expected) || tokens.getType() == Type.Error) new Exception("unexpected token : " + data).printStackTrace();
		tokens = tokens.next;
		return data;
	}

	public String next(Type expected) {
		String data = tokens.getData();
		if(!this.is(expected) || tokens.getType() == Type.Error) new Exception("unexpected token : " + data).printStackTrace();
		tokens = tokens.next;
		return data;
	}

	public boolean is(String data) {
		return tokens.getData().equals(data);
	}

	public boolean is(Type type) {
		return tokens.getType() == type;
	}
}
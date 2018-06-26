package parser;

enum Type {
	Number,
	Symbol,
	Identifier,
	EOF,
	Error;
}

public class Token {
	private Type type;
	public Type getType() {return this.type;}

	private String data;
	public String getData() {return this.data;}

	public Token next;

	public Token(Type type, String data) {
		this.type = type; this.data = data;
	}

	public String toString() {
		switch (this.type) {
			case Number : return "[ number : "+data+" ]";
			case Symbol : return "[ symbol : "+data+" ]";
			case Identifier : return "[ identifier : "+data+" ]";
			case EOF : return "[ EOF ]";
			case Error : return "[ Error : "+data+" ]";
			default : new NullPointerException().printStackTrace(); return null;
		}
	}
}
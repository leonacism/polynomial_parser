package parser;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Tokenizer {
	private StringReader sr;

	private Token tokens;
	private Token lastToken;

	public Tokenizer() {}

	public Token tokenize(String src) {
		this.sr = new StringReader(src);
		this.tokens = null;

		while (sr.hasNext()) readToken();

		appendToken(new Token(Type.EOF, "EOF"));

		return tokens;
	}

	private void readToken() {
		String s = sr.peek();
			 if (Pattern.compile("[\t\r\n ]").matcher(s).find()) sr.next();
		else if (Pattern.compile("[0-9]").matcher(s).find()) {
			String n = "";
			do {
				n += sr.next();
				s = sr.peek();
			} while (sr.hasNext() && Pattern.compile("[0-9]").matcher(s).find());
			appendToken(new Token(Type.Number, n));
		}
		else if (Pattern.compile("[a-zA-Z_$]").matcher(s).find()) {
			String n = "";
			do {
				n += sr.next();
				s = sr.peek();
			} while (sr.hasNext() && Pattern.compile("[a-zA-Z0-9_$]").matcher(s).find());
			appendToken(new Token(Type.Identifier, n));
		}
		else {
			if (Arrays.asList(Constants.SYMBOLS).contains(s)) appendToken(new Token(Type.Symbol, sr.next()));
			else appendToken(new Token(Type.Error, sr.next()));
		}
	}

	private void appendToken(Token token) {
		if(tokens!=null) {
			lastToken = lastToken.next = token;
		}
		else lastToken = tokens = token;
	}
}

package parser;

public class Constants {
	public static final String[] SYMBOLS = {
		"+", "-", "*", "^", "(", ")"
	};

	public static final String[][] OPERATOR_PRECEDENCES = {
		{"+", "-"},
		{"*"},
	};
}

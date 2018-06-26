package parser;

public class StringReader {
	private String data;
	private int index;

	public StringReader(String data) {
		this.data = data;
		this.index = 0;
	}

	public boolean hasNext() {
		return index < data.length();
	}

	public String peek() {
		if (!hasNext()) return null;
		return data.charAt(index)+"";
	}

	public String next() {
		if (!hasNext()) return null;
		return data.charAt(index++)+"";
	}
}

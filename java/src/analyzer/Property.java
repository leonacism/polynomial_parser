package analyzer;

public class Property<S> {
	public String name;
	public S data;

	public Property(String name, S data) {
		this.name = name;
		this.data = data;
	}
}

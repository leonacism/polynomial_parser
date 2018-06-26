package polynomial;

public interface Impl<S> {

	public S add(S a, S b);

	public S sub(S a, S b);

	public S mul(S a, S b);

	public S div(S a, S b);

	public S pow(S a, int n);

	public S scale(S a, int n);

	public S compile(String s);
}

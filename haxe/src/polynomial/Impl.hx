package polynomial;

interface Impl<S> {
	function add(a:S, b:S):S;
	function sub(a:S, b:S):S;
	function mul(a:S, b:S):S;
	function div(a:S, b:S):S;
	function mod(a:S, b:S):S;
	function pow(a:S, n:Int):S;
	function scale(a:S, n:Int):S;
	function compile(s:String):S;
}

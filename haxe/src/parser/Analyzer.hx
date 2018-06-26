package parser;
import parser.node.MainNode;
import polynomial.Impl;

typedef Property<S> = {
	name:String,
	data:S,
}
 
class Analyzer<S> {
	private var stack:Array<Property<S>>;
	private var index:Int;
	
	private var impl:polynomial.Impl<S>;
	
	public function new() {}

	public function analyze(tree:MainNode, impl:polynomial.Impl<S>):S {
		this.stack = new Array<Property<S>>();
		this.impl = impl;
		this.index = 0;
		
		runRPN(tree.toString());
		
		return pop().data;
	}
	
	private function runRPN(rpn:String) {
		var data = rpn.split(' ');
		for(s in data) {
			switch(s) {
				case '@add':
					binOp('@add');
				case '@sub':
					binOp('@sub');
				case '@mul':
					binOp('@mul');
				case '@div':
					binOp('@div');
				case '@mod':
					binOp('@mod');
				case '@pow':
					pow();
				case '@neg':
					unOp('@neg');
				default:
					push(s, impl.compile(s));
			}
		}
	}
	
	private function binOp(op:String) {
		var rhs = pop();
		var lhs = pop();
		
		var a:S = lhs.data==null? lhs.data = impl.compile(lhs.name):lhs.data;
		var b:S = rhs.data == null? rhs.data = impl.compile(rhs.name):rhs.data;
		
		var tmp:S = switch(op) {
			case '@add': impl.add(a, b);
			case '@sub': impl.sub(a, b);
			case '@mul': impl.mul(a, b);
			case '@div': impl.div(a, b);
			case '@mod': impl.mod(a, b);
			default: null;
		}
		push('$tmp', tmp);
	}
	
	private function unOp(op:String) {
		var rhs = pop();
		
		var a:S = rhs.data == null? rhs.data = impl.compile(rhs.name):rhs.data;
		
		var tmp:S = switch(op) {
			case '@neg': impl.scale(a, -1);
			default: null;
		}
		push('$tmp', tmp);
	}
	
	private function pow() {
		var rhs = pop();
		var lhs = pop();
		
		var a:S = lhs.data==null? lhs.data = impl.compile(lhs.name):lhs.data;
		var n:Int = Std.parseInt(rhs.name);
		
		var tmp:S = impl.pow(a, n);
		push('$tmp', tmp);
	}
	
	private function push(name:String, data:S) stack.push({name:name, data:data});
	
	private function pop() return stack.pop();
}
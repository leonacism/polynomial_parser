package polynomial;
import parser.*;
import parser.node.MainNode;
import polynomial.PolynomialImpl.Data;

class Polynomial {
	public var data:Data;
	public var expr:String;
	public var variables:Array<String>;
	
	public var tree:MainNode;
	
	private var impl:PolynomialImpl;

	public function new(expr:String, variables:Array<String>) {
		var t = new Tokenizer();
		var p = new Parser();
		var a = new Analyzer();
		this.tree = p.parse(t.tokenize(expr));
		this.impl = new PolynomialImpl(variables);
		
		this.data = a.analyze(tree, this.impl);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		
		/*
		var tr = new TokenReader(t.tokenize(expr));
		for(a in tr) trace(a);
		trace(tree);
		*/
	}
	
	public function add(p1:Polynomial, p2:Polynomial):Polynomial {
		var tmp = new Array<String>();

		for(s1 in p1.variables) {
			var f =true;
			for(s in tmp) if(s==s1) f=false;
			if(f) tmp.push(s1);
		}
		for(s2 in p2.variables) {
			var f =true;
			for(s in tmp) if(s==s2) f=false;
			if(f) tmp.push(s2);
		}
		impl.variables = tmp;

		if(p1.variables.length!=impl.variables.length) p1 = new Polynomial(p1.expr, impl.variables);
		if(p2.variables.length!=impl.variables.length) p2 = new Polynomial(p2.expr, impl.variables);

		this.data = impl.add(p1.data, p2.data);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}
	
	public function sub(p1:Polynomial, p2:Polynomial):Polynomial {
		var tmp = new Array<String>();

		for(s1 in p1.variables) {
			var f =true;
			for(s in tmp) if(s==s1) f=false;
			if(f) tmp.push(s1);
		}
		for(s2 in p2.variables) {
			var f =true;
			for(s in tmp) if(s==s2) f=false;
			if(f) tmp.push(s2);
		}
		impl.variables = tmp;

		if(p1.variables.length!=impl.variables.length) p1 = new Polynomial(p1.expr, impl.variables);
		if(p2.variables.length!=impl.variables.length) p2 = new Polynomial(p2.expr, impl.variables);

		this.data = impl.sub(p1.data, p2.data);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}
	
	public function mul(p1:Polynomial, p2:Polynomial):Polynomial {
		var tmp = new Array<String>();

		for(s1 in p1.variables) {
			var f =true;
			for(s in tmp) if(s==s1) f=false;
			if(f) tmp.push(s1);
		}
		for(s2 in p2.variables) {
			var f =true;
			for(s in tmp) if(s==s2) f=false;
			if(f) tmp.push(s2);
		}
		impl.variables = tmp;

		if(p1.variables.length!=impl.variables.length) p1 = new Polynomial(p1.expr, impl.variables);
		if(p2.variables.length!=impl.variables.length) p2 = new Polynomial(p2.expr, impl.variables);

		this.data = impl.mul(p1.data, p2.data);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}
	
	public function pow(p:Polynomial, n:Int):Polynomial {
		var tmp = new Array<String>();

		for(s1 in p.variables) {
			var f =true;
			for(s in tmp) if(s==s1) f=false;
			if(f) tmp.push(s1);
		}
		impl.variables = tmp;

		if(p.variables.length!=impl.variables.length) p = new Polynomial(p.expr, impl.variables);

		this.data = impl.pow(p.data, n);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}
	
	public function scale(p:Polynomial, n:Int):Polynomial {
		var tmp = new Array<String>();

		for(s1 in p.variables) {
			var f =true;
			for(s in tmp) if(s==s1) f=false;
			if(f) tmp.push(s1);
		}
		impl.variables = tmp;

		if(p.variables.length!=impl.variables.length) p = new Polynomial(p.expr, impl.variables);

		this.data = impl.scale(p.data, n);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}
	
	public function toString() return this.expr;
}
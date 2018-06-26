package polynomial;

import java.util.Map;

import analyzer.Analyzer;
import analyzer.Property;
import parser.Parser;
import parser.Tokenizer;
import parser.node.Tree;

public class Polynomial {
	public Map<int[], Long> data;
	public String expr;
	public String[] variables;

	public Tree tree;


	private PolynomialImpl impl;


	public Polynomial(String expr, String[] variables) {
		Tokenizer t = new Tokenizer();
		Parser p = new Parser();
		Analyzer analyzer = new Analyzer();

		this.tree = p.parse(t.tokenize(expr));
		this.impl = new PolynomialImpl(variables);
		Property<Map<int[], Long>> property = analyzer.analyze(tree, impl);

		this.data = property.data;
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;

		/*
		System.out.println(expr);
		System.out.println("");
		Token a = t.tokenize(expr);
		while(a != null) {
			System.out.println(a.toString());
			a = a.next;
		}
		System.out.println("");
		System.out.println(this.tree);
		System.out.println("");
		*/
	}


	public Polynomial add(Polynomial p1, Polynomial p2) {
		String[] tmp = new String[p1.variables.length+p2.variables.length];
		for(int i=0;i<tmp.length;i++) tmp[i]="";

		int n = 0;
		for(String s1 : p1.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s1)) f = false;
			if(f) tmp[n++] = s1;
		}
		for(String s2 : p2.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s2)) f = false;
			if(f) tmp[n++] = s2;
		}

		impl.variables = new String[n];
		for(int i=0;i<n;i++) impl.variables[i] = tmp[i];

		if(!p1.variables.equals(impl.variables)) p1 = new Polynomial(p1.expr, impl.variables);
		if(!p2.variables.equals(impl.variables)) p2 = new Polynomial(p2.expr, impl.variables);

		this.data = impl.add(p1.data, p2.data);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}


	public Polynomial sub(Polynomial p1, Polynomial p2) {
		String[] tmp = new String[p1.variables.length+p2.variables.length];
		for(int i=0;i<tmp.length;i++) tmp[i]="";

		int n = 0;
		for(String s1 : p1.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s1)) f = false;
			if(f) tmp[n++] = s1;
		}
		for(String s2 : p2.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s2)) f = false;
			if(f) tmp[n++] = s2;
		}

		impl.variables = new String[n];
		for(int i=0;i<n;i++) impl.variables[i] = tmp[i];

		if(!p1.variables.equals(impl.variables)) p1 = new Polynomial(p1.expr, impl.variables);
		if(!p2.variables.equals(impl.variables)) p2 = new Polynomial(p2.expr, impl.variables);

		this.data = impl.sub(p1.data, p2.data);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}


	public Polynomial mul(Polynomial p1, Polynomial p2) {
		String[] tmp = new String[p1.variables.length+p2.variables.length];
		for(int i=0;i<tmp.length;i++) tmp[i]="";

		int n = 0;
		for(String s1 : p1.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s1)) f = false;
			if(f) tmp[n++] = s1;
		}
		for(String s2 : p2.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s2)) f = false;
			if(f) tmp[n++] = s2;
		}

		impl.variables = new String[n];
		for(int i=0;i<n;i++) impl.variables[i] = tmp[i];

		if(!p1.variables.equals(impl.variables)) p1 = new Polynomial(p1.expr, impl.variables);
		if(!p2.variables.equals(impl.variables)) p2 = new Polynomial(p2.expr, impl.variables);

		this.data = impl.mul(p1.data, p2.data);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}


	public Polynomial pow(Polynomial p, int n) {
		String[] tmp = new String[p.variables.length];
		for(int i=0;i<tmp.length;i++) tmp[i]="";

		int m = 0;
		for(String s1 : p.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s1)) f = false;
			if(f) tmp[m++] = s1;
		}

		impl.variables = new String[m];
		for(int i=0;i<m;i++) impl.variables[i] = tmp[i];

		if(!p.variables.equals(impl.variables)) p = new Polynomial(p.expr, impl.variables);

		this.data = impl.pow(p.data, n);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}


	public Polynomial scale(Polynomial p, int n) {
		String[] tmp = new String[p.variables.length];
		for(int i=0;i<tmp.length;i++) tmp[i]="";

		int m = 0;
		for(String s1 : p.variables) {
			boolean f =true;
			for(String s : tmp) if(s.equals(s1)) f = false;
			if(f) tmp[m++] = s1;
		}

		impl.variables = new String[m];
		for(int i=0;i<m;i++) impl.variables[i] = tmp[i];

		if(!p.variables.equals(impl.variables)) p = new Polynomial(p.expr, impl.variables);

		this.data = impl.scale(p.data, n);
		this.expr = impl.dumpIN(this.data);
		this.variables = impl.variables;
		return this;
	}


	public String toString() {
		return expr;
	}
}
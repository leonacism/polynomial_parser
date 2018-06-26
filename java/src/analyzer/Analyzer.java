package analyzer;

import java.util.ArrayList;

import parser.node.Tree;
import polynomial.Impl;

public class Analyzer<S> {
	private ArrayList<Property<S>> stack;
	private int i;

	private Impl<S> impl;


	public Property<S> analyze(Tree tree, Impl<S> impl) {
		this.stack = new ArrayList<Property<S>>();
		this.impl = impl;
		this.i = 0;

		runRPN(tree.expr.dumpRPN());

		return stack.get(0);
	}


	private void runRPN(String rpn) {
		String[] data = rpn.split(" ");
		for(String s : data) {
			switch(s) {
			case "+" :
				binOp(s); break;
			case "-" :
				binOp(s); break;
			case "*" :
				binOp(s); break;
			case "^" :
				pow(); break;
			case "~" :
				unOp(s); break;
			default :
				push(s, impl.compile(s)); break;
			}
		}
	}


	private void binOp(String op) {
		Property<S> lhs, rhs;
		rhs = pop();
		lhs = pop();

		S a = lhs.data==null?lhs.data = impl.compile(lhs.name):lhs.data;
		S b = rhs.data==null?rhs.data = impl.compile(rhs.name):rhs.data;

		S tmp = null;
		switch(op) {
		case "+" :
			tmp = impl.add(a, b); break;
		case "-" :
			tmp = impl.sub(a, b); break;
		case "*" :
			tmp = impl.mul(a, b); break;
		case "/" :
			tmp = impl.div(a, b); break;
		}
		push(tmp.toString(), tmp);
	}


	private void unOp(String op) {
		Property<S> rhs = pop();

		S a = rhs.data==null?rhs.data = impl.compile(rhs.name):rhs.data;

		S tmp = null;
		switch(op) {
		case "~" :
			tmp = impl.scale(a, -1); break;
		}
		push(tmp.toString(), tmp);
	}


	private void pow() {
		Property<S> lhs, rhs;
		rhs = pop();
		lhs = pop();

		S a = lhs.data==null?lhs.data = impl.compile(lhs.name):lhs.data;
		int n = Integer.parseInt(rhs.name);

		S tmp = impl.pow(a, n);
		push(tmp.toString(), tmp);
	}


	private void push(String name, S data) {
		Property<S> property = new Property<S>(name, data);
		if(i<stack.size()) stack.set(i, property); else stack.add(i, property);
		stack.get(i).name = name;
		stack.get(i).data = data;
		i++;
	}


	private Property<S> pop() {
		return stack.get(--i);
	}

}
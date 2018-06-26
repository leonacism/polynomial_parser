package main;

import polynomial.Polynomial;

public class Main {

	public static void main(String[] args) {
		String[] a = {"x", "y", "y"}, b = {"a", "x", "y"}, c = {"x", "y", "z"};

		Polynomial p1 = new Polynomial("-(x+2*y-(-2+(+7)*x^2)^3)*(-21)", a);
		Polynomial p2 = new Polynomial("a-a*a*4-y + (-x)*2", b);
		Polynomial p3 = new Polynomial("(x)^2+(-y)^2+z^2-3*(-x)*y+(-3)*y*z+3*z*(-x)", c);

		System.out.println(p1.mul(p1, p2));
		System.out.println(p3.pow(p3, 4));
	}
}
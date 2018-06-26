package main;
import polynomial.Polynomial;

class Main {
	static function main() {
		var str = '(x)^2+(-y)^2+z^2-3*(-x)*y+(-3)*y*z+3*z*(-x)';
		var poly = new Polynomial(str, ['x', 'y', 'z']);
		trace('[${str}]^8 = ${poly.pow(poly,8).toString()}');
	}
}
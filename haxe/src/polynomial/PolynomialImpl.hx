package polynomial;
import haxe.Int64;

typedef Data = Map<Array<Int>, Int64>;
 
class PolynomialImpl implements Impl<Data> {
	public var variables:Array<String>;
	
	public function new(variables:Array<String>) {
		this.variables = variables;
	}
	
	public function add(a:Data, b:Data):Data {
		var tmp = new Data();

		for (k in a.keys()) {
			var v = a.get(k);
			tmp.set(k, v);
		}
		for (k in b.keys()) {
			var v = b.get(k);

			var f = true;
			for (l in tmp.keys()) if (equals(k,l)) {
				tmp.set(l, tmp.get(l)+v);
				f = false;
			}
			if (f) tmp.set(k, v);
		}

		for (k in tmp.keys()) if(tmp.get(k)==0) tmp.remove(k);
		if (!tmp.keys().hasNext()) tmp.set([for(i in 0...variables.length) 0], 0);

		return tmp;
	}
	
	public function sub(a:Data, b:Data):Data {
		var tmp = new Data();

		for (k in a.keys()) {
			var v = a.get(k);
			tmp.set(k, v);
		}
		for (k in b.keys()) {
			var v = -b.get(k);

			var f = true;
			for (l in tmp.keys()) if (equals(k,l)) {
				tmp.set(l, tmp.get(l)+v);
				f = false;
			}
			if (f) tmp.set(k, v);
		}

		for (k in tmp.keys()) if(tmp.get(k)==0) tmp.remove(k);
		if (!tmp.keys().hasNext()) tmp.set([for(i in 0...variables.length) 0], 0);

		return tmp;
	}
	
	public function mul(a:Data, b:Data):Data {
		var tmp = new Data();
		
		for(k1 in a.keys()) for(k2 in b.keys()) {
			var k = [for (i in 0...variables.length) k1[i] + k2[i]], v = a.get(k1) * b.get(k2);
			
			var f = true;
			for (l in tmp.keys()) if (equals(k, l)) {
				tmp.set(l, tmp.get(l)+v);
				f = false;
			}
			if(f) tmp.set(k, v);
		}
		
		for (k in tmp.keys()) if(tmp.get(k)==0) tmp.remove(k);
		if (!tmp.keys().hasNext()) tmp.set([for(i in 0...variables.length) 0], 0);

		return tmp;
	}
	
	public function div(a:Data, b:Data):Data {
		throw 'Not Implemented.';
	}
	
	public function mod(a:Data, b:Data):Data {
		throw 'Not Implemented.';
	}
	
	public function pow(a:Data, n:Int):Data {
		var tmp = new Data();
		var m=0; for(i in a) m++;
		
		var c = [for(i in 0...m) 0]; c[0] = n;
		while (true) {
			var k = [for(i in 0...variables.length) 0], v = Int64.ofInt(1), j = 0;
			for(k1 in a.keys()) {
				for (i in 0...variables.length) k[i] += c[j] * k1[i];
				for (i in 0...c[j]) v *= a.get(k1);
				j++;
			}
			
			var r = fact(n);
			for(i in c) r = cast r/fact(i);
			v *= r;
			
			var f = true;
			for(l in tmp.keys()) if(equals(k, l)) {
				tmp.set(l, tmp.get(l)+v);
				f = false;
			}
			if(f) tmp.set(k, v);
			
			if(c[m-1] == n) break;
			for (i in 0...m) {
				if (c[i] > 0) {
					c[0] = c[i] - 1;
					for(j in 1...i+1) c[j] = 0;
					c[i+1]+=i+1<m?1:0;
					break;
				}
			}
		}
		
		for (k in tmp.keys()) if(tmp.get(k)==0) tmp.remove(k);
		if (!tmp.keys().hasNext()) tmp.set([for (i in 0...variables.length) 0], 0);

		return tmp;
	}
	private inline function fact(n:Int64):Int64 return n<=0? 1:n*fact(n-1);
	
	public function scale(a:Data, n:Int):Data {
		var tmp = new Data();
		
		for (k in a.keys()) {
			var v = n*a.get(k);
			tmp.set(k, v);
		}
		
		for (k in tmp.keys()) if (tmp.get(k) == 0) tmp.remove(k);
		if (!tmp.keys().hasNext()) tmp.set([for (i in 0...variables.length) 0], 0);
		
		return tmp;
	}
	
	public function compile(s:String):Data {
		var tmp = new Data();
		
		var t = new Array<String>();

		for(s1 in variables) {
			var f =true;
			for(r in t) if(r==s1) f = false;
			if(f) t.push(s1);
		}
		this.variables = new Array<String>();
		for(i in 0...t.length) this.variables[i] = t[i];

		var a = [for(i in 0...variables.length) 0];
		if (~/[a-zA-Z_\$]/.match(s)) {
			for(i in 0...variables.length) if(s==variables[i]) {
				a[i] = 1; tmp.set(a, 1);
				return tmp;
			}
		}
		else if (~/[0-9]+/.match(s)) {
			tmp.set(a, Std.parseInt(s));
			return tmp;
		}
		throw 'unexpected string : $s';
	}
	
	private function equals(a:Array<Int>, b:Array<Int>) {
		var f = true;
		for(i in 0...variables.length) if(a[i]!=b[i]) f = false;
		return f;
	}
	
	public function dumpIN(data:Data) {
		var s = "";

		var list = new Array<Array<Int>>();
		for(set in data.keys()) list.push(set);
		list = sort(list, 0);

		var b = false;
		for(k in list) {
			var a = new Array<String>();
			for(i in 0...variables.length) {
				var r = variables[i];
				r += k[i]==1?"":"^"+k[i];

				if(k[i]==0) continue;
				a.push(r);
			}

			var t = "", c = a.length-1;
			for(i in 0...a.length) t += a[i] + (c-->0?"*":"");

			var abs = data.get(k)<0? -data.get(k):data.get(k);
			s += (data.get(k)!=0? data.get(k)>0? b? " + ":"":" - ":"") + (t!=""? (abs!=1? abs + "*":"" )+t:abs+"");
			
			if(!b)b=true;
		}
		return s;
	}

	private function sort(list:Array<Array<Int>>, k:Int) {
		if(k>=variables.length) return list;

		var tmp = new Array<Array<Int>>();

		var max = 0;
		for(l in list) max=l[k]>max?l[k]:max;

		var i=0, n=max;
		while(i<list.length) {
			var tmp_ = new Array<Array<Int>>();
			for(l in list) if(l[k]==n) {tmp_.push(l); i++;}

			tmp_ = sort(tmp_, k+1);
			for(l in tmp_) tmp.push(l);
			n--;
		}

		return tmp;
	}
}
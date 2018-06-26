package polynomial;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class PolynomialImpl implements Impl<Map<int[], Long>> {
	public String[] variables;


	public PolynomialImpl(String[] variables) {
		this.variables = variables;
	}


	@Override
	public Map<int[], Long> add(Map<int[], Long> m1, Map<int[], Long> m2) {
		Map<int[], Long> tmp = new HashMap<int[], Long>();
		Set<int[]> set = new HashSet<int[]>();

		for (int[] k : m1.keySet()) {
			long v = m1.get(k);
			tmp.put(k, v); set.add(k);
		}
		for (int[] k : m2.keySet()) {
			long v = m2.get(k);

			boolean b = true;
			for (int[] l : set) {
				if (equals(k,l)) {
					tmp.put(l, tmp.get(l)+v);
					b = false;
				}
			}
			if (b) {tmp.put(k, v); set.add(k);}
		}

		for (int[] k : set) if (tmp.get(k) == 0) tmp.remove(k);
		if (tmp.isEmpty()) {int[] k = new int[variables.length]; tmp.put(k, 0L);}

		return tmp;
	}


	@Override
	public Map<int[], Long> sub(Map<int[], Long> m1, Map<int[], Long> m2) {
		Map<int[], Long> tmp = new HashMap<int[], Long>();
		Set<int[]> set = new HashSet<int[]>();

		for (int[] k : m1.keySet()) {
			long v = m1.get(k);
			tmp.put(k, v); set.add(k);
		}
		for (int[] k : m2.keySet()) {
			long v = -m2.get(k);

			boolean b = true;
			for (int[] l : set) {
				if (equals(k,l)) {
					tmp.put(l, tmp.get(l)+v);
					b = false;
				}
			}
			if (b) {tmp.put(k, v); set.add(k);}
		}

		for (int[] k : set) if (tmp.get(k) == 0) tmp.remove(k);
		if (tmp.isEmpty()) {int[] k = new int[variables.length]; tmp.put(k, 0L);}

		return tmp;
	}


	@Override
	public Map<int[], Long> mul(Map<int[], Long> m1, Map<int[], Long> m2) {
		Map<int[], Long> tmp = new HashMap<int[], Long>();
		Set<int[]> set = new HashSet<int[]>();

		for (int[] k1 : m1.keySet()) for (int[] k2 : m2.keySet()) {
			int[] k = new int[variables.length]; for (int i=0;i<k.length;i++) k[i] = k1[i] + k2[i];
			long v = m1.get(k1) * m2.get(k2);

			boolean b = true;
			for (int[] l : set) {
				if (equals(k,l)) {
					tmp.put(l, v + tmp.get(l));
					b = false;
				}
			}
			if (b) {tmp.put(k, v); set.add(k);}
		}

		for (int[] k : set) if (tmp.get(k) == 0) tmp.remove(k);
		if (tmp.isEmpty()) {int[] k = new int[variables.length]; tmp.put(k, 0L);}

		return tmp;
	}


	@Override
	public Map<int[], Long> div(Map<int[], Long> m1, Map<int[], Long> m2) {
		new Exception("Not Implemented").printStackTrace();
		return null;
	}


	@Override
	public Map<int[], Long> pow(Map<int[], Long> m, int n) {
		Map<int[], Long> tmp = new HashMap<int[], Long>();
		Set<int[]> set = new HashSet<int[]>();
		int s = m.values().size();

		int[] a = new int[s]; a[0] = n;
		while (true) {
			int[] k = new int[variables.length];
			long v = 1; int j = 0;
			for(int[] k1 : m.keySet()) {
				for (int i=0;i<variables.length;i++) k[i] += a[j]*k1[i];
				v *= (long)(Math.pow(m.get(k1), a[j]));
				j++;
			}

			int r = fact(n);
			for (int i : a) r /= fact(i);
			v *= r;

			boolean b = true;
			for (int[] l : set) {
				if (equals(k,l)) {
					tmp.put(l, v + tmp.get(l));
					b = false;
				}
			}
			if (b) {tmp.put(k, v); set.add(k);}

			if (a[s-1] == n) break;
			for (int i=0;i<s;i++) {
				if (a[i] > 0) {

					a[0] = a[i] - 1;
					for(int ii=1;ii<i+1;ii++) a[ii] = 0;
					a[i+1]+=i+1<s?1:0;
					break;
				}
			}
		}

		for (int[] k : set) if (tmp.get(k) == 0) tmp.remove(k);
		if (tmp.isEmpty()) {int[] k = new int[variables.length]; tmp.put(k, 0L);}

		return tmp;
	}
	private int fact(int n) {if(n<0) new IllegalArgumentException("argument must be zero and more : " + n).printStackTrace(); return n==0?1:n*fact(n-1);}


	public Map<int[], Long> scale(Map<int[], Long> m, int n) {
		Map<int[], Long> tmp = new HashMap<int[], Long>();
		Set<int[]> set = new HashSet<int[]>();

		for (int[] k : m.keySet()) {
			long v = n*m.get(k);
			tmp.put(k, v); set.add(k);
		}

		for (int[] k : set) if (tmp.get(k) == 0) tmp.remove(k);
		if (tmp.isEmpty()) {int[] k = new int[variables.length]; tmp.put(k, 0L);}

		return tmp;
	}


	@Override
	public Map<int[], Long> compile(String s) {
		Map<int[], Long> tmp = new HashMap<int[], Long>();

		String[] t = new String[variables.length];
		for(int i=0;i<t.length;i++) t[i]="";

		int m = 0;
		for(String s1 : variables) {
			boolean f =true;
			for(String r : t) if(r.equals(s1)) f = false;
			if(f) t[m++] = s1;
		}

		this.variables = new String[m];
		for(int i=0;i<m;i++) this.variables[i] = t[i];

		int[] a = new int[variables.length];
		for(int i=0;i<variables.length;i++) {
			if(variables[i].equals(s)) {
				a[i] = 1; tmp.put(a, 1L);
				return tmp;
			}
		}

		if (Pattern.compile("[0-9]+").matcher(s).find()) {
			tmp.put(a, Long.valueOf(s));
			return tmp;
		}

		new Exception("unexpected string : " + s).printStackTrace();
		return null;
	}


	public String dumpIN(Map<int[], Long> data) {
		String s = "";

		ArrayList<int[]> list = new ArrayList<int[]>();
		for(int[] set:data.keySet()) list.add(set);
		list = sort(list, 0);

		for(int[] k:list) {
			ArrayList<String> a = new ArrayList<String>();
			for(int i=0;i<variables.length;i++) {
				String r = variables[i];
				r += k[i]==1?"":"^"+k[i];

				if(k[i]==0) continue;
				a.add(r);
			}

			String t = ""; int c = a.size()-1;
			for(int i=0;i<a.size();i++) t += a.get(i) + (c-->0?"*":"");

			s += t!="" ?
				(data.get(k)!=0?data.get(k)>0? " + ":" - ":"") + (Math.abs(data.get(k))!=1?Math.abs(data.get(k)) + "*":"") + t:
				(data.get(k)!=0?data.get(k)>0? " + ":" - ":"") + Math.abs(data.get(k));
		}
		return s;
	}

	private ArrayList<int[]> sort(ArrayList<int[]> list, int k) {
		if(k>=variables.length) return list;

		ArrayList<int[]> tmp = new ArrayList<int[]>();

		int max = 0;
		for(int[] l:list) max=l[k]>max?l[k]:max;

		int i=0;
		for(int n=max;i<list.size();n--) {
			ArrayList<int[]> tmp_ = new ArrayList<int[]>();
			for(int[] l:list) if(l[k]==n) {tmp_.add(l); i++;}

			tmp_ = sort(tmp_, k+1);
			for(int[] l:tmp_) tmp.add(l);
		}

		return tmp;
	}

	private boolean equals(int[] a, int[] b) {
		boolean f = true;
		for (int i=0;i<variables.length;i++) if (a[i]!=b[i]) f = false;
		return f;
	}
}
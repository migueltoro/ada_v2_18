package us.lsi.pli.examples;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;



public class ReinasPLI {

		public static Integer numeroDeReinas = 10;
		
		public static String getConstraints(){
			String r = "min: ;\n\n";
			Integer n = numeroDeReinas;
			boolean first = true;
			
			for (int i = 0; i < n; i++) {
				first = true;
				for (int j = 0; j < n; j++) {
					if (first) first = false; else r = r + "+";
					r = r + String.format("x_%d_%d", i, j);
				}
				r = r +"=";
				r = r +1;
				r = r+";\n";
			}
			
			r = r+"\n\n";
			
			for (int i = 0; i < n; i++) {
				first = true;
				for (int j = 0; j < n; j++) {
					if (first) first = false; else r = r + "+";
					r = r + String.format("x_%d_%d", j, i);
				}
				r = r +"=";
				r = r +1;
				r = r+";\n";
			}
			
			r = r+"\n\n";
			int m;
			for (int d = -n+1; d < n; d++) {
				first = true;
				m=0;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {					
						if (d==j-i) {
							if (first) first = false; else r = r + "+";
							r = r + String.format("x_%d_%d",i, j);	
							m++;
						}
					}				
				}
				if (m>0) {
					r = r + "<=";
					r = r + 1;
					r = r + ";\n";
				}
			}
			
			r = r+"\n\n";
			
			for (int d = 0; d < 2*n-2; d++) {
				first = true;
				m=0;
				for (int i = 0; i < n; i++) {
					for (int j = 0; j < n; j++) {					
						if (d==j+i) {
							if (first) first = false; else r = r + "+";
							r = r + String.format("x_%d_%d",i, j);
							m++;
						}
					}				
				}
				if (m>0) {
					r = r + "<=";
					r = r + 1;
					r = r + ";\n";
				}
			}
			
			r = r+"\n\n";
			
			r = r + "bin ";
			
			first = true;
			
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					if (first) first = false; else r = r + ",";
					r = r + String.format("x_%d_%d",i, j);
				}
				
			}
			
			r = r+";\n\n";
			
			return r;
		}
	
	public static String getConstraints2() {
		String r = "min: ;\n\n";
		Integer n = numeroDeReinas;

		r += IntStream.range(0, n).boxed().map(j -> sum_i(j, n)).collect(Collectors.joining("", "", ""));

		r += IntStream.range(0, n).boxed().map(i -> sum_j(i, n)).collect(Collectors.joining("", "", ""));

		r += IntStream.range(-n + 1, n).boxed().map(d -> sum_f1(d, n)).collect(Collectors.joining("", "", ""));

		r += IntStream.range(0, 2 * n - 1).boxed().map(d -> sum_f2(d, n)).collect(Collectors.joining("", "", ""));

		r += allPairs(n, n).map(p -> String.format("x_%d_%d", p.i, p.j))
				.collect(Collectors.joining(",", "bin ", "; \n"));

		return r;
	}
		
	
	public static void main(String[] args) {
//			System.out.println(getConstraints());
			SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints2());
			System.out.println("-------------------");	
			System.out.println("________");
			System.out.println(s.getGoal());
			System.out.println("________");
			System.out.println("________");
			for(int i=0;i<s.getNumVar();i++){
				if(s.getSolution(i) == 1.0)
					System.out.println(s.getName(i));
			}

	}
	
	static class Pair {
		Integer i;
		Integer j;
		public static  Pair of(Integer i, Integer j){
			return new Pair(i,j);
		}				
		public Pair(Integer i, Integer j) {
			super();
			this.i = i;
			this.j = j;
		}
		
	} 
	
	static String sum_i(int j, int n) {
		return IntStream.range(0,n).boxed().map(i->String.format("x_%d_%d",i,j))
				   .collect(Collectors.joining("+",""," = 1; \n"));
	}
	
	static String sum_j(int i, int n) {
		return IntStream.range(0,n).boxed().map(j->String.format("x_%d_%d",i,j))
				   .collect(Collectors.joining("+",""," = 1; \n"));
	}
	
	static String sum_f1(int d, int n) {
		return allPairs(n, n).filter(p -> p.j - p.i == d).map(p -> String.format("x_%d_%d", p.i, p.j))
				.collect(Collectors.joining("+", "", " <= 1; \n"));
	}
	
	static String sum_f2(int d, int n) {
		return allPairs(n, n).filter(p -> p.j + p.i == d).map(p -> String.format("x_%d_%d", p.i, p.j))
				.collect(Collectors.joining("+", "", " <= 1; \n"));
	}
	
	static Stream<Pair> allPairs(Integer n, Integer m){
		return IntStream.range(0,n).boxed().flatMap(i->IntStream.range(0,m).mapToObj(j->Pair.of(i, j)));
	}

}


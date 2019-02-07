package us.lsi.pl;

import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;

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

		
	
	public static void main(String[] args) {
//			System.out.println(getConstraints());
			SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints());
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

}


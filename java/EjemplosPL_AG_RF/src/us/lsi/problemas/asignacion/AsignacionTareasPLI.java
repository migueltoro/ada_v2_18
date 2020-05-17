package us.lsi.problemas.asignacion;


import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;
import static us.lsi.pli.AuxiliaryPLI.*;


public class AsignacionTareasPLI {

	public static AsignaciondeTareasRF a;
	public static Integer n;

	public static String getConstraints() {

		String r = "min: ";

		boolean first = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				// r = r + String.format("%.2f * x_%d_%d", a.getCoste(i,j),i, j).replaceAll(",",
				// ".");
				r = r + String.format("%s * x_%d_%d", String.valueOf(a.getCoste(i, j)), i, j);
			}
		}

		r = r + "\n\n";

		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", i, j);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}

		r = r + "\n\n";

		for (int i = 0; i < n; i++) {
			first = true;
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + "+";
				r = r + String.format("x_%d_%d", j, i);
			}
			r = r + "=";
			r = r + 1;
			r = r + ";\n";
		}

		r = r + "\n\n";

		r = r + "bin ";

		first = true;

		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (first)
					first = false;
				else
					r = r + ",";
				r = r + String.format("x_%d_%d", i, j);
			}

		}

		r = r + ";\n\n";

		return r;
	}

	public static String getConstraints2() {

		String r = "";
		// r += allPairs(n, n).map(p -> String.format("%.2f *
		// x_%d_%d",a.getCoste(p.i,p.j), p.i, p.j).replaceAll(",", "."))
		r+= "min: "+sum_2(n,n,"x",(i,j)->a.getCoste(i,j).toString())+"; \n\n";

		r += IntStream.range(0, n).boxed().map(j -> constraintEq(sum_2_1(n,j,"x"),"1")).collect(Collectors.joining("", "", "\n"));

		r += IntStream.range(0, n).boxed().map(i -> constraintEq(sum_2_2(n,i,"x"),"1")).collect(Collectors.joining("", "", "\n"));

		r += varBin(n,n,"x");

		return r;
	}
	
	public static String getConstraints3() {	
		StringBuilder r = new StringBuilder();
		r.append(goalMin(sum_2(n,n,"x",(i,j)->a.getCoste(i,j).toString())));
		r.append(forAll_1(n,i->constraintEq(sum_2_2(n,i,"x")," 1")));
		r.append(forAll_1(n,j->constraintEq(sum_2_1(n,j,"x")," 1")));
		r.append(varBin(n,n,"x"));
		return r.toString();		
	}

	public static void main(String[] args) {
//			System.out.println(getConstraints());

		a = AsignaciondeTareasRF.create("ficheros/asignacionDeTareas.txt");
		n = a.getN();
//		System.out.println(getConstraints());
//		System.out.println("________");
//		System.out.println(getConstraints3());
//		System.out.println("________");

		SolutionPLI s = AlgoritmoPLI.getSolution(getConstraints3());
		System.out.println("-------------------");
		System.out.println("________");
		System.out.println(s.getGoal());
		System.out.println("________");
		System.out.println("________");
		for (int i = 0; i < s.getNumVar(); i++) {
			if (s.getSolution(i) == 1.0)
				System.out.println(s.getName(i));
		}

	}

}

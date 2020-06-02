package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;

public class EstanteriaGurobiPLI {

	public static class Libro {
		
		public static Libro of(String linea) {
			String[] partes = linea.split(",");
			return new Libro(Double.parseDouble(partes[0]),Double.parseDouble(partes[1]));
		}
		public static Libro of(Double altura, Double anchura) {
			return new Libro(altura,anchura);
		}
		public static List<Libro> libros;
		public final Double altura;
		public final Double anchura;
		public Libro(Double altura, Double anchura) {
			super();
			this.altura = altura;
			this.anchura = anchura;
		}
		public static String altura(Integer i) {
			return Libro.libros.get(i).altura.toString();
		}
		public static String anchura(Integer i) {
			return Libro.libros.get(i).anchura.toString();
		}
		@Override
		public String toString() {
			return String.format("(%.2f,%.2f)",altura,anchura);
		}		
	}
	
	public static class Estante {
		
		public static Estante of(String linea) {
			return new Estante(Double.parseDouble(linea));
		}
		public static Estante of(Double altura) {
			return new Estante(altura);
		}	
		public static List<Estante> estantes;
		public final Double altura;
		public static Double anchura;
		public Estante(Double altura) {
			super();
			this.altura = altura;
		}
		public static String altura(Integer i) {
			return Estante.estantes.get(i).altura.toString();
		}
		public static String anchura() {
			return Estante.anchura.toString();
		}
		@Override
		public String toString() {
			return String.format("(%.2f)",altura);
		}	
	}
	
	public static void datos(String fichero) {
		List<String> lineas = Files2.linesFromFile(fichero);
		Integer p1 = lineas.indexOf("#");
		Integer p2 = p1+1+lineas.subList(p1+1,lineas.size()).indexOf("#");
		Libro.libros = lineas.subList(0,p1).stream().map(linea->Libro.of(linea)).collect(Collectors.toList());
		Estante.estantes = lineas.subList(p1+1,p2).stream().map(linea->Estante.of(linea)).collect(Collectors.toList());
		Estante.anchura = Double.parseDouble(lineas.get(p2+1));
	}
	
	public static String getConstraints() {
		Integer n = Libro.libros.size();
		Integer m = Estante.estantes.size();
		StringBuilder r = new StringBuilder();
		r.append(max);
		r.append(sum_2_v(n,m,"x"));
		r.append(constraintsSection);
		r.append(forAll_2(n,m,"a",(i,j)->constraintLe(
							String.format("%s %s",Libro.altura(i),var_2("x",i,j)),
							Estante.altura(j))));
		r.append(forAll_1(m,"b",j->constraintLe(
							sum_2_1_f(n,j,"x",(i,x)->Libro.anchura(i)),
							Estante.anchura())));
		r.append(forAll_1(n,"c",i->constraintEq(
							sum_2_2_v(m+1,i,"x"),
							1)));
		r.append(binaryVars);
		r.append(vars_2(n,m+1,"x"));
		r.append(lastEnd);
		return r.toString();
	}
	
	public static void main(String[] args) {
		datos("ficheros/estanteria.txt");
		System.out.println(Libro.libros);
		System.out.println(Estante.estantes);
		System.out.println(Estante.anchura);
		String ct = EstanteriaGurobiPLI.getConstraints();
		Files2.toFile(ct,"ficheros/estanteria_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/estanteria_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}

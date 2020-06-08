package us.lsi.pli.gurobi;

import static us.lsi.pli.AuxiliaryPLI.*;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.gurobi.GurobiLp;
import us.lsi.gurobi.GurobiSolution;
import us.lsi.pli.AuxiliaryPLI;

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
		public static Double altura(Integer i) {
			return Libro.libros.get(i).altura;
		}
		public static Double anchura(Integer i) {
			return Libro.libros.get(i).anchura;
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
		public static Double altura(Integer i) {
			return Estante.estantes.get(i).altura;
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
	
	public static String getConstraints(PLIType type) {
		AuxiliaryPLI.setType(type);
		int n = Libro.libros.size();
		int m = Estante.estantes.size();
		StringBuilder r = new StringBuilder();
		r.append(goalMaxSection(sum(listOf(0,n,0,m,(i,j)->v("x",i,j)))));
		r.append(constraintsSection());
		r.append(forAll("a",listOf(0,n,0,m,(i,j)->constraintLe(f(Libro.altura(i),"x",i,j),Estante.altura(j)))));
		r.append(forAll("b",listOf(0,m,j->constraintLe(sum(listOf(0,n,i->f(Libro.anchura(i),"x",i,j))),Estante.anchura))));
		r.append(forAll("c",listOf(0,n,i->constraintEq(sum(listOf(0,m+1,j->v("x",i,j))),1))));
		r.append(binaryVarsSection(listOf(0,n,0,m+1,(i,j)->v("x",i,j))));
		r.append(endSection());
		return r.toString();
	}
	
	public static void main(String[] args) {
		datos("ficheros/estanteria.txt");
		System.out.println(Libro.libros);
		System.out.println(Estante.estantes);
		System.out.println(Estante.anchura);
		String ct = EstanteriaGurobiPLI.getConstraints(PLIType.Gurobi);
		Files2.toFile(ct,"ficheros/estanteria_sal.lp");
		Locale.setDefault(new Locale("en", "US"));
		GurobiSolution solution = GurobiLp.gurobi("ficheros/estanteria_sal.lp");
		System.out.println(solution.toString((s,d)->d>0.));
	}

}

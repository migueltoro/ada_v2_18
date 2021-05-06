package us.lsi.alg.productos;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Predicate;

import us.lsi.alg.multiconjuntos.DatosMulticonjunto;

public class ProductosHeuristic {

	public static Double heuristic(ProductosVertex v1, Predicate<ProductosVertex> goal, ProductosVertex v2) {
		return heuristic(v1, DatosProductos.NUM_PRODUCTOS);
	}

	public static Double heuristic(ProductosVertex vertice, int lastIndex) {
		return 0.;
	}
	
	public static Double entero(ProductosVertex vertice, Integer lastIndex) {
		ProductosVertex v = vertice;
		Double precioTotal = 0.;
		Set<Producto> productos = new HashSet<>();
		List<String> funciones = DatosProductos.getFunciones();
		while(!v.actions().isEmpty() &&  v.indice < lastIndex) {
			Integer a = v.actionInteger();
			Producto p = DatosProductos.getProducto(v.indice);
			if (a >0) {
				productos.add(p);
				funciones.removeAll(p.funciones());
				precioTotal += p.precio();
			}
			v = v.neighbor(a);
		}
//		System.out.println("Funciones restantes = "+funciones);
		return precioTotal;
	}
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 4; id_fichero++) {

			DatosProductos.iniDatos("ficheros/productos" + id_fichero + ".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
			System.out.println("#### Algoritmo A* ####");

			ProductosVertex start = ProductosVertex.createInitialVertex();
//			Predicate<ProductosVertex> finalVertex = v -> ProductosVertex.goal(v);
			
			System.out.println(ProductosHeuristic.entero(start,DatosProductos.NUM_PRODUCTOS));
		}
	}

}


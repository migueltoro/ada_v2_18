package us.lsi.alg.productos;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


import us.lsi.common.Files2;
import us.lsi.common.String2;

public class DatosProductos {
	public static int NUM_PRODUCTOS, NUM_FUNCIONES;
	private static List<String> funciones;
	private static List<Producto> productos;
	private static Map<String, Integer> map_fObjetivo;
	
	public static void iniDatos(String fichero) {
		List<String> ls = Files2.linesFromFile(fichero);
		
		funciones = Arrays.stream(ls.remove(0).split(":")[1].split(","))
		.map(s->s.trim()).collect(Collectors.toList());
		
		productos = ls.stream().map(s->Producto.create(s))
				.sorted(Comparator.naturalOrder()).collect(Collectors.toList());
		
		NUM_PRODUCTOS = productos.size();
		NUM_FUNCIONES = funciones.size();	
		
		map_fObjetivo = new HashMap<>();
		Integer id = 0;
		for(String f: funciones) {
			map_fObjetivo.put(f, id++);
		}	
	}
	
	public static List<Producto> getProductos() {
		return productos;
	}

	public static Producto getProducto(int index) {
		return productos.get(index);
	}	
	
	public static List<String> getFunciones() {
		return funciones;
	}
	
	public static Set<String> getFuncionesProducto(int index) {
		return productos.get(index).funciones();
	}

	public static Map<String, Integer> getFObj() {
		return map_fObjetivo;
	}		
	
	private static String mem;
	public static void toConsole() {
		String2.toConsole(funciones.stream().map(e->e.toString()).collect(Collectors.joining(",")));
		mem = "Nº de productos: "+NUM_PRODUCTOS;
		productos.forEach(a->mem += ("\n"+a.toString()));
		String2.toConsole(mem);
	}	
	
	// Test de la lectura del fichero
	public static void main(String[] args) {
		iniDatos("ficheros/productos2.txt");
		toConsole();
	}	
}


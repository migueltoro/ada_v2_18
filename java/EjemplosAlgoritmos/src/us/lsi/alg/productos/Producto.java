package us.lsi.alg.productos;

import java.util.Set;

import us.lsi.common.Set2;


public record Producto(String nombre,Double precio,Set<String> funciones) implements Comparable<Producto>{

	public static Producto create(String s) {
		String[] partes = s.split(":");
		String[] tokens = partes[0].split("[()]");
		String nombre = tokens[0].trim();
		Double precio = Double.parseDouble(tokens[1].split(" ")[0].trim());
		Set<String> funciones = Set2.parseSet(partes[1].trim().split(","),tk->tk.trim());
		return new Producto(nombre,precio,funciones);				
	}
	
	@Override
	public String toString() {		
		return String.format("%s (%5s euros) => %s", nombre, precio,funciones);
	}
	
	public Double precioPorFuncionalidad() {
		return this.precio()/this.funciones.size();
	}

	@Override
	public int compareTo(Producto p) {
		return this.precioPorFuncionalidad().compareTo(p.precioPorFuncionalidad());
	}
}


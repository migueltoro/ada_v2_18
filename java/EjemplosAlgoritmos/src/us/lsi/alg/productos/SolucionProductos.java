package us.lsi.alg.productos;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record SolucionProductos(Double precioTotal,Set<Producto> productos, Set<String> funciones) implements Comparable<SolucionProductos>{
	
	public static SolucionProductos create(List<Integer> ls) {
		List<Integer> lsC = new ArrayList<>(ls);
		Double precioTotal = 0.;
		Set<Producto> productos = new HashSet<>();
		Set<String> funciones = new HashSet<>();
		for(int i=0; i<lsC.size(); i++) {
			if(ls.get(i)==1) {
				Producto p = DatosProductos.getProducto(i);
				productos.add(p);
				funciones.addAll(p.funciones());
				precioTotal += p.precio();
			}			
		}
		return new SolucionProductos(precioTotal,productos,funciones);
	}


	@Override
	public String toString() {
		return String.format("Funcionalidades a cubrir: %s\nComposicion del lote seleccionado: %s"
				+ "\nFuncionalidades del lote seleccionado:\n\t%s\nPrecio total del lote seleccionado: %.2f euros", 			
				DatosProductos.getFunciones(),
				productos.stream().map(e->e.toString()).collect(Collectors.joining("\n\t")),
		        funciones,
		        precioTotal);
	}

	@Override
	public int compareTo(SolucionProductos other) {
		return this.precioTotal.compareTo(other.precioTotal);
	}	

}


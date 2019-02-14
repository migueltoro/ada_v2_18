package us.lsi.bt.tareasyprocesadores;

import us.lsi.bt.AlgoritmoBT;
import us.lsi.tareasyprocesadores.datos.Tarea;


public class Test {

	
	
	public static void main(String[] args) {
		EstadoTareasProcesadoresBT p = EstadoTareasProcesadoresBT.create("ficheros/tareas.txt",3);
		System.out.println(Tarea.tareas);
		AlgoritmoBT.metricasOK = true;
		AlgoritmoBT.conFiltro = true;
		var a = AlgoritmoBT.create(p);
		a.ejecuta();
		System.out.println("Solucion Final");		
		System.out.println(a.getSolucion());
	}

}

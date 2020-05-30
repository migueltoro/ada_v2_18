package us.lsi.bt.jarras;



import us.lsi.astar.jarras.ActionJarras;
import us.lsi.bt.AlgoritmoBT;
import us.lsi.jarras.datos.DatosJarras;



public class Test {

	

	public static void main(String[] args) {
		
		DatosJarras.iniDatos("ficheros/acciones.txt");
		DatosJarras.getAcciones().forEach(x->System.out.println(x));
		DatosJarras.capacidadJarra1 = 4;
		DatosJarras.capacidadJarra2 = 3;
		DatosJarras.cantidadFinalEnJarra1 = 2;
		DatosJarras.cantidadFinalEnJarra2 = 0;
		DatosJarras.cantidadInicialEnJarra1 = 0;
		DatosJarras.cantidadInicialEnJarra2 = 0;
		DatosJarras.numMaxAcciones = 10;
		AlgoritmoBT.metricasOK = true;
		AlgoritmoBT.conFiltro = true;
		System.out.println("------");
		EstadoJarras e = EstadoJarras.create();
		AlgoritmoBT<SolucionJarras, ActionJarras, EstadoJarras> a = AlgoritmoBT.create(e);
		a.ejecuta();

		if (a.getSoluciones().isEmpty()) 
			System.out.println("Solución no encontrada");
		else{
			System.out.println("Solución: " + a.getSolucion());
		}
		System.out.println(AlgoritmoBT.metricas);
//		Tuple2<Integer,Integer> p = ActionJarras.ejecuta(Tuple.create(0, 0), a.getSolucion().getListaAcciones());
//		System.out.println("Estado Final = "+p);
	}

}

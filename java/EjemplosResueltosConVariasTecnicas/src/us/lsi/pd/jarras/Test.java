package us.lsi.pd.jarras;

import us.lsi.astar.jarras.ActionJarras;
import us.lsi.jarras.datos.DatosJarras;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.tiposrecursivos.Tree;

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
		AlgoritmoPD.metricasOK = true;
		AlgoritmoPD.metricasOK = true;
		System.out.println("------");
		ProblemaJarrasPD p = ProblemaJarrasPD.create();
		AlgoritmoPD.conFiltro = true;
		var a = AlgoritmoPD.createPDR(p);
		a.ejecuta();
			
		System.out.println(a.getSolucion());
		System.out.println(AlgoritmoPD.metricas);
		Tree<ActionJarras> t = a.getAlternativasDeSolucion(p.asPD());
		t.toDOT("./ficheros/jarras.gv", "Jarras");
	}

}

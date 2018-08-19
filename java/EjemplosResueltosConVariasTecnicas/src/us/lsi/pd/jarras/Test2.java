package us.lsi.pd.jarras;


import us.lsi.jarras.datos.DatosJarras;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.AlgunosTestsPD;

public class Test2 {

	public static void main(String[] args) {
		DatosJarras.iniDatos("acciones.txt");
		DatosJarras.getAcciones().forEach(x->System.out.println(x));
		DatosJarras.capacidadJarra1 = 4;
		DatosJarras.capacidadJarra2 = 3;
		DatosJarras.cantidadFinalEnJarra1 = 2;
		DatosJarras.cantidadFinalEnJarra2 = 0;
		DatosJarras.cantidadInicialEnJarra1 = 0;
		DatosJarras.cantidadInicialEnJarra2 = 0;
		DatosJarras.numMaxAcciones = 10;
		AlgoritmoPD.metricasOK = true;
		System.out.println("------");
		ProblemaJarrasPD p = ProblemaJarrasPD.create();
		System.out.println(p);
		var t = AlgunosTestsPD.test2(p);
		System.out.println(t);
//		System.out.println(t.getLabel());
//		var p1 = p.getSubProblema(t.getLabel());
//		System.out.println(p1);
//		var s = AlgunosTestsPD.test1(p,t);
//		System.out.println(s);
		
	}

}

package us.lsi.bt.mochila;


import us.lsi.bt.AlgoritmoBT;
import us.lsi.mochila.datos.DatosMochila;

public class Test {

	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		System.out.println(DatosMochila.getObjetos());
		AlgoritmoBT.metricasOK = true;
		AlgoritmoBT.conFiltro = true;
		System.out.println("------");
		EstadoMochila e = EstadoMochila.createInitial();
		var a = AlgoritmoBT.create(e);
		a.ejecuta();
		System.out.println(a.getSolucion());
		System.out.println(AlgoritmoBT.metricas);
	}

}

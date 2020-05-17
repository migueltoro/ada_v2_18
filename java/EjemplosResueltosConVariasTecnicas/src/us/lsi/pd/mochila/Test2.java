package us.lsi.pd.mochila;


import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.pd.AlgoritmoPD;
import us.lsi.pd.ProblemaPDRAdapt;

public class Test2 {

	public static void main(String[] args) {
		
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;		
		AlgoritmoPD.isRandomize = false;
		AlgoritmoPD.conFiltro = true;
		AlgoritmoPD.metricasOK = true;
		System.out.println(DatosMochila.getObjetos());
		ProblemaMochilaPD  p = ProblemaMochilaPD.createInitial();	
		System.out.println("Problema Inicial ="+p);
		System.out.println("Cota Superior ="+DatosMochila.getCotaSuperior(0,78));	
		AlgoritmoPD<SolucionMochila, Integer, ProblemaPDRAdapt<SolucionMochila, Integer, ProblemaMochilaPD>> a = AlgoritmoPD.createPDR(p);
		a.ejecuta();
		System.out.println(AlgoritmoPD.metricas.toString());	
		a.showAllGraph("ficheros/pruebaMochilaSinFiltro.gv","Mochila");
		System.out.println("Solucion= "+a.getSolucion());
	}

}

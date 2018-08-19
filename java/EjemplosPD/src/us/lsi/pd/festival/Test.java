package us.lsi.pd.festival;

import us.lsi.pd.AlgoritmoPD;

public class Test {

	public static void main(String[] args) {
		AlgoritmoPD.isRandomize = false;
		ProblemaFestival.create("./ficheros/grupos.txt");
		ProblemaFestival.presupuestoTotal = 10000;
		System.out.println("------");
		for(String dia: ProblemaFestival.gruposPorDiaYHora.keySet()){
			for(String hora: ProblemaFestival.gruposPorDiaYHora.get(dia).keySet()){
				System.out.println("Grupos " + dia + " " + hora + ": "+ ProblemaFestival.gruposPorDiaYHora.get(dia).get(hora));
			}
		}
		System.out.println("------");
		System.out.println("Presupuesto total: " + ProblemaFestival.presupuestoTotal);
		System.out.println("------");
		AlgoritmoPD.metricasOK = true;
		var p = ProblemaFestivalPD.create();
		AlgoritmoPD.conFiltro = true;
		var a = AlgoritmoPD.createPDR(p);
		a.ejecuta();
		var sp = a.getSolucionParcial();
		if (sp != null){
			System.out.println("Votos: " + sp.propiedad);
			System.out.println("Solucion: " + a.getSolucion());
			a.showAllGraph("./ficheros/solucionFestival.gv", "SolucionFestival");
		}else{
			System.out.println("Solución no encontrada");
		}
		System.out.println(AlgoritmoPD.metricas);
	}
}


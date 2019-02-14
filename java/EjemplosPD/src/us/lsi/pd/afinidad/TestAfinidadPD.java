package us.lsi.pd.afinidad;


import us.lsi.pd.AlgoritmoPD;


public class TestAfinidadPD {
	public static void main(String[] args) {
		//configuraPD
				
		//creaProblema y lanza algoritmo
		ProblemaAfinidad.create("ficheros/afinidad_test1.txt");
		AlgoritmoPD.metricasOK = true;
		var p= ProblemaAfinidadPD.create();
		AlgoritmoPD.conFiltro = true;
		var a= AlgoritmoPD.createPDR(p);
		a.ejecuta();
		a.showAllGraph("ficheros/PDAfinidad_22.gv","Afinidad");
		//recuperasolución		
		System.out.println(a.getSolucion());
		System.out.println(a.getSolucionesParciales().size()+","+a.getNumeroDeProblemas());
		
	}
}


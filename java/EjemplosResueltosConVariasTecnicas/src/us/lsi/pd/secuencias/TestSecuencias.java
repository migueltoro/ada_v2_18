package us.lsi.pd.secuencias;



import us.lsi.pd.AlgoritmoPD;

public class TestSecuencias {

	

	public static void main(String[] args) {
		
		ProblemaSecuenciasPD p = ProblemaSecuenciasPD.create("cbrrrarreterb","carretera");
		AlgoritmoPD.conFiltro = true;
		var a = AlgoritmoPD.createPDR(p);
		a.ejecuta();
		System.out.println(a.getSolucion());

	}

}

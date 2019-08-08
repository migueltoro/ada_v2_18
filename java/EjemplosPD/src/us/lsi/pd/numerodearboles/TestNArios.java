package us.lsi.pd.numerodearboles;

import us.lsi.pd.AlgoritmoPD;

public class TestNArios {


	public static void main(String[] args) {
		NumeroDeArbolesNArios.nmh = 2;
		NumeroDeArbolesNArios p = NumeroDeArbolesNArios.create(4);
		var a = AlgoritmoPD.createPD(p);
		a.ejecuta();		
		a.showAllGraph("ficheros/NumeroDeArboles.gv","NumeroDeArboles");
		System.out.println("Solucion= "+a.getSolucionParcial().valorDeObjetivo);

	}

}

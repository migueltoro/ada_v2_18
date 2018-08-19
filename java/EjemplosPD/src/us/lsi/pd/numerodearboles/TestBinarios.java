package us.lsi.pd.numerodearboles;

import us.lsi.pd.AlgoritmoPD;

public class TestBinarios {

	public static void main(String[] args) {
		NumeroDeArbolesBinarios p = NumeroDeArbolesBinarios.create(5);
		var a = AlgoritmoPD.createPD(p);
		a.ejecuta();		
		a.showAllGraph("ficheros/NumeroDeArboles.gv","NumeroDeArboles");
		System.out.println("Solucion= "+a.getSolucionParcial().propiedad);
	}

}

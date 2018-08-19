package us.lsi.pd.reinas;


import us.lsi.pd.AlgoritmoPD;

public class TestReinasPD {

	public static void main(String[] args) {
		AlgoritmoPD.isRandomize = false;
		ReinasPDNumSoluciones.numeroDeReinas = 10;
		ReinasPDNumSoluciones  p = ReinasPDNumSoluciones.create();
		var a = AlgoritmoPD.createPDR(p);
		a.ejecuta();
		System.out.println("Solucion Parcial= "+a.getSolucionParcial().propiedad+"\n\n");
		
		
		/*		int i=0;
		for (List<Reina> ls:a.getSolucion()) {
			System.out.println("Solucion= " + i +","+ ls);
			i++;
			//		a.showAllGraph(ruta+"numArboles.gv","NumArboles");
		}
*/
	}

}

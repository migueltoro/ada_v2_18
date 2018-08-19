package us.lsi.astar.laberinto;

import us.lsi.astar.AlgoritmoAStar;
import us.lsi.common.Strings2;


public class TestLaberinto {

	
	public static void main(String[] args) {
		Casilla.iniDatos("ficheros/laberinto.txt", 8, 6);
		Casilla c1 = Casilla.create(0, 5);
		Casilla c2 = Casilla.create(7, 5);
		LaberintoCaminoMinimo g = new LaberintoCaminoMinimo(c1,c2);
		var d = AlgoritmoAStar.create(g, c1, c2);
		var p = d.getPath();
		System.out.println(p.getWeight());
//		System.out.println(p.getVertexList());
		System.out.println(Casilla.show(p));
		Strings2.toConsole(Casilla.casillas.entrySet(),"Casillas");
	}

}

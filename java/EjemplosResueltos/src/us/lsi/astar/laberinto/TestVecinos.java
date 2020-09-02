package us.lsi.astar.laberinto;

import us.lsi.common.IntPair;

public class TestVecinos {

	public static void main(String[] args) {
		Laberinto.leeDatos("ficheros/laberinto.txt", 6, 8);
		System.out.println(Laberinto.showLaberinto());
		Casilla c1 = Laberinto.get(IntPair.of(0,0));		
		Casilla c2 = Laberinto.get(IntPair.of(5,7));
		System.out.println(String.format("%s,%s",c1,c1.isValid()));
		System.out.println(c1.neighbor(Laberinto.actions.get(2)));
		System.out.println(c1.actions());
		System.out.println(c1.getNeighborListOf());
		System.out.println(String.format("%s,%s",c2,c2.isValid()));
		System.out.println(c2.actions());
		System.out.println(c2.getNeighborListOf());
	}

}

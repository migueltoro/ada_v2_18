package us.lsi.astar.laberinto;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.ActionSimpleEdge;

public class CasillaEdge extends ActionSimpleEdge<Casilla, IntPair> {

	public static CasillaEdge of(Casilla c1, Casilla c2, IntPair action) {
		return new CasillaEdge(c1, c2, action);
	}

	private CasillaEdge(Casilla c1, Casilla c2, IntPair action) {
		super(c1, c2, action);
	}

}

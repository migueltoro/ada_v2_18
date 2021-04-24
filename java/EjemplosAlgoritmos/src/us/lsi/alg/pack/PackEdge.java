package us.lsi.alg.pack;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class PackEdge extends ActionSimpleEdge<PackVertex,Integer> {

	public static PackEdge of(PackVertex c1, PackVertex c2, Integer action) {
		return new PackEdge(c1, c2, action);
	}

	protected PackEdge(PackVertex c1, PackVertex c2, Integer action) {
		super(c1, c2, action, 1.);
	}

}

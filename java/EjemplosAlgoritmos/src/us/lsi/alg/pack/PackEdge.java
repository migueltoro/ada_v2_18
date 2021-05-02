package us.lsi.alg.pack;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public record PackEdge(PackVertex source, PackVertex target, Integer action, Double weight) 
           implements ActionSimpleEdge<PackVertex,Integer> {

	public static PackEdge of(PackVertex c1, PackVertex c2, Integer action) {
		return new PackEdge(c1, c2, action, 1.);
	}

}

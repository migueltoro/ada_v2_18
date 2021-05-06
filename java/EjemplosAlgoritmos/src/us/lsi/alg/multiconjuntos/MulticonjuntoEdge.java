package us.lsi.alg.multiconjuntos;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public record MulticonjuntoEdge(MulticonjuntoVertex source,MulticonjuntoVertex target,Integer action, Double weight) 
           implements ActionSimpleEdge<MulticonjuntoVertex, Integer> {

	public static MulticonjuntoEdge of(MulticonjuntoVertex c1, MulticonjuntoVertex c2, Integer action) {
		MulticonjuntoEdge a = new MulticonjuntoEdge(c1, c2, action, action * 1.0);
		return a;
	}

}

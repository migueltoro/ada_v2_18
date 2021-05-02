package us.lsi.alg.jarras;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public record JarrasEdge(JarrasVertex source, JarrasVertex target, JarrasAction action, Double weight)
              implements ActionSimpleEdge<JarrasVertex,JarrasAction> {

	public static JarrasEdge of(JarrasVertex c1, JarrasVertex c2, JarrasAction action) {
		return new JarrasEdge(c1, c2, action, 1.);
	}

}

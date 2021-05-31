package us.lsi.alg.jarras;

import us.lsi.graphs.virtual.Action;
import us.lsi.graphs.virtual.ActionSimpleEdge;

public record JarrasEdge(JarrasVertex source, JarrasVertex target, Action<JarrasVertex> action, Double weight)
              implements ActionSimpleEdge<JarrasVertex,Action<JarrasVertex>> {

	public static JarrasEdge of(JarrasVertex c1, JarrasVertex c2, Action<JarrasVertex> action) {
		return new JarrasEdge(c1, c2, action, 1.);
	}

}

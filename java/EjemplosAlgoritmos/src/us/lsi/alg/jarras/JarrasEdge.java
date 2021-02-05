package us.lsi.alg.jarras;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class JarrasEdge extends ActionSimpleEdge<JarrasVertex,JarrasAction> {

	public static JarrasEdge of(JarrasVertex c1, JarrasVertex c2, JarrasAction action) {
		return new JarrasEdge(c1, c2, action);
	}

	private JarrasEdge(JarrasVertex c1, JarrasVertex c2, JarrasAction action) {
		super(c1, c2, action);
	}

	@Override
	public String toString() {
		return String.format("[%s,%s]",super.getSource(),action);
	}

}

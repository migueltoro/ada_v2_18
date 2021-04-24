package us.lsi.alg.colorgraphs;

import us.lsi.graphs.virtual.ActionSimpleEdge;

public class ColorEdge extends ActionSimpleEdge<ColorVertex,Integer> {

	public static ColorEdge of(ColorVertex c1, ColorVertex c2, Integer action) {
		return new ColorEdge(c1, c2, action);
	}

	private ColorEdge(ColorVertex c1, ColorVertex c2, Integer action) {
		super(c1, c2, action, 1.);
	}

}

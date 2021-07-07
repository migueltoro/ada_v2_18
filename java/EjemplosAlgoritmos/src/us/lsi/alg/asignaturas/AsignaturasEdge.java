package us.lsi.alg.asignaturas;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public record AsignaturasEdge(AsignaturasVertice source, AsignaturasVertice target, Integer action, Double weight) implements ActionSimpleEdge<AsignaturasVertice,Integer> {

	public static AsignaturasEdge of(AsignaturasVertice c1, AsignaturasVertice c2, Integer action) {
		return new AsignaturasEdge(c1, c2, action, 1.0);
	}
}


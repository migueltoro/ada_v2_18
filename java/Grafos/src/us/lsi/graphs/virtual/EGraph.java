package us.lsi.graphs.virtual;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.common.TriFunction;
import us.lsi.path.EGraphPath;
import us.lsi.path.EGraphPath.PathType;

public interface EGraph<V, E> extends Graph<V, E> {

	double getVertexPassWeight(V vertex, E edgeIn, E edgeOut);

	double getVertexWeight(V vertex);

	List<E> edgesListOf(V v);

	EGraphPath<V, E> initialPath();

	V oppositeVertex(E edge, V v);

	V startVertex();

	Predicate<V> goal();

	V endVertex();

	Predicate<V> constraint();

	PathType pathType();
	
	public default Double add(Double acumulateValue, V vertexActual, E nextEdge, E lastEdge) {
		return this.initialPath().add(acumulateValue, vertexActual, nextEdge, lastEdge);
	}

	public default Double boundedValue(Double acumulateValue, V vertexActual, E edge,
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		return this.initialPath().boundedValue(acumulateValue, vertexActual, edge, this.goal(), this.endVertex(),
				heuristic);
	}

	public default Double estimatedWeightToEnd(Double acumulateValue, V vertexActual,
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		return this.initialPath().estimatedWeightToEnd(acumulateValue, vertexActual, this.goal(), this.endVertex(),
				heuristic);
	}

	public default Double goalBaseSolution(V vertexActual) {
		if (pathType().equals(PathType.Sum))
			return 0.;
		else
			return getVertexWeight(vertexActual);
	}

	public default Double fromNeighbordSolution(Double weight, V vertexActual, E edge, E lastEdge) {
		if (pathType().equals(PathType.Sum))
			return initialPath().add(weight, vertexActual, edge, lastEdge);
		else
			return weight;
	}
}

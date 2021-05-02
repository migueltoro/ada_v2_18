package us.lsi.path;

import java.util.List;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.Preconditions;

public class GraphPaths {
	
	public static <V, E> GraphPath<V, E> of(Graph<V, E> graph, List<V> vertexList) {
		Preconditions.checkNotNull(graph);
		Preconditions.checkNotNull(vertexList);
		Integer n = vertexList.size();
		Double r = IntStream.range(0, n - 1)
				.mapToDouble(i -> {
						E e = graph.getEdge(vertexList.get(i), vertexList.get(i + 1));
						Preconditions.checkNotNull(e, String.format("No existe la arista %d, %d", i, i + 1));
						return graph.getEdgeWeight(e);})
				.sum();
		return new GraphWalk<V, E>(graph, vertexList, r);
	}

}

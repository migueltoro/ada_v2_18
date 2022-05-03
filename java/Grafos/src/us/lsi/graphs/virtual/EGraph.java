package us.lsi.graphs.virtual;

import java.util.List;
import java.util.function.Predicate;

import org.jgrapht.Graph;

import us.lsi.path.EGraphPath;
import us.lsi.path.EGraphPath.PathType;

public interface EGraph<V, E> extends Graph<V,E> {
	
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
	
	public default Double goalBaseSolution(V vertexActual) {
		if(pathType().equals(PathType.Sum)) return 0.;
		else return getVertexWeight(vertexActual);
	}

	public default Double fromNeighbordSolution(Double weight, V vertexActual, E edge, E lastEdge) {
		if(pathType().equals(PathType.Sum)) return initialPath().add(weight,vertexActual,edge,lastEdge);
		else return weight;
	}
}

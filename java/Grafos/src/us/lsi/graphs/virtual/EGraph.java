package us.lsi.graphs.virtual;

import java.util.List;

import org.jgrapht.Graph;

import us.lsi.path.EGraphPath;
import us.lsi.path.EGraphPath.PathType;

public interface EGraph<V, E> extends Graph<V,E> {
	
	double getVertexPassWeight(V vertex, E edgeIn, E edgeOut);
	
	double getVertexWeight(V vertex);
	
	List<E> edgesListOf(V v); 
	
	EGraphPath<V, E> initialPath();
	
	V startVertex();
	
	PathType pathType(); 
	

}

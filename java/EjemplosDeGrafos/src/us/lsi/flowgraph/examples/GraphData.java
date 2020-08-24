package us.lsi.flowgraph.examples;

import org.jgrapht.Graph;
import us.lsi.graphs.SimpleEdge;

public class GraphData {
	
	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static Integer n;
	public static Integer origin;
	public static Integer target;
	
	public static  Double edgeCost(Integer i, Integer j) {
		 var edge = graph.getEdge(i,j);
		 return graph.getEdgeWeight(edge);
	}
	
	public static Boolean containsEdge(Integer i, Integer j) {
		return graph.containsEdge(i,j);
	}
	
	public static Boolean containsVertex(Integer i) {
		return graph.containsVertex(i);
	}
	
	public static Integer getN() {
		return n;
	}
	
	public static Integer origin() {
		return origin;
	}
	
	public static Integer target() {
		return target;
	}


}

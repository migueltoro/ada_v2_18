package us.lsi.graphs;

import java.util.Map;

import org.jgrapht.Graph;


public class GraphData {
	
	public static Graph<Integer,SimpleEdge<Integer>> graph;
	public static Map<Integer,Double> vertexWeight;
	
	public static Boolean containsEdge(Integer i, Integer j) {
		return graph.containsEdge(i,j);
	}
	
	public static Double edgeWeight(Integer i, Integer j) {
		return graph.getEdge(i,j).getEdgeWeight();
	}
	
	public static Boolean containsVertex(Integer i) {
		return graph.containsVertex(i);
	}
	
	public static Double vertexWeight(Integer i) {
		return vertexWeight.get(i);
	}
	
	public static Integer getN() {
		return graph.vertexSet().size();
	}
	
	public static Integer getM() {
		return graph.edgeSet().size();
	}


}

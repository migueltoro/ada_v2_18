package us.lsi.flowgraph;


public class FlowData {
	
	public static FlowGraph graph;
	
	public static Double maxEdge(Integer i, Integer j) {	
		return  graph.maxEdge(i,j);
	}
	
	public static Double minEdge(Integer i, Integer j) {
		return  graph.minEdge(i,j);
	}
	
	public static  Double costEdge(Integer i, Integer j) {
		 return graph.costEdge(i,j);
	}
	
	public static Boolean containsEdge(Integer i, Integer j) {
		return graph.containsEdge(i,j);
	}
	
	public static Double maxVertex(Integer i) {
		return graph.maxVertex(i);
	}
	
	public static Double minVertex(Integer i) {
		return graph.minVertex(i);
	}
	
	public static Double costVertex(Integer i) {
		return graph.costVertex(i);
	}
	
	public static Boolean containsVertex(Integer i) {
		return graph.containsVertex(i);
	}
	
	public static Integer getN() {
		return graph.getN();
	}
	
	public static Boolean isSource(Integer i) {
		return graph.vertex(i).isSource();
	}
	
	public static Boolean isSink(Integer i) {
		return graph.vertex(i).isSink();
	}
	
	public static Boolean isIntermediate(Integer i) {
		return graph.vertex(i).isIntermediate();
	}

}

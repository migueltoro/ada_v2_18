package us.lsi.astar;

import java.util.*;
import java.util.function.Predicate;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.util.FibonacciHeap;
import org.jgrapht.util.FibonacciHeapNode;

import us.lsi.common.Metricas;
import us.lsi.common.Preconditions;
import us.lsi.common.TriFunction;
import us.lsi.graphs.virtual.EGraph;


/**
 * <p> Implementación del algoritmo A*. Adaptación de la clase AStarShortestPath en <p> <a href="http://jgrapht.org/javadoc/" target="_blank">JGrapht</a></p> 
 * 
 *
 * @param <V> the graph vertex type
 * @param <E> the graph edge type
 *
 * @author Miguel Toro
 */
public class AStarAlgorithm<V, E>  {
	
	public static Metricas metricas = new Metricas();
	public static Boolean metricasOK = false;
	
	/**
	 * Un algoritmo AStar para ir del vértice de inicio hasta el vértice destino
	 * 
	 * @param <V> Tipo del vértice
	 * @param <E> Tipo de la arista
	 * @param graph Grafo 
	 * @param startVertex Vértice origen
	 * @param endVertex Vértice destino
	 * @param heuristic La heuristica del algoritmo
	 * @return Algoritmo AStar
	 * 
	 */
	public static <V, E> AStarAlgorithm<V, E> of(EGraph<V, E> graph, V sourceVertex, Predicate<V> goal, V targetVertex,
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		return new AStarAlgorithm<V, E>(graph, sourceVertex, goal, targetVertex, heuristic);
	}
	
	public static <V, E> AStarAlgorithm<V, E> of(EGraph<V, E> graph, V sourceVertex, V targetVertex,
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		return new AStarAlgorithm<V, E>(graph, sourceVertex,null, targetVertex, heuristic);
	}
	
	public static <V, E> AStarAlgorithm<V, E> of(EGraph<V, E> graph, V sourceVertex, Predicate<V> goal,
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		return new AStarAlgorithm<V, E>(graph, sourceVertex, goal, null, heuristic);
	}

   
//	private final AStarGraph<V, E> graph;

    // List of open nodes
    protected FibonacciHeap<V> openList;
    protected Map<V, FibonacciHeapNode<V>> vertexToHeapNodeMap;

    // List of closed nodes
    protected Set<V> closedList;

    // Mapping of nodes to their g-scores (g(x)).
    protected Map<V, Double> gScoreMap;

    // Predecessor map: mapping of a node to an edge that leads to its
    // predecessor on its shortest path towards the targetVertex
    protected Map<V, E> cameFrom;

    // Reference to the admissible heuristic
    // protected AStarAdmissibleHeuristic<V> admissibleHeuristic;

    // Counter which keeps track of the number of expanded nodes
    protected int numberOfExpandedNodes;
    
    private Predicate<V> goal = null;
    private V sourceVertex;
    private V targetVertex;
    private GraphPath<V,E> graphPath = null;
    private EGraph<V, E> graph = null;
    private TriFunction<V,Predicate<V>,V,Double> heuristic = null;

    /**
     * Create a new instance of the A* shortest path algorithm.
     * 
     * @param sourceVertex Source Vertex
     * @param targetVertex Target Vertex
     * @param graph the input graph
     * @param heuristic La heurística desde el vértice actual al target
     */
    private AStarAlgorithm(EGraph<V, E> graph, V sourceVertex, 
    		Predicate<V> goal, V targetVertex, TriFunction<V,Predicate<V>,V,Double> heuristic) {
       
    	Preconditions.checkNotNull(graph,"Graph cannot be null!");
    	Preconditions.checkNotNull(graph.containsVertex(sourceVertex),"Source vertex not contained in the graph!");
    	Preconditions.checkNotNull(targetVertex == null || graph.containsVertex(targetVertex),
    			"Target vertex not contained in the graph!");	
    	Preconditions.checkNotNull(!(targetVertex == null && goal ==null), "Target vertex y goal null in the graph!");
        this.graph = graph;
        this.sourceVertex = sourceVertex;
        this.targetVertex = targetVertex;
        this.goal = null;
        this.heuristic = heuristic;
    }
     
    /**
     * Initializes the data structures
     *
     * @param admissibleHeuristic admissible heuristic
     */
//    private void initialize(AStarAdmissibleHeuristic<V> admissibleHeuristic)
    private void initialize()
    {
//        this.admissibleHeuristic = admissibleHeuristic;
        openList = new FibonacciHeap<>();
        vertexToHeapNodeMap = new HashMap<>();
        closedList = new HashSet<>();
        gScoreMap = new HashMap<>();
        cameFrom = new HashMap<>();
        numberOfExpandedNodes = 0;
    }

    /**
     * Calculates (and returns) the shortest path from the sourceVertex to the targetVertex.
     * @return The shortest path
     */
//   public GraphPath<V, E> getShortestPath(
//       V sourceVertex, V targetVertex, AStarAdmissibleHeuristic<V> admissibleHeuristic)
//   {
//        if (!graph.containsVertex(sourceVertex) || !graph.containsVertex(targetVertex)) {
//            throw new IllegalArgumentException(
//                "Source or target vertex not contained in the graph!");
//        }
//    public GraphPath<V, E> getShortestPath(V sourceVertex, V targetVertex){
//      this.initialize(admissibleHeuristic);
    
    public GraphPath<V, E> getShortestPath(){
    	this.initialize();
        gScoreMap.put(sourceVertex, graph.getVertexWeight(sourceVertex));
        FibonacciHeapNode<V> heapNode = new FibonacciHeapNode<>(sourceVertex);
        openList.insert(heapNode, graph.getVertexWeight(sourceVertex));
        vertexToHeapNodeMap.put(sourceVertex, heapNode);

        do {
            FibonacciHeapNode<V> currentNode = openList.removeMin();

			// Check whether we reached the target vertex
			if ((goal != null && goal.test(currentNode.getData()) || 
				 currentNode.getData().equals(targetVertex))
				) {
				// Build the path
				// return this.buildGraphPath(sourceVertex, targetVertex,
				// currentNode.getKey());
				E edgeBeforeCurrent = cameFrom.get(currentNode.getData());
				return this.buildGraphPath(sourceVertex, currentNode.getData(),		
						currentNode.getKey()+graph.getVertexPassWeight(currentNode.getData(), edgeBeforeCurrent, null));
			}

            // We haven't reached the target vertex yet; expand the node
            expandNode(currentNode, targetVertex);
            closedList.add(currentNode.getData());
        } while (!openList.isEmpty());

        // No path exists from sourceVertex to TargetVertex
        return null;
    }


	private void expandNode(FibonacciHeapNode<V> currentNode, V endVertex) {
		numberOfExpandedNodes++;

		Set<E> outgoingEdges = null;
		if (graph instanceof Graph) {
			outgoingEdges = graph.edgesOf(currentNode.getData());
		} else if (graph instanceof Graph) {
			outgoingEdges = graph.outgoingEdgesOf(currentNode.getData());
		}

		for (E edge : outgoingEdges) {
			V successor = Graphs.getOppositeVertex(graph, edge, currentNode.getData());
			if ((successor == currentNode.getData()) || closedList.contains(successor)) { // Ignore
																							// self-loops
																							// or
																							// nodes
																							// which
																							// have
																							// already
																							// been
																							// expanded
				continue;
			}

			E edgeBeforeCurrent = cameFrom.get(currentNode.getData());
			double gScore_current = gScoreMap.get(currentNode.getData());
			double tentativeGScore = gScore_current + graph.getEdgeWeight(edge) + graph.getVertexWeight(successor)
					+ graph.getVertexPassWeight(currentNode.getData(), edgeBeforeCurrent, edge);

			if (!vertexToHeapNodeMap.containsKey(successor) || (tentativeGScore < gScoreMap.get(successor))) {
				cameFrom.put(successor, edge);
				gScoreMap.put(successor, tentativeGScore);

				double fScore = tentativeGScore + heuristicWeight(successor,goal,endVertex,heuristic);
				if (!vertexToHeapNodeMap.containsKey(successor)) {
					FibonacciHeapNode<V> heapNode = new FibonacciHeapNode<>(successor);
					openList.insert(heapNode, fScore);
					vertexToHeapNodeMap.put(successor, heapNode);
				} else {
					openList.decreaseKey(vertexToHeapNodeMap.get(successor), fScore);
				}
			}
		}
	}

    /**
     * Builds the graph path
     *
     * @param startVertex starting vertex of the path
     * @param targetVertex ending vertex of the path
     * @param pathLength length of the path
     *
     * @return the shortest path from startVertex to endVertex
     */
    private GraphPath<V, E> buildGraphPath(V startVertex, V targetVertex, double pathLength)
    {
        List<E> edgeList = new ArrayList<>();
        List<V> vertexList = new ArrayList<>();
        vertexList.add(targetVertex);

        V v = targetVertex;
      while (!v.equals(startVertex)) {  //cambiado por mí
//    while (v != startVertex) {
            edgeList.add(cameFrom.get(v));
            v = Graphs.getOppositeVertex(graph, cameFrom.get(v), v);
            vertexList.add(v);
        }
        Collections.reverse(edgeList);
        Collections.reverse(vertexList);
        return new GraphWalk<>(graph, startVertex, targetVertex,edgeList, pathLength);
    }

    /**
     * Returns how many nodes have been expanded in the A* search procedure in its last invocation.
     * A node is expanded if it is removed from the open list.
     *
     * @return number of expanded nodes
     */
    public int getNumberOfExpandedNodes()
    {
        return numberOfExpandedNodes;
    }
    
    
    public GraphPath<V, E> getPath(){
    	AStarAlgorithm.metricas.setTiempoDeEjecucionInicial();
    	if(graphPath==null)
    		graphPath = this.getShortestPath();
    	AStarAlgorithm.metricas.setTiempoDeEjecucionFinal();
    	return graphPath;
    }
    
    
	public List<V> getPathVertexList() {
		return getPath().getVertexList();
	}
	
	public List<E> getPathEdgeList() {
		return getPath().getEdgeList();
	}
	
	/**
	 * @param actual El vértice actual
	 * @param endVertex El vértice destino. Este vértice puede ser null. 
	 * @param goal 
	 * @param heuristic
	 * @param predicateHeuristic
	 * @return Una cota inferior del peso del camino desde el vértice actual al destino, 
	 * o desde el vértice actual al conjunto de vértices descrito por un predicado objetivo que se especificará en el AStarAlgorithm.
	 * Debe cumplirse la distancia es cero si el vértice actual cumple el predicado objetivo
	 */
	private double heuristicWeight(V actual, Predicate<V> goal, V endVertex, 
			TriFunction<V, Predicate<V>, V, Double> heuristic) {
		Double r = 0.;
		if(heuristic != null) r = heuristic.apply(actual,goal,endVertex); 
		return r;
	}
}

// End AStarShortestPath.java

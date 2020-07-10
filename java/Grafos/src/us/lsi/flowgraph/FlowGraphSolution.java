package us.lsi.flowgraph;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Style;
import us.lsi.common.Maps2;
import us.lsi.graphs.Graphs2;
import us.lsi.lpsolve.SolutionLpSolve;

public class FlowGraphSolution {
	
	public static FlowGraphSolution createOnlySaturated(FlowGraphSolution fs) {
		FlowGraph graph = Graphs2.subGraph(fs.getGraph(), 
				null, 
				e->fs.getSaturatedEdges().contains(e), 
				()->FlowGraph.create());
		Map<FlowVertex, Double> flowVertices = fs.getFlowVertices();
		Map<FlowEdge, Double> flowEdges = Maps2.newHashMap(fs.getFlowEdges());
		Double goal = fs.getGoal();
		return FlowGraphSolution.create(graph,flowVertices,flowEdges,goal);	
	}
	
	public static FlowGraphSolution create(FlowGraph graph,SolutionLpSolve s) {
		Map<FlowVertex, Double> flowVertices = Maps2.newHashMap();
		Map<FlowEdge, Double> flowEdges = Maps2.newHashMap(); 
		for(int i=0;i<s.getNumVar();i++) {
			String name = s.getName(i);
			if(name.charAt(0) == 'v') {
				flowVertices.put(FlowVertex.get(name), s.getSolution(i));				
			}
			if(name.charAt(0) == 'e') {
				flowEdges.put(FlowEdge.get(name), s.getSolution(i));
			}
		}
		Double goal = s.getGoal();
		return new FlowGraphSolution(graph, flowVertices, flowEdges, goal);
	}
	
	public static FlowGraphSolution create(FlowGraph graph, 
			Map<FlowVertex, Double> flowVertices,
			Map<FlowEdge, Double> flowEdges, 
			Double goal) {
		return new FlowGraphSolution(graph, flowVertices, flowEdges, goal);
	}
	
	private FlowGraphSolution(FlowGraph graph, Map<FlowVertex, Double> flowVertices, 
							Map<FlowEdge, Double> flowEdges, Double goal) {
		super();
		this.graph = graph;
		this.flowVertices = flowVertices;
		this.flowEdges = flowEdges;
		this.goal = goal;
		this.saturatedEdges = graph
				.edgeSet()
				.stream()
				.filter(e->e.getMax().equals(flowEdges.get(e)))
				.collect(Collectors.toSet());
		this.saturatedVertices = graph
				.vertexSet()
				.stream()
				.filter(v->v.getMax().equals(flowVertices.get(v)))
				.collect(Collectors.toSet());
	}
	private FlowGraph graph;
	private Map<FlowVertex,Double> flowVertices;
	private Map<FlowEdge,Double> flowEdges;
	private Double goal;
	private Set<FlowEdge> saturatedEdges;
	private Set<FlowVertex> saturatedVertices;
	
	public Map<FlowVertex, Double> getFlowVertices() {
		return flowVertices;
	}
	public Map<FlowEdge, Double> getFlowEdges() {
		return flowEdges;
	}
	public Double getGoal() {
		return goal;
	}
	public FlowGraph getGraph() {
		return graph;
	}
	
	public Set<FlowEdge> getSaturatedEdges() {
		return saturatedEdges;
	}

	public Set<FlowVertex> getSaturatedVertices() {
		return saturatedVertices;
	}

	public static void exportToDot(FlowGraph graph, FlowGraphSolution solution, String file) {
		Graphs2.<FlowVertex, FlowEdge>toDot(graph, file, 
				v->v.getName()+"="+solution.getFlowVertices().get(v).toString(),
	    		e->e.getName()+"="+solution.getFlowEdges().get(e).toString(),
		        v->GraphColors.getColorIf(Color.blue,solution.getSaturatedVertices().contains(v)),
		        e->GraphColors.getStyleIf(Style.bold,solution.getSaturatedEdges().contains(e)));
	}
}

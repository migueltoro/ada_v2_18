package us.lsi.alg.floyd;


import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.graph.GraphWalk;

import us.lsi.common.Lists2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.alg.DynamicProgramming.Sp;
import us.lsi.hypergraphs.GraphTree;
import us.lsi.hypergraphs.VirtualHyperVertex;
import us.lsi.path.EGraphPath;


public class FloydVertex extends VirtualHyperVertex<FloydVertex,FloydEdge,FloydVertex.ActionFloyd>{
	
	public static enum ActionFloyd{Yes, No};	
	
	public static FloydVertex of(Graph<Integer,SimpleEdge<Integer>> graph, Integer origen, Integer destino) {
		FloydVertex.n = graph.vertexSet().size();
		return new FloydVertex(graph,origen,destino);
	}
	
	public Integer i;
	public Integer j;
	public Integer k;
	public Graph<Integer,SimpleEdge<Integer>> graph;
	private static Integer n;
	
	public FloydVertex(Graph<Integer,SimpleEdge<Integer>> graph,Integer origen, Integer destino) {
		super();
		this.graph = graph;
		this.i = origen;
		this.j = destino;
		this.k = 0;
	}
	
	public FloydVertex(Integer i, Integer j, Integer k, Graph<Integer,SimpleEdge<Integer>> graph) {
		super();
		this.i = i;
		this.j = j;
		this.k = k;
		this.graph = graph;
	}
	
	public FloydVertex neigbord(Integer i, Integer j, Integer k){
		return new FloydVertex(i,j,k,this.graph);
	}

	@Override
	public String toString() {
		return "(" + i + "," + j + "," + k + ")";
	}
	@Override
	public Boolean isValid() {
		return true;
	}
	@Override
	public List<ActionFloyd> actions() {
		if(this.isBaseCase()) return List.of();
		return List.of(ActionFloyd.No,ActionFloyd.Yes);
	}
	
	@Override
	public List<FloydVertex> neighbors(ActionFloyd a) {
		List<FloydVertex> r=null;
		switch(a){
		case No : r = List.of(this.neigbord(i,j,k+1)); break;
		case Yes : r = List.of(this.neigbord(i, k, k+1),this.neigbord(k, j, k+1)); break;
		}
		return r;
	}
	
	@Override
	public FloydEdge edge(ActionFloyd a) {
		return FloydEdge.of(this,this.neighbors(a), a);
	}

	
	@Override
	public Boolean isBaseCase() {
		return this.graph.containsEdge(this.i,this.j)  ||  k == n;
	}
	
	@Override
	public Double baseCaseSolution() {
		Double r = null;
		if(this.graph.containsEdge(this.i, this.j)){
			SimpleEdge<Integer> e = this.graph.getEdge(i, j);
			Double w = this.graph.getEdgeWeight(e);
			r = w;
		}
		return r;
	}
	
	public Integer getSource() {
		return this.i;
	}
	
	public Integer getTarget() {
		return j;
	}

	public static GraphWalk<Integer,SimpleEdge<Integer>> solution(GraphTree<FloydVertex,FloydEdge,ActionFloyd> tree){
		GraphWalk<Integer,SimpleEdge<Integer>> gp = null;		
		if(tree.isBaseCase()) {
			Integer origen = tree.vertex().i;
			Integer destino = tree.vertex().j;
			List<Integer> ls = Lists2.of(origen,destino);
			gp = new GraphWalk<>(tree.vertex().graph,ls,tree.weight());
		} else if(tree.action() == ActionFloyd.No){
			gp = solution(tree.neighbords().get(0));
		} else {
			GraphWalk<Integer,SimpleEdge<Integer>> gp1 = solution(tree.neighbords().get(0));
			GraphWalk<Integer,SimpleEdge<Integer>> gp2 = solution(tree.neighbords().get(1));
			gp = gp1.concat(gp2,g->EGraphPath.weight(g));
		}
		return gp;
	}
	
	public static GraphWalk<Integer,SimpleEdge<Integer>> solution(Map<FloydVertex, Sp<FloydEdge>> tree,FloydVertex vertex){
		GraphWalk<Integer,SimpleEdge<Integer>> gp = null;
		Sp<FloydEdge> s = tree.get(vertex);
		if(s.edge() == null) {
			Integer origen = vertex.i;
			Integer destino = vertex.j;
			List<Integer> ls = Lists2.of(origen,destino);
			gp = new GraphWalk<>(vertex.graph,ls,s.weight());
		} else if(s.edge().action == ActionFloyd.No){
			gp = solution(tree,s.edge().targets.get(0));
		} else {
			GraphWalk<Integer,SimpleEdge<Integer>> gp1 = solution(tree,s.edge().targets.get(0));
			GraphWalk<Integer,SimpleEdge<Integer>> gp2 = solution(tree,s.edge().targets.get(1));
			gp = gp1.concat(gp2,g->EGraphPath.weight(g));
		}
		return gp;
	}

}

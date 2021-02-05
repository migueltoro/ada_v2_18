package us.lsi.alg.colorgraphs;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import us.lsi.common.Lists2;
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;


public class ColorVertex extends ActionVirtualVertex<ColorVertex,ColorEdge,Integer>{

	public static ColorVertex of(Integer index, Map<Integer, Integer> cav) {
		return new ColorVertex(index, cav);
	}
	
	public static ColorVertex first() {
		return new ColorVertex(0,new HashMap<>());
	}
	
	public static void data(Integer m, Graph<Integer,SimpleEdge<Integer>> graph) {
		ColorVertex.m = m;; 
		ColorVertex.n = graph.vertexSet().size(); 
		ColorVertex.graph = graph;
		ColorVertex.colors = Lists2.rangeList(0, ColorVertex.m);
	}
	

	final Integer index;
	final Map<Integer,Integer> cav; //colores asignados ya a vertices
	final Set<Integer> ca;//derivada, colores ya asignados
	final Integer nc; // derivada, número de colores ya asignados
	final Set<Integer> cv; //derivada, colores asignados a los vecinos de index que ya tienen color
	static Integer m; // número maximo de colores, obtenido previamente mediante un camino voraz
	static Integer n; // número de vértices
	static Graph<Integer,SimpleEdge<Integer>> graph;
	static List<Integer> colors;
	
	
	private ColorVertex(Integer index, Map<Integer, Integer> cav) {
		super();
		this.index = index;
		this.cav = new HashMap<>(cav);
		this.ca = cav.values().stream().distinct().collect(Collectors.toSet());
		this.nc = this.ca.size();
		if (index < ColorVertex.n) {
			this.cv = AuxiliaryColor.coloresDeVecinos(index, ColorVertex.graph, cav);
		} else {
			this.cv = new HashSet<>();
		}
	}


	@Override
	public Boolean isValid() {
		return this.index>=0 && this.index <= ColorVertex.n;
	}


	@Override
	public List<Integer> actions() {		
		List<Integer> r = Lists2.difference(ColorVertex.colors,this.cv);
		return r;
	}
	
	public Integer greedyAction() {
		List<Integer> r = Lists2.difference(this.ca,this.cv);
		if(r.isEmpty()) r = Lists2.difference(ColorVertex.colors,this.cv);
		return Lists2.randomUnitary(r).get(0);
	}


	@Override
	public ColorVertex neighbor(Integer a) {
		Map<Integer,Integer> m = new HashMap<>(this.cav);
		m.put(this.index,a);
		return ColorVertex.of(this.index+1,m);
	}


	@Override
	public ColorEdge edge(Integer a) {
		return ColorEdge.of(this,this.neighbor(a), a);
	}


	@Override
	public String toString() {
		return String.format("(%s,%s)",index,cav);
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ca == null) ? 0 : ca.hashCode());
		result = prime * result + ((cav == null) ? 0 : cav.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ColorVertex other = (ColorVertex) obj;
		if (ca == null) {
			if (other.ca != null)
				return false;
		} else if (!ca.equals(other.ca))
			return false;
		if (cav == null) {
			if (other.cav != null)
				return false;
		} else if (!cav.equals(other.cav))
			return false;
		return true;
	}
	
	
}

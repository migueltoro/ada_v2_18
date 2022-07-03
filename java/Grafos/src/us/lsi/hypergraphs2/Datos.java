package us.lsi.hypergraphs2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import us.lsi.colors.GraphColors;
import us.lsi.colors.GraphColors.ArrowHead;
import us.lsi.colors.GraphColors.Color;
import us.lsi.colors.GraphColors.Shape;
import us.lsi.common.SetMultimap;
import us.lsi.common.Union;
import us.lsi.graphs.alg.DP.Sp;

public class Datos<V extends HyperVertex2<V, E, A, ?>,E extends HyperEdge2<V,E,A,?>,A> {	
	
	private static Object datos = null;
	
	@SuppressWarnings("unchecked")
	public static <V extends HyperVertex2<V, E, A, ?>,E extends HyperEdge2<V,E,A,?>,A> Datos<V, E, A> get() {
		if(datos == null) datos = new Datos<>();
		return (Datos<V, E, A>) datos;
	}
	
	private Comparator<Sp<E>> cmp;
	
	private Datos() {
		super();
		this.cmp = Datos.type.equals(Datos.DpType.Min) ? Comparator.naturalOrder()
				: Comparator.reverseOrder();
	}
	private Map<V,Sp<E>> memory = new HashMap<>();
	public SetMultimap<V,Sp<E>> allProblems = SetMultimap.create();
	
	public static enum DpType{Max, Min}
	public static DpType type = DpType.Min;
	
	
	public Sp<E> get(V v){
		return memory.get(v);
	}
	public Set<Sp<E>> getAll(V v){
		return allProblems.get(v);
	}
	public void put(V v, Sp<E> s){
		memory.put(v, s);
	}
	public void putAll(V v, Sp<E> s){
		allProblems.put(v,s);
	}	
	public Boolean contains(V v){
		return memory.containsKey(v);
	}
	public Set<V> vertices(){
		return memory.keySet();
	}
	public Comparator<Sp<E>> order(){
		return cmp;
	}
	
	public SimpleDirectedGraph<Union<V, E>, DefaultEdge> graph() {	
		
		SimpleDirectedGraph<Union<V, E>, DefaultEdge> graph = 
				new SimpleDirectedGraph<Union<V, E>, DefaultEdge>(null, ()->new DefaultEdge(),true);
		
		Set<V> vertices = this.vertices();
		for (V v : vertices) {
			graph.addVertex(Union.ofA(v));
		}
		for (V v : vertices) {
			Set<Sp<E>> alls = this.getAll(v);			
			if (alls != null) {
				for (Sp<E> s : alls) {
					if (s != null) {
						E e = s.edge();
						if (e != null) {
							Union<V,E> source = Union.ofA(e.source());
							List<Union<V,E>> targets = e.targets().stream().map(x -> Union.<V,E>ofA(x)).toList();
							Union<V,E> ve = Union.ofB(e);
							graph.addVertex(ve);
							graph.addEdge(source, ve);
							for (Union<V, E> t : targets) {
								graph.addEdge(ve, t);
							}
						} 
					} 
				}
			}
		}
		return graph;
	}
	
	public static <V,E> String stv(Union<V,E> un) {
		if(un.isA()) return un.a().toString();
		else return un.b().toString().substring(0, 1).toUpperCase();
	}
	
	public static <V,E> String ste(Union<V,E> un) {
		if(un.isA()) return un.a().toString();
		else return un.b().toString().substring(0, 1).toUpperCase();
	}
	
	public static <V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V,E,A,?>, A> 
		void toDotHypergraph(SimpleDirectedGraph<Union<V,E>, DefaultEdge> g, String file, V initial) {

		Set<Union<V,E>> vt = initial.graphTree().vertices().stream()
				.<Union<V,E>>map(x -> Union.ofA(x)).collect(Collectors.toSet());
		
		Set<Union<V,E>> et = initial.graphTree().hyperEdges().stream()
				.<Union<V,E>>map(v -> Union.ofB(v))
				.collect(Collectors.toSet());
		
		Predicate<DefaultEdge> pd = e->et.contains(g.getEdgeSource(e)) || et.contains(g.getEdgeTarget(e));

		GraphColors.toDot(g, file, 
				x -> stv(x),
				x -> g.getEdgeSource(x).isA() ? g.getEdgeTarget(x).b().toString().substring(0, 1).toUpperCase() : "",
				x -> GraphColors.all(GraphColors.shapeIf(Shape.point, x.isB()),
						GraphColors.colorIf(Color.red, vt.contains(x))),
				e -> GraphColors.all(GraphColors.arrowHead(ArrowHead.none),
						GraphColors.colorIf(Color.red, pd.test(e))));
	}
	
	public static <V extends HyperVertex2<V, E, A, ?>, E extends HyperEdge2<V,E,A,?>,A> 
		void toDotAndOr(SimpleDirectedGraph<Union<V,E>, DefaultEdge> g,
			String file, V initial) {

		Set<Union<V,E>> vt = initial.graphTree().vertices().stream()
				.<Union<V,E>>map(x -> Union.ofA(x)).collect(Collectors.toSet());
		
		Set<Union<V,E>> et = initial.graphTree().hyperEdges().stream()
				.<Union<V,E>>map(v -> Union.ofB(v))
				.collect(Collectors.toSet());
		
		Predicate<DefaultEdge> pd = e->et.contains(g.getEdgeSource(e)) || et.contains(g.getEdgeTarget(e));
				
		GraphColors.toDot(g, file, 
				x -> stv(x),
				x -> "",
				x -> GraphColors.all(GraphColors.shapeIf(Shape.box, x.isA()),
						GraphColors.colorIf(Color.red, vt.contains(x) || et.contains(x))),
				e -> GraphColors.all(GraphColors.arrowHead(ArrowHead.none),
						GraphColors.colorIf(Color.red, pd.test(e))));
	}
	
	
}

package us.lsi.alg.mochila;


import java.util.Locale;
import java.util.Optional;
import java.util.function.Predicate;


import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.path.EGraphPath;


public class TestGreadyMochila {
	
	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		MochilaVertex.capacidadInicial = 78;
		MochilaVertex v1 = MochilaVertex.initialVertex();
		MochilaVertex v2 = MochilaVertex.lastVertex();
		Predicate<MochilaVertex> goal = v->v.equals(v2);
//		System.out.println(e1);
//		System.out.println(e2);			
		Double r2 = MochilaHeuristic.heuristic(v1,v->v.equals(v2),v2);
		System.out.println(r2);
		
		EGraph<MochilaVertex,MochilaEdge> graph = Graphs2.simpleVirtualGraphSum(v1);
		
		Optional<EGraphPath<MochilaVertex, MochilaEdge>> r = 
				GraphAlg.greedy(graph,MochilaVertex::greedyEdge,goal,v->true).search();
		System.out.println(r.get().getWeight());
//		System.out.println(r.getWeight());
//		Double r3 = MochilaHeuristic.voraz(e1, e->e.equals(e2),e2);
//		System.out.println(r3);
//		GraphPath<MochilaVertex, MochilaEdge> r4 = MochilaHeuristic.greadyPath(e1,e->e.equals(e2));
//		System.out.println(r4);
//		System.out.println(r4.getWeight());
//		EGraph<MochilaVertex,MochilaEdge> graph = Graphs2.simpleVirtualGraph(e1);
//		GreedySearch<MochilaVertex, MochilaEdge> rr = GraphAlg.greedy(graph,
//				MochilaVertex::greedyEdgeHeuristic,
//				e->e.equals(e2));
//		System.out.println(rr.weightToEnd());

	}

}

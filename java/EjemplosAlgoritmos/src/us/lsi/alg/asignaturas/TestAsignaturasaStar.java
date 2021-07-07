package us.lsi.alg.asignaturas;


import java.util.Locale;
import java.util.function.Predicate;
import org.jgrapht.GraphPath;

import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.BT;
import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.DPR;
import us.lsi.graphs.alg.DynamicProgramming.PDType;
import us.lsi.graphs.alg.DynamicProgrammingReduction;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestAsignaturasaStar {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "us"));
		String fichero = "ficheros/asignaturas.txt";

		DatosAsignaturas.iniciarDatos(fichero);
		System.out.println(DatosAsignaturas.mejoras);
		AsignaturasVertice ini = AsignaturasVertice.inicial();
		Predicate<AsignaturasVertice> predicado = t ->AsignaturasVertice.goal(t);
		
		SimpleVirtualGraph<AsignaturasVertice,AsignaturasEdge> grafoAStar = 
				Graphs2.simpleVirtualGraphLast(ini,predicado,null,v->v.constraint(),v->-(double)v.getPeso());
	
		
		AStar<AsignaturasVertice, AsignaturasEdge> as = GraphAlg
				.aStar(grafoAStar,Heuristica::heuristic_negate);
		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s1 = as.search().get();
		
		System.out.println(SolucionAsignaturas.of(s1));
		
		System.out.println("___________________");
		
		SimpleVirtualGraph<AsignaturasVertice,AsignaturasEdge> grafoPDR = 
				Graphs2.simpleVirtualGraphLast(ini, predicado,null,v->true,v->(double)v.getPeso());

		DynamicProgrammingReduction<AsignaturasVertice, AsignaturasEdge> pd = DPR
						.dynamicProgrammingReduction(grafoPDR, Heuristica::heuristic, PDType.Max);

		
		GraphPath<AsignaturasVertice, AsignaturasEdge> s2 = pd.search().get();
		
		System.out.println(SolucionAsignaturas.of(s2));
		
		System.out.println("___________________");
		
		SimpleVirtualGraph<AsignaturasVertice,AsignaturasEdge> grafoBT = 
				Graphs2.simpleVirtualGraphLast(ini,predicado,null,v->true,v->(double)v.getPeso());

		BackTracking<AsignaturasVertice, AsignaturasEdge,SolucionAsignaturas> bt = BT.backTracking(
				grafoBT, 
				Heuristica::heuristic, 
				SolucionAsignaturas::of,
				AsignaturasVertice::copy,
				BTType.Max);
		bt.search();
		System.out.println(bt.getSolution().get());
		System.out.println("___________________");

	}
}

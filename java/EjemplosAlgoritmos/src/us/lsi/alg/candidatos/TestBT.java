package us.lsi.alg.candidatos;

import java.util.Locale;

import us.lsi.graphs.alg.BackTracking;
import us.lsi.graphs.alg.BackTracking.BTType;
import us.lsi.graphs.virtual.EGraph;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class TestBT {

	public static void main(String[] args) {

		// Set up
		Locale.setDefault(new Locale("en", "US"));

		for (Integer id_fichero = 1; id_fichero < 2; id_fichero++) {

			DatosCandidatos.iniDatos("ficheros/candidatos"+ id_fichero +".txt");
			System.out.println("\n\n>\tResultados para el test " + id_fichero + "\n");
//			DatosSubconjuntos.toConsole();
			

			// Vértices clave

			VertexCandidatos start = VertexCandidatos.initial();

			// Grafo

			EGraph<VertexCandidatos, EdgeCandidatos> graph = 
					SimpleVirtualGraph.sum(start,VertexCandidatos.goal(),x -> x.weight(),VertexCandidatos.constraint());

			System.out.println("\n\n#### PI-7 Ej3 Algoritmo BT ####");

			// Algoritmo BT
			BackTracking<VertexCandidatos, EdgeCandidatos, SolucionCandidatos> bta = 
				BackTracking.of(graph, 
					CandidatosHeuristic::heuristic,
					SolucionCandidatos::of, 
					BTType.Max);
/*
			bta.withGraph = true;
			bta.bestValue = SubconjuntoHeuristic.voraz(start,DatosSubconjunto.getNumSubconjuntos());
			System.out.println("Best = "+bta.bestValue);
			SolucionSubconjunto sv = SubconjuntoHeuristic.solucionVoraz(start,DatosSubconjunto.getNumSubconjuntos());
			List<SubconjuntoEdge> le = SubconjuntoHeuristic.pathVoraz(start,DatosSubconjunto.getNumSubconjuntos());
			System.out.println("Sv = "+sv);
			*/
			bta.search();
			
			if (bta.getSolution().isPresent()) {
			    System.out.println(bta.getSolution());
			} else {
				System.out.println("No hay solución");
			}
			//System.out.println(bta.getSolution().isPresent()?bta.getSolution().get():sv);
			//List<SubconjuntoEdge> ls = bta.optimalPath != null?bta.optimalPath.getEdgeList():null;
	/*		
			GraphColors.toDot(bta.graph(),"ficheros/subconjuntosBTGraph.gv",
					v->v.toGraph(),
					e->e.action().toString(),
					v->GraphColors.colorIf(Color.red,SubconjuntoVertex.goal().test(v)),
					e->GraphColors.colorIf(Color.red,(bta.optimalPath != null?ls:le).contains(e))
					);
		*/	
//			System.out.println(ls.stream().map(e->e.action()).toList());
		}
	}

}


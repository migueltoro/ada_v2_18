package us.lsi.graphs.examples;


import java.util.List;

import org.jgrapht.alg.cycle.HierholzerEulerianCycle;
import org.jgrapht.alg.cycle.PatonCycleBase;
import org.jgrapht.alg.interfaces.CycleBasisAlgorithm.CycleBasis;
import org.jgrapht.alg.tour.TwoApproxMetricTSP;
import org.jgrapht.graph.GraphWalk;
import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.common.Strings2;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.GraphsReader;




public class Ciclos {
	

	public static void main(String[] args) {
		
		SimpleWeightedGraph<Ciudad,Carretera> graph =  
				GraphsReader.newGraph("ficheros/andalucia.txt",
						Ciudad::ofFormat, 
						Carretera::ofFormat,
						()->new SimpleWeightedGraph<>(Ciudad::of,Carretera::of),
						Carretera::getKm);
		
		SimpleWeightedGraph<Ciudad, Carretera> gc = 
				Graphs2.explicitCompleteGraph(
						graph,
						200000.,
						()-> new SimpleWeightedGraph<>(Ciudad::of,Carretera::of),
						Carretera::ofWeight,
						Carretera::getKm);
		
		TwoApproxMetricTSP<Ciudad, Carretera> tsp = new  TwoApproxMetricTSP<>();
		List<Ciudad> r3 = tsp.getTour(gc).getVertexList();
		GraphWalk<Ciudad,Carretera> gw = new GraphWalk<>(gc,r3,0.);
		
		Strings2.toConsole("Hamiltonian");
		Strings2.toConsole(r3,"Ciudades en el camino");
		Strings2.toConsole(gw.getEdgeList(), "Aristas en el camino");	
		
		HierholzerEulerianCycle<Ciudad,Carretera> hc = new HierholzerEulerianCycle<>();
		Boolean r2 = hc.isEulerian(graph);
		
		Strings2.toConsole(r2.toString());
		
		var sc = new PatonCycleBase<Ciudad,Carretera>(graph);
		CycleBasis<Ciudad,Carretera> r = sc.getCycleBasis();
		
//		Set<GraphWalk2<Ciudad,Carretera>> sgw = 
//				r.getCycles()
//				 .stream()
//				 .map(x->new GraphWalk2<Ciudad,Carretera>(g,x))
//				 .collect(Collectors.toSet());
//		
//		sgw.stream().forEach(x->System.out.println(x.getWeight()+"==="+x.getLength()+"==="+x.getVertexList()));
		Strings2.toConsole(r.getCyclesAsGraphPaths(),"Ciclos");
	}
	
	

}

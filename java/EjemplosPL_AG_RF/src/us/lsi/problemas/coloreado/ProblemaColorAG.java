package us.lsi.problemas.coloreado;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;


import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.GraphsReader;


public class ProblemaColorAG implements ValuesInRangeProblemAG<Integer,Map<Ciudad,Integer>> {

	private static Graph<Ciudad,Carretera> grafo; 
	private static List<Ciudad> ciudades;
	
	public ProblemaColorAG(String ficheroGrafo) { //"./ficheros/Andalucia.txt"
		grafo = cargaGrafo(ficheroGrafo);
		ciudades = new ArrayList<>(grafo.vertexSet());
	}
	private static Graph<Ciudad, Carretera> cargaGrafo(String f) {
		return GraphsReader.newGraph(f, 
				Ciudad::ofFormat,
				Carretera::ofFormat, 
				() -> new SimpleWeightedGraph<>(Ciudad::of, Carretera::of),
				Carretera::getKm);
	}

	@Override
	public Integer getVariableNumber() {
		return ciudades.size();
	}
	
	@Override	
    public Integer getMax(Integer index){
		return ciudades.size()-1;		
	}

	@Override	
    public Integer getMin(Integer index){
		return 0;		
	}

	@Override
	public Map<Ciudad,Integer> getSolucion(ValuesInRangeChromosome<Integer> cromosoma) {
		List<Integer> solucion = cromosoma.decode();
		Map<Ciudad,Integer> res = new HashMap<>();
		IntStream.range(0, solucion.size())
		         .boxed()
		         .forEach(i ->res.put(ciudades.get(i), solucion.get(i)+1));
		return res;		
	}
	
	@Override
	public Double fitnessFunction(ValuesInRangeChromosome<Integer> cromosoma) {
		Map<Ciudad,Integer> m = getSolucion(cromosoma);
		
		long rko = grafo.edgeSet().stream()
				.filter(c -> m.get(c.getSource())== m.get(c.getTarget()))
				.count();		
  		int N = ciudades.size();
  		int CU = m.values().stream()
  				.collect(Collectors.toSet())
  				.size();
		double fitness = -((rko * N * N) + CU);
		
		return fitness;		
	}

	public Set<Set<Ciudad>> getComponentes(ValuesInRangeChromosome<Integer> cromosoma) {
		Map<Ciudad,Integer> m = getSolucion(cromosoma);
			
		return m.entrySet().stream()
				   .collect(Collectors.groupingBy(e -> e.getValue(),Collectors.mapping(e->e.getKey(),Collectors.toSet())))
				   .values().stream()
				   .collect(Collectors.toSet());
	}

}

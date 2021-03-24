package us.lsi.color;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.jgrapht.graph.SimpleWeightedGraph;

import us.lsi.ag.ValuesInRangeData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.grafos.datos.Carretera;
import us.lsi.grafos.datos.Ciudad;
import us.lsi.graphs.GraphsReader;
import us.lsi.graphs.Graphs2;


public class DatosColorAG implements ValuesInRangeData<Integer,Map<Ciudad,Integer>> {

	
	private static SimpleWeightedGraph<Ciudad,Carretera> grafo; 
	private static List<Ciudad> ciudades;
	
	public DatosColorAG(String ficheroGrafo) { //"./ficheros/Andalucia.txt"
		grafo = cargaGrafo(ficheroGrafo);
		ciudades = grafo.vertexSet().stream().collect(Collectors.toList());
	}
	
	private static SimpleWeightedGraph<Ciudad, Carretera> cargaGrafo(String f) {
		return GraphsReader.newGraph(f, 
				Ciudad::ofFormat,
				Carretera::ofFormat, 
				Graphs2::simpleWeightedGraph,
				Carretera::getKm);
	}

	@Override
	public Integer size() {
		return ciudades.size();
	}
	
	@Override	
    public Integer getMax(Integer index){
		return 5;		
	}

	@Override	
    public Integer getMin(Integer index){
		return 0;		
	}

	@Override
	public Map<Ciudad,Integer> getSolucion(List<Integer> solucion) {
		Map<Ciudad,Integer> res = IntStream.range(0, solucion.size()).boxed()
				.collect(Collectors.toMap(i->ciudades.get(i),i->solucion.get(i)));
		return res;		
	}
	
	@Override
	public Double fitnessFunction(List<Integer> solucion) {
		Map<Ciudad,Integer> m = getSolucion(solucion);			
  		Integer N = ciudades.size();
  		Long numAristasIlegales = grafo.edgeSet().stream()
				.filter(c -> m.get(c.getSource())== m.get(c.getTarget()))
				.count();
  		Integer numeroDeColores = m.values().stream()
  				.collect(Collectors.toSet())
  				.size();
		Double fitness = -(double) numeroDeColores -numAristasIlegales * N * N;
		
		return fitness;		
	}

	public Set<Set<Ciudad>> getComponentes(List<Integer> solucion) {
		Map<Ciudad,Integer> m = getSolucion(solucion);
			
		return m.entrySet().stream()
				   .collect(Collectors.groupingBy(e -> e.getValue(),Collectors.mapping(e->e.getKey(),Collectors.toSet())))
				   .values().stream()
				   .collect(Collectors.toSet());
	}
	
	@Override
	public ChromosomeType getType() {
		return ChromosomeType.Range;
	}
}

package us.lsi.graphs;

public interface EdgeWeightFactory<V,E> {
	
	E create(V source, V target, Double weight);

}

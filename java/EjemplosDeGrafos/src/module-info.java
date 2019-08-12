/**
 * 
 */
/**
 * @author migueltoro
 *
 */
module ejemplos_de_grafos {
	exports us.lsi.flowgraph.examples;
	exports us.lsi.graphs.examples;

	requires transitive datos_compartidos;
	requires transitive grafos;
	requires org.jgrapht.core;
	requires org.jgrapht.io;
	requires transitive partecomun;
}
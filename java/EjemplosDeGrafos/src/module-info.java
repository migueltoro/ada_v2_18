/**
 * <h2>
 * 
 * <p> <em> Author:</em>  Miguel Toro </p>
 * <p> <em> Módulos Requeridos:</em> 
 * <ul>
 * <li> datos_compartidos </li>
 * <li> grafos </li>
 * <li> org.jgrapht.core </li>
 * <li> org.jgrapht.io </li>
 * <li> partecomun </li>
 * </ul>
 * 
 * </h2>
 *
 */
module ejemplos_de_grafos {
	exports us.lsi.flowgraph.examples;
	exports us.lsi.graphs.examples;

	requires transitive datos_compartidos;
	requires transitive grafos;
	requires transitive org.jgrapht.core;
	requires transitive org.jgrapht.io;
	requires transitive partecomun;
}
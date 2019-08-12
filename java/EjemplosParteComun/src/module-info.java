/**
 * <h2>
 * 
 * <p> <em> Author:</em>  Miguel Toro </p>
 * <p> <em> Módulos Requeridos:</em> 
 * <ul>
 * <li> commons.math3 </li>
 * <li> datos_compartidos </li>
 * <li> junit </li>
 * <li> partecomun </li>
 * </ul>
 * 
 * </h2>
 *
 */
module ejemplos_parte_comun {
	exports us.lsi.iterativorecursivos;
	exports us.lsi.recursivos.problemasdelistas;
	exports us.lsi.trees;
	exports us.lsi.java8ejemplos;
	exports us.lsi.iterativos;
	exports us.lsi.streams;
	exports us.lsi.recursivos.puntos;

	requires transitive commons.math3;
	requires transitive datos_compartidos;
	requires transitive junit;
	requires transitive partecomun;
}
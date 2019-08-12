
/**
 * <h2>
 * 
 * <p> <em> Author:</em>  Miguel Toro </p>
 * <p> <em> Módulos Requeridos:</em> 
 * <ul>
 * <li> lpsolve </li>
 * <li> datos_compartidos </li>
 * </ul>
 * 
 * </h2>
 *
 */


module ejemplos_pl {
	exports us.lsi.pli.examples;
	exports us.lsi.pli.examples.test;

	requires transitive datos_compartidos;
	requires transitive lpsolve;
}
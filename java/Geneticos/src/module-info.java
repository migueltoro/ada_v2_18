
/**
 * <h2>
 * 
 * <p> <em> Author:</em>  Miguel Toro </p>
 * <p> <em> Módulos Requeridos:</em> 
 * <ul>
 * <li> commons.math3 </li>
 * <li> partecomun </li>
 * </ul>
 * 
 * </h2>
 *
 */

module geneticos {
	exports us.lsi.ag.agchromosomes;
	exports us.lsi.ag.agstopping;
	exports us.lsi.sa;
	exports us.lsi.ag.agoperators;
	exports us.lsi.ag;

	requires transitive commons.math3;
	requires transitive partecomun;
}
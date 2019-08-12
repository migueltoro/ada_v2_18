/**
 * <h2>
 * 
 * <p> <em> Author:</em>  Miguel Toro </p>
 * <p> <em> Módulos Requeridos:</em> 
 * <ul>
 * <li> commons.math3 </li>
 * <li> datos_compartidos </li>
 * <li> geneticos </li>
 * <li> partecomun </li>
 * </ul>
 * 
 * </h2>
 *
 */


module ejemplos_geneticos {
	exports us.lsi.ag.reinas;
	exports us.lsi.ag.mochila;
	exports us.lsi.ag.expression;
	exports us.lsi.ag.real;
	exports us.lsi.ag.sudoku;
	exports us.lsi.ag.anuncios;

	requires transitive geneticos;
	requires transitive commons.math3;
	requires transitive datos_compartidos;
	requires transitive partecomun;
}
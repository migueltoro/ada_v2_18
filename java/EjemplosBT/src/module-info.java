/**
 * <h2>
 * 
 * <p> <em> Author:</em>  Miguel Toro </p>
 * <p> <em> Módulos Requeridos:</em> 
 * <ul>
 * <li> algoritmos_recursivos </li>
 * <li> datos_compartidos </li>
 * <li> partecomun </li>
 * </ul>
 * 
 * </h2>
 *
 */


module ejemplos_bt {
	exports us.lsi.bt.reinas;
	exports us.lsi.bt.tareasyprocesadores;
	exports us.lsi.bt.sudoku;
	exports us.lsi.bt.afinidad;
	exports us.lsi.bt.anuncios;

	requires transitive algoritmos_recursivos;
	requires transitive datos_compartidos;
	requires transitive partecomun;
}
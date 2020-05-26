/**
 * 
 */
/**
 * @author migueltoro
 *
 */
module solve {
	exports us.lsi.lpsolve;
	exports us.lsi.gurobi;
	exports us.lsi.pli;

	requires transitive gurobi;
	requires transitive lpsolve;
	requires transitive partecomun;
}
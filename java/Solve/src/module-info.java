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

	requires transitive gurobi;
	requires transitive lpsolve;
	requires transitive partecomun;
}
/**
 * 
 */
/**
 * @author Miguel Toro
 *
 */
module us.lsi.ejemplospd {
	exports us.lsi.pd.festival;
	exports us.lsi.pd.matrices;
	exports us.lsi.pd.afinidad;
	exports us.lsi.pd.numerodearboles;
	exports us.lsi.pd.floyd;
	exports us.lsi.pd.reinas;
	
	requires transitive us.lsi.algoritmosrecursivos;
	requires transitive us.lsi.grafos;
	requires transitive us.lsi.datoscompartidos;
	requires us.lsi.partecomun;
}
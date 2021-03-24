package us.lsi.ag;

import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;

public interface Data {
	
	/**
	 * @return Numero de casillas del cromosoma
	 */
	Integer size();
	
	/**
	 * @return El tipo del cromosoma
	 */
	ChromosomeType getType();

}

package us.lsi.lpsolve.solution;

import java.util.List;

public interface SolutionPLI {
	/**
	 * @return El coste total del objetivo
	 */
	public double getGoal();
	/**
	 * @return El punto solución
	 */
	public double[] getSolution();	

	/**
	 * @param i El valor de la variable i
	 * @return La solución de la variable i
	 */
	public double getSolution(int i); 
		
	/**
	 * @return Los identificadores de las variables
	 */
	public List<String> getNames(); 

	/**
	 * @param i Un índice de variable en el rango 0..getNumVar()-1
	 * @return El identificador de la variable i
	 */
	public String getName(int i); 
	
	/**
	 * @return Número de variables
	 */
	public int getNumVar();
	
}

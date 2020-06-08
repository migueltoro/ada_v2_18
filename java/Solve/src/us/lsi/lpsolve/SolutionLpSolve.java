package us.lsi.lpsolve;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public interface SolutionLpSolve {
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
	
	/**
	 * @return Los pares nombre de variable valor de la solucion
	 */
	public Map<String,Double> solutions();
	
	public default String toString(BiPredicate<String,Double> pd) {
		return String.format("\n\n\nEl valor objetivo es %.2f\nLos valores de la variables\n%s",this.getGoal(),
				 this.solutions().entrySet()
				.stream()
				.sorted(Comparator.comparing(e->e.getKey()))
				.filter(e->pd.test(e.getKey(),e.getValue()))
				.map(e->String.format("%s == %d",e.getKey(),e.getValue().intValue()))
				.collect(Collectors.joining("\n")));
	}
	
}

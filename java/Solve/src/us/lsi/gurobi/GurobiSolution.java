package us.lsi.gurobi;

import java.util.Map;

public class GurobiSolution {

	public static GurobiSolution of(Double objVal, Map<String, Double> values) {
		return new GurobiSolution(objVal, values);
	}

	public Double objVal;
	public Map<String,Double> values;
	
	private GurobiSolution(Double objVal, Map<String, Double> values) {
		super();
		this.objVal = objVal;
		this.values = values;
	}

	@Override
	public String toString() {
		return String.format("El valor objetivo es %.2f \nLos valores de la variables %s",objVal,values);
	}
	
}

package us.lsi.alg.pack;


import java.util.function.Predicate;

public class Heuristica {
	
	public static Double heuristic(PackVertex v1, Predicate<PackVertex> goal, PackVertex v2) {
		return (double)(v1.nc()+PackVertex.n-v1.index());
	}

}

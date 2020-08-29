package us.lsi.astar.laberinto;


import java.util.function.Predicate;

import org.jgrapht.GraphPath;

import us.lsi.common.IntPair;
import us.lsi.common.TriFunction;
import us.lsi.graphs.Graphs2;
import us.lsi.graphs.alg.AStar;
import us.lsi.graphs.alg.GraphAlg;
import us.lsi.graphs.virtual.SimpleVirtualGraph;

public class Test {

	public static void main(String[] args) {
		TriFunction<Casilla,Predicate<Casilla>,Casilla,Double> heuristic = (c1,p,c2)->1.*c1.getPosition().manhattan(c2.getPosition());
		
		Laberinto.leeDatos("ficheros/laberinto.txt", 6, 8);
		System.out.println(Laberinto.showLaberinto());
		System.out.println("______________");
		Casilla c1 = Laberinto.get(IntPair.of(0,0));		
		Casilla c2 = Laberinto.get(IntPair.of(5,7));
		SimpleVirtualGraph<Casilla,CasillaEdge> graph = Graphs2.simpleVirtualGraph(c1,x->1.);
		
		AStar<Casilla, CasillaEdge> a = GraphAlg.aStarEnd(graph,c2,heuristic);
		
		GraphPath<Casilla, CasillaEdge> path = a.search();
		System.out.println(Laberinto.showSolucionLaberinto(path));
		
	}

}

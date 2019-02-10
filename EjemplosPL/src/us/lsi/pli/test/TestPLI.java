package us.lsi.flow.test;

import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;

public class TestFlow {

	public static void main(String[] args) {
		SolutionPLI a = AlgoritmoPLI.getSolutionFromFile("ficheros/ejemplo1.txt");
//		System.out.println(p.toStringConstraints());
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println(a.getGoal());
		for (int j = 0; j < 5; j++) {
			System.out.println(a.getSolution()[j]);
		}
		System.out.println("________");
//		System.out.println(s);
	}

}

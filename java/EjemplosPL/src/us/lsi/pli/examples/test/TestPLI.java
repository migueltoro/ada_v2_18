package us.lsi.pli.examples.test;

import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;

public class TestPLI {

	public static void main(String[] args) {
		SolutionLpSolve a = AlgoritmoLpSolve.getSolutionFromFile("ficheros/ejemplo1.txt");
//		System.out.println(p.toStringConstraints());
		System.out.println("-------------------");	
		System.out.println("________");
		System.out.println("Goal = "+a.getGoal());
		for (int j = 0; j < 4; j++) {
			System.out.println(a.getName(j)+" = "+a.getSolution()[j]);
		}
		System.out.println("________");
//		System.out.println(s);
	}

}

package us.lsi.lpsolve.test;

import us.lsi.lpsolve.solution.AlgoritmoPLI;
import us.lsi.lpsolve.solution.SolutionPLI;

public class Test {

	public static void main(String[] args) {
				SolutionPLI a = AlgoritmoPLI.getSolutionFromFile("ficheros/ejemplo.txt");
				System.out.println("-------------------");	
				System.out.println("________");
				System.out.println(a.getGoal());
				for (int j = 0; j < a.getNumVar(); j++) {
					System.out.println(a.getName(j)+" = "+a.getSolution()[j]);
				}
				System.out.println("________");


	}

}

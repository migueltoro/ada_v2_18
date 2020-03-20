package us.lsi.pli.test;

import us.lsi.pli.AlgoritmoPLI;
import us.lsi.pli.SolutionPLI;

public class Test {

	public static void main(String[] args) {
				SolutionPLI a = AlgoritmoPLI.getSolutionFromFile("ficheros/infeasible.txt");
				System.out.println("-------------------");	
				System.out.println("________");
				System.out.println(a.getGoal());
				for (int j = 0; j < a.getNumVar(); j++) {
					System.out.println(a.getName(j)+" = "+a.getSolution()[j]);
				}
				System.out.println("________");


	}

}

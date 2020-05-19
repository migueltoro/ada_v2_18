package us.lsi.lpsolve;

public class TestLpSolve {

	public static void main(String[] args) {
				SolutionLpSolve a = AlgoritmoLpSolve.getSolutionFromFile("ficheros/ejemplo.txt");
				System.out.println("-------------------");	
				System.out.println("________");
				System.out.println(a.getGoal());
				for (int j = 0; j < a.getNumVar(); j++) {
					System.out.println(a.getName(j)+" = "+a.getSolution()[j]);
				}
				System.out.println("________");


	}

}

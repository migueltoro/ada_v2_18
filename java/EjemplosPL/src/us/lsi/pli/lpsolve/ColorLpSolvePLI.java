package us.lsi.pli.lpsolve;

import static us.lsi.pli.AuxiliaryPLI.binaryVarsSection;
import static us.lsi.pli.AuxiliaryPLI.constraintEq;
import static us.lsi.pli.AuxiliaryPLI.constraintLe;
import static us.lsi.pli.AuxiliaryPLI.constraintsSection;
import static us.lsi.pli.AuxiliaryPLI.endSection;
import static us.lsi.pli.AuxiliaryPLI.f;
import static us.lsi.pli.AuxiliaryPLI.forAll;
import static us.lsi.pli.AuxiliaryPLI.goalMinSection;
import static us.lsi.pli.AuxiliaryPLI.listOf;
import static us.lsi.pli.AuxiliaryPLI.sum;
import static us.lsi.pli.AuxiliaryPLI.v;

import java.util.Locale;
import java.util.stream.Collectors;

import org.jgrapht.Graph;

import us.lsi.lpsolve.AlgoritmoLpSolve;
import us.lsi.lpsolve.SolutionLpSolve;
import us.lsi.pli.AuxiliaryPLI;
import us.lsi.pli.AuxiliaryPLI.PLIType;
import us.lsi.pli.gurobi.ColorGraphGurobi;
import us.lsi.common.Files2;
import us.lsi.graphs.SimpleEdge;

public class ColorLpSolvePLI {
	
	public static Graph<Integer, SimpleEdge<Integer>> graph;
	public static int n; //numero de vertices
	public static int m;

	public static String getConstraints(PLIType type) {
		AuxiliaryPLI.setType(type);
		Integer n = ColorGraphGurobi.n;
		Integer m = ColorGraphGurobi.m;
		StringBuilder r = new StringBuilder();
		r.append(goalMinSection(sum(listOf(0,m,k->v("y",k)))));
		r.append(constraintsSection());
		r.append(forAll("a",listOf(0,n,i->constraintEq(sum(listOf(0,m,k->v("x",i,k))),1))));
		r.append(forAll("b",listOf(0,n,0,m,(i,k)->constraintLe(sum(v("x",i,k),f(-1,"y",k)),0))));
		r.append(forAll("c",listOf(0,n,0,n,0,m,(i,j,k)->graph.containsEdge(i,k), 
							(i,j,k)->constraintLe(sum(v("x",i,k),v("x",j,k)),1))));
		r.append(binaryVarsSection(listOf(0,n,0,m,(i,k)->v("x",i,k)),listOf(0,m,k->v("y",k))));
		r.append(endSection());
		return r.toString();
	}
	
	
	
	public static void main(String[] args) {		
		ColorGraphGurobi.graph = ColorGraphGurobi.graph(10,0.3);
		ColorGraphGurobi.n = ColorGraphGurobi.graph.vertexSet().size();
		ColorGraphGurobi.m = 10;
//		System.out.println(ColorGraphGurobi.graph);
		String ct = getConstraints(PLIType.LPSolve);
		Files2.toFile(ct,"ficheros/color_sal.txt");
		Locale.setDefault(new Locale("en", "US"));
		SolutionLpSolve s = AlgoritmoLpSolve.getSolutionFromFile("ficheros/color_sal.txt");
		System.out.println(s.getGoal());
		System.out.println(s.solutions()
				.entrySet().stream()
				.filter(e->e.getKey().startsWith("x") && e.getValue()>0.)
				.map(e->String.format("%s",e.getKey()))
				.collect(Collectors.joining("\n")));
	}
}

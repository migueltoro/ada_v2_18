package us.lsi.flowgraph.examples;

import java.util.stream.Collectors;

import us.lsi.flowgraph.FlowGraph;
import us.lsi.flowgraph.FlowGraph.FGType;

public class FlowExample {

	public static void main(String[] args) {
		FlowGraph fg = FlowGraph.newGraph("ficheros/PI3Ej10DatosEntrada_andalucia.txt",FGType.Max);
		String constraints = fg.getConstraints();
		System.out.println(constraints);	
		System.out.println(fg.vertexSet().stream()				
				.map(v->v.toStringLong())
				.collect(Collectors.joining(",","{","}")));
				System.out.println(fg);		
		fg.vertexSet().stream()
				.map(v->String.format("%s,%.2f",v.getVariable(),v.getCost()))
				.forEach(x->System.out.println(x));
		System.out.println(fg);
	}

}

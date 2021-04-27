package us.lsi.alg.monedas;

import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

public class TestHeuristic {

	public static void main(String[] args) {
		Locale.setDefault(new Locale("en", "US"));
		MonedaVertex.datosIniciales("ficheros/monedas.txt", 307);
		System.out.println(Moneda.monedas.size());

		System.out.println("Orden de mayor a menor");
		System.out.println(Moneda.monedas);
		
		MonedaVertex e1 = MonedaVertex.first();
		MonedaVertex e2 = MonedaVertex.last();
		
		System.out.println("GL1 "+MonedasHeuristica.voraz_left(0,MonedaVertex.valorInicial,MonedaVertex.n));
		System.out.println("HL1 "+MonedasHeuristica.heuristic_left(0,MonedaVertex.valorInicial.doubleValue(),MonedaVertex.n));
		System.out.println("GR1 "+MonedasHeuristica.voraz_right(0,MonedaVertex.valorInicial,MonedaVertex.n));
		System.out.println("HR1 "+MonedasHeuristica.heuristic_right(0,MonedaVertex.valorInicial.doubleValue(),MonedaVertex.n));
		
		Collections.sort(Moneda.monedas,Comparator.comparing(m->m.pesoUnitario()));
		System.out.println("Orden de menor a mayor");
		System.out.println(Moneda.monedas);
		
		e1 = MonedaVertex.first();
		e2 = MonedaVertex.last();
		
		System.out.println("GL2 "+MonedasHeuristica.voraz_left(0,MonedaVertex.valorInicial,MonedaVertex.n));
		System.out.println("HL2 "+MonedasHeuristica.heuristic_left(0,MonedaVertex.valorInicial.doubleValue(),MonedaVertex.n));
		System.out.println("GR2 "+MonedasHeuristica.voraz_right(0,MonedaVertex.valorInicial,MonedaVertex.n));
		System.out.println("HR2 "+MonedasHeuristica.heuristic_right(0,MonedaVertex.valorInicial.doubleValue(),MonedaVertex.n));

	}

}

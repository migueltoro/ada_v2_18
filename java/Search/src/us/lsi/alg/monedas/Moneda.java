package us.lsi.alg.monedas;

import java.util.List;
import java.util.stream.Collectors;

import us.lsi.common.Files2;

public class Moneda {
	
	public static Moneda of(Integer valor, Integer peso) {
		return new Moneda(valor, peso);
	}
	
	public static Moneda parse(String linea) {
		String[] p = linea.split(",");
		return new Moneda(Integer.parseInt(p[0]),Integer.parseInt(p[1]));
	}

	public final Integer valor;
	public final Integer peso;
	
	private Moneda(Integer valor, Integer peso) {
		super();
		this.valor = valor;
		this.peso = peso;
	}

	@Override
	public String toString() {
		return String.format("(%d,%d)",this.valor,this.peso);
	}
	
	public Double getPesoUnitario() {
		return (1.*this.peso)/this.valor;
	}

	public static List<Moneda> monedas;
	
	public static Integer getValor(Integer i) {
		return Moneda.monedas.get(i).valor;
	}
	
	public static Integer getPeso(Integer i) {
		return Moneda.monedas.get(i).peso;
	}
	
	public static void datos(String fichero){	
		monedas = Files2.streamFromFile(fichero).map(ln->Moneda.parse(ln)).collect(Collectors.toList());
	}
	
}

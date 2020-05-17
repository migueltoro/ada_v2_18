package us.lsi.monedas;

import java.util.List;

public class Moneda {
	
	public static Moneda of(Integer valor, Integer peso) {
		return new Moneda(valor, peso);
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
	
	public static List<Moneda> datos(String fichero){
		return null;
	}
	
}

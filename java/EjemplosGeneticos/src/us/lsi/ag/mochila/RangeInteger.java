package us.lsi.ag.mochila;

import java.util.ArrayList;
import java.util.List;

import us.lsi.ag.AuxiliaryAg;
import us.lsi.math.Math2;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;
import us.lsi.sa.StateSa;

public class RangeInteger implements StateSa {
	
	public static RangeInteger of(List<Integer> values) {
		return new RangeInteger(values);
	}
	
	public final Integer n;
	public final List<Integer> values;
	
	protected RangeInteger(List<Integer> values) {
		super();
		this.values = values;
		this.n = values.size();
	}
	
	public RangeInteger() {
		super();
		this.values = null;
		this.n = DatosMochila.numeroDeObjetos;
	}	
	
	/**
	 * @pre 0 &le; i &lt; getVariableNumber()
	 * @param i Un entero 
	 * @return El máximo valor, sin incluir, del rango de valores de la variable i
	 */
	
	public Integer getMax(Integer i) {
		return DatosMochila.getObjetos().get(i).getNumMaxDeUnidades()+1;
	}
	
	/**
	 * @pre 0 &le; i &lt; 
	 * @param i Un entero getVariableNumber()
	 * @return El mínimo valor del rango de valores de la variable i
	 */
	public Integer getMin(Integer i) {
		return 0;
	}
	
	private Double valor;
	private Double peso;
	private Double fitness = null;
	
	private void calcula(List<Integer> ls) {
		this.peso = 0.;
		this.valor = 0.;
		for (int i = 0; i < ls.size(); i++) {
			peso = peso + ls.get(i) * DatosMochila.getPeso(i);
			valor = valor + ls.get(i) * DatosMochila.getValor(i);
		}
	}
	
	@Override
	public Double fitness() {
		calcula(this.values);	
		fitness = valor - 100*AuxiliaryAg.distanceToGeZero(DatosMochila.capacidadInicial - peso);
		return -fitness;
	}	
	
	@Override
	public RangeInteger mutate() {
		Integer n = this.values.size();
		Integer i = Math2.getEnteroAleatorio(0,n);
		Integer e = Math2.getEnteroAleatorio(this.getMin(i),this.getMax(i));
		List<Integer> r = new ArrayList<>(this.values);
		r.set(i, e);		
		return RangeInteger.of(r);
	}
	
	@Override
	public RangeInteger random() {
		List<Integer> r = new ArrayList<>();		
		for(int i = 0; i< this.n; i++) {
			Integer e = Math2.getEnteroAleatorio(this.getMin(i),this.getMax(i));
			r.add(e);
		}
		return RangeInteger.of(r);
	}
	
	public RangeInteger copy() {
		List<Integer> r = new ArrayList<>(this.values);
		return RangeInteger.of(r);
	}
	
	public static SolucionMochila getSolucion(List<Integer> values) {
		SolucionMochila s = SolucionMochila.empty();
		Integer n = values.size();
		for (int i=0; i< n;i++) {
			s.add(DatosMochila.getObjeto(i),values.get(i));
		}
		return s;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
}

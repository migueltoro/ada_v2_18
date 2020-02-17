package us.lsi.ag.mochila;

import java.util.List;

import us.lsi.ag.HelpAg;
import us.lsi.ag.ValuesInRangeChromosome;
import us.lsi.ag.ValuesInRangeProblemAG;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.ObjetoMochila;
import us.lsi.mochila.datos.SolucionMochila;


public class ProblemaMochilaAGRange implements ValuesInRangeProblemAG<Integer,SolucionMochila>{

	
	public ProblemaMochilaAGRange(String fichero) {
		DatosMochila.iniDatos(fichero);
	}	

	@Override
	public SolucionMochila getSolucion(ValuesInRangeChromosome<Integer> chromosome) {
		SolucionMochila s = SolucionMochila.empty();
		List<Integer> ls = chromosome.decode();
		for (int i=0; i< this.getVariableNumber();i++) {
			s.add(DatosMochila.getObjeto(i),ls.get(i));
		}
		return s;
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
	public Double fitnessFunction(ValuesInRangeChromosome<Integer> c) {
		List<Integer> ls = c.decode();
		calcula(ls);
//		fitness = dif >= 0.? valor:valor-10000*(dif*dif);	
		fitness = valor - 10000*HelpAg.mayorQueCero(DatosMochila.capacidadInicial - peso);
		return fitness;
	}

	
	public List<ObjetoMochila> getObjetos() {
		return DatosMochila.getObjetos();
	}
	

	@Override
	public Integer getVariableNumber() {
		return this.getObjetos().size();
	}

	@Override
	public Integer getMax(Integer i) {
		return this.getObjetos().get(i).getNumMaxDeUnidades()+1;
	}

	@Override
	public Integer getMin(Integer i) {
		return 0;
	}

}

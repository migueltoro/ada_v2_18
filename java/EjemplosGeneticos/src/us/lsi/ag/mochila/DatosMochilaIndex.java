package us.lsi.ag.mochila;

import java.util.List;

import us.lsi.ag.SeqNormalData;
import us.lsi.ag.agchromosomes.ChromosomeFactory.ChromosomeType;
import us.lsi.ag.AuxiliaryAg;
import us.lsi.mochila.datos.DatosMochila;
import us.lsi.mochila.datos.SolucionMochila;

public class DatosMochilaIndex implements SeqNormalData<SolucionMochila> {

	public DatosMochilaIndex(String fichero) {
		DatosMochila.iniDatos(fichero);
	}
	
	@Override
	public Integer size() {
		return DatosMochila.getObjetos().size();
	}

	@Override
	public ChromosomeType type() {
		return ChromosomeType.SubList;
	}
	
	private Double valor;
	private Double peso;
	private Double fitness = null;
	
	private void calcula(List<Integer> ls) {
		this.peso = 0.;
		this.valor = 0.;
		for (int i = 0; i < ls.size(); i++) {
			peso = peso + DatosMochila.getPeso(ls.get(i));
			valor = valor + DatosMochila.getValor(ls.get(i));
		}
	}

	@Override
	public Double fitnessFunction(List<Integer> cr) {
		calcula(cr);
		fitness = valor - 100*AuxiliaryAg.distanceToGeZero(DatosMochila.capacidadInicial - peso);
		return fitness;
	}

	@Override
	public SolucionMochila solucion(List<Integer> cr) {
		SolucionMochila s = SolucionMochila.empty();
		for (int i=0; i< cr.size();i++) {
			s.add(DatosMochila.getObjeto(cr.get(i)),1);
		}
		return s;
	}
	
	@Override
	public Integer maxMultiplicity(int index) {
		return DatosMochila.getObjetos().get(index).getNumMaxDeUnidades();
	}

	@Override
	public Integer itemsNumber() {
		return DatosMochila.getObjetos().size();
	}
}

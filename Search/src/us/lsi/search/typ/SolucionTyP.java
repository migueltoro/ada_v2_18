package us.lsi.search.typ;

import java.util.List;
import java.util.Map;

import us.lsi.tareasyprocesadores.datos.Tarea;

public class SolucionTyP {
	
	public static SolucionTyP of(Map<Integer, List<Tarea>> carga, Double maxCarga, Integer npMin, Integer npMax) {
		return new SolucionTyP(carga,maxCarga,npMin,npMax);
	}
	private Map<Integer,List<Tarea>> carga;
	private Double maxCarga;
	private Integer npMin;
	private Integer npMax;
	
	
	private SolucionTyP(Map<Integer, List<Tarea>> carga,Double maxCarga, Integer npMin, Integer npMax) {
		super();
		this.carga = carga;
		this.maxCarga = maxCarga;
		this.npMin = npMin;
		this.npMax = npMax;
	}
	@Override
	public String toString() {
		return String.format("(%.2f,%d,%d,%s)",maxCarga,npMin,npMax,carga);
	}
	public Map<Integer, List<Tarea>> getCarga() {
		return carga;
	}
	public Double getMaxCarga() {
		return maxCarga;
	}
	public Integer getNpMin() {
		return npMin;
	}
	public Integer getNpMax() {
		return npMax;
	}
	
}

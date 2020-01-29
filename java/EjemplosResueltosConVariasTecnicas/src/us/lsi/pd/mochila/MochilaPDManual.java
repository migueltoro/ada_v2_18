package us.lsi.pd.mochila;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.common.Strings2;
import us.lsi.mochila.datos.DatosMochila;

public class MochilaPDManual {
	
	public static Double vorazDouble(int index, Double capacidadRestante) {
		Double r = 0.;
		while (index < DatosMochila.numeroDeObjetos) {			
			Double a = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			r = r + a*DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
			index = index+1;			
		}
		return  r;
	}
	
	public static Integer vorazInteger(int index, Integer capacidadRestante) {
		Integer r = 0;
		while (index < DatosMochila.numeroDeObjetos) {			
			Integer a = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			r = r + a*DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
			index = index+1;			
		}
		return  r;
	}
	public static Double solve(Integer capacity) {
		Map<Dat,Double> memory = new HashMap<>();
		return solve(0,capacity,0.,memory);
	}
	
	private static Double maxValue = Double.MIN_VALUE;
	
	public static Double solve(int index, Integer capacidadRestante, Double value, Map<Dat,Double> memory) {
		Double r;
		Dat d = Dat.of(index, capacidadRestante);
		if(memory.containsKey(d)) {
			r = memory.get(d);
		} else if(index == DatosMochila.numeroDeObjetos) {
			r = 0.;
			if(value > maxValue) maxValue = value;
		} else {
			Integer nu = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			List<Integer> alternativas = IntStream.rangeClosed(0,nu).boxed().collect(Collectors.toList());
			Collections.reverse(alternativas);
			List<Double> soluciones = new ArrayList<>();
			for(Integer a:alternativas) {	
				Double cota = value+a*DatosMochila.getValor(index)+vorazDouble(index+1,(double)capacidadRestante-a*DatosMochila.getPeso(index));
				if( cota <= maxValue) continue;
				Integer cr = capacidadRestante-a*DatosMochila.getPeso(index);
				Double va = value + a*DatosMochila.getValor(index);
				Double s = solve(index+1,cr,va,memory);
				if(s!=null) {
					s = s +a*DatosMochila.getValor(index);
					soluciones.add(s);
				}
			}
			r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(d,r);
		}
		return r;
	}
	
	public static Sol solve2(Integer capacity) {
		Map<Dat,Sol> memory = new HashMap<>();
		return solve2(0,capacity,0.,memory);
	}
	
	public static Sol solve2(int index, Integer capacidadRestante, Double value, Map<Dat,Sol> memory) {
		Sol r;
		Dat d = Dat.of(index, capacidadRestante);
		if(memory.containsKey(d)) {
			r = memory.get(d);
		} else if(index == DatosMochila.numeroDeObjetos) {
			r = Sol.of(DatosMochila.numeroDeObjetos);
			if(value > maxValue) {
				maxValue = value;
				System.out.println("MaxValue "+maxValue);
			}
		} else {
			Integer nu = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			List<Integer> alternativas = IntStream.rangeClosed(0,nu).boxed().collect(Collectors.toList());
			Collections.reverse(alternativas);
			List<Sol> soluciones = new ArrayList<>();
			for(Integer a:alternativas) {	
				Double cota = value+a*DatosMochila.getValor(index)+vorazDouble(index+1,(double)capacidadRestante-a*DatosMochila.getPeso(index));
				if( cota <= maxValue) continue;
				Integer cr = capacidadRestante-a*DatosMochila.getPeso(index);
				Double va = value + a*DatosMochila.getValor(index);
				Sol s = solve2(index+1,cr,va,memory);
				if(s!=null) {
					s = s.add(a, index);
					soluciones.add(s);
				}
			}
			r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(d,r);
		}
		return r;
	}
	
	
	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		Strings2.toConsole(DatosMochila.getObjetos(),"Objetos");
		Sol s = solve2(DatosMochila.capacidadInicial);
		System.out.println(s);
		System.out.println("Voraz "+vorazDouble(0,(double)DatosMochila.capacidadInicial));
		System.out.println("Voraz "+vorazInteger(0,DatosMochila.capacidadInicial));
	}
	
	public static class Sol implements Comparable<Sol> {
		public static Sol of(Integer n) {
			return new Sol(0.,Lists2.copy(0, n));
		}
		public static Sol of(Double val, List<Integer> unidades) {
			return new Sol(val,unidades);
		}
		Double val;
		List<Integer> unidades;
		public Sol(Double val, List<Integer> unidades) {
			super();
			this.val = val;
			this.unidades = Lists2.ofCollection(unidades);
		}
		
		Sol add(Integer a, Integer index) {
			Double val = this.val+a*DatosMochila.getValor(index);
			this.unidades.set(index,a);
			return Sol.of(val, unidades);			
		}
		@Override
		public int compareTo(Sol s) {
			return this.val.compareTo(s.val);
		}
		
		@Override
		public String toString() {
			return String.format("valor = %.2f, unidades = %s",this.val,this.unidades.toString());
		}
		
	}
	
	public static class Dat {
		public static Dat of(int index, Integer capacidadRestante) {
			return new Dat(index,capacidadRestante);
		}
		int index;
		Integer capacidadRestante;
		public Dat(int index, Integer capacidadRestante) {
			super();
			this.index = index;
			this.capacidadRestante = capacidadRestante;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((capacidadRestante == null) ? 0 : capacidadRestante.hashCode());
			result = prime * result + index;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Dat other = (Dat) obj;
			if (capacidadRestante == null) {
				if (other.capacidadRestante != null)
					return false;
			} else if (!capacidadRestante.equals(other.capacidadRestante))
				return false;
			if (index != other.index)
				return false;
			return true;
		}
	}

}

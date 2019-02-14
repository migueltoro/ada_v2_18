package us.lsi.bt.mochila;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.mochila.datos.DatosMochila;


public class MochilaBTManual {

	public static Double voraz(int index, Double capacidadRestante) {
		Double r = 0.;
		while (index < DatosMochila.numeroDeObjetos) {			
			Double a = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			r = r + a*DatosMochila.getValor(index);
			capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
			index = index+1;			
		}
		return  r;
	}
	
	public static void btm(Integer capacity) {
		index =0;
		capacidadRestante = capacity;
		value =0.;
		btm();
	}
	
	private static Double maxValue = Double.MIN_VALUE;
	
	private static int index;
	private static Integer capacidadRestante;
	private static Double value;
	
	public static void btm() {
		if(index == DatosMochila.numeroDeObjetos) {
			if(value > maxValue) maxValue = value;
		} else {
			Integer nu = Math.min(capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
			List<Integer> alternativas = IntStream.rangeClosed(0,nu).boxed().collect(Collectors.toList());
			Collections.reverse(alternativas);
			for(Integer a:alternativas) {	
				Double cota = value+a*DatosMochila.getValor(index)+voraz(index+1,(double)capacidadRestante-a*DatosMochila.getPeso(index));
				if(cota <= maxValue) continue;
				capacidadRestante = capacidadRestante-a*DatosMochila.getPeso(index);
				value = value + a*DatosMochila.getValor(index);
				index = index +1; 
				btm();  
				index = index-1;
				capacidadRestante = capacidadRestante+a*DatosMochila.getPeso(index);
				value = value-a*DatosMochila.getValor(index);
			}
		}
	}
	
	public static void main(String[] args) {
		DatosMochila.iniDatos("ficheros/objetosMochila.txt");
		DatosMochila.capacidadInicial = 78;	
		btm(DatosMochila.capacidadInicial);
		System.out.println(maxValue);
		System.out.println("Voraz "+voraz(0,(double)DatosMochila.capacidadInicial));

	}

}

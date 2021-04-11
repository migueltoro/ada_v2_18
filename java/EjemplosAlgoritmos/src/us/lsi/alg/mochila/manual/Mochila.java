package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.common.Lists2;
import us.lsi.mochila.datos.DatosMochila;

public record Mochila(Integer index, Integer capacidadRestante) {
	
	public static Mochila of(Integer index, Integer capacidadRestante) {
		return new Mochila(index, capacidadRestante);
	}
	
	public Mochila vecino(Integer a) {
		Mochila r;
		if (this.capacidadRestante == 0.) r = Mochila.of(DatosMochila.n, 0);
		else r = Mochila.of(this.index() + 1,this.capacidadRestante() - a * DatosMochila.getPeso(this.index()));
		return r;
	}
	
	public Mochila anterior(Integer a) {
		return Mochila.of(this.index()-1, this.capacidadRestante() + a * DatosMochila.getPeso(this.index()-1));
	}
	
	public Integer greedyAction() {
		return Math.min(this.capacidadRestante/DatosMochila.getPeso(index),DatosMochila.getNumMaxDeUnidades(index));
	}

	public List<Integer> acciones() {
		if(this.index == DatosMochila.n) return new ArrayList<>();
		Integer nu = this.greedyAction();
		if(this.index == DatosMochila.n-1) return Lists2.of(nu);
		List<Integer> alternativas = IntStream.rangeClosed(0,nu)
				.boxed()
				.collect(Collectors.toList());
		Collections.reverse(alternativas);
		return alternativas;
	}

}

package us.lsi.alg.mochila.manual;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import us.lsi.alg.mochila.MochilaEdge;
import us.lsi.alg.mochila.MochilaVertex;


public class MochilaPD {
	
	public static Integer maxValue = Integer.MIN_VALUE;
	
	public static List<MochilaEdge> pd(Integer initialCapacity, Integer maxValue) {
		MochilaPD.maxValue = maxValue;
		Map<MochilaVertex,Spm> memory = new HashMap<>();
		MochilaVertex initial = MochilaVertex.initialVertex();
		pd(initial,0,memory);
		return edges(memory,initial);
	}
	
	public static Spm pd(MochilaVertex vertex, Integer accumulateValue, Map<MochilaVertex,Spm> memory) {
		Spm r;
		if(memory.containsKey(vertex)) {
			r = memory.get(vertex);
		} else if(vertex.index == MochilaVertex.n) {
			r = Spm.empty();
			memory.put(vertex,r);
			if(accumulateValue > maxValue) maxValue = accumulateValue;
		} else {
			List<Spm> soluciones = new ArrayList<>();
			for(Integer a:vertex.actions()) {	
				Double cota = accumulateValue + Auxiliar.cota(vertex,a);
				if( cota < maxValue) continue;				
				Spm s = pd(vertex.neighbor(a),accumulateValue+Auxiliar.pesoArista(vertex, a),memory);
				if(s!=null) {
					Spm sp = Spm.of(a,s.weight+Auxiliar.pesoArista(vertex, a));
					soluciones.add(sp);
				}
			}
			r = soluciones.stream().max(Comparator.naturalOrder()).orElse(null);
			if(r!=null) memory.put(vertex,r);
		}
		return r;
	}
	
	public static List<MochilaEdge> edges(Map<MochilaVertex,Spm> memory, MochilaVertex v){
		List<MochilaEdge> edges = new ArrayList<>();	
		Spm s = memory.get(v);
		while(s.a != null) {
			MochilaVertex old = v;
			v = old.neighbor(s.a);			
			edges.add(MochilaEdge.of(old, v, s.a));
			s = memory.get(v);
		}
		return edges;
	}

}

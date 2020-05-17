package us.lsi.search.secuencias;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import us.lsi.graphs.virtual.ActionI;

public class SeqAction extends ActionI<SeqVertex> {
	
	private static String sustitute(String s1, int index, String s2) {
		String pf = s1.substring(0,index);
		String sf = s1.substring(index+1,s1.length());
		return pf+s2.charAt(index)+sf;
	}
	
	private static String eliminate(String s1, int index) {
		String pf = s1.substring(0,index);
		String sf = s1.substring(index+1,s1.length());
		return pf+sf;
	}
	
	public static SeqAction of(Integer id, String name,Predicate<SeqVertex> isApplicable, Function<SeqVertex, SeqVertex> neighbor,
			Function<SeqVertex, Double> weight) {
		return new SeqAction(id,name,isApplicable, neighbor, weight);
	}

	private SeqAction(Integer id, String name,Predicate<SeqVertex> isApplicable, Function<SeqVertex, SeqVertex> neighbor,
			Function<SeqVertex, Double> weight) {
		super(id,name,isApplicable, neighbor, weight);
	}

	public static SeqAction a = SeqAction.of(0,"A",
			v->(v.n-v.index)==0 && (SeqVertex.n2-v.index) > 0,
			v->SeqVertex.of(v.index+1,v.s+SeqVertex.s2.charAt(v.index)),
			v->1.);
	
	public static SeqAction e = SeqAction.of(1,"E",
			v->((v.n-v.index)> 0 && (SeqVertex.n2-v.index) == 0) || 
					(v.n-v.index)>0 && (SeqVertex.n2-v.index) > 0  && v.s.charAt(v.index) != SeqVertex.s2.charAt(v.index) ,
			v->SeqVertex.of(v.index,eliminate(v.s,v.index)),
			v->1.);		
	
	public static SeqAction c = SeqAction.of(2,"C",
			v->(v.n-v.index)>0 && (SeqVertex.n2-v.index) > 0  && v.s.charAt(v.index) != SeqVertex.s2.charAt(v.index),
			v->SeqVertex.of(v.index,sustitute(v.s,v.index,SeqVertex.s2)),
			v->1.);	
	
	public static SeqAction m = SeqAction.of(3,"M",
			v->((v.n-v.index)>0 && (SeqVertex.n2-v.index) > 0)  && v.s.charAt(v.index) == SeqVertex.s2.charAt(v.index),
			v->SeqVertex.of(v.index+1,v.s),
			v->0.);	

	@Override
	public String toString() {
		return this.name;
	}

	public static List<SeqAction> actions = List.of(a,e,c,m);
}

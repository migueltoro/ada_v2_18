package us.lsi.alg.bufete;


import us.lsi.graphs.virtual.ActionSimpleEdge;

public record BufeteEdge(BufeteVertex source, BufeteVertex target, Integer action, Double weight)
        implements ActionSimpleEdge<BufeteVertex, Integer> {
	
	public static BufeteEdge of(BufeteVertex source, BufeteVertex target, Integer action) {
		Double w = DatosBufete.horas(action, source.index())*1.;
		return new BufeteEdge(source,target, action,w);
	}

}

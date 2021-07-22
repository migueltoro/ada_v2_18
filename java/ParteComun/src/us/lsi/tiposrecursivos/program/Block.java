package us.lsi.tiposrecursivos.program;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record Block(List<Sentence> sentences) implements Sentence{
	
	public static Block of(List<Sentence> sentences) {
		return new Block(sentences);
	}

	@Override
	public String toString() {
		return this.sentences.stream().map(x->x.toString()).collect(Collectors.joining(""));
	}
	
	@Override
	public String label() {
		return "Bloque";
	}
	
	@Override
	public void toDot(PrintStream file, Map<Object,Integer> map) {
		List<Sentence> dc = this.sentences();
		Integer d0n = Program.getIndex(dc.get(0),map,dc.get(0).label(),file);
		for(int i=1;i<dc.size();i++) {
			Integer dn = Program.getIndex(dc.get(i),map,dc.get(i).label(),file);
			Program.edge(d0n, dn, file);
			d0n = dn;
		}
		for(Sentence s:dc)
			s.toDot(file, map);
	}

}

package us.lsi.tiposrecursivos.program;

import java.util.List;
import java.util.stream.Collectors;

public record Block(List<Sentence> sentences) implements Sentence{
	
	public static Block of(List<Sentence> sentences) {
		return new Block(sentences);
	}

	@Override
	public String toString() {
		return this.sentences.stream().map(x->x.toString()).collect(Collectors.joining(""));
	}

}

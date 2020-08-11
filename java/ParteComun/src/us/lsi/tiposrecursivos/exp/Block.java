package us.lsi.tiposrecursivos.exp;

import java.util.List;
import java.util.stream.Collectors;

public class Block {
	
	public static Block of(List<Sentence> sentences) {
		return new Block(sentences);
	}

	public final List<Sentence> sentences;

	private Block(List<Sentence> sentences) {
		super();
		this.sentences = sentences;
	}
	
	@Override
	public String toString() {
		return this.sentences.stream().map(x->x.toString()).collect(Collectors.joining(""));
	}

}

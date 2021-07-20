package us.lsi.tiposrecursivos.program;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.tiposrecursivos.parsers.ProgramLexer;
import us.lsi.tiposrecursivos.parsers.ProgramParser;
import us.lsi.tiposrecursivos.parsers.ProgramVisitorC;

public record Program(List<Declaration> declarations, Block block) {
	
	public static Program of(List<Declaration> declarations, Block block) {
		return new Program(declarations, block);
	}
	
	public static Program parse(String file) throws IOException {
		ProgramLexer lexer = new ProgramLexer(CharStreams.fromFileName("ficheros/program.txt"));
		ProgramParser parser = new ProgramParser(new CommonTokenStream(lexer));
	    ParseTree parseTree = parser.program();
	    Program program =  (Program) parseTree.accept(new ProgramVisitorC());
	    return program;
	}


	@Override
	public String toString() {
		String s = this.declarations().stream().map(x->x.toString()).collect(Collectors.joining("\n"));
		return String.format("Declaraciones\n%s\nCodigo\n%s",s,this.block());
	}
}

package us.lsi.tiposrecursivos.parsers;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.tiposrecursivos.program.Program;
import us.lsi.tiposrecursivos.program.ProgramVisitorC;


public class TestProgram {
		
		public static void main(String[] args) throws IOException {
			ProgramLexer lexer = new ProgramLexer(CharStreams.fromFileName("ficheros/program.txt"));
			ProgramParser parser = new ProgramParser(new CommonTokenStream(lexer));
		    ParseTree parseTree = parser.program();
		    Program program =  (Program) parseTree.accept(new ProgramVisitorC());
		    System.out.println(program);
		}

	}


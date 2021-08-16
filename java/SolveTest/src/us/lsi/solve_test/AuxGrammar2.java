package us.lsi.solve_test;

import java.io.IOException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.Files2;
import us.lsi.model_test.PLIModelLexer;
import us.lsi.model_test.PLIModelParser;
import us.lsi.solve.AuxGrammar;

public class AuxGrammar2 {
	
	public static void generate(Class<?> dataClass, String model, String outFile) throws IOException {
		AuxGrammar.dataClass = dataClass;
	    PLIModelLexer lexer = new PLIModelLexer(CharStreams.fromFileName(model));
	    PLIModelParser parser = new PLIModelParser(new CommonTokenStream(lexer));
	    ParseTree tree = parser.model();
	    String answer = AuxGrammar.asString(tree.accept(new PLIModelVisitorC()));
	    Files2.toFile(answer,outFile);
	}

}

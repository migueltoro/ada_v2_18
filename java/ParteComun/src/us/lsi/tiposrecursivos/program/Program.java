package us.lsi.tiposrecursivos.program;

import java.io.IOException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.Printers;
import us.lsi.tiposrecursivos.parsers.ProgramLexer;
import us.lsi.tiposrecursivos.parsers.ProgramParser;

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
	
	public static Integer getIndex(Object e, Map<Object,Integer> map, String label, PrintStream file) {
		Integer r;
		if(map.containsKey(e)) r = map.get(e);
		else {
			r = map.get("maxValue")+1;
			map.put("maxValue",r);
			map.put(e, r);
			Program.vertex(r,label,file);
		}
		return r;
	}
	
	public static void vertex(Integer n, String text, PrintStream file) {
		String txt = String.format("\"%s\" [label=\"%s\"];",n,text);
		file.println(txt);
	}
	
	public static void edge(Integer v1, Integer v2, PrintStream file) {
		edge(v1,v2,null,file);
	}
	
	public static void edge(Integer v1, Integer v2, String text, PrintStream file) {
		String txt;
		if (text!=null) 
			txt = String.format("\"%s\" -> \"%s\" [label=\"%s\"];", v1, v2, text);
		else
			txt = String.format("\"%s\" -> \"%s\";", v1, v2);
		file.println(txt);
	}
	
	public void toDot(String file) {
		PrintStream p = Printers.file(file);
		Map<Object,Integer> map = new HashMap<>();
		map.put("maxValue",0);
		String txt = "digraph Program { \n \n"; 
		p.println(txt);
		toDot(p,map);
		p.println("}");
		p.close();
	}
	
	public void toDot(PrintStream file, Map<Object,Integer> map) {
		Integer pn = Program.getIndex(this,map,"Program", file);
		List<Declaration> dc = this.declarations();
		Sentence b = this.block().sentences().get(0);
		Integer bn = Program.getIndex(b,map,b.label(), file);
		Program.edge(pn, bn, "Cuerpo", file);
		Integer d0n = Program.getIndex(dc.get(0),map,dc.get(0).label(),file);
		Program.edge(pn, d0n,"Declaraciones", file);
		for(int i = 1;i<dc.size();i++) {
			Integer dn = Program.getIndex(dc.get(i),map,dc.get(i).label(),file);
			Program.edge(d0n, dn, file);
			d0n = dn;
		}
		b.toDot(file,map);
	}


	@Override
	public String toString() {
		String s = this.declarations()!=null?
				this.declarations().stream().filter(x->x!=null).map(x->x.toString()).collect(Collectors.joining("\n"))
				:"";
		String b = this.block()!=null?this.block().toString():"";
		return String.format("Declaraciones\n%s\nCodigo\n%s",s,b);
	}
}

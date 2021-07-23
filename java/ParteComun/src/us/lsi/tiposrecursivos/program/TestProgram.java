package us.lsi.tiposrecursivos.program;

import java.io.IOException;

public class TestProgram {

	public static void main(String[] args) throws IOException {
		Program p = Program.parse("ficheros/program.txt");
		if(p!=null) {
//			System.out.println(p);
			p.toDot("ficheros/program.gv");
//			System.out.println(p.block().sentences());
			IfThenElse s = (IfThenElse)p.block().sentences().get(0);
			Exp.toDot("ficheros/exp.gv",((Assign)s.trueBlock().sentences().get(0)).exp());
		}
		
	}
}

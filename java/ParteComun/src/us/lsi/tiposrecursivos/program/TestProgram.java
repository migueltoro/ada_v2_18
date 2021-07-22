package us.lsi.tiposrecursivos.program;

import java.io.IOException;

public class TestProgram {

	public static void main(String[] args) throws IOException {
		Program p = Program.parse("ficheros/program.txt");
		if(p!=null) {
			System.out.println(p);
			p.toDot("ficheros/program.gv");
		}
		
	}
}

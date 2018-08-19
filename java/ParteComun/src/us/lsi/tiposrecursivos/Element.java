package us.lsi.tiposrecursivos;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;


/**
 * 
 * Clase que modela un elemento de un programa
 * 
 * @author Miguel
 *
 */
public abstract class Element {
	/**
	 * @return Copia profunda del elemento
	 */
	public abstract Element copy();

	/**
	 * @return Igualdad
	 */
	public abstract boolean equals(Object other);
	/**
	 * @return Igualdad
	 */
	public abstract int hashCode();
	
	protected abstract String getId();
	
	public static PrintStream file = System.out;
	
	/**
	 * @return - Nombre del fichero dónde se almacenará el resultado
	 */
	public static PrintStream getFile() {
		return file;
	}

	/**
	 * @param f - Nombre del fichero dónde se almacenará el resultado
	 */
	public static void setFile(String f) {
		try {
			file = new PrintStream(new File(f));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("No se puede abrir el fichero "+f);
		}
	}
	
	public void toDOT(String file, String titulo) {
		Element.setFile(file);
		Element.getFile().println("digraph "+titulo+" {  \n    size=\"100,100\"; ");	
		setFlagFalse();
		toDOT(file);
		Element.getFile().println("}");
	}
	
	protected abstract void toDOT(String file);
	
	protected abstract void setFlagFalse();
	
	
}

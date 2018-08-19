package us.lsi.common;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;





public class Files2 {
		
	
	
	private static PipedOutputStream os = null;
	private static PipedInputStream is = null;
	private final static Integer bufferSize=1000000;
	private static ObjectOutputStream p;
	private static ObjectInputStream p2;
	
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
	
	public static Boolean existeFichero(String f){
		File file = new File(f);
		return file.exists();
	}
	
	public static <T extends Serializable> void guarda(T o, String f){
		if(existeFichero(f))
			throw new IllegalArgumentException("El fichero " + f + " ya existe");
		try{
			FileOutputStream ostream = new FileOutputStream(f);
			p = new ObjectOutputStream(ostream);
			p.writeObject(o);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T carga(String f){
		T o1=null;
		try{
		   FileInputStream istream = new FileInputStream(f);
		   p2 = new ObjectInputStream(istream);
		   o1 =(T) p2.readObject();
		}
		catch(Exception e){e.printStackTrace();}
		return o1;
	}
	
	@SuppressWarnings("unchecked")
	public static <T extends Serializable> T copia(T o){
		T o1 = null;
		try{
			if(os ==null)
				os = new PipedOutputStream();
			
			if(is==null)
				is = new PipedInputStream(os,bufferSize);
			
			ObjectOutputStream p = new ObjectOutputStream(os);
			p.writeObject(o);
			
			ObjectInputStream p1 = new ObjectInputStream(is);
			o1=(T)p1.readObject();
		}
		catch(Exception e){e.printStackTrace();}
		return o1;		
	}

	/**
	 * @param f Un nombre de fichero
	 * @return Un objeto adecuado para escribir el fichero abierto con el nombre f
	 */
	public static PrintWriter getWriter(String f) {
		PrintWriter fout = null;
		try {
			fout = new PrintWriter(new BufferedWriter(new FileWriter(f)));
		} catch (IOException e) {
			System.err.println(e.toString());
		}
		return fout;
	}

	/**
	 * @param f El nombre del fichero
	 * @return Una lista con las líneas del fichero
	 */
	public static List<String> getLines(String f) {
		List<String> lineas = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			lineas = bufferedReader.lines().collect(Collectors.toList());
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return lineas;
	}
	
	public static void toFile(Stream<String> s, String file) {
		try {
			final PrintWriter f = 
					new PrintWriter(new BufferedWriter(
							new FileWriter(file)));
			s.forEach(x->f.println(x));
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	public static void toFile(String s, String file) {
		try {
			final PrintWriter f = 
					new PrintWriter(new BufferedWriter(
							new FileWriter(file)));
			f.println(s);
			f.close();
		} catch (IOException e) {
			throw new IllegalArgumentException(
					"No se ha podido crear el fichero " + file);
		}
	}
	
	/**
	 * @param f El nombre del fichero
	 * @return Una lista con las líneas del fichero
	 */
	public static Stream<String> getStream(String f) {
		Stream<String> lineas = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
			lineas = bufferedReader.lines();
			bufferedReader.close();
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return lineas;
	}
	
	
	
	/**
	 * @param f El nombre del fichero
	 * @return El BufferedReader asociado
	 */
	public static BufferedReader get(String f) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
		} catch (IOException e) {
			System.out.println(e.toString());
		}
		return br;
	}
}


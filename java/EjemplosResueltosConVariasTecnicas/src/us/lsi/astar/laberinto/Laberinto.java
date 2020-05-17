package us.lsi.astar.laberinto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.common.IntPair;
import us.lsi.common.Lists2;
import us.lsi.common.Maps2;
import us.lsi.common.Preconditions;
import us.lsi.common.Streams2;

public class Laberinto {
	
	public static Casilla get(IntPair position) {
		return Laberinto.casillas.get(position);
	}
	
	public static boolean isValidPosition(IntPair p) {
		return  p.first >=0 && p.first< Laberinto.nf &&
				p.second >=0 && p.second < Laberinto.nc;
	}

	public static List<IntPair> actions  = Lists2.of(IntPair.of(1,0),
			IntPair.of(0,1),IntPair.of(-1,0),IntPair.of(0,-1));
	public static List<String> nombreAcciones = List.of("Down","Right","Up","Left");
	public static int nf;
	public static int nc;
	public static Map<IntPair,Casilla> casillas = Maps2.newHashMap();
	
	public static void leeDatos(String file, int nf, int nc){
		Laberinto.nf = nf;
		Laberinto.nc = nc;
		List<Integer> ls = Streams2.fromFile(file)
				.flatMap(x->Streams2.fromString(x, ","))
				.map(x->Integer.parseInt(x))
				.collect(Collectors.toList());
				
		Preconditions.checkState(ls.size()== nf*nc,
				String.format("No hay el número adecuado de datos size() = %d, nf = nc = %d, %d",ls.size(),nf,nc));
		
		int p = 0;
		for(int f = 0; f < nf; f++) {
			for(int  c= 0; c < nc; c++){
				IntPair position = IntPair.of(f, c);
				Casilla cs = Casilla.of(position, ls.get(p));
				casillas.put(position, cs);
				p++;
			}
		}
	}

	public static String showLaberinto(){		
		String s = "";
		for(int f = 0; f < Laberinto.nf; f++) {
			for(int c=0; c < nc; c++){
				IntPair position = IntPair.of(f, c);		
					Casilla cs = Laberinto.get(position);
					if(cs.getInfo() < 0){
						s = s+String.format("%3s", "X");
					}else{
						s = s+String.format("%3s",cs.getInfo()==0?"_":cs.getInfo().toString());
					}
			}
			s = s+"\n";
		}
	    return s;
	}
	
	
	public static String showSolucionLaberinto(GraphPath<Casilla,CasillaEdge> p){	
		List<Casilla> lc = p.getVertexList();
		String s = "";
		for(int f = 0; f < Laberinto.nf; f++) {
			for(int c=0; c < nc; c++){
				IntPair position = IntPair.of(f, c);		
					Casilla cs = Laberinto.get(position);
					if(cs.getInfo() < 0){
						s = s+String.format("%3s", "X");
					}else if (lc.contains(cs)){
						s = s+String.format("%3s","=");
					} else {
						s = s+String.format("%3s",cs.getInfo()==0?"_":cs.getInfo().toString());
					}
			}
			s = s+"\n";
		}
	    return s;
	}

}

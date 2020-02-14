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
import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.graphs.virtual.VirtualVertex;



public class Casilla extends ActionVirtualVertex<Casilla,SimpleEdge<Casilla>,IntPair> implements VirtualVertex<Casilla,SimpleEdge<Casilla>>{
	
	private static List<IntPair> actions  = Lists2.ofElements(IntPair.of(1,0),
			IntPair.of(0,1),IntPair.of(-1,0),IntPair.of(0,-1));
	
	private IntPair position;
	private Integer info;
	public static int numX;
	public static int numY;
	public static Map<IntPair,Casilla> casillas = Maps2.newHashMap();
	
	
	public static Casilla get(IntPair position) {
		return Casilla.casillas.get(position);
	}
	
	public static Casilla of(IntPair position) {
		return new Casilla(position);
	}
	
	private Casilla(IntPair position) {
		super();
		this.position = position;
		this.info = null;
		Preconditions.checkState(isValid(),String.format("Casilla no valida con %s,%s",this.position,this.info));
	}
	
	private Casilla(IntPair position, Integer info) {
		super();
		this.position = position;
		this.info = info;
		Preconditions.checkState(isValid(),String.format("Casilla no valida con %s,%s",this.position,this.info));
	}
	
	public Integer getX() {
		return this.position.a;
	}
	
	public Integer getY() {
		return this.position.b;
	}
	
	public IntPair getPosition() {
		return position;
	}
	
	public Integer getInfo() {
		return info;
	}

	public static void iniDatos(String nf, int nx, int ny){
		numX = nx;
		numY = ny;
		List<Integer> ls = Streams2.fromFile(nf)
				.flatMap(x->Streams2.fromString(x, ","))
				.map(x->Integer.parseInt(x))
				.collect(Collectors.toList());
				
		if(ls.size()!= numX*numY){
			throw new IllegalArgumentException("No hay el número adecuado de datos");
		}
		int p = 0;
		for(int y = numY-1; y>=0; y --) {
			for(int x=0; x < Casilla.numX;x++){
				IntPair position = IntPair.of(x, y);
				Casilla c = new Casilla(position, ls.get(p));
				casillas.put(position, c);
				p++;
			}
		}
	}

	

	@Override
	public String toString() {
		return String.format("(%s,%s)",this.position,this.info);
	}

	@Override
	public Boolean isNeighbor(Casilla e) {
		return Math.abs(this.getX()-e.getX()) + Math.abs(this.getY()-e.getY()) == 1;
	}
	
	@Override
	public boolean isValid() {
		return  this.getX() >=0 && this.getX()< numX && this.getY() >=0 && this.getY()< numY;
	}
	
	@Override
	protected List<IntPair> actions() {
		return Casilla.actions.parallelStream()
				.filter(a->Casilla.of(this.getPosition().add(a)).isValid())
				.collect(Collectors.toList());
	}

	@Override
	protected Casilla neighbor(IntPair a) {
		return casillas.get(this.position.add(a));
	}
	
	public static String show(GraphPath<Casilla,SimpleEdge<Casilla>> p){
		List<Casilla> lc = p.getVertexList();		
		String s = "";
		Casilla c;
		for(int y = Casilla.numY-1; y>=0; y--){
			for(int x=0; x < Casilla.numX;x++){
					IntPair position = IntPair.of(x, y);
					c = Casilla.of(position);
					if(lc.contains(c)){
						s = s+String.format("%3s", "X");
					}else{
						s = s+String.format("%3d",Casilla.get(position).getInfo());
					}
			}
			s = s+"\n";
		}
	    return s;
	}
	
}

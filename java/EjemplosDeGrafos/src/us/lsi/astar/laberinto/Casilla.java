package us.lsi.astar.laberinto;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.jgrapht.GraphPath;

import us.lsi.common.Lists2;

import us.lsi.common.Maps2;
import us.lsi.common.Streams2;
import us.lsi.common.Tuple;
import us.lsi.graphs.VirtualVertex;
import us.lsi.graphs.SimpleEdge;



public class Casilla implements VirtualVertex<Casilla,SimpleEdge<Casilla>> {
	private Integer x;
	private Integer y;
	private Integer info;
	public static int numX;
	public static int numY;
	public static Map<Casilla,Casilla> casillas = Maps2.newHashMap();
	public static Casilla get(Integer x, Integer y) {
		Casilla c = new Casilla(x,y);
		return casillas.get(c);
	}
	
	private Casilla(int x, int y) {
		super();
		this.x = x;
		this.y = y;
		this.info = null;
		if(!isValid()){
			throw new IllegalArgumentException("Casilla no valida con "+this.x+","+this.y+","+this.info);
		}
	}
	
	private Casilla(int x, int y, int info) {
		super();
		this.x = x;
		this.y = y;
		this.info = info;
		if(!isValid()){
			throw new IllegalArgumentException("Casilla no valida con "+this.x+","+this.y+","+this.info);
		}
	}
	
	public Integer getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public Integer getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
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
				Casilla c = new Casilla(x, y, ls.get(p));
				casillas.put(c, c);
				p++;
			}
		}
	}

	
	public static Casilla create(int x, int y) {
		return new Casilla(x, y);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Casilla other = (Casilla) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "(" + x + "," + y + "," + info + ")";
	}

	@Override
	public Set<Casilla> getNeighborListOf() {
		var ls = Lists2.newList(Tuple.create(1,0),
				Tuple.create(0,1),Tuple.create(-1,0),Tuple.create(0,-1));
		return ls.stream()
				 .map(x-> Tuple.create(x.v1+this.x, x.v2+this.y))
				 .filter(x-> x.v1>=0 && x.v1 < numX && x.v2>=0 && x.v2 < numY && 
				 		  Casilla.get(x.v1, x.v2).getInfo() >=0)
				 .map(x-> Casilla.create(x.v1,x.v2))
				 .collect(Collectors.<Casilla>toSet());
	}

	@Override
	public Set<SimpleEdge<Casilla>> edgesOf() {
		return getNeighborListOf().stream()
				.map(c->SimpleEdge.create(this,c))
				.collect(Collectors.toSet());
	}

	@Override
	public boolean isNeighbor(Casilla e) {
		return Math.abs(this.getX()-e.getX()) + Math.abs(this.getY()-e.getY()) == 1;
	}
	
	@Override
	public boolean isValid() {
		return  this.getX() >=0 && this.getX()< numX && this.getY() >=0 && this.getY()< numY;
	}
	
	public static String show(GraphPath<Casilla,SimpleEdge<Casilla>> p){
		List<Casilla> lc = p.getVertexList();		
		String s = "";
		Casilla c;
		for(int y = Casilla.numY-1; y>=0; y--){
			for(int x=0; x < Casilla.numX;x++){
					c = Casilla.create(x, y);
					if(lc.contains(c)){
						s = s+String.format("%3s", "X");
					}else{
						s = s+String.format("%3d",Casilla.get(x, y).getInfo());
					}
			}
			s = s+"\n";
		}
	    return s;
	}
	
	

	
	
}

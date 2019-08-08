package us.lsi.astar.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.graphs.SimpleEdge;
import us.lsi.graphs.virtual.ActionVirtualVertex;

public class VertexPuzzle extends ActionVirtualVertex<VertexPuzzle, SimpleEdge<VertexPuzzle>, ActionPuzzle> {

	/**
	 * @param d Lista de valores del puzzle dados por filas de abajo arriba
	 * @return Un EstadoPuzzle
	 */
	public static VertexPuzzle of(Integer... d) {
		return new VertexPuzzle(d);
	}
	
	public static VertexPuzzle of(Integer[][] datos, int x0, int y0) {
		return new VertexPuzzle(datos, x0, y0);
	}
	
	Integer[][] datos; 
	int x0;
	int y0;
	public static int numFilas = 3;
	
	private VertexPuzzle(Integer[][] datos, int x0, int y0) {
		super();
		this.datos = datos;
		this.x0 = x0;
		this.y0 = y0;
	}
	
	private VertexPuzzle(Integer... d) {
		super(); 
		this.datos = new Integer[VertexPuzzle.numFilas][VertexPuzzle.numFilas];
		Set<Integer> s = new HashSet<Integer>();	
		int x=0,y=0;
		for (int e:d) {							
					if(e<0 || e> (VertexPuzzle.numFilas*VertexPuzzle.numFilas-1)){
						throw new IllegalArgumentException();
					}
					if(e==0){
						this.x0 =x;
						this.y0 =y;
					}
					s.add(e);
					this.datos[x][y] = e;
					if(y== VertexPuzzle.numFilas-1){
						x++;
						y=0;
					}else{
						y++;
					}
				
		}
		if(d.length!= VertexPuzzle.numFilas*VertexPuzzle.numFilas || 
				s.size()!= VertexPuzzle.numFilas*VertexPuzzle.numFilas)
			throw new IllegalArgumentException();
	}
	
	@Override
	public boolean isValid() {
		Set<Integer> s = new HashSet<Integer>();	
		for (int y = 0; y < VertexPuzzle.numFilas; y++) {
			for (int x = 0; x < VertexPuzzle.numFilas; x++) {
				s.add(datos[x][y]);
			}
		}
		return s.size()== VertexPuzzle.numFilas*VertexPuzzle.numFilas;
	}

	@Override
	protected List<ActionPuzzle> actions() {
		return ActionPuzzle.actions().stream().
				filter(a->a.isApplicable(this))
				.collect(Collectors.toList());
	}

	@Override
	protected VertexPuzzle getThis() {
		return this;
	}

	@Override
	protected SimpleEdge<VertexPuzzle> getEdge(ActionPuzzle a) {
		return SimpleEdge.of(this, a.neighbor(this));
	}

	@Override
	protected VertexPuzzle neighbor(ActionPuzzle a) {
		return a.neighbor(this);
	}
	
	public Integer getDato(int x, int y) {
		if(x<0 || x>=VertexPuzzle.numFilas || y<0 || y>=VertexPuzzle.numFilas)
			throw new IllegalArgumentException();
		return datos[x][y];
	}
	
	public Integer getNumDiferentes(VertexPuzzle e){
		Integer s = 0;
		for (int x = 0; x < VertexPuzzle.numFilas; x++) {
			for (int y = 0; y < VertexPuzzle.numFilas; y++) {
				if (!this.datos[x][y].equals(e.datos[x][y])) {
					s++;
				}
			}
		}
		return s;
	}

	@Override
	public String toString() {
		String s = IntStream.range(0,VertexPuzzle.numFilas).boxed().map(y->fila(y)).collect(Collectors.joining("\n", "", ""));
		return s;
	}

	private String fila(int y) {
		return IntStream.range(0,VertexPuzzle.numFilas).boxed()
				.map(j->datos[y][j].toString()).collect(Collectors.joining("|", "|", "|"));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.deepHashCode(datos);
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
		VertexPuzzle other = (VertexPuzzle) obj;
		if (!Arrays.deepEquals(datos, other.datos))
			return false;
		return true;
	}
	
	
}

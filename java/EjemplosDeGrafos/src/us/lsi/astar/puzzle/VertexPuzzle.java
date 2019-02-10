package us.lsi.astar.puzzle;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import us.lsi.graphs.ActionVirtualVertex;
import us.lsi.graphs.SimpleEdge;

public class VertexPuzzle extends ActionVirtualVertex<VertexPuzzle, SimpleEdge<VertexPuzzle>, ActionPuzzle> {

	/**
	 * @param d Lista de valores del puzzle dados por filas de abajo arriba
	 * @return Un EstadoPuzzle
	 */
	public static VertexPuzzle of(Integer... d) {
		return new VertexPuzzle(d);
	}
	
	public static VertexPuzzle of(Integer[][] datos, int i0, int j0) {
		return new VertexPuzzle(datos, i0, j0);
	}
	
	Integer[][] datos; 
	int i0;
	int j0;
	public static int numFilas = 3;
	
	private VertexPuzzle(Integer[][] datos, int i0, int j0) {
		super();
		this.datos = datos;
		this.i0 = i0;
		this.j0 = j0;
	}
	
	private VertexPuzzle(Integer... d) {
		super(); 
		this.datos = new Integer[VertexPuzzle.numFilas][VertexPuzzle.numFilas];
		Set<Integer> s = new HashSet<Integer>();	
		int i=0,j=0;
		for (int e:d) {							
					if(e<0 || e> (VertexPuzzle.numFilas*VertexPuzzle.numFilas-1)){
						throw new IllegalArgumentException();
					}
					if(e==0){
						this.i0 =i;
						this.j0 =j;
					}
					s.add(e);
					this.datos[i][j] = e;
					if(j== VertexPuzzle.numFilas-1){
						i++;
						j=0;
					}else{
						j++;
					}
				
		}
		if(d.length!= VertexPuzzle.numFilas*VertexPuzzle.numFilas || 
				s.size()!= VertexPuzzle.numFilas*VertexPuzzle.numFilas)
			throw new IllegalArgumentException();
	}
	
	@Override
	public boolean isValid() {
		Set<Integer> s = new HashSet<Integer>();	
		for (int j = 0; j < VertexPuzzle.numFilas; j++) {
			for (int i = 0; i < VertexPuzzle.numFilas; i++) {
				s.add(datos[i][j]);
			}
		}
		return s.size()== VertexPuzzle.numFilas*VertexPuzzle.numFilas;
	}

	@Override
	protected List<ActionPuzzle> acciones() {
		return ActionPuzzle.actions();
	}

	@Override
	protected VertexPuzzle getThis() {
		return this;
	}

	@Override
	protected SimpleEdge<VertexPuzzle> getEdge(VertexPuzzle v, ActionPuzzle a) {
		return SimpleEdge.of(v, a.neighbor(v));
	}

	public Integer getDato(int i, int j) {
		if(i<0 || i>=VertexPuzzle.numFilas || j<0 || j>=VertexPuzzle.numFilas)
			throw new IllegalArgumentException();
		return datos[i][j];
	}
	
	public Integer getNumDiferentes(VertexPuzzle e){
		Integer s = 0;
		for (int i = 0; i < VertexPuzzle.numFilas; i++) {
			for (int j = 0; j < VertexPuzzle.numFilas; j++) {
				if (!this.datos[i][j].equals(e.datos[i][j])) {
					s++;
				}
			}
		}
		return s;
	}

}

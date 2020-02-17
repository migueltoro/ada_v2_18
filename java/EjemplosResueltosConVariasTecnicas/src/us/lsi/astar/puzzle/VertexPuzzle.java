package us.lsi.astar.puzzle;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import us.lsi.common.Arrays2;
import us.lsi.common.IntPair;
import us.lsi.common.Preconditions;
import us.lsi.graphs.virtual.ActionVirtualVertex;
import us.lsi.graphs.virtual.ActionSimpleEdge;



public class VertexPuzzle extends ActionVirtualVertex<VertexPuzzle, ActionSimpleEdge<VertexPuzzle,ActionPuzzle>, ActionPuzzle> {

	/**
	 * @param d Lista de valores del puzzle dados por filas de abajo arriba
	 * @return Un EstadoPuzzle
	 */
	public static VertexPuzzle of(Integer... d) {				
		return new VertexPuzzle(d);
	}
	
	public static VertexPuzzle of(Integer[][] datos, IntPair blackPosition) {
		return new VertexPuzzle(datos, blackPosition);
	}
	
	private final Integer[][] datos; 
	private final IntPair blackPosition;
	public static int numFilas = 3;
	
	private static boolean validDato(Integer d) {
		return 0<=d && d < VertexPuzzle.numFilas*VertexPuzzle.numFilas;
	}
	
	public boolean validPosition(IntPair p) {
		return p.a>=0 && p.a< VertexPuzzle.numFilas && p.b>=0 && p.b<VertexPuzzle.numFilas;
	}
	
	private VertexPuzzle(Integer...datos) {
		Integer n = VertexPuzzle.numFilas;
		Integer dt[][] = Arrays2.toMultiArray(datos, n, n);	
		IntPair bp = Arrays2.findPosition(dt, e->e==0);
		this.datos = dt;
		this.blackPosition = bp;
		this.isValid();
	}
	
	private VertexPuzzle(Integer[][] datos, IntPair blackPosition) {
		super();
		this.datos = Arrays2.copyArray(datos);
		this.blackPosition = blackPosition;
	}
	
	@Override
	public Boolean isValid() {
		Integer n = VertexPuzzle.numFilas;
		Set<Integer> s = Arrays.stream(this.datos)
				.flatMap(f->Arrays.stream(f))
				.filter(e->VertexPuzzle.validDato(e))
				.collect(Collectors.toSet());
		return s.size()== n*n;
	}	

	public IntPair getBlackPosition() {
		return blackPosition;
	}

	@Override
	protected List<ActionPuzzle> actions() {
		return ActionPuzzle.actions().stream().
				filter(a->a.isApplicable(this))
				.collect(Collectors.toList());
	}
	
	@Override
	protected VertexPuzzle neighbor(ActionPuzzle a) {
		Preconditions.checkArgument(a.isApplicable(this), String.format("La acción %s no es aplicable",a.toString()));
		IntPair np = this.blackPosition.add(a.direction());
		IntPair op = this.blackPosition;
		Integer dd[][] = Arrays2.copyArray(this.datos);
		Integer value = dd[np.a][np.b];
		dd[op.a][op.b] = value;
		dd[np.a][np.b] = 0;
		VertexPuzzle v  = VertexPuzzle.of(dd,np);
		Preconditions.checkState(!this.equals(v),String.format("No deben ser iguales %s \n %s \n %s",a.toString(),this.toString(),v.toString()));
		return v;
	}
	
	public Integer getDato(int x, int y) {
		return getDato(IntPair.of(x, y));
	}
	
	public Integer getDato(IntPair p) {
		Preconditions.checkArgument(validPosition(p),"No se cumple la precondición");
		return datos[p.a][p.b];
	}
	
	public Integer getNumDiferentes(VertexPuzzle e){
		Integer n = VertexPuzzle.numFilas;
		Long s = IntStream.range(0,n).boxed()
				.flatMap(f->IntStream.range(0,n).boxed().map(c->IntPair.of(f, c)))
				.filter(p->this.getDato(p) != e.getDato(p))
				.count();
		return s.intValue();
	}

	@Override
	public String toString() {
		String s = IntStream.range(0,VertexPuzzle.numFilas).boxed()
				.map(y->fila(y))
				.collect(Collectors.joining("\n", "", ""));
		return s;
	}

	private String fila(int y) {
		Integer n = VertexPuzzle.numFilas;
		return IntStream.range(0,n).boxed()
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

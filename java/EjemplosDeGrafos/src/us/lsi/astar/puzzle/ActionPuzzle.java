package us.lsi.astar.puzzle;

import java.util.Arrays;
import java.util.List;

import us.lsi.graphs.Action;

public class ActionPuzzle implements Action<VertexPuzzle> {

	public static ActionPuzzle of(Integer incx, Integer incy) {
		return new ActionPuzzle(incx, incy);
	}
	
	public static List<ActionPuzzle> actions() {
		return Arrays.asList(of(-1,0), of(1,0),of(0,-1),of(0,1));
	}

	Integer incx;
	Integer incy;	
	
	private ActionPuzzle(Integer incx, Integer incy) {
		super();
		this.incx = incx;
		this.incy = incy;
	}

	@Override
	public VertexPuzzle neighbor(VertexPuzzle v) {
		int f = v.i0+incx;
		int c = v.j0+incy;
		if(f<0 || f>=VertexPuzzle.numFilas || c<0 || c>=VertexPuzzle.numFilas)
			throw new IllegalArgumentException();
		Integer[][] dd = new Integer[VertexPuzzle.numFilas][VertexPuzzle.numFilas];
		for (int i = 0; i < VertexPuzzle.numFilas; i++) {
			for (int j = 0; j < VertexPuzzle.numFilas; j++) {
				dd[i][j] = v.datos[i][j];
			}
		}
		dd[f][c] = v.datos[v.i0][v.j0];
		dd[v.i0][v.j0] = v.datos[f][c];
		return VertexPuzzle.of(dd, f, c);
	}

	@Override
	public boolean isApplicable(VertexPuzzle v) {
		return false;
	}

}

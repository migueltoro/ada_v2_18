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
		int x = v.x0+incx;
		int y = v.y0+incy;
		if(x<0 || x>=VertexPuzzle.numFilas || y<0 || y>=VertexPuzzle.numFilas)
			throw new IllegalArgumentException();
		Integer[][] dd = new Integer[VertexPuzzle.numFilas][VertexPuzzle.numFilas];
		for (int i = 0; i < VertexPuzzle.numFilas; i++) {
			for (int j = 0; j < VertexPuzzle.numFilas; j++) {
				dd[i][j] = v.datos[i][j];
			}
		}
		dd[x][y] = v.datos[v.x0][v.y0];
		dd[v.x0][v.y0] = v.datos[x][y];
		return VertexPuzzle.of(dd, x, y);
	}

	@Override
	public boolean isApplicable(VertexPuzzle v) {
		Boolean r = false;
		int x = v.x0+incx;
		int y = v.y0+incy;
		if(x>=0 && x<VertexPuzzle.numFilas && y>=0 && y<VertexPuzzle.numFilas)	r = true;		
		return r;
	}

}

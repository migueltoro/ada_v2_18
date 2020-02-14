package us.lsi.astar.puzzle;

import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.Action;

public class ActionPuzzle implements Action<VertexPuzzle> {

	public static ActionPuzzle of(Integer incx, Integer incy) {
		return new ActionPuzzle(IntPair.of(incx, incy));
	}
	
	private static List<ActionPuzzle> actions = List.of(ActionPuzzle.of(-1,0), ActionPuzzle.of(1,0),ActionPuzzle.of(0,-1),ActionPuzzle.of(0,1));
	
	public static List<ActionPuzzle> actions() {
		return ActionPuzzle.actions;
	}
	
	public List<String> nombreAcciones = List.of("Up","Down","Left","Right");

	private IntPair direction;
	private Integer index;
	
	private ActionPuzzle(IntPair direction) {
		super();
		this.direction = direction;
		this.index = null;
	}
	
	public IntPair direction() {
		return this.direction;
	}
	
	public Integer getIndex() {
		if(this.index == null) this.index = actions.indexOf(this);
		return this.index;
	}

	@Override
	public VertexPuzzle neighbor(VertexPuzzle v) {
		return v.neighbor(this);
	}

	@Override
	public boolean isApplicable(VertexPuzzle v) {
		return v.validPosition(v.getBlackPosition().add(this.direction()));
	}

	@Override
	public String toString() {
		return String.format("%s,%d,%s",nombreAcciones.get(this.getIndex()),this.index,this.direction.toString());
	}

}

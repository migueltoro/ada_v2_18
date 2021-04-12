package us.lsi.alg.puzzle;

import java.util.List;

import us.lsi.common.IntPair;
import us.lsi.graphs.virtual.Action;

public class ActionPuzzle implements Action<VertexPuzzle> {
	
	public static ActionPuzzle of(String name) {
		Integer index = -1;
		switch(name) {
		case "Up" : index = 0; break;
		case "Down" : index = 1; break;
		case "Left" : index = 2; break;
		case "Right" : index = 3; break;
		}		
		return actions.get(index);
	}
	
	public static ActionPuzzle of(IntPair direction, String name) {
		return new ActionPuzzle(direction, name);
	}
	
	public static final List<ActionPuzzle> actions = 
			List.of(ActionPuzzle.of(IntPair.of(-1,0),"Up"), ActionPuzzle.of(IntPair.of(1,0),"Down"),
					ActionPuzzle.of(IntPair.of(0,-1),"Left"),ActionPuzzle.of(IntPair.of(0,1),"Right"));

	public final IntPair direction;
	public final String name;
	
	private ActionPuzzle(IntPair direction, String name) {
		super();
		this.direction = direction;
		this.name = name;
	}
	
	@Override
	public VertexPuzzle neighbor(VertexPuzzle v) {
		return v.neighbor(this);
	}

	@Override
	public boolean isApplicable(VertexPuzzle v) {
		return v.validPosition(v.getBlackPosition().add(this.direction));
	}

	@Override
	public String toString() {
		return String.format("%s,%s",this.name,this.direction.toString());
	}

	@Override
	public Double weight(VertexPuzzle v) {
		return 1.;
	}

}

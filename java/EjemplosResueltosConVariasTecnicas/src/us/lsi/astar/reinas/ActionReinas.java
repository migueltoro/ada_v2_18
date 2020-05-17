package us.lsi.astar.reinas;

import java.util.List;

import us.lsi.common.Lists2;
import us.lsi.common.SetRangeInteger;
import us.lsi.graphs.virtual.Action;

public class ActionReinas implements Action<VertexReinas> {

	public static ActionReinas of(Integer y) {
		return new ActionReinas(y);
	}

	private ActionReinas(Integer y) {
		super();
		this.y = y;
	}

	private Integer y;

	@Override
	public VertexReinas neighbor(VertexReinas v) {
		Integer dp = this.y - v.x;
		Integer ds = this.y + v.x;
		List<Integer> yO = Lists2.copy(v.yO);
		yO.add(y);
		SetRangeInteger dpO = v.dpO.copy();
		dpO.add(dp);
		SetRangeInteger dsO = v.dsO.copy();
		dsO.add(ds);
		return VertexReinas.of(v.x+1, yO, dpO, dsO);
	}
	
	@Override
	public boolean isApplicable(VertexReinas v) {
		Integer dp = this.y - v.x;
		Integer ds = this.y + v.x;
		return !(v.yO.contains(this.y) || v.dpO.contains(dp) || v.dsO.contains(ds));
	}

	@Override
	public String toString() {
		return this.y.toString();
	}

	@Override
	public Double weight(VertexReinas v) {
		return 1.;
	}

}

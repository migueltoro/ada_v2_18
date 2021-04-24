package us.lsi.graphs.virtual;

import us.lsi.graphs.SimpleEdge;

public class ActionSimpleEdge<V, A> extends SimpleEdge<V> {
	
	private final A action;
	
	public static <V, A> ActionSimpleEdge<V, A> of(V c1, V c2, A action, Double weight) {
		return new ActionSimpleEdge<V, A>(c1, c2, action, weight);
	}

	protected ActionSimpleEdge(V source, V target, A action, Double weight) {
		super(source, target, weight);
		this.action = action;
	}

	public A getAction() {
		return action;
	}

	@Override
	public String toString() {
		return "ActionSimpleEdge ["+ "source=" + getSource() + ", target=" + getTarget() + 
				", action=" + getAction()+", weight=" + getWeight()
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActionSimpleEdge<?,?> other = (ActionSimpleEdge<?,?>) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		return true;
	}
	
}

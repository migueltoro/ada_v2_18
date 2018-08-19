package us.lsi.common;

import java.util.List;

public interface Tuple {
	
	public static <T1, T2> Tuple2<T1, T2> create(T1 p1, T2 p2) {
		return new Tuple2<T1, T2>(p1, p2);
	}
	
	public static <T1, T2, T3> Tuple3<T1, T2, T3> create(T1 p1, T2 p2, T3 p3) {
		return new Tuple3<T1, T2, T3>(p1, p2, p3);
	}
	
	public static <T1, T2, T3, T4> Tuple4<T1, T2, T3, T4> create(T1 p1, T2 p2, T3 p3, T4 p4) {
		return new Tuple4<T1, T2, T3, T4>(p1, p2, p3, p4);
	}
	
	public static <T1, T2, T3, T4, T5> Tuple5<T1, T2, T3, T4,T5> create(T1 p1, T2 p2, T3 p3, T4 p4, T5 p5) {
		return new Tuple5<T1, T2, T3, T4, T5>(p1, p2, p3, p4, p5);
	}
	
	public static <T> Tuple2<T, T> asTuple2(List<T> t) {
		Preconditions.checkArgument(t.size()==2);
		return create(t.get(0), t.get(1));
	}
	
	public static <T> Tuple2<T, T> asTuple2(T[] t) {
		Preconditions.checkArgument(t.length==2);
		return create(t[0], t[1]);
	}
	
	public static <T> Tuple3<T, T, T> asTuple3(List<T> t) {
		Preconditions.checkArgument(t.size()==3);
		return create(t.get(0), t.get(1), t.get(2));
	}
	
	public static <T> Tuple3<T, T, T> asTuple3(T[] t) {
		Preconditions.checkArgument(t.length==3);
		return create(t[0], t[1], t[2]);
	}

	public static <T> Tuple4<T, T, T, T> asTuple4(List<T> t) {
		Preconditions.checkArgument(t.size()==4);
		return create(t.get(0), t.get(1), t.get(2), t.get(3));
	}
	
	public static <T> Tuple4<T, T, T, T> asTuple4(T[] t) {
		Preconditions.checkArgument(t.length==4);
		return create(t[0], t[1], t[2], t[3]);
	}
	
	public static <T> Tuple5<T, T, T, T, T> asTuple5(List<T> t) {
		Preconditions.checkArgument(t.size()==5);
		return create(t.get(0), t.get(1), t.get(2), t.get(3), t.get(4));
	}
	
	public static <T> Tuple5<T, T, T, T, T> asTuple5(T[] t) {
		Preconditions.checkArgument(t.length==5);
		return create(t[0], t[1], t[2], t[3], t[4]);
	}
}

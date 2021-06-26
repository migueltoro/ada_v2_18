package us.lsi.ag;

public interface BinaryData<S> extends ValuesInRangeData<Integer,S>{
	
	default Integer getMax(Integer i) {
		return 2;
	}
	
	default Integer getMin(Integer i) {
		return 0;
	}

}

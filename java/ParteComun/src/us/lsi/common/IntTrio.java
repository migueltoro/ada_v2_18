package us.lsi.common;

public class IntTrio extends Trio<Integer,Integer,Integer>{

	public static IntTrio of(Integer first, Integer second, Integer third) {
		return new IntTrio(first, second, third);
	}

	private IntTrio(Integer first, Integer second, Integer third) {
		super(first,second,third);
	}	
}

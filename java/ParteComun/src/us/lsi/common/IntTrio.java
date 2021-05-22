package us.lsi.common;

public record IntTrio(Integer first, Integer second, Integer third) {

	public static IntTrio of(Integer first, Integer second, Integer third) {
		return new IntTrio(first, second, third);
	}
	
}

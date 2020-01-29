package us.lsi.common;

/**
 * @author migueltoro
 *
 * Rangos de valores de tipos numéricos
 */
public class Ranges {

	public static class IntRange {
		public final Integer a;
		public final Integer b;
		public final Integer c;
		public static IntRange of(Integer a, Integer b, Integer c) {
			return new IntRange(a,b,c);
		}
		public static IntRange of(Integer a, Integer b) {
			return new IntRange(a,b,1);
		}
		public IntRange(Integer a, Integer b, Integer c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}
		public Boolean isEmpty() {
			return a>=b;
		}
		public Boolean contains(Integer e) {
			return e>=a && e < b;
		}
		public Integer size() {
			return (b-a)/c;
		}
		public View1<IntRange,Integer> view1(){
			return new IntView1(this.a,IntRange.of(a+c,b));
		}
		public View2<IntRange,Integer> view2(){
			Integer central = (b+a)/(2*c)*c;
			return new IntView2(central,IntRange.of(a,central,c),IntRange.of(central,b,c));
		}
		public View2<IntRange,Integer> view2Overlapping(){
			Integer central = (b+a)/(2*c)*c;
			return new IntView2(central,IntRange.of(a,central+c,c),IntRange.of(central,b,c));
		}
		@Override
		public String toString() {
			String sc = c==1?"":("," + c);
			return "[" + a + "," + b + sc + ")";
		}	
	}
	
	private static class IntView1 implements View1<IntRange,Integer>{
		private Integer element;
		private IntRange rest;		
		public IntView1(Integer element, IntRange rest) {
			super();
			this.element = element;
			this.rest = rest;
		}
		@Override
		public Integer element() {
			return element;
		}
		@Override
		public IntRange rest() {
			return rest;
		}		
	}
	
	private static class IntView2 implements View2<IntRange,Integer>{
		public final Integer centralElement;
		public final IntRange left;
		public final IntRange right;		
		public IntView2(Integer centralElement, IntRange left, IntRange right) {
			super();
			this.centralElement = centralElement;
			this.left = left;
			this.right = right;
		}
		@Override
		public Integer centralElement() {
			return centralElement;
		}
		@Override
		public IntRange left() {
			return left;
		}
		@Override
		public IntRange right() {
			return right;
		}				
	}
	
	
	public static class LongRange {
		public final Long a;
		public final Long b;
		public final Long c;
		public static LongRange of(Long a, Long b, Long c) {
			return new LongRange(a,b,c);
		}
		public static LongRange of(Long a, Long b) {
			return new LongRange(a,b,1L);
		}
		public LongRange(Long a, Long b, Long c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}
		public Boolean isEmpty() {
			return a>=b;
		}
		public Boolean contains(Long e) {
			return e>=a && e < b;
		}
		public Long size() {
			return (b-a)/c;
		}
		public View1<LongRange,Long> view1(){
			return new LongRangeView1(this.a,LongRange.of(a+c,b));
		}
		public View2<LongRange,Long> view2(){
			Long central = (b+a)/(2*c)*c;
			return new LongRangeView2(central,LongRange.of(a,central,c),LongRange.of(central,b,c));
		}
		public View2<LongRange,Long> view2Overlapping(){
			Long central = (b+a)/(2*c)*c;
			return new LongRangeView2(central,LongRange.of(a,central+c,c),LongRange.of(central,b,c));
		}
		@Override
		public String toString() {
			String sc = c==1?"":("," + c);
			return "[" + a + "," + b + sc + ")";
		}
	}
	
	private static class LongRangeView1 implements View1<LongRange,Long>{
		private Long element;
		private LongRange rest;		
		public LongRangeView1(Long element, LongRange rest) {
			super();
			this.element = element;
			this.rest = rest;
		}
		@Override
		public Long element() {
			return element;
		}
		@Override
		public LongRange rest() {
			return rest;
		}		
	}
	
	private static class LongRangeView2 implements View2<LongRange,Long>{
		public final Long centralElement;
		public final LongRange left;
		public final LongRange right;		
		public LongRangeView2(Long centralElement, LongRange left, LongRange right) {
			super();
			this.centralElement = centralElement;
			this.left = left;
			this.right = right;
		}
		@Override
		public Long centralElement() {
			return centralElement;
		}
		@Override
		public LongRange left() {
			return left;
		}
		@Override
		public LongRange right() {
			return right;
		}				
	}
	
	
	public static class DoubleRange {
		public final Double a;
		public final Double b;
		public final Double c;
		public static DoubleRange of(Double a, Double b, Double c) {
			return new DoubleRange(a,b,c);
		}
		public static DoubleRange of(Double a, Double b) {
			return new DoubleRange(a,b,1.);
		}
		DoubleRange(Double a, Double b, Double c) {
			super();
			this.a = a;
			this.b = b;
			this.c = c;
		}
		public Boolean isEmpty() {
			return a>=b;
		}
		public Boolean contains(Double e) {
			return e>=a && e < b;
		}
		public Double size() {
			return (b-a)/c;
		}
		public View1<DoubleRange,Double> view1(){
			return new DoubleRangeView1(this.a,DoubleRange.of(a+c,b));
		}
		public View2<DoubleRange,Double> view2(){
			Double central = (b+a)/2;
			return new DoubleRangeView2(central,DoubleRange.of(a,central,c),DoubleRange.of(central,b,c));
		}
		public View2<DoubleRange,Double> view2Overlapping(){
			Double central = (b+a)/2;
			return new DoubleRangeView2(central,DoubleRange.of(a,central+c,c),DoubleRange.of(central,b,c));
		}
		@Override
		public String toString() {
			String sc = c==1?"":("," + c);
			return "[" + a + "," + b + sc + ")";
		}
	}
	
	private static class DoubleRangeView1 implements View1<DoubleRange,Double>{
		private Double element;
		private DoubleRange rest;				
		public DoubleRangeView1(Double element, DoubleRange rest) {
			super();
			this.element = element;
			this.rest = rest;
		}
		@Override
		public Double element() {
			return element;
		}
		@Override
		public DoubleRange rest() {
			return rest;
		}		
	}
	
	private static class DoubleRangeView2 implements View2<DoubleRange,Double>{
		public final Double centralElement;
		public final DoubleRange left;
		public final DoubleRange right;				
		public DoubleRangeView2(Double centralElement, DoubleRange left, DoubleRange right) {
			super();
			this.centralElement = centralElement;
			this.left = left;
			this.right = right;
		}
		@Override
		public Double centralElement() {
			return centralElement;
		}
		@Override
		public DoubleRange left() {
			return left;
		}
		@Override
		public DoubleRange right() {
			return right;
		}		
	}
	
}

package us.lsi.tiposrecursivos;

import java.util.Map;
import java.util.function.Function;
import us.lsi.tiposrecursivos.BinaryPatternImpl.Label;
import us.lsi.tiposrecursivos.BinaryPatternImpl.PatternType;

public interface BinaryPattern<E> {
	
	public static <E> BinaryPattern<E> binary(Label<E> label, BinaryPattern<E> left, BinaryPattern<E> right) {
		return BinaryPatternImpl.binary(label, left, right);
	}
	
	public static <E> BinaryPattern<E> leaf(Label<E> label) {
		return BinaryPatternImpl.leaf(label);
	}
	
	public static <E> BinaryPattern<E> empty() {
		return BinaryPatternImpl.empty();
	}
	
	public static <E> BinaryPattern<E> variable(String name) {
		return BinaryPatternImpl.variable(name);
	}
	
	public static <E> BinaryPattern<E> parse(String s, Function<String,E> f) {
		return BinaryPatternImpl.parse(s,f);
	}
	
	public static <E> BinaryTree<E> transform(BinaryTree<E> tree, BinaryPattern<E> pattern, BinaryPattern<E> result){
		return BinaryPatternImpl.transform(tree, pattern, result);
	}
	
	public static <E> BinaryPattern<E> parse(String s){
		return BinaryPatternImpl.parse(s);
	}
	
	boolean isEmpty();

	boolean isLeaf();

	boolean isBinary();

	boolean isVariable();

	Label<E> getLabel();

	BinaryPattern<E> getLeft();

	BinaryPattern<E> getRight();

	Variable<BinaryTree<E>> asVariable();

	PatternType getType();

	Map<String, E> varLabels();

	Map<String, BinaryTree<E>> varTrees();

	String toString();

	<R> BinaryPattern<R> map(Function<E, R> f);

	BinaryTree<E> valuesToVariables();

	BinaryTree<E> valuesToVariables(Map<String, E> labels, Map<String, BinaryTree<E>> trees);
	
	

}
package us.lsi.tiposrecursivos;


import java.util.function.Function;

import us.lsi.tiposrecursivos.BinaryPatternImpl.Matches;

public interface BinaryPattern<E> {
	
	public enum PatternType{Empty, Leaf, Binary, Binary_Variable, Variable}
	
	public static <E> BinaryPattern<E> binary(E label, BinaryPattern<E> left, BinaryPattern<E> right) {
		return BinaryPatternImpl.binary(label, left, right);
	}
	
	public static <E> BinaryPattern<E> binary_variable(String variable_name, BinaryPattern<E> left, BinaryPattern<E> right) {
		return BinaryPatternImpl.binary_variable(variable_name, left, right);
	}
	
	public static <E> BinaryPattern<E> leaf(E label) {
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
	
	public static <E> BinaryPattern<E> parse(String s) {
		return BinaryPattern.parse(s,x->null);
	}
	
	public static <E> Matches<E> match(BinaryTree<E> tree, BinaryPattern<E> pt){
		return BinaryPatternImpl.match(tree,pt);
	}
	
	public static <E> BinaryTree<E> transform(BinaryTree<E> tree, BinaryPattern<E> pattern, BinaryPattern<E> result){
		return BinaryPatternImpl.transform(tree, pattern, result);
	}
	
	boolean isEmpty();

	boolean isLeaf();

	boolean isBinary();
	
	boolean isBinary_Variable();

	boolean isVariable();

	E getLabel();

	BinaryPattern<E> getLeft();

	BinaryPattern<E> getRight();

	PatternType getType();
	
	String getVariable_Name();

	String toString();

	<R> BinaryPattern<R> map(Function<E, R> f);
	
	BinaryTree<E> toBinaryTree(Matches<E> matches);

}
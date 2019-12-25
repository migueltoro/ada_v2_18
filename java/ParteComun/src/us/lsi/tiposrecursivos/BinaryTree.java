package us.lsi.tiposrecursivos;

import java.util.List;
import java.util.function.Function;


public interface BinaryTree<E> {
	
	public enum BinaryType{Empty,Leaf,Binary}
	public enum ChildType{Left,Right,Root}
	
	public static <E> BinaryTree<E> empty() {
		return BinaryTreeImpl.empty();
	}
	
	public static <E> BinaryTree<E> leaf(E label) {
		return BinaryTreeImpl.leaf(label);
	}
	
	public static <E> BinaryTree<E> binary(E label, BinaryTree<E> left, BinaryTree<E> right) {
		return BinaryTreeImpl.binary(label, (BinaryTreeImpl<E>)left, (BinaryTreeImpl<E>)right);
	}
	
	public static BinaryTree<String> parse(String s){
		return BinaryTreeImpl.parse(s);
	}
	
	public static <E> BinaryTree<E> parse(String s, Function<String,E> f) {
		BinaryTreeImpl<String> tree = BinaryTreeImpl.parse(s);
		return tree.map(f);
	}
	
	boolean isEmpty();

	boolean isLeaf();

	boolean isBinary();

	BinaryType getType();

	BinaryTree<E> getFather();

	boolean isRoot();

	Boolean isLeftChild();

	Boolean isRightChild();

	ChildType getChildType();

	E getLabel();

	BinaryTree<E> getLeft();

	BinaryTree<E> getRight();

	MutableBinaryTree<E> mutableView();

	int size();

	int getHeight();

	List<BinaryTree<E>> getLevel(int n);

	List<Integer> getHeights(int n);

	BinaryTree<E> copy();

	<R> BinaryTree<R> copy(Function<E, R> f);

	BinaryTree<E> getReverse();

	<R> BinaryTree<R> map(Function<E, R> f);

	BinaryTree<E> transform(BinaryPattern<E> pattern, BinaryPattern<E> result);

	BinaryPattern<E> match(BinaryPattern<E> pt);

	String toString();

	List<E> getPreOrder();

	List<E> getPostOrder();

	List<E> getInOrder();

}
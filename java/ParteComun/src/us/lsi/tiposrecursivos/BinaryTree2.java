package us.lsi.tiposrecursivos;

import us.lsi.tiposrecursivos.BinaryTree2.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree2.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree2.BTree;

public sealed interface BinaryTree2<E> permits BEmpty<E>,BLeaf<E>,BTree<E> {
	
	
	public static <E> BinaryTree2<E> of() {
		return new BEmpty<E>();
	}
	
	public static <E> BinaryTree2<E> of(E label) {
		return new BLeaf<E>(label);
	}
	
	public static <E> BinaryTree2<E> of(E label, BinaryTree2<E> left, BinaryTree2<E> right) {
		return new BTree<E>(label,left,right);
	}
	
	public static record BEmpty<E>() implements BinaryTree2<E> {}
	
	public static record BLeaf<E>(E label) implements BinaryTree2<E> {}
	
	public static record BTree<E>(E label, BinaryTree2<E> left, BinaryTree2<E> right) 
		implements BinaryTree2<E> {}

}

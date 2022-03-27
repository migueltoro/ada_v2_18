package us.lsi.tiposrecursivos;

import us.lsi.tiposrecursivos.BinaryTree2.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree2.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree2.BTree;

public class BinaryTrees2 {
	
	public static <E> Integer size(BinaryTree2<E> tree) {
		return switch(tree) {
		case BEmpty<E> t -> 0;
		case BLeaf<E> t -> 1;
		case BTree<E> t -> size(t.left())+size(t.right());
		};
	}

}

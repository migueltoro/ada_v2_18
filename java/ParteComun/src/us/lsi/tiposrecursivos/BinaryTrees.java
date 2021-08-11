package us.lsi.tiposrecursivos;

import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;

public class BinaryTrees {
	
	public static <E> Integer size(BinaryTree<E> tree) {
		return (int) tree.stream().filter(t->!t.isEmpty()).mapToInt(t->1).count();
	}
	
	public static <E> Integer countIfPredicate(BinaryTree<E> tree, Predicate<E> pd) {
		return (int) tree.stream().filter(t->!t.isEmpty()).map(t->t.getLabel()).mapToInt(t->1).count();
	}
	
	public static <E> Optional<E> minLabel(BinaryTree<E> tree, Comparator<E> cmp) {
		return tree.stream().filter(t->!t.isEmpty()).map(t->t.getLabel()).min(cmp);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}

package us.lsi.trees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import us.lsi.common.Comparators;
import us.lsi.tiposrecursivos.BinaryTree;
import us.lsi.tiposrecursivos.BinaryTree.BinaryType;


public class BinaryTrees {
	
	
	public static <E> Integer size(BinaryTree<E> tree) {
		BinaryType type = tree.getType();
		return switch (type) {
		case Empty -> 0;
		case Leaf -> 1;
		case Binary -> 1 + size(tree.getLeft()) + size(tree.getRight());
		};
	}
	
	
	public static <E> E minLabel(BinaryTree<E> tree, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		return switch (type) {
		case Empty -> null;
		case Leaf -> tree.getLabel();
		case Binary -> {
			E e1 = minLabel(tree.getLeft(), cmp);
			E e2 = minLabel(tree.getRight(), cmp);
			yield Comparators.min(tree.getLabel(), Comparators.min(e1, e2, cmp), cmp);
		}
		};
	}
	
	public static <E> E minLabelOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		return switch (type) {
		case Empty -> null;
		case Leaf -> tree.getLabel();
		case Binary -> tree.getLeft().isEmpty()?tree.getLabel():minLabelOrdered(tree.getLeft(), cmp);
		};
	}
	
	public static <E> Boolean containsLabel(BinaryTree<E> tree, E label) {
		BinaryType type = tree.getType();
		return switch (type) {
		case Empty -> false;
		case Leaf -> tree.getLabel().equals(label);
		case Binary -> tree.getLabel().equals(label) || containsLabel(tree.getLeft(), label)
				|| containsLabel(tree.getRight(), label);
		};
	}
	
	public static <E> Boolean containsLabelOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		return switch(type) {
		case Empty -> false; 
		case Leaf -> tree.getLabel().equals(label); 
		case Binary -> {
			E nLabel = tree.getLabel();
			yield Comparators.isEQ(label,nLabel,cmp) || 
				(Comparators.isLT(label, nLabel, cmp) && containsLabelOrdered(tree.getLeft(),label,cmp)) ||	
				(Comparators.isGT(label, nLabel, cmp) && containsLabelOrdered(tree.getRight(),label,cmp));
		}	
		};
	}
	
	public static <E> Boolean equals(BinaryTree<E> t1, BinaryTree<E> t2) {
		BinaryType t1Type = t1.getType();
		BinaryType t2Type = t2.getType();
		Boolean r = t1Type == t2Type;
		if (!r) return r;
		return switch (t1Type) {
		case Empty -> true;
		case Leaf -> t1.getLabel().equals(t2.getLabel());
		case Binary -> t1.getLabel().equals(t2.getLabel()) && equals(t1.getLeft(), t2.getLeft())
				|| equals(t1.getRight(), t1.getRight());
		};
	}
		
	public static <E> BinaryTree<E> copy(BinaryTree<E> t1) {
		BinaryType type = t1.getType();
		return switch(type) {
		case Empty -> BinaryTree.empty(); 
		case Leaf -> BinaryTree.leaf(t1.getLabel()); 
		case Binary -> BinaryTree.binary(t1.getLabel(), copy(t1.getLeft()), copy(t1.getRight()));	
		};	
	}
		
	public static <E> BinaryTree<E> reverseCopy(BinaryTree<E> t1) {
		BinaryType type = t1.getType();
		return switch(type) {
		case Empty -> BinaryTree.empty(); 
		case Leaf -> BinaryTree.leaf(t1.getLabel()); 
		case Binary -> BinaryTree.binary(t1.getLabel(), reverseCopy(t1.getRight()), reverseCopy(t1.getLeft()));
		};	
	}
	
	public static <E> List<E> toListPostOrden(BinaryTree<E> tree) {
		BinaryType type = tree.getType();
		return switch(type) {
		case Empty -> new ArrayList<>(); 
		case Leaf -> {
			List<E> r = new ArrayList<>();
			r.add(tree.getLabel());
			yield r;
		}
		case Binary -> { 
			List<E> r = toListPostOrden(tree.getLeft());
			r.addAll(toListPostOrden(tree.getRight()));
			r.add(tree.getLabel());	
			yield r;
		}
		};
	}
	
	public static <E> BinaryTree<E> addOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		return switch (type) {
		case Empty -> BinaryTree.leaf(label);
		case Leaf -> {
			E aLabel = tree.getLabel();
			BinaryTree<E> r = switch (Comparators.compare(label, aLabel, cmp)) {
			case EQ -> tree;
			case LT -> BinaryTree.binary(aLabel, BinaryTree.leaf(label), BinaryTree.empty());
			case GT -> BinaryTree.binary(aLabel, BinaryTree.empty(), BinaryTree.leaf(label));
			};
			yield r;
		}
		case Binary -> {
			E aLabel = tree.getLabel();
			BinaryTree<E> r = switch (Comparators.compare(label, aLabel, cmp)) {
			case EQ -> tree;
			case LT -> BinaryTree.binary(aLabel, addOrdered(tree.getLeft(), label, cmp), tree.getRight());
			case GT -> BinaryTree.binary(aLabel, tree.getLeft(), addOrdered(tree.getRight(), label, cmp));
			};
			yield r;
		}
		};
	}

	public static <E> BinaryTree<E> addOrdered(List<E> labels, Comparator<E> cmp){
		BinaryTree<E> r = BinaryTree.empty();
		for(E e:labels) {
			r = addOrdered(r,e,cmp);
		}
		return r;
	}
	
	public static <E> BinaryTree<E> removeOrdered(BinaryTree<E> tree, E label, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		BinaryTree<E> r = null;
		switch (type) {
		case Empty:
			r = tree;
			break;
		case Leaf:
			E aLabel = tree.getLabel();
			r = switch (Comparators.compare(label, aLabel, cmp)) {
			case EQ -> BinaryTree.empty();
			case LT -> tree;
			case GT -> tree;
			};
			break;
		case Binary:
			aLabel = tree.getLabel();
			r = switch (Comparators.compare(label, aLabel, cmp)) {
			case EQ -> {
				if (!tree.getLeft().isEmpty()) {
					E lLabel = tree.getLeft().getLabel();
					yield BinaryTree.binary(lLabel, removeOrdered(tree.getLeft(), lLabel, cmp), tree.getRight());
				} else if (!tree.getRight().isEmpty()) {
					E rLabel = tree.getRight().getLabel();
					yield BinaryTree.binary(rLabel, tree.getLeft(), removeOrdered(tree.getRight(), rLabel, cmp));
				} else {
					yield BinaryTree.empty();
				}
			}
			case LT -> BinaryTree.binary(aLabel, removeOrdered(tree.getLeft(), label, cmp), tree.getRight());
			case GT -> BinaryTree.binary(aLabel, tree.getLeft(), removeOrdered(tree.getRight(), label, cmp));
			};
		}
		return r;
	}
	
	public static <E> Boolean isOrdered(BinaryTree<E> tree, Comparator<E> cmp) {
		BinaryType type = tree.getType();
		return switch (type) {
		case Empty -> true;
		case Leaf -> true;
		case Binary -> {
			E aLabel = tree.getLabel();
			yield (tree.getLeft().isEmpty() || Comparators.isGT(aLabel, tree.getLeft().getLabel(), cmp))
					&& (tree.getRight().isEmpty() || Comparators.isLT(aLabel, tree.getRight().getLabel(), cmp))
					&& isOrdered(tree.getLeft(), cmp) && isOrdered(tree.getRight(), cmp);
		}
		};
	}
	
	public static Integer sumIfPredicate(BinaryTree<Integer> tree, Predicate<Integer> predicate) {
		BinaryType type = tree.getType();
		return switch(type) {
		case Empty -> 0; 
		case Leaf -> predicate.test(tree.getLabel())?tree.getLabel():0; 
		case Binary -> {
			yield predicate.test(tree.getLabel())?tree.getLabel():0 +
			    sumIfPredicate(tree.getLeft(),predicate) +
			    sumIfPredicate(tree.getRight(),predicate);
		}
		};		
	}
	
	
	
	public static void main(String[] args) {
		BinaryTree<Integer> tree = addOrdered(List.of(1,7,2,5,10,9,-1,14,67,67), Comparator.naturalOrder());
		System.out.println(tree.toString());
		System.out.println(isOrdered(tree,Comparator.naturalOrder()));
		BinaryTree<Integer> tree2 = removeOrdered(tree, 10,Comparator.naturalOrder());
		System.out.println(tree2.toString());
		System.out.println(isOrdered(tree2,Comparator.naturalOrder()));
		BinaryTree<Integer> tree3 = removeOrdered(tree2,9,Comparator.naturalOrder());
		System.out.println(tree3.toString());
		System.out.println(isOrdered(tree3,Comparator.naturalOrder()));
		System.out.println(sumIfPredicate(tree3,x->x%2==0));
		BinaryTree<Integer> tree4 = copy(tree);
		System.out.println(equals(tree,tree4));
	}
	
}

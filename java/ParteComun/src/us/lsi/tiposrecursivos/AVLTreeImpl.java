package us.lsi.tiposrecursivos;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Stream;

import us.lsi.common.Comparators;
import us.lsi.common.MutableType;
import us.lsi.common.Preconditions;
import us.lsi.common.String2;


/**
 * Un &aacute;rbol binario ordenado y equilibrado
 * 
 * @author Miguel Toro
 *
 * @param <E> El tipo de los elementos del &aacute;rbol 
 * 
 * @inv La implementaci&oacute;n mantiene el invariante: getTree() está ordenado y equilibrado
 */
public class AVLTreeImpl<E> implements AVLTree<E>  {
	
	private static <E> AVLTreeImpl<E> create(BinaryTree<E> tree, Comparator<E> comparator) {
		Preconditions.checkNotNull(tree);
		return new AVLTreeImpl<E>(tree, comparator);
	}
	
	
	protected Comparator<E> comparator;
	protected BinaryTree<E> tree;
	private Integer height = null; 
	private BinaryTree<E> first = null;
	private BinaryTree<E> last = null;
	private Integer size = null;
	
	
	protected AVLTreeImpl(BinaryTree<E> tree, Comparator<E> comparator) {
		super();
		this.comparator = comparator;
		this.tree = tree;
	}	
	
	
	@Override
	public BinaryTree<E> getTree() {
		return tree;
	}

	@Override
	public boolean isEmpty() {
		return tree.isEmpty();
	}
	
	
	@Override
	public boolean contains(E e) {
		return contains(this.tree,e);
	}
	
	private boolean contains(BinaryTree<E> tree, E e) {
		Boolean r = null;
		switch(tree.getType()) {
		case Empty: r = false; break;
		case Leaf: r = Comparators.isEQ(e, tree.getLabel(), comparator); break;
		case Binary: r = contains(tree.getLeft(),e) || contains(tree.getRight(),e); break;
		}
		return r;
	}
	
	
	
	/**
	 * @param tree Un arbol binario ordenado
	 * @param element Un elemento
	 * @param comparator Un orden
	 * @return Un &aacute;rbol binario con la etiqueta igual al element o vac&iacute;o
	 */
	protected static <E> BinaryTree<E> find(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = tree;
		switch(tree.getType()) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf: 
			switch(Comparators.compare(element, tree.getLabel(), comparator)) {
			case EQ: break;			
			case LT: 
			case GT: r = BinaryTree.empty();		
			}
			break;
		case Binary: 
			switch(Comparators.compare(element, tree.getLabel(), comparator)) {
			case EQ: break;			
			case LT: r = find(tree.getLeft(),element,comparator); break;
			case GT: r = find(tree.getRight(),element,comparator); break;	
			}
			break;
		}
		return r;
	}
	

	
	@Override
	public E first() {
		if (this.first == null) this.first = getFirst(this.tree);
		return first.getLabel();
	}
	
	protected static <E> BinaryTree<E> getFirst(BinaryTree<E> tree) {
		BinaryTree<E> r = tree;
		switch(tree.getType()) {		
		case Empty: Preconditions.checkArgument(!tree.isEmpty()); break;
		case Leaf: break;
		case Binary: if (!tree.getLeft().isEmpty()) r = getFirst(tree.getLeft()); break;		
		}
		return r;
	}
	
	@Override
	public E last() {
		if (this.last == null) this.last = getLast(this.tree);
		return last.getLabel();
	}
	
	protected static <E> BinaryTree<E> getLast(BinaryTree<E> tree) {
		BinaryTree<E> r = tree;
		switch(tree.getType()) {		
		case Empty: Preconditions.checkArgument(!tree.isEmpty()); break;
		case Leaf: break;
		case Binary: if (!tree.getRight().isEmpty()) r = getLast(tree.getRight()); break;		
		}
		return r;
	}

	
	@Override
	public int size() {
		if(size == null) this.size = tree.size();
		return size;
	}

	protected int getHeight() {
		if(this.height == null) this.height = this.tree.getHeight();
		return this.height;
	}
	
	
	@Override
	public boolean add(E element) {
		BinaryTree<E> t = add(this.tree,element,comparator);
		Boolean r = false;
		if(this.tree != t) {
			this.height = null; 
			this.first = null;
			this.last = null;
			this.size = null;
			r = true;
			this.tree = t;
		}
		return r;
	}
	
	
	@Override
	public boolean add(Stream<E> elements) {
		final MutableType<Boolean> r = MutableType.of(false);
		elements.forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	
	@Override
	public boolean add(Collection<E> elements) {
		final MutableType<Boolean> r = MutableType.of(false);
		elements.stream().forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	
	@Override
	public boolean add(@SuppressWarnings("unchecked") E... elements) {
		final MutableType<Boolean> r = MutableType.of(false);
		Arrays.stream(elements).forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	/**
	 * @pre El arbol es ordenado y equilibrado
	 * @post El &aacute;rbol resultante contiene al elemento, est&aacute; ordenado y es equilibrado. El &aacute;rbol de entrada no se modifica
	 * @param tree Un &aacute;rbol de entrada de entrada
	 * @param element un elemento par añadir al &aacute;rbol
	 * @param comparator Un orden
	 * @return El &aacute;rbol con el elemento añadido y si ha cambiado
	 */
	protected BinaryTree<E> add(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = tree;		
		switch(tree.getType()) {		
		case Empty: r = BinaryTreeImpl.leaf(element); break;
		case Leaf:
			switch(Comparators.compare(element, tree.getLabel(), comparator)) {
			case EQ: break;
			case LT: r = BinaryTreeImpl.binary(tree.getLabel(), BinaryTreeImpl.leaf(element), BinaryTreeImpl.empty()); break;
			case GT: r = BinaryTreeImpl.binary(tree.getLabel(), BinaryTreeImpl.empty(), BinaryTreeImpl.leaf(element)); break;
			}
			break;
		case Binary:
			switch(Comparators.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = tree; break;
			case LT: 
				BinaryTree<E> new_left = add(tree.getLeft(), element, comparator);
				if(tree.getLeft() != new_left) {
					r = BinaryTree.binary(tree.getLabel(), new_left, tree.getRight());
					r = r.equilibrate();
				}
				break;
			case GT: 
				BinaryTree<E> new_right = add(tree.getRight(), element, comparator);
				if(tree.getRight() != new_right) {
					r = BinaryTree.binary(tree.getLabel(), tree.getLeft(), new_right);
					r = r.equilibrate();
				}
				break;
			}
			break;	
		}
		return r;
	}
	
	
	@Override
	public boolean remove(E element) {
		BinaryTree<E> t = remove(this.tree,element,comparator);
		Boolean r = false;
		if(this.tree != t) {
			this.height = null; 
			this.first = null;
			this.last = null;
			this.size = null;
			r = true;
			this.tree = t;
		}
		return r;
	}
	
	@Override
	public boolean remove(Stream<E> elements) {
		final MutableType<Boolean> r = MutableType.of(false);
		elements.forEach(e->r.value = this.remove(e) || r.value);
		return r.value;
	}
	
	
	@Override
	public boolean remove(Collection<E> elements) {
		final MutableType<Boolean> r = MutableType.of(false);
		elements.stream().forEach(e->r.value = this.remove(e) || r.value );
		return r.value;
	}

	
	@Override
	public boolean remove(@SuppressWarnings("unchecked") E... elements) {
		final MutableType<Boolean> r = MutableType.of(false);
		Arrays.stream(elements).forEach(e->r.value = this.remove(e) ||  r.value);
		return r.value;
	}

	protected BinaryTree<E> remove(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = tree;
		switch(tree.getType()) {
		case Empty:  break;
		case Leaf:
			switch(Comparators.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = BinaryTreeImpl.empty(); break;		
			case LT:
			case GT: 
			}
			break;
		case Binary:
			switch(Comparators.compare(element, tree.getLabel(), comparator)) {
			case EQ: 
				if(!tree.getLeft().isEmpty()) {
					E label = getLast(tree.getLeft()).getLabel();
					BinaryTree<E> rl = remove(tree.getLeft(),label,comparator);
					r = BinaryTree.binary(label,rl, tree.getRight());
				} else {
					E label = getFirst(tree.getRight()).getLabel();
					BinaryTree<E> rr = remove(tree.getRight(),label,comparator);
					r = BinaryTree.binary(label,tree.getLeft(),rr);
				}
				break;		
			case LT: 
				BinaryTree<E> rl = remove(tree.getLeft(),element,comparator);
				if(rl != tree.getLeft()) {
					r = BinaryTree.binary(tree.getLabel(), rl, tree.getRight()); 
				}
				break;
			case GT: 
				BinaryTree<E> rr = remove(tree.getRight(),element,comparator);
				if(rr != tree.getRight()) {
					r = BinaryTree.binary(tree.getLabel(), tree.getLeft(), rr); 
				}
				break;
			}
			r = r.equilibrate();			
			break;		
		}		
		return r;	 	
	}

	/**
	 * @return Una copia del &aacute;rbol
	 */
	@Override
	public AVLTree<E> copy() {
		return create(tree.copy(), comparator);
	}


	
	@Override
	public String toString() {
		return tree.toString();
	}
	
	public static void main(String[] args) {
		AVLTree<Integer> tree = AVLTree.of();
//		Strings2.toConsole(String.format("%d,%s",tree.getHeight(), tree.toString()));
		for (int i = 0; i < 500 ; i++) {
			tree.add(i);
//			Strings2.toConsole(String.format("%d,%d,%d", tree.first(),tree.last(), tree.getHeight()));
		}
		
		for (int i = 0; i < 50 ; i++) {
			tree.remove(i);
//			Strings2.toConsole(String.format("%d,%d,%d", tree.first(),tree.last(), tree.getHeight()));
		}
		
		for (int i = 600; i > 450 ; i--) {
			tree.remove(i);
//			Strings2.toConsole(String.format("%d,%d,%d", tree.first(),tree.last(), tree.getHeight()));
		}
		
		String2.toConsole(String.format("%d,%d,%d", tree.first(),tree.last(), tree.getTree().getHeight()));
		String2.toConsole(String.format("%s", tree.getTree().toString()));
	}

}

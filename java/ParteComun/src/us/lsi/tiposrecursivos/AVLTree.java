package us.lsi.tiposrecursivos;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import us.lsi.common.Comparator2;
import us.lsi.common.MutableType;
import us.lsi.common.Preconditions;
import us.lsi.common.Strings2;


/**
 * Un &aacute;rbol binario ordenado y equilibrado
 * 
 * @author Miguel Toro
 *
 * @param <E> El tipo de los elementos del &aacute;rbol 
 */
public class AVLTree<E> {
	
	private enum Type{LeftRight, LeftLeft, RightLeft, RightRight, Equilibrate} 
	
	/**
	 * @param <E> Tipo de los elementos del &aacute;rbol
	 * @return Un &aacute;rbol binario vac&iacute;o cuyos elementos se ordenar&aacute;n mediante el orden natural de E
	 */
	public static <E extends Comparable<? super E>> AVLTree<E> create() {
		return new AVLTree<E>(BinaryTree.empty(), Comparator.naturalOrder());
	}
	
	/**
	 * @param <E> Tipo de los elementos del &aacute;rbol
	 * @param comparator Un orden
	 * @return Un &aacute;rbol binario vac&iacute;o cuyos elementos se ordenar&aacute;n mediante comparator
	 */
	public static <E> AVLTree<E> create(Comparator<E> comparator) {
		return new AVLTree<E>(BinaryTree.empty(), comparator);
	}
	
	private static <E> AVLTree<E> create(BinaryTree<E> tree, Comparator<E> comparator) {
		Preconditions.checkNotNull(tree);
		return new AVLTree<E>(tree, comparator);
	}
	
	protected Comparator<E> comparator;
	protected BinaryTree<E> tree;
	private Integer height = null; 
	private BinaryTree<E> first = null;
	private BinaryTree<E> last = null;
	private Integer size = null;
	
	
	protected AVLTree(BinaryTree<E> tree, Comparator<E> comparator) {
		super();
		this.comparator = comparator;
		this.tree = tree;
	}	
	
	/**
	 * @return Si est&aacute; vac&iacute;o el &aacute;rbol AVL
	 */
	public boolean isEmpty() {
		return tree.isEmpty();
	}
	
	/**
	 * @param e Un elemento
	 * @return Si contiene al elemento
	 */
	public boolean contains(E e) {
		return contains(this.tree,e);
	}
	
	private boolean contains(BinaryTree<E> tree, E e) {
		Boolean r = null;
		switch(tree.getType()) {
		case Empty: r = false; break;
		case Leaf: r = Comparator2.isEQ(e, tree.getLabel(), comparator); break;
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
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: break;			
			case LT: 
			case GT: r = BinaryTree.empty();		
			}
			break;
		case Binary: 
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: break;			
			case LT: r = find(tree.getLeft(),element,comparator); break;
			case GT: r = find(tree.getRight(),element,comparator); break;	
			}
			break;
		}
		return r;
	}
	

	/**
	 * @pre El &aacute;rbol no puede estar vac&iacute;o
	 * @return El elemento m&aacute;s pequeño del &aacute;rbol
	 */
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
	/**
	 * @pre El &aacute;rbol no puede estar vac&iacute;o
	 * @return El elemento m&aacute;s grande del &aacute;rbol
	 */
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

	/**
	 * @return El n&uacute;mero de elementos
	 */
	public int size() {
		if(size == null) this.size = tree.size();
		return size;
	}

	protected int getHeight() {
		if(this.height == null) this.height = this.tree.getHeight();
		return this.height;
	}
	
	/**
	 * @post El elemento est&aacute; contenido en el &aacute;rbol
	 * @param element Un elemento
	 * @return Verdadero si el elemento no estaba y se ha incluido
	 */
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
	
	/**
	 * @post Todos los elementos est&aacute;n contenidos en el &aacute;rbol
	 * @param elements Una colecci&oacute;n de elementos
	 * @return Si el &aacute;rbol ha cambiado al añadir los elementos
	 */
	public boolean add(Stream<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	/**
	 * @post Todos los elementos est&aacute;n contenidos en el &aacute;rbol
	 * @param elements Una colecci&oacute;n de elementos
	 * @return Si el &aacute;rbol ha cambiado al añadir los elementos
	 */
	public boolean add(Collection<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.stream().forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	/**
	 * @post Todos los elementos est&aacute;n contenidos en el &aacute;rbol
	 * @param elements Una serie de elementos
	 * @return Si el &aacute;rbol ha cambiado al añadir los elementos
	 */
	public boolean add(@SuppressWarnings("unchecked") E... elements) {
		final MutableType<Boolean> r = MutableType.create(false);
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
		case Empty: r = BinaryTree.leaf(element); break;
		case Leaf:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: break;
			case LT: r = BinaryTree.binary(tree.getLabel(), BinaryTree.leaf(element), BinaryTree.empty()); break;
			case GT: r = BinaryTree.binary(tree.getLabel(), BinaryTree.empty(), BinaryTree.leaf(element)); break;
			}
			break;
		case Binary:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = tree; break;
			case LT: 
				BinaryTree<E> new_left = add(tree.getLeft(), element, comparator);
				if(tree.getLeft() != new_left) {
					r = BinaryTree.binary(tree.getLabel(), new_left, tree.getRight());
					r = equilibrate(r);
				}
				break;
			case GT: 
				BinaryTree<E> new_right = add(tree.getRight(), element, comparator);
				if(tree.getRight() != new_right) {
					r = BinaryTree.binary(tree.getLabel(), tree.getLeft(), new_right);
					r = equilibrate(r);
				}
				break;
			}
			break;	
		}
		return r;
	}
	/**
	 * @post El elemento no est&aacute; contenidos en el &aacute;rbol
	 * @param element Un elemento
	 * @return Si el &aacute;rbol ha cambiado al eliminar el elemento
	 */
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
	
	/**
	 * @post Los elementos no est&aacute;n  en el &aacute;rbol
	 * @param elements Un stream de elementos
	 * @return Si el &aacute;rbol ha cambiado al eliminar los elementos
	 */
	public boolean remove(Stream<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.forEach(e->r.value = this.remove(e) || r.value);
		return r.value;
	}
	
	/**
	 * @post Los elementos no est&aacute;n  en el &aacute;rbol
	 * @param elements Una colecci&oacute;n de elementos
	 * @return Si el &aacute;rbol ha cambiado al eliminar los elementos
	 */
	public boolean remove(Collection<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.stream().forEach(e->r.value = this.remove(e) || r.value );
		return r.value;
	}

	/**
	 * @post Los elementos no est&aacute;n  en el &aacute;rbol
	 * @param elements Una serie de elementos
	 * @return Si el &aacute;rbol ha cambiado al eliminar los elementos
	 */
	public boolean remove(@SuppressWarnings("unchecked") E... elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		Arrays.stream(elements).forEach(e->r.value = this.remove(e) ||  r.value);
		return r.value;
	}

	protected BinaryTree<E> remove(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = tree;
		switch(tree.getType()) {
		case Empty:  break;
		case Leaf:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = BinaryTree.empty(); break;		
			case LT:
			case GT: 
			}
			break;
		case Binary:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
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
			r = equilibrate(r);			
			break;		
		}		
		return r;	 	
	}
	
	protected static <E> Type getType(BinaryTree<E> tree) {
		Type r = null;
		List<Integer> n1 = tree.getHeights(1);
		List<Integer> n2 = tree.getHeights(2);
		int left = n1.get(0);
		int right = n1.get(1);
		int leftleft = n2.get(0);
		int leftright = n2.get(1);
		int rightleft = n2.get(2);
		int rightright = n2.get(3);
		if (Math.abs(left - right) < 2) {
			r = Type.Equilibrate;
		} else if (left - right >= 2) {
			if (leftleft >= leftright) {
				r = Type.LeftLeft;
			} else {
				r = Type.LeftRight;
			}
		} else if (left - right < 2) {
			if (rightleft >= rightright) {
				r = Type.LeftRight;
			} else {
				r = Type.RightRight;
			}
		}
		Preconditions.checkArgument(r != null, String.format("%d,%d,%d,%d,%d,%d,%s", left, right, leftleft, leftright,
				rightleft, rightright, tree.toString()));
		return r;
	}
	
	
   private static class Patterns<R> {
    	BinaryPattern<R> leftRight; 
        BinaryPattern<R> rightLeft;
        BinaryPattern<R> leftLeft;
        BinaryPattern<R> rightRight;
        BinaryPattern<R> result;
		
        public Patterns() {
			super();
			this.leftRight = BinaryPattern.parse("_e5(_e3(_A,_e4(_B,_C)),_D)");
			this.rightLeft = BinaryPattern.parse("_e3(_A,_e5(_e4(_B,_C),_D))");
			this.leftLeft = BinaryPattern.parse("_e5(_e4(_e3(_A,_B),_C),_D)");
			this.rightRight = BinaryPattern.parse("_e3(_A,_e4(_B,_e5(_C,_D)))");
			this.result = BinaryPattern.parse("_e4(_e3(_A,_B),_e5(_C,_D))");
		}
        
    }

    private static Patterns<?> patterns = null;
    @SuppressWarnings("unchecked")
	public static <T> Patterns<T> getPatterns(){
    	if(patterns==null) {
    		patterns = new Patterns<T>();
    	}
    	return (Patterns<T>) patterns;
    }
     
	protected static <E> BinaryTree<E> equilibrate(BinaryTree<E> tree) {
		Patterns<E> pt = getPatterns();
		BinaryTree<E> r = null;
		switch(getType(tree)) {
		case Equilibrate: r = tree; break;
		case LeftLeft: r = tree.transform(pt.leftLeft,pt.result);	break;
		case LeftRight: r = tree.transform(pt.leftRight,pt.result); break;
		case RightLeft: r = tree.transform(pt.rightLeft,pt.result);	break;
		case RightRight: r = tree.transform(pt.rightRight,pt.result); break;
		}
		return r;
	}


	/**
	 * @return Una copia del &aacute;rbol
	 */
	public AVLTree<E> copy() {
		return create(tree.copy(), comparator);
	}


	public String toString() {
		return tree.toString();
	}

	public int hashCode() {
		return tree.hashCode();
	}

	public boolean equals(Object obj) {
		return tree.equals(obj);
	}
	
	public static void main(String[] args) {
		AVLTree<Integer> tree = AVLTree.create();
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
		
		Strings2.toConsole(String.format("%d,%d,%d", tree.first(),tree.last(), tree.getHeight()));
		Strings2.toConsole(String.format("%s", tree.tree.toString()));
	}

}

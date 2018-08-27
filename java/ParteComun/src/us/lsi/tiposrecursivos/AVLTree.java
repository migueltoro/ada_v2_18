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
import us.lsi.common.Tuple;
import us.lsi.common.Tuple2;


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
	 * @return Un árbol binario vacío cuyos elementos se ordenarán mediante el orden natural de E
	 */
	public static <E extends Comparable<? super E>> AVLTree<E> create() {
		return new AVLTree<E>(BinaryTree.empty(), Comparator.naturalOrder());
	}
	
	/**
	 * @param <E> Tipo de los elementos del &aacute;rbol
	 * @param comparator Un orden
	 * @return Un árbol binario vacío cuyos elementos se ordenarán mediante comparator
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
	private Integer height; 
	private Boolean heightOk;
	private BinaryTree<E> first;
	private Boolean firstOk;
	private BinaryTree<E> last;
	private Boolean lastOk;
	private Boolean sizeOk;
	private int size;
	
	
	protected AVLTree(BinaryTree<E> tree, Comparator<E> comparator) {
		super();
		this.comparator = comparator;
		this.tree = tree;
		this.heightOk = false;
		this.firstOk = false;
		this.lastOk = false;
		this.sizeOk = false;
	}	
	
	/**
	 * @return Si está vacío el árbol AVL
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
	 * @return Un árbol binario con la etiqueta igual al element o vacío
	 */
	protected static <E> BinaryTree<E> find(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = null;
		switch(tree.getType()) {
		case Empty: r = BinaryTree.empty(); break;
		case Leaf: 
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = tree; break;			
			case LT: 
			case GT: r = BinaryTree.empty();		
			}
			break;
		case Binary: 
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = tree; break;			
			case LT: r = find(tree.getLeft(),element,comparator); break;
			case GT: r = find(tree.getRight(),element,comparator); break;	
			}
			break;
		}
		return r;
	}
	

	/**
	 * @pre El árbol no puede estar vacío
	 * @return El elemento más pequeño del árbol
	 */
	public E first() {
		if (!this.firstOk) {
			this.first = getFirst(this.tree);
			this.firstOk = true;
		}
		return first.getLabel();
	}
	
	protected static <E> BinaryTree<E> getFirst(BinaryTree<E> tree) {
		BinaryTree<E> r = null;
		switch(tree.getType()) {		
		case Empty: Preconditions.checkArgument(!tree.isEmpty()); break;
		case Leaf: r = tree; break;
		case Binary: 
			if (tree.getLeft().isEmpty()) {
				r = tree;
			} else {
				r = getFirst(tree.getLeft());			
			}
			break;		
		}
		return r;
	}
	/**
	 * @pre El árbol no puede estar vacío
	 * @return El elemento más grande del árbol
	 */
	public E last() {
		if (!this.lastOk) {
			this.last = getLast(this.tree);
			this.lastOk = true;
		}
		return last.getLabel();
	}
	
	protected static <E> BinaryTree<E> getLast(BinaryTree<E> tree) {
		BinaryTree<E> r = null;
		switch(tree.getType()) {		
		case Empty: Preconditions.checkArgument(!tree.isEmpty()); break;
		case Leaf: r = tree; break;
		case Binary: 
			if (tree.getRight().isEmpty()) {
				r = tree;
			} else {
				r = getLast(tree.getRight());			
			}
			break;		
		}
		return r;
	}

	/**
	 * @return El número de elementos
	 */
	public int size() {
		if(!sizeOk) {
			this.size = tree.size();
			this.sizeOk = true;
		}
		return size;
	}

	protected int getHeight() {
		if(!this.heightOk) {
			this.height = this.tree.getHeight();
			this.heightOk = true;
		}
		return this.height;
	}
	
	/**
	 * @post El elemento está contenido en el árbol
	 * @param element Un elemento
	 * @return Verdadero si el elemento no estaba y se ha incluido
	 */
	public boolean add(E element) {
		Tuple2<Boolean,BinaryTree<E>> tree = add(this.tree,element,comparator);
		Boolean r = tree.v1;
		this.tree = tree.v2;
		this.heightOk = false;
		this.firstOk = false;
		this.lastOk = false;
		this.sizeOk = false;
		return r;
	}
	
	/**
	 * @post Todos los elementos están contenidos en el árbol
	 * @param elements Una colección de elementos
	 * @return Si el árbol ha cambiado al añadir los elementos
	 */
	public boolean add(Stream<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	/**
	 * @post Todos los elementos están contenidos en el árbol
	 * @param elements Una colección de elementos
	 * @return Si el árbol ha cambiado al añadir los elementos
	 */
	public boolean add(Collection<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.stream().forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	/**
	 * @post Todos los elementos están contenidos en el árbol
	 * @param elements Una serie de elementos
	 * @return Si el árbol ha cambiado al añadir los elementos
	 */
	public boolean add(@SuppressWarnings("unchecked") E... elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		Arrays.stream(elements).forEach(e->r.value = this.add(e) || r.value);
		return r.value;
	}
	
	/**
	 * @pre El arbol es ordenado y equilibrado
	 * @post El árbol resultante contiene al elemento, está ordenado y es equilibrado. El árbol de entrada no se modifica
	 * @param tree Un árbol de entrada de entrada
	 * @param element un elemento par añadir al árbol
	 * @param comparator Un orden
	 * @return El árbol con el elemento añadido
	 */
	protected Tuple2<Boolean,BinaryTree<E>> add(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = null;		
		Boolean changed = false;
		switch(tree.getType()) {		
		case Empty: r = BinaryTree.leaf(element); changed = true; break;
		case Leaf:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = tree; changed = false; break;
			case LT: r = BinaryTree.binary(tree.getLabel(), BinaryTree.leaf(element), BinaryTree.empty()); changed = true; break;
			case GT: r = BinaryTree.binary(tree.getLabel(), BinaryTree.empty(), BinaryTree.leaf(element)); changed = true; break;
			}
			break;
		case Binary:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = tree; changed = false; break;
			case LT: 
				r = BinaryTree.binary(tree.getLabel(), add(tree.getLeft(), element, comparator).v2, tree.getRight());
				r = equilibrate(r);
				changed = true;
				break;
			case GT: 
				r = BinaryTree.binary(tree.getLabel(), tree.getLeft(), add(tree.getRight(), element, comparator).v2);
				r = equilibrate(r);
				changed = true;
				break;
			}
			break;	
		}
		return Tuple.create(changed,r);
	}
	/**
	 * @post El elemento no está contenidos en el árbol
	 * @param element Un elemento
	 * @return Si el árbol ha cambiado al eliminar el elemento
	 */
	public boolean remove(E element) {
		Tuple2<Boolean,BinaryTree<E>> tree = remove(this.tree,element,comparator);
		Boolean r = tree.v1;
		this.tree = tree.v2;
		this.heightOk = false;
		this.firstOk = false;
		this.lastOk = false;
		this.sizeOk = false;
		return r;
	}
	
	/**
	 * @post Los elementos no están  en el árbol
	 * @param elements Un stream de elementos
	 * @return Si el árbol ha cambiado al eliminar los elementos
	 */
	public boolean remove(Stream<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.forEach(e->r.value = this.remove(e) || r.value);
		return r.value;
	}
	
	/**
	 * @post Los elementos no están  en el árbol
	 * @param elements Una colección de elementos
	 * @return Si el árbol ha cambiado al eliminar los elementos
	 */
	public boolean remove(Collection<E> elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		elements.stream().forEach(e->r.value = this.remove(e) || r.value );
		return r.value;
	}

	/**
	 * @post Los elementos no están  en el árbol
	 * @param elements Una serie de elementos
	 * @return Si el árbol ha cambiado al eliminar los elementos
	 */
	public boolean remove(@SuppressWarnings("unchecked") E... elements) {
		final MutableType<Boolean> r = MutableType.create(false);
		Arrays.stream(elements).forEach(e->r.value = this.remove(e) ||  r.value);
		return r.value;
	}

	protected Tuple2<Boolean,BinaryTree<E>> remove(BinaryTree<E> tree, E element, Comparator<E> comparator) {
		BinaryTree<E> r = null;
		Boolean changed = false;
		Tuple2<Boolean, BinaryTree<E>> rt;
		switch(tree.getType()) {
		case Empty: r  = tree; changed = false; break;
		case Leaf:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: r = BinaryTree.empty(); changed = true; break;		
			case LT:
			case GT: r = tree;	changed = false;
			}
			break;
		case Binary:
			switch(Comparator2.compare(element, tree.getLabel(), comparator)) {
			case EQ: 
				if(!tree.getLeft().isEmpty()) {
					AVLTree<E> left = AVLTree.create(tree.getLeft(), comparator);
					E label = left.last();
					rt = remove(tree.getLeft(),label,comparator);
					r = BinaryTree.binary(label, rt.v2, tree.getRight());
				} else {
					AVLTree<E> right = AVLTree.create(tree.getRight(), comparator);
					E label = right.first();
					rt = remove(tree.getRight(),label,comparator);
					r = BinaryTree.binary(label, tree.getLeft(),rt.v2);
				}
				changed = true;
				break;		
			case LT: 
				rt = remove(tree.getLeft(),element,comparator);
				r = BinaryTree.binary(tree.getLabel(), rt.v2, tree.getRight()); 
				changed = rt.v1;
				break;
			case GT: 
				rt = remove(tree.getRight(),element,comparator);
				r = BinaryTree.binary(tree.getLabel(), tree.getLeft(), rt.v2); 
				changed = rt.v1;
				break;
			}
			r = equilibrate(r);			
			break;		
		}		
		return Tuple.create(changed,r);	 	
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
		case LeftLeft: r = tree.transform(pt.leftLeft,pt.result).v2;	break;
		case LeftRight: r = tree.transform(pt.leftRight,pt.result).v2; break;
		case RightLeft: r = tree.transform(pt.rightLeft,pt.result).v2;	break;
		case RightRight: r = tree.transform(pt.rightRight,pt.result).v2; break;
		}
		return r;
	}


	/**
	 * @return Una copia del árbol
	 */
	public AVLTree<E> copy() {
		return create(tree.copy(), comparator);
	}


	/**
	 * @post El fichero file contiene el árbol en formato .dot
	 * @param file El nombre de un fichero
	 * @param titulo El título
	 */
	public void toDOT(String file, String titulo) {
		tree.toDOT(file, titulo);
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
		tree.add(100);
		Strings2.toConsole(String.format("%d,%s",tree.getHeight(), tree.toString()));
		for (int i = 0; i < 500 ; i++) {
			tree.add(i);
			Strings2.toConsole(String.format("%d,%d,%d === %s",tree.getHeight(),tree.tree.getLeft().getHeight(), tree.tree.getRight().getHeight(), tree.toString()));
//			Strings2.toConsole(String.format("%s,%s,%d", tree.first(),tree.last(), tree.getHeight()));
		}
		
		for (int i = 600; i > 450 ; i--) {
			tree.remove(i);
//			Strings2.toConsole(String.format("%d,%d,%d === %s",tree.getHeight(),tree.tree.getLeft().getHeight(), tree.tree.getRight().getHeight(), tree.toString()));
////			Strings2.toConsole(String.format("%s,%s,%d", tree.first(),tree.last(), tree.getHeight()));
		}
		
		Strings2.toConsole(String.format("%s,%s,%d", tree.first(),tree.last(), tree.getHeight()));
		Strings2.toConsole(String.format("%s", find(tree.tree,tree.last(),Comparator.naturalOrder()).toString()));
		
	}

}

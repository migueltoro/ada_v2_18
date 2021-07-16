package us.lsi.tiposrecursivos;


import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.ViewL;

public interface Tree<E> extends Iterable<Tree<E>>{
	
public static Tree<Object> empty = new TreeImpl<Object>();
	
	public enum TreeType{Empty,Leaf,Nary}
	
	public static <R> Tree<R> empty() {
		return TreeImpl.empty();
	}
	
	public static <R> Tree<R> leaf(R label) {
		return TreeImpl.leaf(label);
	}

	public static <R> Tree<R> nary(R label, List<Tree<R>> elements) {
		List<TreeImpl<R>> ls = elements.stream().map(x->(TreeImpl<R>)x).collect(Collectors.toList());
		return TreeImpl.nary(label,ls);
	}

	@SafeVarargs
	public static <R> Tree<R> nary(R label, Tree<R>... elements) {
		List<TreeImpl<R>> ls = Arrays.stream(elements).map(x->(TreeImpl<R>)x).collect(Collectors.toList());
		return TreeImpl.nary(label,ls);
	}
	
	
	public static <R> Tree<R> toTree(BinaryTree<R> t){
		return TreeImpl.toTree(t);
	}

	public static Tree<String> parse(String s){
		return TreeImpl.parse(s);
	}
	
	public static <R> Tree<R> parse(String s, Function<String,R> f){
		return TreeImpl.parse(s,f);
	}
	
	/**
	 * @return El tipo del árbol
	 */
	TreeType getType();

	/**
	 * @return Verdadero si el árbol es vacio. 
	 */
	boolean isEmpty();

	/**
	 * @return Verdadero si el árbol es hoja. 
	 */
	boolean isLeaf();

	/**
	 * @param i Un entero
	 * @return Si this es el hijo i de su padre
	 */
	boolean isChild(int i);

	/**
	 * @return Verdadero si el árbol es nario. 
	 */
	boolean isNary();

	E getLabel();

	List<Tree<E>> getChildren();

	Tree<E> getFather();

	boolean isRoot();

	Tree<E> getChild(int index);

	int getNumOfChildren();

	MutableTree<E> mutableView();

	int size();

	int getHeight();

	Tree<E> copy();

	<R> Tree<R> map(Function<E, R> f);

	/**
	 * @return Un árbol que es la imagen especular de this
	 */
	Tree<E> getReverse();

	void toDOT(String file, String titulo);

	/**
	 * @return Una lista con el recorrido en preorden. 
	 */
	List<E> getPreOrder();

	/**
	 * @return Una lista con el recorrido en postorden
	 */
	List<E> getPostOrder();

	/**
	 * @post La etiqueta se insertará en al posición min(k,nh). Si k = 0 resulta el recorrido en preorden y si 
	 * k &ge; nh en postorden.
	 * @param k Posición de inserción de la etiqueta
	 * @return Una lista con el recorrido en inorden. 
	 */
	List<E> getInOrder(int k);

	/**
	 * @return Una lista con los árboles por niveles. Versión iterativa
	 */
	List<Tree<E>> getByLevel();

	/**
	 * @return Una lista con las etiquetas por niveles. Versión iterativa
	 */
	List<E> getLabelByLevel();

	/**
	 * @param level Los arboles de un nivel dado
	 * @return Los arboles del siguiente nivel
	 */
	List<Tree<E>> getNextLevel(List<Tree<E>> level);

	/**
	 * @param n Un entero
	 * @return Los arboles del nivel n
	 */
	List<Tree<E>> getLevel(Integer n);

	/**
	 * @param root La raiz del árbol dónde t es un subarbol
	 * @return La profundidad de t en root o -1 si no está
	 */
	int getDepth(Tree<E> root);

	String toString();

	int hashCode();

	boolean equals(Object obj);
	
	/**
	 * @return Una vista de tipo L del árbol nario
	 */
	ViewL<Tree<E>,E> view();
	
	Iterator<TreeLevel<E>> byLevel();
	
	public static record TreeLevel<E>(Integer level, Tree<E> tree){
		public static <R> TreeLevel<R> of(Integer level, Tree<R> tree){
			return new TreeLevel<R>(level,tree);
		}
		@Override
		public String toString() {
			return String.format("(%d,%s)",this.level,this.tree);
		}
	}
}
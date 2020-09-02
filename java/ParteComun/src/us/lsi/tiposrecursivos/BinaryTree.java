package us.lsi.tiposrecursivos;

import java.util.List;
import java.util.function.Function;

import us.lsi.tiposrecursivos.BinaryPatternImpl.Matches;


public interface BinaryTree<E> {
	
	public enum BinaryType{Empty,Leaf,Binary}
	public enum ChildType{Left,Right,Root}
	
	/**
	 * @post isEmpty()
	 * @return Construye un árbol vacío
	 */
	public static <E> BinaryTree<E> empty() {
		return BinaryTreeImpl.empty();
	}
	
	/**
	 * @param label Una etiqueta
	 * @post isLeaf()
	 * @return Construye un árbol hoja
	 */
	public static <E> BinaryTree<E> leaf(E label) {
		return BinaryTreeImpl.leaf(label);
	}
	
	/**
	 * @param label Una etiqueta
	 * @param left Un arbol
	 * @param right Un arbol
	 * @post isBinary()
	 * @return Construye un árbol binario
	 */
	public static <E> BinaryTree<E> binary(E label, BinaryTree<E> left, BinaryTree<E> right) {
		return BinaryTreeImpl.binary(label, left, right);
	}
	
	/**
	 * @param s Una cadena
	 * @return Construye un árbol a partir de su representación textual en s
	 */
	public static BinaryTree<String> parse(String s){
		return BinaryTreeImpl.parse(s);
	}
	
	/**
	 * @param s Una cadena que representa el árbol
	 * @param f Una función de String al tipo E
	 * @return Construye un árbol a partir de la cadena s y aplicando posteriormente la función f a las etiquetas
	 */
	public static <E> BinaryTree<E> parse(String s, Function<String,E> f) {
		BinaryTree<String> tree = BinaryTreeImpl.parse(s);
		return tree.map(f);
	}
	
	/**
	 * @return Si es árbol es vacío
	 */
	boolean isEmpty();

	/**
	 * @return Si el árbol es hoja
	 */
	boolean isLeaf();

	/**
	 * @return Si el árbol es binario
	 */
	boolean isBinary();

	/**
	 * @return El tipo del árbol
	 */
	BinaryType getType();

	/**
	 * @return El ábol del cual this es hijo
	 */
	BinaryTree<E> getFather();

	/**
	 * @return Si no tiene padre
	 */
	boolean isRoot();

	/**
	 * @return Si es un hijo izquierdo
	 */
	Boolean isLeftChild();

	/**
	 * @return Si es un hijo derecho
	 */
	Boolean isRightChild();

	/**
	 * @return Si es hijo izquierdo, derecho o raiz
	 */
	ChildType getChildType();

	/**
	 * @pre isLeaf() || isBinary()
	 * @return La etiqueta
	 */
	E getLabel();

	/**
	 * @pre isBinary()
	 * @return El hijo izquierdo
	 */
	BinaryTree<E> getLeft();

	/**
	 * @pre isBinary()
	 * @return El hijo derecho
	 */
	BinaryTree<E> getRight();

	/**
	 * @return Una vista mutable del árbol
	 */
	MutableBinaryTree<E> mutableView();

	/**
	 * @return El número de etiquetas del árbol
	 */
	int size();

	/**
	 * @return La altura del árbol: longitud del camino más largo a un árbol vacío o una de las hojas
	 */
	int getHeight();

	/**
	 * @param n Un entero mayor o igual a cero
	 * @return Los árboles que están a nivel n
	 */
	List<BinaryTree<E>> getLevel(int n);

	/**
	 * @param n Un entero 
	 * @return Las alturas de los árboles de nivel n
	 */
	List<Integer> getHeights(int n);

	/**
	 * @return Una copia del árbol
	 */
	BinaryTree<E> copy();

	/**
	 * @return Una copia simétrica del árbol
	 */
	BinaryTree<E> getReverse();

	/**
	 * @param f Una función
	 * @return El árbol resultante tras aplicar la función de a cada una de las etiquetas
	 */
	<R> BinaryTree<R> map(Function<E, R> f);

	/**
	 * @param pattern Un patrón con el que se debe hacer matching
	 * @param result Un patrón que será el esquema al que queremos transformar el árbol
	 * @return Un árbol que se obtiene sustituyendo en result las variables obtenidas en el matching de this con pattern.
	 * Si no hay matching se devuelve el árbol sin transformar
	 */
	BinaryTree<E> transform(BinaryPattern<E> pattern, BinaryPattern<E> result);

	/**
	 * @param pt Un patrón
	 * @return Un matching que contiene si ha habido o no matching y los valores para las etiquetas 
	 * y variables obtenidos en el matching
	 */
	Matches<E> match(BinaryPattern<E> pt);

	/**
	 * @return la representación de árbol en cadenas de caracteres
	 */
	String toString();

	/**
	 * @return Una lista con las etiquetas el árbol en preorden
	 */
	List<E> getPreOrder();
	/**
	 * @return Una lista con las etiquetas el árbol en postorden
	 */
	List<E> getPostOrder();
	/**
	 * @return Una lista con las etiquetad el árbol en ineorden
	 */
	List<E> getInOrder();
	
	/**
	 * @return Un árbol equilibrado con las mismas etiquetas
	 */
	BinaryTree<E> equilibrate();

}
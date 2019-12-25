package us.lsi.tiposrecursivos;

public interface MutableTree<E> extends Tree<E> {

	void setLabel(E label); 
	
	void setChild(int i, Tree<E> tree); 

	void addChild(Tree<E> tree); 
	
	void addChild(int i, Tree<E> tree); 
	
	void removeChild(int i); 
	
	void setFather(Tree<E> father); 
	
	/**
	 * @post this pasa a ser un arbol raiz si no lo era antes
	 * @param nTree Un árbol 
	 * @return Si this no es raiz devuelve el arbol padre con el hijo this cambiado por nTree. Si this es raiz devuelve nTree
	 */
	Tree<E> changeFor(Tree<E> nTree); 

}

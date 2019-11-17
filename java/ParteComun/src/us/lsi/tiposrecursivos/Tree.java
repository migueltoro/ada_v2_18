package us.lsi.tiposrecursivos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import us.lsi.common.Files2;
import us.lsi.common.Lists2;
import us.lsi.common.Preconditions;
import us.lsi.common.Streams2;
import us.lsi.regularexpressions.Token;
import us.lsi.regularexpressions.Tokenizer;
import us.lsi.regularexpressions.Tokenizer.TokenType;

import java.io.PrintWriter;

/**
 * 
 * Un árbol n-ario inmutable
 * 
 * @author Miguel Toro
 *
 * @param <E> El tipo de los elementos del árbol
 * 
 */
public class Tree<E> {
	
	public enum TreeType{Empty,Leaf,Nary}
	
	public static Tree<Object> empty = new Tree<Object>();
	
	@SuppressWarnings("unchecked")
	public static <R> Tree<R> empty() {
		return (Tree<R>) empty;
	}
	
	public static <R> Tree<R> leaf(R label) {
		Preconditions.checkNotNull(label);
		return new Tree<R>(label);
	}

	public static <R> Tree<R> nary(R label, List<Tree<R>> elements) {
		Preconditions.checkNotNull(label);
		Preconditions.checkNotNull(elements);
		Preconditions.checkArgument(elements.stream().allMatch(x->x!=null));		
		if (elements.isEmpty()) {
			return new Tree<R>(label);
		} else {
			return new Tree<R>(label, elements);
		}
	}

	@SafeVarargs
	public static <R> Tree<R> nary(R label, Tree<R>... elements) {
		List<Tree<R>> nElements = Arrays.asList(elements);
		return nary(label, nElements);
	}
	
	
	public static <R> Tree<R> toTree(BinaryTree<R> t){
		Tree<R> r = null;
		switch(t.getType()) {
		case Empty: r = Tree.empty(); break;
		case Leaf: r = Tree.leaf(t.getLabel()); break;
		case Binary: r = Tree.nary(t.getLabel(), List.of(toTree(t.getLeft()),toTree(t.getRight())));
		}
		return r;
	}
	
	protected E label;
	private Integer id;
	protected final List<Tree<E>> elements;
	protected Tree<E> father;
	private final TreeType type;

	protected Tree() {
		super();
		this.id = null;
		this.label = null;
		this.elements = null;
		this.father = null;
		this.type = TreeType.Empty;
	}
	
	protected Tree(E label){
		super();		
		this.id = null;
		this.label = label;
		this.elements = null;
		this.father = null;
		this.type = TreeType.Leaf;
	}
		
	protected Tree(E label, List<Tree<E>> elements) {
		super();
		Preconditions.checkArgument(!elements.isEmpty(), "La lista no puede estar vacía");
		this.id = null;
		this.label = label;
		this.elements = new ArrayList<>(elements);
		this.father = null;
		this.elements.stream().forEach(e->e.father = this);
		this.type = TreeType.Nary;
	}
	
	public static Tree<String> parse(String s){
		Tokenizer t = Tokenizer.create(s);
		return Tree.parse(t);
	}
	
	private static Tree<String> parse(Tokenizer tk) {
		Tree<String> r;
		Token token = label_parse(tk);
		if (token.text.equals("_")) {
			r = Tree.empty();
		} else {
			r = Tree.leaf(token.text);
			if (tk.hasMoreTokens() && tk.isNextInTokens("(")) {
				List<Tree<String>> elements = new ArrayList<>();
				Tree<String> t;
				tk.matchTokens("(");
				t = parse(tk);
				elements.add(0, t);
				while (tk.isNextInTokens(",")) {
					tk.matchTokens(",");
					t = parse(tk);
					elements.add(0, t);
				}
				tk.matchTokens(")");
				r = Tree.nary(token.text, elements);
			}
		}
		return r;
	}
	
	private static Token label_parse(Tokenizer tk) {	
		Token token = tk.nextToken();
		switch (token.type) {			
		case Operator:
			if(tk.isCurrentInTokens("+","-")) {
				String op = token.text;
				token = tk.matchTokens(TokenType.Integer,TokenType.Double); 
				String nb = token.text;
				token.text = op+nb;
			} else tk.error("+","-");
			break;
		case Integer:
		case Double:	
		case VariableIdentifier:
			break;
		default: tk.error();
		}
		return token;	
	}
	
	/**
	 * @return El tipo del árbol
	 */
	public TreeType getType() {
		return type;
	}

	/**
	 * @return Verdadero si el árbol es vacio. 
	 */
	public boolean isEmpty() {
		return type.equals(TreeType.Empty);
	}
	/**
	 * @return Verdadero si el árbol es hoja. 
	 */
	public boolean isLeaf(){	
		return type.equals(TreeType.Leaf);
	}
	
	/**
	 * @param i Un entero
	 * @return Si this es el hijo i de su padre
	 */
	public boolean isChild(int i) {
		Boolean r;
		if(isRoot()) {
			r = false;
		} else {
			r = i>=0 && i < getFather().getNumOfChildren() && getFather().getChild(i) == this;
		}
		return r;
	}
	
	/**
	 * @return Verdadero si el árbol es nario. 
	 */
	public boolean isNary(){
		return type.equals(TreeType.Nary);
	}
	
	public E getLabel() {
		Preconditions.checkState(!isEmpty());
		return label;
	}

	public List<Tree<E>> getChildren() {
		return elements;
	}

	public Tree<E> getFather() {
		return father;
	}
	
	public boolean isRoot() {
		return father == null;
	}

	public Tree<E> getChild(int index) {
		Preconditions.checkNotNull(elements);
		Preconditions.checkElementIndex(index, elements.size());	 
		return elements.get(index);
	}

	public int getNumOfChildren(){
		int r = 0;
		switch(this.getType()) {
		case Empty: r = 0; break;
		case Leaf: r=0; break;
		case Nary: r = elements.size(); break;
		}
		return r;
	}
	
	public MutableTree<E> mutableView(){
		return MutableTree.mutableView(this);
	}
	
	public int size(){
		int r =0;
		switch(this.getType()) {
		case Empty: r = 0; break;
		case Leaf:  r = 1; break;
		case Nary: r = 1+(int)elements.stream().mapToInt(x->x.size()).sum(); ; break;
		}
		return r;
	}
	
	public int getHeight(){
		Integer r=0;
		switch(this.getType()) {
		case Empty: r = -1; break;
		case Leaf:  r = 0; break;
		case Nary: r = 1+ elements.stream().mapToInt(x->x.getHeight()).max().getAsInt(); break;
		}
		return r;
	}
	
	public Tree<E> copy(){
		Tree<E> r= null;
		switch(this.getType()) {
		case Empty: r = Tree.empty(); break;
		case Leaf:  r = Tree.leaf(label); break;
		case Nary:
			List<Tree<E>> nElements = elements.stream().map(x->x.copy()).collect(Collectors.toList());	
			r = Tree.nary(label, nElements);
			break;
		}
		return r;
	}
	
	
	public <R> Tree<R> copy(Function<E,R> f){
		Tree<R> r = null;
		switch(this.getType()) {
		case Empty: r = Tree.empty(); break;
		case Leaf:  r = Tree.leaf(f.apply(label)); break;
		case Nary:
			List<Tree<R>> nElements = elements.stream().map(x->x.copy(f)).collect(Collectors.toList());	
			return Tree.nary(f.apply(label), nElements);
		}
		return r;
	}
	/**
	 * @return Un árbol que es la imagen especular de this
	 */
	public Tree<E> getReverse() {
		Tree<E> r = null;
		switch (this.getType()) {
		case Empty: r = Tree.empty(); break;
		case Leaf: r = Tree.leaf(label); break;
		case Nary:
			List<Tree<E>> nElements = Lists2.reverse(elements).stream().map(x -> x.getReverse())
					.collect(Collectors.toList());
			r = Tree.nary(label, nElements);
		}
		return r;
	}
	
	private static Integer nId = 0;
	
	private void asignarNullAlId(){		
		switch (this.getType()) {
		case Empty: id = null; break;
		case Leaf: id = null; break;
		case Nary: elements.stream().forEach(x->x.asignarNullAlId());
		}
	}

	private PrintWriter head(String file, String titulo){	
		PrintWriter f = Files2.getWriter(file);		
		f.println("digraph "+titulo+" {  \n    size=\"100,100\"; ");	
		asignarNullAlId();
		return f;
	}
	
	private void writeLabel(PrintWriter file) {
		String s = "    \"%d\" [label=\"%s\"];";
		file.println(String.format(s,id,this.isEmpty()?"":getLabel().toString()));
	}
	
	private void writeEdge(PrintWriter file, int index){
		String s = "    \"%d\" -> \"%d\" [label=\"%d\"];";
		file.println(String.format(s,id,getChild(index).id,index));
	}
	
	public void toDOT(String file, String titulo) {
		nId = 0;
		PrintWriter f = head(file,titulo);
		toDOT(f);
		f.println("}");
		f.close();
	}
	
	private void toDOT(PrintWriter file) {
		if (id == null) {
			id = Tree.nId;
			Tree.nId++;
			writeLabel(file);
		}
		switch (this.getType()) {
		case Empty: break;
		case Leaf:  break;
		case Nary: 
			elements.stream().forEach(x -> x.toDOT(file));
			for (int i = 0; i < elements.size(); i++) {			
				writeEdge(file, i);
			}
		}
	}
	
	/**
	 * @return Una lista con el recorrido en preorden. 
	 */
	public List<E> getPreOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.newList(); break;
		case Leaf: r = Lists2.newList(this.label); break;
		case Nary:
			r = Lists2.newList(this.label);
			r.addAll(elements.stream().map(x -> x.getPreOrder()).reduce(Lists2.newList(), Lists2::concat));
		}
		return r;
	}
	
	
	
	/**
	 * @return Una lista con el recorrido en postorden
	 */
	public List<E> getPostOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.newList(); break;
		case Leaf: r = Lists2.newList(this.label); break;
		case Nary:
			r = elements.stream().map(x -> x.getPostOrder()).reduce(Lists2.newList(), Lists2::concat);
			r.add(label);
		}
		return r;
	}
	
	/**
	 * @post La etiqueta se insertará en al posición min(k,nh). Si k = 0 resulta el recorrido en preorden y si 
	 * k &ge; nh en postorden.
	 * @param k Posición de inserción de la etiqueta
	 * @return Una lista con el recorrido en inorden. 
	 */
	public List<E> getInOrder(int k){
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.newList(); break;
		case Leaf: r = Lists2.newList(this.label); break;
		case Nary:
			List<Tree<E>> nElements = Lists2.newList(elements);
			int nk = Math.min(k, elements.size());
			nElements.add(nk,Tree.leaf(label));
			r = nElements.stream()
					.map(x->x.getInOrder(k))
					.reduce(Lists2.newList(),Lists2::concat);
		}
		return r;
	}
	/**
	 * @return Una lista con los árboles por niveles. Versión iterativa
	 */
	public List<Tree<E>> getByLevel(){
		List<Tree<E>> r = Lists2.newList(this);
		List<Tree<E>> level = Lists2.newList(this);		
		while(!level.isEmpty()){
			level = getNextLevel(level);
			r.addAll(level);
		}
		return r;
	}
	
	/**
	 * @return Una lista con las etiquetas por niveles. Versión iterativa
	 */
	public List<E> getLabelByLevel(){
		return getByLevel().stream()
				.filter(x->!x.isEmpty())
				.map(x->x.getLabel())
				.collect(Collectors.toList());
	}
	
	/**
	 * @param level Los arboles de un nivel dado
	 * @return Los arboles del siguiente nivel
	 */
	public List<Tree<E>> getNextLevel(List<Tree<E>> level) {
		List<Tree<E>> nLevel;
		nLevel = new ArrayList<>();
		for (Tree<E> t : level) {
			switch (this.getType()) {
			case Empty:  break;
			case Leaf:  break;
			case Nary:
				nLevel.addAll(t.getChildren());
			}
		}
		return nLevel;
	}
	
	/**
	 * @param n Un entero
	 * @return Los arboles del nivel n
	 */
	public List<Tree<E>> getLevel(Integer n) {
		Integer i =0;
		List<Tree<E>> level = Arrays.asList(this);
		while(i<n){
			level = level.stream().flatMap(x->x.elements.stream()).collect(Collectors.toList());
			i++;
		}
		return level;
	}
	
	/**
	 * @param root La raiz del árbol dónde t es un subarbol
	 * @return La profundidad de t en root o -1 si no está
	 */
	public int getDepth(Tree<E> root){
		List<Tree<E>> level = Lists2.newList(this);
		int n = 0;		
		while(!level.isEmpty()){
			if(level.stream().anyMatch(x->x==this)){
				break;
			}
			level = getNextLevel(level);
			n++;
		}
		return n;
	}
	
	
	public String toString(){		
		String r = null;
		switch (this.getType()) {
		case Empty: r ="_"; break;
		case Leaf: r = label.toString(); break;
		case Nary:
			r = label.toString()+
			 elements.stream()
			.map(x->x.toString())
			.collect(Collectors.joining(",", "(", ")"));
		}
		return r;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elements == null) ? 0 : elements.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Tree))
			return false;
		Tree<?> other = (Tree<?>) obj;
		if (elements == null) {
			if (other.elements != null)
				return false;
		} else if (!elements.equals(other.elements))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;
		return true;
	}
	
	public static void test1() {
		List<String> filas = Streams2.fromFile("ficheros/test.txt").collect(Collectors.toList());
		Tree<String> nary = null;
		for (String fila : filas) {
			nary = Tree.parse(fila);
			System.out.println(nary);
		}
	}

	public static void main(String[] args) {
//		Tree<Integer> t1 = Tree.empty();
//		Tree<Integer> t2 = Tree.leaf(2);
//		Tree<Integer> t3 = Tree.leaf(3);
//		Tree<Integer> t4 = Tree.leaf(4);
//		Tree<Integer> t5 = Tree.nary(27,t1,t2,t3,t4);
//		Tree<Integer> t6 = Tree.nary(39, t2,t5);
//		System.out.println(t1);
//		System.out.println(t2);
//		System.out.println(t6);
//		String ex = "39(2,27(_,2,3,4))";
//		Tree<String> t7 = Tree.tree(ex);
//		System.out.println(t7);
//		System.out.println(Lists2.reverse(Lists2.newList(1,2,3,4,5,6,7,8,9)));
//		Tree<String> t8 = t7.getReverse();
//		System.out.println(t8);
//		System.out.println(t8.getChild(0).getFather());
//		System.out.println(Tree.tree("39(2.,27(_,2,3,4),9(8.,_))"));
		test1();
	}
	
}

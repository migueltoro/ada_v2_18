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
public class TreeImpl<E> implements MutableTree<E> {
	
	public static Tree<Object> empty = new TreeImpl<Object>();
	
	@SuppressWarnings("unchecked")
	public static <R> TreeImpl<R> empty() {
		return (TreeImpl<R>) empty;
	}
	
	public static <R> TreeImpl<R> leaf(R label) {
		Preconditions.checkNotNull(label);
		return new TreeImpl<R>(label);
	}

	public static <R> TreeImpl<R> nary(R label, List<TreeImpl<R>> elements) {
		Preconditions.checkNotNull(label);
		Preconditions.checkNotNull(elements);
		Preconditions.checkArgument(elements.stream().allMatch(x->x!=null));		
		if (elements.isEmpty()) {
			return new TreeImpl<R>(label);
		} else {
			return new TreeImpl<R>(label, elements);
		}
	}

	@SafeVarargs
	public static <R> TreeImpl<R> nary(R label, TreeImpl<R>... elements) {
		List<TreeImpl<R>> nElements = Arrays.asList(elements);
		return nary(label, nElements);
	}
	
	
	public static <R> Tree<R> toTree(BinaryTree<R> t){
		Tree<R> r = null;
		switch(t.getType()) {
		case Empty: r = TreeImpl.empty(); break;
		case Leaf: r = Tree.leaf(t.getLabel()); break;
		case Binary: r = Tree.nary(t.getLabel(), List.of(toTree(t.getLeft()),toTree(t.getRight())));
		}
		return r;
	}
	
	protected E label;
	private Integer id;
	protected final List<TreeImpl<E>> elements;
	protected Tree<E> father;
	private final TreeType type;

	protected TreeImpl() {
		super();
		this.id = null;
		this.label = null;
		this.elements = null;
		this.father = null;
		this.type = TreeType.Empty;
	}
	
	protected TreeImpl(E label){
		super();		
		this.id = null;
		this.label = label;
		this.elements = null;
		this.father = null;
		this.type = TreeType.Leaf;
	}
		
	protected TreeImpl(E label, List<TreeImpl<E>> elements) {
		super();
		Preconditions.checkArgument(!elements.isEmpty(), "La lista no puede estar vacía");
		this.id = null;
		this.label = label;
		this.elements = new ArrayList<>(elements);
		this.father = null;
		this.elements.stream().forEach(e->((TreeImpl<E>)e).setFather(this));
		this.type = TreeType.Nary;
	}
	
	public static Tree<String> parse(String s){
		Tokenizer t = Tokenizer.create(s);
		return TreeImpl.parse(t);
	}
	
	private static Tree<String> parse(Tokenizer tk) {
		Tree<String> r;
		Token token = label_parse(tk);
		if (token.text.equals("_")) {
			r = TreeImpl.empty();
		} else {
			r = TreeImpl.leaf(token.text);
			if (tk.hasMoreTokens() && tk.isNextInTokens("(")) {
				List<Tree<String>> elements = new ArrayList<>();
				Tree<String> t;
				tk.matchTokens("(");
				t = parse(tk);
				elements.add(t);
				while (tk.isNextInTokens(",")) {
					tk.matchTokens(",");
					t = parse(tk);
					elements.add(t); //cambio aqui
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
		case Variable:
			break;
		default: tk.error();
		}
		return token;	
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getType()
	 */
	@Override
	public TreeType getType() {
		return type;
	}

	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return type.equals(TreeType.Empty);
	}
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#isLeaf()
	 */
	@Override
	public boolean isLeaf(){	
		return type.equals(TreeType.Leaf);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#isChild(int)
	 */
	@Override
	public boolean isChild(int i) {
		Boolean r;
		if(isRoot()) {
			r = false;
		} else {
			r = i>=0 && i < getFather().getNumOfChildren() && getFather().getChild(i) == this;
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#isNary()
	 */
	@Override
	public boolean isNary(){
		return type.equals(TreeType.Nary);
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getLabel()
	 */
	@Override
	public E getLabel() {
		Preconditions.checkState(!isEmpty());
		return label;
	}

	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getChildren()
	 */
	@Override
	public List<Tree<E>> getChildren() {
		return elements.stream().map(x->x).collect(Collectors.toList());
	}

	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getFather()
	 */
	@Override
	public Tree<E> getFather() {
		return father;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#isRoot()
	 */
	@Override
	public boolean isRoot() {
		return father == null;
	}

	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getChild(int)
	 */
	@Override
	public Tree<E> getChild(int index) {
		Preconditions.checkNotNull(elements);
		Preconditions.checkElementIndex(index, elements.size());	 
		return elements.get(index);
	}
	
	public void setLabel(E label) {
		Preconditions.checkNotNull(label);
		this.label = label;
	}
	
	public void setChild(int i, Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		TreeImpl<E> tt = (TreeImpl<E>) tree;
		tt.father = this;
		this.elements.set(i, tt);
	}

	public void addChild(Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		TreeImpl<E> tt = (TreeImpl<E>) tree;
		tt.father = this;
		this.elements.add(tt);
	}
	
	public void addChild(int i, Tree<E> tree) {
		Preconditions.checkNotNull(tree);
		TreeImpl<E> tt = (TreeImpl<E>) tree;
		tt.father = this;
		this.elements.add(i,tt);
	} 
	
	public void removeChild(int i) {
		TreeImpl<E> tt = (TreeImpl<E>) this.elements.get(i);
		tt.setFather(null);
		this.elements.remove(i);
	}
	
	public void setFather(Tree<E> father) {
		this.father = father;
	}
	
	/**
	 * @post this pasa a ser un arbol raiz si no lo era antes
	 * @param nTree Un árbol 
	 * @return Si this no es raiz devuelve el arbol padre con el hijo this cambiado por nTree. Si this es raiz devuelve nTree
	 */
	public Tree<E> changeFor(Tree<E> nTree) {
		Tree<E> r;
		if(this.isRoot()) {
			r = nTree;
		} else {
			r = this.getFather();
			MutableTree<E> rm = r.mutableView();
			setFather(null);
			for (int i = 0; i < this.getNumOfChildren(); i++) {
				if (this.isChild(i)) {
					rm.setChild(i, nTree);
				}
			}
		}
		return r;
	}

	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getNumOfChildren()
	 */
	@Override
	public int getNumOfChildren(){
		int r = 0;
		switch(this.getType()) {
		case Empty: r = 0; break;
		case Leaf: r=0; break;
		case Nary: r = elements.size(); break;
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#mutableView()
	 */
	@Override
	public MutableTree<E> mutableView(){
		return (MutableTree<E>) this;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#size()
	 */
	@Override
	public int size(){
		int r =0;
		switch(this.getType()) {
		case Empty: r = 0; break;
		case Leaf:  r = 1; break;
		case Nary: r = 1+(int)elements.stream().mapToInt(x->x.size()).sum(); ; break;
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getHeight()
	 */
	@Override
	public int getHeight(){
		Integer r=0;
		switch(this.getType()) {
		case Empty: r = -1; break;
		case Leaf:  r = 0; break;
		case Nary: r = 1+ elements.stream().mapToInt(x->x.getHeight()).max().getAsInt(); break;
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#copy()
	 */
	@Override
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
	
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#copy(java.util.function.Function)
	 */
	@Override
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
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getReverse()
	 */
	@Override
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
		case Nary: elements.stream().forEach(x->((TreeImpl<E>)x).asignarNullAlId());
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
		
		file.println(String.format(s,id,((TreeImpl<E>)getChild(index)).id,index));
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#toDOT(java.lang.String, java.lang.String)
	 */
	@Override
	public void toDOT(String file, String titulo) {
		nId = 0;
		PrintWriter f = head(file,titulo);
		toDOT(f);
		f.println("}");
		f.close();
	}
	
	private void toDOT(PrintWriter file) {
		if (id == null) {
			id = TreeImpl.nId;
			TreeImpl.nId++;
			writeLabel(file);
		}
		switch (this.getType()) {
		case Empty: break;
		case Leaf:  break;
		case Nary: 
			elements.stream().forEach(x -> ((TreeImpl<E>)x).toDOT(file));
			for (int i = 0; i < elements.size(); i++) {			
				writeEdge(file, i);
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getPreOrder()
	 */
	@Override
	public List<E> getPreOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.empty(); break;
		case Leaf: r = Lists2.of(this.label); break;
		case Nary:
			r = Lists2.of(this.label);
			r.addAll(elements.stream().map(x -> x.getPreOrder()).reduce(Lists2.empty(), Lists2::concat));
		}
		return r;
	}
	
	
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getPostOrder()
	 */
	@Override
	public List<E> getPostOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.empty(); break;
		case Leaf: r = Lists2.of(this.label); break;
		case Nary:
			r = elements.stream().map(x -> x.getPostOrder()).reduce(Lists2.empty(), Lists2::concat);
			r.add(label);
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getInOrder(int)
	 */
	@Override
	public List<E> getInOrder(int k){
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = Lists2.empty(); break;
		case Leaf: r = Lists2.of(this.label); break;
		case Nary:
			List<TreeImpl<E>> nElements = Lists2.ofCollection(elements);
			int nk = Math.min(k, elements.size());
			nElements.add(nk,TreeImpl.leaf(label));
			r = nElements.stream()
					.map(x->x.getInOrder(k))
					.reduce(Lists2.empty(),Lists2::concat);
		}
		return r;
	}
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getByLevel()
	 */
	@Override
	public List<Tree<E>> getByLevel(){
		List<Tree<E>> r = Lists2.of(this);
		List<Tree<E>> level = Lists2.of(this);		
		while(!level.isEmpty()){
			level = getNextLevel(level);
			r.addAll(level);
		}
		return r;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getLabelByLevel()
	 */
	@Override
	public List<E> getLabelByLevel(){
		return getByLevel().stream()
				.filter(x->!x.isEmpty())
				.map(x->x.getLabel())
				.collect(Collectors.toList());
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getNextLevel(java.util.List)
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getLevel(java.lang.Integer)
	 */
	@Override
	public List<Tree<E>> getLevel(Integer n) {
		Integer i =0;
		List<Tree<E>> level = Arrays.asList(this);
		while(i<n){
			level = level.stream().flatMap(x->x.getChildren().stream()).collect(Collectors.toList());
			i++;
		}
		return level;
	}
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#getDepth(us.lsi.tiposrecursivos.Tree)
	 */
	@Override
	public int getDepth(Tree<E> root){
		List<Tree<E>> level = Lists2.of(this);
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
	
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#toString()
	 */
	@Override
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

	
	
	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#hashCode()
	 */
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

	/* (non-Javadoc)
	 * @see us.lsi.tiposrecursivos.Tree#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TreeImpl))
			return false;
		TreeImpl<?> other = (TreeImpl<?>) obj;
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
		System.out.println("--------------------");
		List<String> filas = Files2.linesFromFile("ficheros/test.txt");
		Tree<String> nary = null;
		for (String fila : filas) {
			nary = TreeImpl.parse(fila);
			System.out.println(nary);
		}
	}

	public static void main(String[] args) {
		Tree<Integer> t1 = Tree.empty();
		Tree<Integer> t2 = Tree.leaf(2);
		Tree<Integer> t3 = Tree.leaf(3);
		Tree<Integer> t4 = Tree.leaf(4);
		Tree<Integer> t5 = Tree.nary(27,t1,t2,t3,t4);
		Tree<Integer> t6 = Tree.nary(39,t2,t5);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t6);
		String ex = "39(2,27(_,2,3,4))";
		Tree<String> t7 = Tree.parse(ex);
		System.out.println(t7);
		System.out.println(Lists2.reverse(Lists2.of(1,2,3,4,5,6,7,8,9)));
		Tree<String> t8 = t7.getReverse();
		System.out.println(t8);
		System.out.println(t8.getChild(0).getFather());
		System.out.println(Tree.parse("39(2.,27(_,2,3,4),9(8.,_))"));
		test1();
	}
	
}

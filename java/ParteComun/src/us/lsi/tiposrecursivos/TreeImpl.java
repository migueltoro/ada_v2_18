package us.lsi.tiposrecursivos;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Preconditions;
import us.lsi.common.Printers;
import us.lsi.common.ViewL;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.parsers.TreeLexer;
import us.lsi.tiposrecursivos.parsers.TreeParser;

import java.io.PrintStream;
import java.io.PrintWriter;

/**
 * 
 * Un �rbol n-ario inmutable
 * 
 * @author Miguel Toro
 *
 * @param <E> El tipo de los elementos del �rbol
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
	
	@SuppressWarnings("unchecked")
	public static Tree<String> parse(String s){
		TreeLexer lexer = new TreeLexer(CharStreams.fromString(s));
		TreeParser parser = new TreeParser(new CommonTokenStream(lexer));
	    ParseTree parseTree = parser.nary_tree();
	    Tree<String> tree =  (Tree<String>) parseTree.accept(new TreeVisitorC());
	    return tree;
	}
	
	public static <R> Tree<R> parse(String s, Function<String,R> f){
		Tree<String> tree = TreeImpl.parse(s);
		return tree.map(f);
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
		this.elements = new ArrayList<>();
		this.father = null;
		this.type = TreeType.Empty;
	}
	
	protected TreeImpl(E label){
		super();		
		this.id = null;
		this.label = label;
		this.elements = new ArrayList<>();
		this.father = null;
		this.type = TreeType.Leaf;
	}
		
	protected TreeImpl(E label, List<TreeImpl<E>> elements) {
		super();
		Preconditions.checkArgument(!elements.isEmpty(), "La lista no puede estar vac�a");
		this.id = null;
		this.label = label;
		this.elements = new ArrayList<>(elements);
		this.father = null;
		this.elements.stream().forEach(e->((TreeImpl<E>)e).setFather(this));
		this.type = TreeType.Nary;
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
	
	@Override
	public ViewL<Tree<E>,E> viewL() {
		Preconditions.checkArgument(!this.isEmpty(),String.format("El �rbol no puede ser vac�o y es %s",this));
		return switch(this.type) {
		case Nary -> ViewL.of(this.getLabel(),this.getChildren());
		case Empty -> null;
		case Leaf -> ViewL.of(this.getLabel(),List.of());
		};		
	}
	
	@Override
	public Iterator<Tree<E>> iterator(){
		return DepthPathTree.of(this);
	}
	
	@Override
	public Stream<Tree<E>> stream(){
		return Stream2.asStream(()->this.iterator());
	}
	
	public Iterator<TreeLevel<E>> byLevel(){
		return BreadthPathTree.of(this);
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
	 * @param nTree Un �rbol 
	 * @return Si this no es raiz devuelve el arbol padre con el hijo this cambiado por nTree. Si this es raiz devuelve nTree
	 */
	
	public Tree<E> changeFor(Tree<E> nTree) {
		Tree<E> r;
		if(this.isRoot()) {
			r = nTree;
		} else {
			r = this.getFather();
			MutableTree<E> rm = (MutableTree<E>)r;
			setFather(null);
			for (int i = 0; i < this.getNumOfChildren(); i++) {
				if (this.isChild(i)) {
					rm.setChild(i, nTree);
				}
			}
		}
		return r;
	}

	
	@Override
	public int getNumOfChildren(){
		return switch(this.getType()) {
		case Empty->0;
		case Leaf->0;
		case Nary->elements.size();
		};
	}
	
	
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
	
	
	@Override
	public int getHeight(){
		return switch(this.getType()) {
		case Empty ->0;
		case Leaf -> 0;
		case Nary->1+ elements.stream().mapToInt(x->x.getHeight()).max().getAsInt(); 
		};	
	}
	
	
	@Override
	public Tree<E> copy(){
		return switch(this.getType()) {
		case Empty -> Tree.empty(); 
		case Leaf -> Tree.leaf(label);
		case Nary -> Tree.nary(label,elements.stream().map(x->x.copy()).toList());
		};
	}
	
	
	
	@Override
	public <R> Tree<R> map(Function<E,R> f){
		Tree<R> r = null;
		switch(this.getType()) {
		case Empty: r = Tree.empty(); break;
		case Leaf:  r = Tree.leaf(f.apply(label)); break;
		case Nary:
			List<Tree<R>> nElements = elements.stream().map(x->x.map(f)).collect(Collectors.toList());	
			return Tree.nary(f.apply(label), nElements);
		}
		return r;
	}
	
	
	
	@Override
	public Tree<E> getReverse() {
		Tree<E> r = null;
		switch (this.getType()) {
		case Empty: r = Tree.empty(); break;
		case Leaf: r = Tree.leaf(label); break;
		case Nary:
			List<Tree<E>> nElements = List2.reverse(elements).stream().map(x -> x.getReverse())
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
	
	@Override
	public List<E> getPreOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = List2.empty(); break;
		case Leaf: r = List2.of(this.label); break;
		case Nary:
			r = List2.of(this.label);
			r.addAll(elements.stream().map(x -> x.getPreOrder()).reduce(List2.empty(), List2::concat));
		}
		return r;
	}
	
	
	
	@Override
	public List<E> getPostOrder() {
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = List2.empty(); break;
		case Leaf: r = List2.of(this.label); break;
		case Nary:
			r = elements.stream().map(x -> x.getPostOrder()).reduce(List2.empty(), List2::concat);
			r.add(label);
		}
		return r;
	}
	
	
	@Override
	public List<E> getInOrder(int k){
		List<E> r = null;
		switch (this.getType()) {
		case Empty: r = List2.empty(); break;
		case Leaf: r = List2.of(this.label); break;
		case Nary:
			List<TreeImpl<E>> nElements = List2.ofCollection(elements);
			int nk = Math.min(k, elements.size());
			nElements.add(nk,TreeImpl.leaf(label));
			r = nElements.stream()
					.map(x->x.getInOrder(k))
					.reduce(List2.empty(),List2::concat);
		}
		return r;
	}
	
	
	@Override
	public List<Tree<E>> getByLevel(){
		List<Tree<E>> r = List2.of(this);
		List<Tree<E>> level = List2.of(this);		
		while(!level.isEmpty()){
			level = getNextLevel(level);
			r.addAll(level);
		}
		return r;
	}
	
	@Override
	public List<E> getLabelByLevel(){
		return getByLevel().stream()
				.filter(x->!x.isEmpty())
				.map(x->x.getLabel())
				.collect(Collectors.toList());
	}
	
	
	@Override
	public List<Tree<E>> getNextLevel(List<Tree<E>> level) {
		List<Tree<E>> nLevel;
		nLevel = new ArrayList<>();
		for (Tree<E> t : level) {
			switch (t.getType()) {
			case Empty:  break;
			case Leaf:  break;
			case Nary: nLevel.addAll(t.getChildren());
			}
		}
		return nLevel;
	}
	
	
	@Override
	public List<Tree<E>> getLevel(Integer n) {
		Preconditions.checkArgument(n>=0,String.format("El nivel debe ser mayor o igual a cero y es %d",n));
		List<Tree<E>> level = Arrays.asList(this);
		for(Integer i=0;i<n; i++){
			level = this.getNextLevel(level);
		}
		return level;
	}
	
	@Override
	public int getDepth(Tree<E> root){
		List<Tree<E>> level = List2.of(root);
		int n = 0;	
		int r = -1;
		while(!level.isEmpty()){
			if(level.stream().anyMatch(x->x.equals(this))) {
				r = n;
				break;
			}
			level = getNextLevel(level);
			n++;
		}
		return r;
	}
	
	
	private static Integer getIndex(Object e, Map<Object,Integer> map, String label, PrintStream file) {
		Integer r;
		if(map.containsKey(e)) r = map.get(e);
		else {
			r = map.get("maxValue")+1;
			map.put("maxValue",r);
			map.put(e, r);
			vertex(r,label,file);
		}
		return r;
	}
	
	private static void vertex(Integer n, String text, PrintStream file) {
		String txt = String.format("\"%s\" [label=\"%s\"];",n,text);
		file.println(txt);
	}
	
	private static void edge(Integer v1, Integer v2, PrintStream file) {
		edge(v1,v2,null,file);
	}
	
	private static void edge(Integer v1, Integer v2, String text, PrintStream file) {
		String txt;
		if (text!=null) 
			txt = String.format("\"%s\" -> \"%s\" [label=\"%s\"];", v1, v2, text);
		else
			txt = String.format("\"%s\" -> \"%s\";", v1, v2);
		file.println(txt);
	}
	
	public void toDot(String file) {
		PrintStream p = Printers.file(file);
		Map<Object,Integer> map = new HashMap<>();
		map.put("maxValue",0);
		String txt = "digraph Tree { \n \n"; 
		p.println(txt);
		toDot(p,map);
		p.println("}");
		p.close();
	}
	
	private void toDot(PrintStream p, Map<Object, Integer> map) {
		switch(this.getType()) {
		case Nary:
			Integer n = getIndex(this,map,this.getLabel().toString(),p);
			for(Tree<E> t:this.getChildren()) {
				Integer nt = getIndex(t,map,t.isEmpty()?"_":t.getLabel().toString(),p);
				edge(n,nt,p);
				((TreeImpl<E>)t).toDot(p,map);
			}
			break;
		case Empty:
			getIndex(this,map,"_",p);
			break;
		case Leaf:
			getIndex(this,map,this.getLabel().toString(),p);
			break;
		}
	}
	

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
	
	public static class DepthPathTree<E> implements Iterator<Tree<E>>, Iterable<Tree<E>> {
		
		public static <E> DepthPathTree<E> of(Tree<E> tree){
			return new DepthPathTree<E>(tree);
		}

		private Stack<Tree<E>> stack;
		
		public DepthPathTree(Tree<E> tree) {
			super();
			this.stack = new Stack<>();
			this.stack.add(tree);
		}

		@Override
		public Iterator<Tree<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		@Override
		public Tree<E> next() {
			Tree<E> actual = stack.pop();
			switch(actual.getType()) {
			case Nary: 
				for(Tree<E> v:List2.reverse(actual.getChildren())) {
					stack.add(v);
				}
				break;
			case Empty:break;
			case Leaf:break;
			}
			return actual;
		}
		
	}
	
	public static class BreadthPathTree<E> implements Iterator<TreeLevel<E>>, Iterable<TreeLevel<E>> {
		
		public static <E> BreadthPathTree<E> of(Tree<E> tree){
			return new BreadthPathTree<E>(tree);
		}

		private Queue<TreeLevel<E>> queue;
		
		public BreadthPathTree(Tree<E> tree) {
			super();
			this.queue = new LinkedList<>();
			this.queue.add(TreeLevel.of(0,tree));
		}

		@Override
		public Iterator<TreeLevel<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		@Override
		public TreeLevel<E> next() {
			TreeLevel<E> actual = queue.remove();
			switch(actual.tree().getType()) {
			case Nary: 
				for(TreeLevel<E> v:actual.tree().getChildren().stream().map(t->TreeLevel.of(actual.level()+1,t)).toList()) {
					queue.add(v);
				}
				break;
			case Empty:break;
			case Leaf:break;
			}
			return actual;
		}
		
	}
	
}

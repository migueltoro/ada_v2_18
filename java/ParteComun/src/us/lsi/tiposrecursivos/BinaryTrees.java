package us.lsi.tiposrecursivos;

import java.io.Writer;
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

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.BinaryTree.BEmpty;
import us.lsi.tiposrecursivos.BinaryTree.BLeaf;
import us.lsi.tiposrecursivos.BinaryTree.BTree;

public class BinaryTrees {
	
	public static <E> Stream<BinaryTree<E>> byDeph(BinaryTree<E> tree) {
		return Stream2.ofIterator(DepthPathBinaryTree.of(tree));
	}
	
	public static <E> Stream<BinaryTreeLevel<E>> byLevel(BinaryTree<E> tree) {
		return Stream2.ofIterator(BreadthPathBinaryTree.of(tree));
	}
	
	public static <E> void toDot(BinaryTree<E> tree, String file) {
		Graph<Nv<E>,DefaultEdge> graph = 
				new SimpleDirectedGraph<Nv<E>,DefaultEdge>(null,()->new DefaultEdge(),true);
		BinaryTree<Nv<E>> tt = BinaryTrees.map(tree);
		toDot1(tt,graph);
		System.out.println(graph);
		toDot2(tt,graph);
		toDot3(graph,file,v->v.v()!=null?v.v().toString().toString():"_",e->"");
	}
	
	private static Integer nv = 0;
	public static Integer nextInteger() {
		Integer n = nv;
		nv++;
		return n;
	}
	
	public static record Nv<E>(Integer n, E v) {
		public static <E> Nv<E> of(Integer n, E v) {return new Nv<E>(n,v);}
		public String toString() {return String.format("(%d,%s)",this.n(),this.v());}
	}
	
	public static <E> BinaryTree<Nv<E>> map(BinaryTree<E> tree) {
		return switch(tree) {
		case BEmpty<E> t -> BinaryTree.leaf(Nv.of(BinaryTrees.nextInteger(),null));
		case BLeaf<E> t -> BinaryTree.leaf(Nv.of(BinaryTrees.nextInteger(),t.label()));
		case BTree<E> t -> BinaryTree.binary(Nv.of(BinaryTrees.nextInteger(),t.label()),
				BinaryTrees.map(t.left()),BinaryTrees.map(t.right()));
		};
	}
	
	private static <E> void toDot1(BinaryTree<Nv<E>> tt, Graph<Nv<E>,DefaultEdge> graph) {
		switch(tt) {
		case BEmpty<Nv<E>> t: break;
		case BLeaf<Nv<E>> t : graph.addVertex(t.label()); break;
		case BTree<Nv<E>> t : 
			graph.addVertex(t.label()); 
			toDot1(t.left(), graph);
			toDot1(t.right(), graph);
			break;
		}
	}
	
	private static <E> void toDot2(BinaryTree<Nv<E>> tt, Graph<Nv<E>,DefaultEdge> graph) {
		switch(tt) {
		case BEmpty<Nv<E>> t: break;
		case BLeaf<Nv<E>> t : break;
		case BTree<Nv<E>> t : 
			if(t.left() instanceof BLeaf<Nv<E>> tl) graph.addEdge(t.label(),tl.label());
			if(t.left() instanceof BTree<Nv<E>> tl) graph.addEdge(t.label(),tl.label());
			if(t.right() instanceof BLeaf<Nv<E>> tr) graph.addEdge(t.label(),tr.label());
			if(t.right() instanceof BTree<Nv<E>> tr) graph.addEdge(t.label(),tr.label());
			toDot2(t.left(),graph);
			toDot2(t.right(),graph);
			break;
		}
	}

	private static Map<String, Attribute> label(String label) {
		if(label.equals("")) return new HashMap<>();
		return Map.of("label", DefaultAttribute.createAttribute(label));
	}
	
	private static <V,E> void toDot3(Graph<V,E> graph, String file, 
			Function<V,String> vertexLabel,
			Function<E,String> edgeLabel) {		
		DOTExporter<V,E> de = new DOTExporter<V,E>();
		de.setVertexAttributeProvider(v->BinaryTrees.label(vertexLabel.apply(v)));
		de.setEdgeAttributeProvider(e->BinaryTrees.label(edgeLabel.apply(e)));		
		Writer f1 = Files2.getWriter(file);
		de.exportGraph(graph, f1);
	}

	public static class DepthPathBinaryTree<E> implements Iterator<BinaryTree<E>>, Iterable<BinaryTree<E>> {

		public static <E> DepthPathBinaryTree<E> of(BinaryTree<E> tree) {
			return new DepthPathBinaryTree<E>(tree);
		}

		private Stack<BinaryTree<E>> stack;

		public DepthPathBinaryTree(BinaryTree<E> tree) {
			super();
			this.stack = new Stack<>();
			this.stack.add(tree);
		}

		@Override
		public Iterator<BinaryTree<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.stack.isEmpty();
		}

		@Override
		public BinaryTree<E> next() {
			BinaryTree<E> actual = stack.pop();
			switch (actual) {
			case BTree<E> t -> {
				for (BinaryTree<E> v : List.of(t.left(), t.right())) {
					stack.add(v);
				}
			}
			case BEmpty<E> t -> {}
			case BLeaf<E> t -> {}
			}
			return actual;
		}	
	}
	
	public static record BinaryTreeLevel<E>(Integer level, BinaryTree<E> tree){
		public static <R> BinaryTreeLevel<R> of(Integer level, BinaryTree<R> tree){
			return new BinaryTreeLevel<R>(level,tree);
		}
		@Override
		public String toString() {
			return String.format("(%d,%s)",this.level,this.tree);
		}
	}
	
	public static class BreadthPathBinaryTree<E> implements Iterator<BinaryTreeLevel<E>>, Iterable<BinaryTreeLevel<E>> {
		
		public static <E> BreadthPathBinaryTree<E> of(BinaryTree<E> tree){
			return new BreadthPathBinaryTree<E>(tree);
		}

		private Queue<BinaryTreeLevel<E>> queue;
		
		public BreadthPathBinaryTree(BinaryTree<E> tree) {
			super();
			this.queue = new LinkedList<>();
			this.queue.add(BinaryTreeLevel.of(0,tree));
		}

		@Override
		public Iterator<BinaryTreeLevel<E>> iterator() {
			return this;
		}

		@Override
		public boolean hasNext() {
			return !this.queue.isEmpty();
		}

		@Override
		public BinaryTreeLevel<E> next() {
			BinaryTreeLevel<E> actual = queue.remove();
			switch(actual.tree()) {
			case BTree<E> t -> { 
				for(BinaryTreeLevel<E> v:
					List.of(t.left(),t.right()).stream()
						.map(x->BinaryTreeLevel.of(actual.level()+1,x)).toList()) {
					queue.add(v);
				}
			}
			case BEmpty<E> t -> {}
			case BLeaf<E> t -> {}
			}
			return actual;
		}
		
	}
	
	public static void test1() {
		List<String> filas = Files2.streamFromFile("ficheros/test2.txt").collect(Collectors.toList());
		BinaryTree<String> tree = null;
		for (String fila : filas) {
			tree = BinaryTree.parse(fila);
			System.out.println(tree);
		}
	}

	public static void test2() {
		BinaryTree<Integer> t1 = BinaryTree.empty();
		BinaryTree<Integer> t2 = BinaryTree.leaf(2);
		BinaryTree<Integer> t3 = BinaryTree.leaf(3);
		BinaryTree<Integer> t4 = BinaryTree.leaf(4);
		BinaryTree<Integer> t5 = BinaryTree.binary(27, BinaryTree.binary(42, t1, t2), BinaryTree.binary(59, t3, t4));
		BinaryTree<Integer> t6 = BinaryTree.binary(39, t2, t5);
		System.out.println(t1);
		System.out.println(t2);
		System.out.println(t6);
		String ex = "-43.7(2.1,abc(-27.3(_,2),78.2(3,4)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);
		System.out.println(t7);
		System.out.println(List2.reverse(List2.of(1, 2, 3, 4, 5, 6, 7, 8, 9)));
		BinaryTree<String> t8 = t7.reverse();
		System.out.println(t8);
		
	}
	
	public static void test3() {
		String ex = "-43.7(2.1,abc(-27.3(_,2),78.2(3,4)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);		
		System.out.println(t7);
		BinaryTrees.byLevel(t7).forEach(t->System.out.println(t));
		System.out.println("______________");
		System.out.println(t7);
		BinaryTrees.byLevel(t7).forEach(t->System.out.println(t));
	}
	
	public static void test4() {
		String ex = "-43.7(2.1,56.7(-27.3(_,2),78.2(3,4)))";
		String ex2 = "-43.7(2.1,5(_,8.(3.,5.)))";
		BinaryTree<String> t7 = BinaryTree.parse(ex);	
		BinaryTree<String> t8 = BinaryTree.parse(ex2);	
		System.out.println(t7);
		System.out.println(t8);
		System.out.println(t7.equilibrateType());
		System.out.println(t8.equilibrateType());
		t7.toDot("ficheros/binary_tree.gv");
		BinaryTree<String> t9 = t7.equilibrate();
		BinaryTree<String> t10 = t8.equilibrate();
		t9.toDot("ficheros/binary_tree_equilibrate.gv");
		System.out.println(t9);
		System.out.println(t10);
		BinaryTree<Double> t11 =  t7.map(e->Double.parseDouble(e));
		System.out.println(t11);
	}
	
	public static void main(String[] args) {
		test4();
	}
	
	
}

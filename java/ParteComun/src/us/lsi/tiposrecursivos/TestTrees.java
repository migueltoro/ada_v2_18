package us.lsi.tiposrecursivos;

import java.util.List;
import java.util.function.Function;

import us.lsi.common.Files2;
import us.lsi.common.List2;
import us.lsi.common.Pair;
import us.lsi.streams.Stream2;
import us.lsi.tiposrecursivos.Tree.TreeLevel;

public class TestTrees {
	public static void test0() {
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
		System.out.println(List2.reverse(List2.of(1,2,3,4,5,6,7,8,9)));
		Tree<String> t8 = t7.getReverse();
		System.out.println(t8);
		System.out.println(t8.getChild(0).getFather());
		System.out.println(Tree.parse("39(2.,27(_,2,3,4),9(8.,_))"));
	}
	
	public static void test1() {
		System.out.println("--------------------");
		List<String> filas = Files2.linesFromFile("ficheros/test2.txt");
		Tree<String> nary = null;
		for (String fila : filas) {
			nary = TreeImpl.parse(fila);
			System.out.println(nary);
		}
	}
	
	
	public static void test2() {
		System.out.println("--------------------");
		List<String> filas = Files2.linesFromFile("ficheros/test2.txt");
		for (String fila : filas) {
			Tree<String> nary = TreeImpl.parse(fila);
			List<Tree<String>> nary2 = nary.getLevel(2);
			System.out.println("Tree ="+nary);
			System.out.println("Level 2 ="+nary2);
			System.out.println("Labels ="+nary.getLabelByLevel());
		}
	}
	
	
	public static void test3() {
		Tree<String> t1 = Tree.parse("39(2.,27(_,2,3,4),9(8.,_))");
		Tree<Double> t2 = t1.map(s->Double.parseDouble(s));
		Tree<String> t3 = Tree.parse("9(8.,_)");
		Tree<Double> t4 = t3.map(s->Double.parseDouble(s));
		int d = t4.getDepth(t2);
		System.out.println(t2);
		System.out.println(t4);
		System.out.println(d);
	}
	
	public static void test4() {
		Function<TreeLevel<String>,String> f = t->t.tree().isEmpty()?"_":t.tree().getLabel().toString();
		String ex = "39(2.,27(_,2,3,4),9(8.,_))";
		Tree<String> t7 = Tree.parse(ex);		
		System.out.println(t7);
		Stream2.of(t7).map(t->t.isEmpty()?"_":t.getLabel()).forEach(t->System.out.println(t));
		System.out.println("______________");
		System.out.println(t7);
		Stream2.of(()->t7.byLevel())
			.map(t->Pair.of(t.level(),f.apply(t)))
			.forEach(t->System.out.println(t));
	}
	
	public static void test5() {
		String ex = "39(2.,27(_,2,3,4),9(8.,_))";
		Tree<String> t7 = Tree.parse(ex);		
		System.out.println(t7);
		t7.toDot("ficheros/tree.gv");
	}

	public static void main(String[] args) {
		test2();
	}

}

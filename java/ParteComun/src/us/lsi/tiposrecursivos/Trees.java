package us.lsi.tiposrecursivos;

import us.lsi.common.String2;

public class Trees {
	
	public static <E> Integer size(Tree<E> tree) {
		return (int) tree.stream().filter(t->!t.isEmpty()).mapToInt(t->1).count();
	}
	
	public static void test1() {
		Tree<String> t1 = Tree.parse("39(2.,27(_,2,3,4),9(8.,_))");
		String2.toConsole("%d,%d",Trees.size(t1),t1.size());
	}
	
	public static void main(String[] args) {
		test1();
	}

}

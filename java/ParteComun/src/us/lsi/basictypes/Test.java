package us.lsi.basictypes;

import us.lsi.common.Strings2;

public class Test {

	/**
	 * @param args Argumentos
	 */
	public static void main(String[] args) {

		AList<Integer> d = AList.empty();
		for(int i = 0; i < 23; i++){
			d.add(i);
		}
		d.add(23,-1);
		d.remove(3);
		d.set(2,-3);
		d.set(23,-1);
		System.out.println("Array = "+d+"=="+d.size());
		
		LList<Integer> d2 = new LList<Integer>();
		for(int i = 0; i < 23; i++){
			d2.add(i);
		}
		d2.add(23, -1);
		d2.remove(3);
		d2.set(2,-3);		

		System.out.println("Array = "+d2+"=="+d2.size());
		
		AList<Integer> d3 = AList.empty();
		for(int i = 0; i < 23; i++){
			d3.set(i,2*i);
		}
		System.out.println("Array = "+d3+"=="+d3.size());
		HashTable<Integer,Double> d4 = HashTable.empty();
		for(int i=0;i<35;i++){
			d4.put(i, 5.*i);
		}
		double r = d4.put(3, -15.);
		double r2 = d4.remove(6);
		System.out.println("Hash Size = "+d4.size()+", r = "+r+", r2 = "+r2);
		Strings2.toConsole(d4.entryList(), "Hash Table 1");
	}

}

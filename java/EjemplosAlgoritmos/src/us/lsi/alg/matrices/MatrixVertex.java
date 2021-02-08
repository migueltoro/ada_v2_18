package us.lsi.alg.matrices;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import us.lsi.hypergraphs.VirtualHyperVertex;

public class MatrixVertex extends VirtualHyperVertex<MatrixVertex,MatrixEdge,Integer>{

	
	public static MatrixVertex of(Integer i, Integer j) {
		return new MatrixVertex(i, j);
	}
	
	public static MatrixVertex initial() {
		return new MatrixVertex(0, n);
	}
	
	public static List<MatrixInf> matrices;
	public static Integer n;
	public Integer i;
	public Integer j;
	
	private MatrixVertex(Integer i, Integer j) {
		super();
		this.i = i;
		this.j = j;
	}

	@Override
	public Boolean isValid() {
		return i>=0 && i<=n && j>=i && j<=n;
	}

	@Override
	public List<Integer> actions() {
		return IntStream.range(i+1,j).boxed().collect(Collectors.toList());
	}

	@Override
	public Boolean isBaseCase() {
		return j-i < 3;
	}

	@Override
	public Double baseCaseSolution() {
		Double r;
		Integer d = j-i;
		switch(d) {
		case 0: r = 0.; break;
		case 1: r = 0.; break;
		case 2: r = (double) matrices.get(i).nf*matrices.get(i).nc*matrices.get(i+1).nf; break;
		default: r = null;
		
		}
		return r;
	}

	@Override
	public List<MatrixVertex> neighbors(Integer a) {
		return Arrays.asList(MatrixVertex.of(i, a),MatrixVertex.of(a, j));
	}

	@Override
	public MatrixEdge edge(Integer a) {
		return MatrixEdge.of(this, this.neighbors(a),a);
	}
	
	

	@Override
	public String toString() {
		return "(" + i + "," + j + ")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((i == null) ? 0 : i.hashCode());
		result = prime * result + ((j == null) ? 0 : j.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MatrixVertex other = (MatrixVertex) obj;
		if (i == null) {
			if (other.i != null)
				return false;
		} else if (!i.equals(other.i))
			return false;
		if (j == null) {
			if (other.j != null)
				return false;
		} else if (!j.equals(other.j))
			return false;
		return true;
	}
	
	
}

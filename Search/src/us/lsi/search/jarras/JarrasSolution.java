package us.lsi.search.jarras;

import java.util.List;
import java.util.stream.Collectors;

import org.jgrapht.GraphPath;

import us.lsi.common.Lists2;

public class JarrasSolution {
	
	public static JarrasSolution of(GraphPath<JarrasVertex, JarrasEdge> path) {
		return new JarrasSolution(path);
	}

	List<JarrasEdge> edges;
	GraphPath<JarrasVertex, JarrasEdge> path;


	private JarrasSolution(GraphPath<JarrasVertex, JarrasEdge> path) {
		super();
		this.path = path;
		this.edges = path.getEdgeList();
	}

	@Override
	public String toString() {
		String s = this.edges.stream().map(e->e.toString()).collect(Collectors.joining("\n"));
		s = s +"\n"+Lists2.last(this.path.getVertexList());
		return s;
	}

}

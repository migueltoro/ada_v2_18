package us.lsi.astar.jarras;

import us.lsi.astar.AStarGraph;
import us.lsi.graphs.SimpleVirtualGraph;

public class GraphJarras extends SimpleVirtualGraph<VertexJarras, EdgeJarras> 
    implements AStarGraph<VertexJarras, EdgeJarras>{

	public GraphJarras() {
	}

	public GraphJarras(VertexJarras... vertexSet) {
		super(vertexSet);
	}
	
	

}

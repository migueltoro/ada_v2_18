package us.lsi.flowgraph;

import java.util.Locale;

import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.io.DOTExporter;
import org.jgrapht.io.IntegerComponentNameProvider;

import us.lsi.colors.GraphColors;
import us.lsi.common.Files2;
import us.lsi.common.Preconditions;
import us.lsi.common.Strings2;
import us.lsi.graphs.GraphsReader;


/**
 * @author Miguel Toro
 * 
 * Un grafo simple dirigido y sin peso. La información de la red está
 * guardada en los vértices y las aristas que son de los tipos 
 * FlowVertex y FlowEdge.
 *
 */


public class FlowGraph extends SimpleDirectedGraph<FlowVertex, FlowEdge> {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Un vértice de una red de Flujo puede ser una Fuente, un Sumidero o
	 * un vértice intermedio
	 *
	 */
	public enum FGType{Max,Min}	
	
	public static Boolean allInteger = false;

	public static FlowGraph create() {
		return new FlowGraph(FlowEdge.class);
	}

	private FlowGraph(Class<? extends FlowEdge> arg0) {
		super(arg0);
	}
	
	private FGType tipo = FGType.Max;
	private String constraints = null;

	public static FlowGraph newGraph(String file, FGType tipo) {
		FlowGraph r = GraphsReader.<FlowVertex,FlowEdge,FlowGraph>
		    newGraph(file, 
				FlowVertex::create, 
				FlowEdge::create, 
				FlowGraph::create);
		Preconditions.checkArgument(check(r),checkMessage(r));
		r.tipo = tipo;
		return r;		
	}
	
	private static boolean check(FlowGraph fg){
		boolean r = true;
		for(FlowVertex v: fg.vertexSet()){
			if(v.isSource()){
				r = fg.incomingEdgesOf(v).isEmpty();
			}
			if(!r) break;
			if(v.isSink()){
				r = fg.outgoingEdgesOf(v).isEmpty();
			}
			if(!r) break;
			if(v.isIntermediate()) {
				r = !fg.incomingEdgesOf(v).isEmpty() && !fg.outgoingEdgesOf(v).isEmpty();
			}
		}
		return r;
	}
	
	private static String checkMessage(FlowGraph fg){
		String r = "";
		for(FlowVertex v: fg.vertexSet()){
			if(v.isSource() && !fg.incomingEdgesOf(v).isEmpty()) {
				r = String.format("El vértice %s es source pero tiene aristas de entrada", v);
				break;
			}
			if(v.isSink() && !fg.outgoingEdgesOf(v).isEmpty()){
				r = String.format("El vértice %s es sumidero pero tiene aristas de salida", v);
				break;
			} 
			if(v.isIntermediate() && (fg.incomingEdgesOf(v).isEmpty() || fg.outgoingEdgesOf(v).isEmpty())){
				r = String.format("El vértice %s es intermedio pero o no tiene aristas de entrada o de salida", v);
				break;
			}
		}
		return r;
	}

	public FGType getTipo() {
		return tipo;
	}

	private String kirchoff(FlowVertex v) {
		String r = "";
		if(v.isSource()) {
			r = v.getVariable()+" = "+
				 Strings2.format(this.outgoingEdgesOf(v), x->x.getVariable(), "+")+";\n";
		} else if(v.isSink()){
			r = Strings2.format(this.incomingEdgesOf(v), x->x.getVariable(), "+")+" = "+v.getVariable()+";\n";			 
		} else {
			String in = Strings2.format(this.incomingEdgesOf(v), x->x.getVariable(), "+");
			r = v.getVariable() + " = " + in + ";\n";
			r = r + in +" = "+ Strings2.format(this.outgoingEdgesOf(v), x->x.getVariable(), "+")+";\n";
		}
		return r;
	}
	
	public String getConstraints() {
		Locale.setDefault(new Locale("en", "US"));
		if (this.constraints == null) {
			String goal = tipo.equals(FGType.Min) ? "min: " : "max: ";
			goal = goal + Strings2.format(this.vertexSet(), v -> v.toObjective(), "");
			goal = goal + Strings2.format(this.edgeSet(), e -> e.toObjective(), "");
			goal = goal + ";\n";
			goal = goal + Strings2.format(this.vertexSet(), v -> v.toConstraints(), "");
			goal = goal + Strings2.format(this.edgeSet(), e -> e.toConstraints(), "");
			goal = goal + Strings2.format(this.vertexSet(), v -> this.kirchoff(v), "");
			if (FlowGraph.allInteger) {
				goal = goal + "int " + Strings2.format(this.vertexSet(), v -> v.getVariable(), ",");
				goal = goal + ",";
				goal = goal + Strings2.format(this.edgeSet(), e -> e.getVariable(), ",");
				goal = goal + ";\n";
			}
			this.constraints = goal;
		}
		return this.constraints;
	}
	
	private String edgeMinCut(FlowEdge edge) {
		return String.format("%s-%s+%s >= 0; \n",edge.getSource().getVariable(),edge.getTarget().getVariable(),edge.getVariable());
	}
	
	private String vertexMinCut(FlowVertex vertex) {
		String r = "";
		if(vertex.isSource()) {
			r = String.format("R%s: %s = 0;\n",vertex.getVariable(),vertex.getVariable());
		} else if(vertex.isSink()) {
			r = String.format("R%s: %s = 1; \n",vertex.getVariable(),vertex.getVariable());
		}
		return r;
	}
	
	public String getMinCutConstraints() {
		String r = "";
		r = r+ "min: "+Strings2.format(this.edgeSet(),e->e.getMax()+" "+e.getVariable(), "+")+";\n";
		r = r+ Strings2.format(this.vertexSet(),v->vertexMinCut(v), "");
		r = r+ Strings2.format(this.edgeSet(),e->edgeMinCut(e), "");
		r = r+ "bin "+Strings2.format(this.vertexSet(),v->v.getVariable(), ",")+",";
		r = r+Strings2.format(this.edgeSet(),e->e.getVariable(), ",")+";\n";
		return r;
	}
	
	public void exportToDot(String file) {
		DOTExporter<FlowVertex, FlowEdge> de = 
				new DOTExporter<FlowVertex, FlowEdge>(
					new IntegerComponentNameProvider<>(),
					v->v.getName(),
					e->e.getName(),
					v->GraphColors.getFilledColor(v.getColor()),
					null);
		de.exportGraph(this, Files2.getWriter(file));
	}
	
	private String vertexFormat(FlowVertex v) {
		return String.format("%s,%.1f,%s,%.1f",
				v.getVariable(),
				v.getMin(),
				v.getMax()<Double.MAX_VALUE?String.format("%.1f",v.getMax()):"_",
				v.getCost());
	}
	
	private String edgeFormat(FlowEdge e) {
		return String.format("%s,%.1f,%s,%.1f",
				e.getVariable(),
				e.getMin(),
				e.getMax()<Double.MAX_VALUE?String.format("%.1f",e.getMax()):"_",
				e.getCost());
	}
	
	public void exportToDotVariables(String file) {
		DOTExporter<FlowVertex, FlowEdge> de = 
				new DOTExporter<FlowVertex, FlowEdge>(
					new IntegerComponentNameProvider<>(),
					v->vertexFormat(v),
					e->edgeFormat(e));
		de.exportGraph(this, Files2.getWriter(file));
	}

}

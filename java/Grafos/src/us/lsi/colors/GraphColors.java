package us.lsi.colors;

import java.util.HashMap;
import java.util.Map;

import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;


public class GraphColors {
	
	public enum Color {
		blank, red, yellow, gray, cyan, orange, magenta, blue, black, green
	}
	
	public enum Style {
		dotted, bold, filled, solid, invis
	}
	
	public enum Shape {
		box, polygon, ellipse, point, triangle, doublecircle
	}
	
	/**
	 * @param c color
	 * @return Un Map para ser añadido en un exportToDot.
	 */
	public static Map<String,Attribute> getColor(Color c) {
		String cl = c == Color.blank? "" : c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));
		return m;
	}
	
	public static Map<String,Attribute> getColor(Integer c) {
		return getColor(Color.values()[c]);
	}
	
	public static Map<String,Attribute> getColorIf(Color yesColor, Color noColor, Boolean test) {		
		Color c;
		if(test) c = yesColor;
		else c = noColor;
		String cl = c.toString();
		Map<String,Attribute> m = Map.of("color", DefaultAttribute.createAttribute(cl));		
		return m;
	}
	
	public static Map<String,Attribute> getColorIf(Color yesColor, Boolean test) {		
		Map<String,Attribute> m = new HashMap<>();
		if(test) m = Map.of("color", DefaultAttribute.createAttribute(yesColor.toString()));
		return m;
	}
	
	public static <E> Map<String, Attribute> getLabel(String label) {
		return Map.of("label", DefaultAttribute.createAttribute(label));
	}
	
	public static <E> Map<String, Attribute> getStyle(Style style) {
		return Map.of("style", DefaultAttribute.createAttribute(style.name()));
	}
	
	public static <E> Map<String, Attribute> getShape(Shape shape) {
		return Map.of("shape", DefaultAttribute.createAttribute(shape.name()));
	}
	
	public static <E> Map<String, Attribute> getStyleIf(Style style, Boolean test) {
		if(!test) style = Style.solid;
		return Map.of("style", DefaultAttribute.createAttribute(style.name()));
	}
	
	public static <E> Map<String, Attribute> getShapeIf(Shape shape, Boolean test) {
		Map<String,Attribute> m = new HashMap<>();
		if(!test) m = Map.of("shape", DefaultAttribute.createAttribute(shape.name()));
		return m;
	}
	
}

package us.lsi.graphcolors;

import java.util.Map;
import java.util.function.Predicate;

import org.jgrapht.io.DefaultAttribute;

import us.lsi.common.Colors;
import us.lsi.common.Maps2;

import org.jgrapht.io.Attribute;

public class GraphColors {
	
	/**
	 * @param n Un entero que representa un color como codificado en la clase 
	 * Colors.
	 * @return Un Map para ser añadido en un exportToDot.
	 */
	public static Map<String,Attribute> getColor(Integer n) {
		Map<String,Attribute> m = Map.of("color", 
					DefaultAttribute.createAttribute(
					    Colors.getNameOfColor(n)));
		return m;
	}
	
	public static <E> Map<String, Attribute> getColorIf(Integer n, E e, Predicate<E> p) {
		Map<String, Attribute> m = Maps2.newHashMap();
		if (p.test(e)) {
			m = Map.of("color", DefaultAttribute.createAttribute(Colors.getNameOfColor(n)));
		}
		return m;
	}
	
	public static <E> Map<String, Attribute> getFilledColor(Integer n) {
		Map<String, Attribute> m = Maps2.newHashMap("color",
				DefaultAttribute.createAttribute(Colors.getNameOfColor(n)));
		m.putAll(Maps2.newHashMap("style", DefaultAttribute.createAttribute("filled")));
		return m;
	}
	
	public static <E> Map<String,Attribute> getFilledColorIf(Integer n,E e, Predicate<E> p) {
		Map<String,Attribute> m = Maps2.newHashMap();
		if (p.test(e)) {
			m = Maps2.newHashMap("color", DefaultAttribute.createAttribute(Colors.getNameOfColor(n)));
			m.putAll(Maps2.newHashMap("style", DefaultAttribute.createAttribute("filled")));
		}
		return m;
	}
	
	public static <E> Map<String, Attribute> getStyle(String style) {
		return Map.of("style", DefaultAttribute.createAttribute(style));
	}
	
	public static <E> Map<String, Attribute> getStyleIf(String style, E e, Predicate<E> p) {
		Map<String, Attribute> m = Maps2.newHashMap();
		if (p.test(e)) {
			m = Map.of("style", DefaultAttribute.createAttribute(style));
		}
		return m;
	}
	
	public static <E> Map<String, Attribute> getLabel(String style) {
		return Map.of("label", DefaultAttribute.createAttribute(style));
	}
	
}

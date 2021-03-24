package us.lsi.bufete.datos;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Lists3 {

	public static <E> List<E> parse(String[] tokens, Function<String,E> f_map) {
		return Arrays.stream(tokens)
		.filter(e->e!=null && e.length()>0)
		.map(e->f_map.apply(e.trim())).collect(Collectors.toList());
	}
	
	public static <E> List<E> parse(String s, Function<String,E> f_map) {
		return Arrays.stream(s.split("[{, }]"))
		.filter(e->e!=null && e.length()>0)
		.map(e->f_map.apply(e.trim())).collect(Collectors.toList());
	}
}


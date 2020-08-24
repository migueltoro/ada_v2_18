package us.lsi.sa;


public interface StateSa {
	
	Double fitness();
	StateSa mutate();
	StateSa random();
	StateSa copy();

}

package us.lsi.ag;

public class HelpAg {
	
	public static Double menorQueCero(Double in) {
		Double r = 0.;		
		if(in > 0) {
			r = in*in;
		}
		return r;
	}
	
	public static Double mayorQueCero(Double in) {
		Double r = 0.;		
		if(in < 0) {
			r = in*in;
		}
		return r;
	}
	
	public static Double igualACero(Double in) {
		return in*in;
	}

}

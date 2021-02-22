package objektorientiert;

/** Hält alle Windmessstationen
 *
 */
public class Landkreis {
	//Attribute 
	private String name; 
	private Windmessstation wm;
	private double eismanWert;
	private int nabenlevel;
	
	//Constructor 
	/**
	 * @param name 
	 * @param wm associated Windmessstation
	 * @param eismanWert wind speed for Eisman
	 * @param nabenlevel level of wind turbine height
	 */
	public Landkreis(String name, Windmessstation wm,double eismanWert, int nabenlevel) {
		// super();
		this.name=name;
		this.wm=wm;
		this.eismanWert=eismanWert;
		this.nabenlevel=nabenlevel;
	}
	
	//Methoden
	public Windmessstation getWindmessstation() {
		return wm;
	}
	
	/**
	 * @return wind speed for Eisman
	 */
	public double getEismanWert() {
		return eismanWert;
	}

	/**
	 * @return level of wind turbine height
	 */
	public int getNabenlevel() {
		return nabenlevel;
	}

	public String toString() {
		return name;
	}
	
	/** gives a forecast for wind speed
	 * @param hour of the year
	 * @return average wind speed of the coming 10 days
	 */
	public double windPrognose(int hour) {
		double prognose=0;
		for(int i=0;i<240;i++) {
			prognose+=wm.getWind((hour+i)%8760);
		}
		return prognose/240;
	}
}

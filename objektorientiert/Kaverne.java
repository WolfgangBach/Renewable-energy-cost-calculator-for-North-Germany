package objektorientiert;

/**allows to manage the hydrogen storage
 * 
 */
public class Kaverne {
	private double kapazitaet;
	private double invKosten;
	private double flaeche;
	private double ladestand;
	private int anzahl;
	private double umsatzGes;
	public static final double ANFANG=0.3;
	
	/**
	 * @param anzahl number inserted by user
	 * @param invKosten CAPEX of one unit
	 * @param flaeche area of one unit
	 */
	public Kaverne(int anzahl, double invKosten, double flaeche) {
		kapazitaet=anzahl*100000000.0;
		this.invKosten=invKosten;
		this.flaeche=flaeche;
		this.anzahl=anzahl;
		ladestand=ANFANG*kapazitaet;
		umsatzGes=0;
	}
	
	public double getLadestand() { //in kWh
		return ladestand;
	}
	
	public double getStatus() { //0...1
		return ladestand/kapazitaet;
	}
	
	public double getUmsatzGes() { //in kWh
		return umsatzGes;
	}
	
	public double invKosten() {
		return invKosten/1000000; //in Mio €
	}
	
	public double bKosten() {
		return (invKosten*0.025+umsatzGes/1000*2)/1000000; //pro Jahr in Mio €
	}
	
	public double flaeche() {
		return flaeche/10000; //in ha
	}

	/** allows to fill the cavern
	 * @param diff available energy amount
	 * @return afterwards available energy amount
	 */
	public double fuellen(double diff) {
		//System.out.print("fuellen "+diff+" "+ladestand);
		double delta=0;
		double alt=ladestand;
		ladestand+=diff;
		if (ladestand>kapazitaet) {
			delta=ladestand-kapazitaet;
			ladestand=kapazitaet;
		}
		umsatzGes+=ladestand-alt; //neue Energie zum Gesamtumsatz addieren
		//System.out.println(" "+delta+" "+ladestand);
		return delta;
	}
	
	/** allows to take out of the cavern
	 * @param diff needed energy amount
	 * @return afterwards needed energy amount
	 */
	public double entnehmen(double diff) {
		//System.out.print("entnehmen "+diff+" "+ladestand);
		double delta=0;
		ladestand+=diff;
		if(ladestand<0) {
			delta=ladestand;
			ladestand=0;
		}
		//System.out.println(" "+delta+" "+ladestand);
		return delta;
	}
}

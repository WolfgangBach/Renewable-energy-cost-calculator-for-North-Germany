package objektorientiert;

/**
 *
 */
public class Elektrolyseur {
	private Kaverne kaverne;
	private int anzahl;
	private double leistung;
	private double invKosten;
	private double bKosten;
	private double flaeche;
	private final double ETA=0.74;
	
	public Elektrolyseur(double leistung, double invKosten, double bKosten, double flaeche, Kaverne kav, int anzahl) {
		kaverne=kav;
		this.leistung=leistung;
		this.invKosten=invKosten;
		this.bKosten=bKosten;
		this.flaeche=flaeche;
		this.anzahl=anzahl;
	}
	
	/**
	 * @param diff available energy amount
	 * @return afterwards available energy amount
	 */
	public double elektrolyse(double diff) {
		double rest=0;
		double h2 = diff*ETA;
		if (h2<leistung*anzahl) {
			rest = kaverne.fuellen(h2);
		} else {
			rest=h2-leistung*anzahl;
			rest+=kaverne.fuellen(leistung*anzahl);
		}
		return rest;
	}
	
	/**
	 * @return power-to-gas CAPEX
	 */
	public double invKosten() {
		return anzahl*invKosten/1000000; //in Mio €
	}
	
	/**
	 * @return power-to-gas OPEX
	 */
	public double bKosten() {
		return anzahl*bKosten/1000000; //in Mio €
	}
	
	/**
	 * @return power-to-gas area
	 */
	public double flaeche() {
		return anzahl*flaeche/10000; //in ha
	}
}

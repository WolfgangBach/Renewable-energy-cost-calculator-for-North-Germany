package objektorientiert;

/** Oberklasse für GuD und Brennstoffzellen
 *
 */
abstract public class GasToPower {
	protected double leistung; //in kW
	protected double invKosten; //in €
	protected double bKosten; //in €
	protected double flaeche; //in m²
	protected int anzahl=1;
	protected Kaverne kav;
	
	protected final double ETA=0.4;
	
	/** takes hydrogen out of the cavern and returns electrical energy
	 * @param diff needed energy amount
	 * @return afterwards needed energy amount
	 */
	public double gasToPower(double diff) {
		double delta=0;
		if(Math.abs(diff)<Math.abs(leistung*anzahl)) {
			delta=kav.entnehmen(diff/ETA)*ETA;
		}else {
			delta=diff+leistung*anzahl;
			delta+=kav.entnehmen(-leistung*anzahl/ETA)*ETA;
		}
		return delta;
	}
	
	/**
	 * @return CAPEX
	 */
	public double invKosten() {
		return anzahl*invKosten/1000000; //in Mio €
	}
	
	/**
	 * @return OPEX
	 */
	public double bKosten() {
		return anzahl*bKosten/1000000; //in Mio €
	}
	
	/**
	 * @return area
	 */
	public double flaeche() {
		return anzahl*flaeche/10000; //in ha
	}
}

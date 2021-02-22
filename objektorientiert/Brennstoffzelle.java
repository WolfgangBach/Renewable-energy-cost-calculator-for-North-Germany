package objektorientiert;

public class Brennstoffzelle extends GasToPower {
	
	private final double ETA=0.4;

	public Brennstoffzelle(double leistung, double invKosten, double bKosten, double flaeche, int anzahl, Kaverne kav) {
		this.leistung=leistung;
		this.invKosten=invKosten;
		this.bKosten=bKosten;
		this.flaeche=flaeche;
		this.anzahl=anzahl;
		this.kav=kav;
	}

}

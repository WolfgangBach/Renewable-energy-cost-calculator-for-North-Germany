package objektorientiert;

public class GuDK extends GasToPower {
	
	private final double ETA=0.4;

	public GuDK(double leistung, Kaverne kav) {
		this.leistung=leistung;
		invKosten=0;
		bKosten=0;
		flaeche=0;
		anzahl=1;
		this.kav=kav;
	}

}

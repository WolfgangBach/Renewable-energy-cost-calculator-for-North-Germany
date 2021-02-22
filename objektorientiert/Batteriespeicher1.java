package objektorientiert;
 

 

public class Batteriespeicher1 extends Speicher{
	
	private final double SELBSTENTLADUNG=0.0013; //Selbstentladung in %/Tag, entspricht monatlichem Wert von 0.04
	private final double ETA=0.95; //Wirkungsgrad Laden
	private final double ENTLADETIEFE=0.95;
	
	public Batteriespeicher1(double kapazitaet, double invKosten, double bKosten, double flaeche, double leistung, int anzahl) {
		this.kapazitaet=kapazitaet;
		this.invKosten=invKosten;
		this.bKosten=bKosten;
		this.flaeche=flaeche;
		this.leistung=leistung;
		this.anzahl=anzahl;
		ladestand=ANFANG*anzahl*kapazitaet;
	}
	
	public String toString() {
		return "Li-Ionen";
	}
}

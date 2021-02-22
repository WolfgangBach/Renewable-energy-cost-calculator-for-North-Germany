package objektorientiert;
 

 

public class Pumpspeicher extends Speicher{
	protected final double SELBSTENTLADUNG=0.0; //Selbstentladung in %/Tag
	protected final double ETA=0.7; //Wirkungsgrad Laden 
	protected final double ENTLADETIEFE=1;
	
	public Pumpspeicher(double kapazitaet, double invKosten, double bKosten, double flaeche, double leistung, int anzahl) {
		this.kapazitaet=kapazitaet;
		this.invKosten=invKosten;
		this.bKosten=bKosten;
		this.flaeche=flaeche;
		this.leistung=leistung;
		this.anzahl=anzahl;
		ladestand=ANFANG*kapazitaet;
	}
	
	public String toString() {
		return "Pumpspeicher";
	}
}

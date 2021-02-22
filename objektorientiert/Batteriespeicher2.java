package objektorientiert;
 

public class Batteriespeicher2 extends Speicher{
	protected final double SELBSTENTLADUNG=0.0; //Selbstentladung unter 1% im jahr
	protected final double ETA=0.78; //Wirkungsgrad Laden
	protected final double ENTLADETIEFE=1.0;  //muss nochmal mit quelle belegt werden 
	
	public Batteriespeicher2(double kapazitaet, double invKosten, double bKosten, double flaeche,double leistung,int anzahl) {
		this.kapazitaet=kapazitaet;
		this.invKosten=invKosten;
		this.bKosten=bKosten;
		this.flaeche=flaeche;
		this.leistung=leistung;
		this.anzahl=anzahl;
		ladestand=ANFANG*anzahl*kapazitaet;
	}
	
	public String toString() {
		return "Redox-Flow";
	}
}

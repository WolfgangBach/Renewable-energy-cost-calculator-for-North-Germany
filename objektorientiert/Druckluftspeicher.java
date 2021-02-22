package objektorientiert;
 
												
public class Druckluftspeicher extends Speicher{
	protected final double SELBSTENTLADUNG=0.0; //keine betrachtenswerte selbstentladung 
	protected final double ETA=0.70; //Wirkungsgrad Laden
	protected final double ENTLADETIEFE=1.0; 
	
	public Druckluftspeicher(double kapazitaet, double invKosten, double bKosten, double flaeche,double leistung,int anzahl) {
		this.kapazitaet=kapazitaet;
		this.invKosten=invKosten;
		this.bKosten=bKosten;
		this.flaeche=flaeche;
		this.leistung=leistung;
		this.anzahl=anzahl;
		ladestand=ANFANG*anzahl*kapazitaet;
	}
	
	public String toString() {
		return "Druckluftspeicher";
	}
	
	@Override
	public double getBKosten() {
		return (anzahl*invKosten*0.025+umsatzGes/1000*2);
	}
	@Override
	public double getInvKosten() {
		double kosten=anzahl*invKosten;
		if(anzahl>0) {
			kosten+= 1320000;
		}
		if(anzahl>125) {
			kosten+=1800000;
		}
		if (anzahl >250) {
			kosten+=2400000;
		}
		if (anzahl >375) {
			kosten+=4200000;
		}
		return kosten;
	}
}

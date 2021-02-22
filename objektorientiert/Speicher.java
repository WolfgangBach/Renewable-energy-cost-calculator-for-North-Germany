package objektorientiert;
 /**allows to manage the electrical energy storage
 *
 */
public abstract class Speicher {
	
	protected double kapazitaet; //Kapazität in kWh
	protected double leistung; //Leistung in kW
	protected double invKosten; //investition (pro Instanz)
	protected double bKosten; //pacht + betrieb (pro Instanz)
	protected double flaeche; //Fläche pro Instanz
	protected double ladestand;
	protected double anzahl;
	protected double umsatzGes;
	protected final double SELBSTENTLADUNG=0;
	protected final double ETA=1;
	protected final double ENTLADETIEFE=1;
	public static final double ANFANG=0.3;
	
	public double getKapazitaet() {
		return anzahl*kapazitaet;
	}
	
	public double getInvKosten() {
		return anzahl*invKosten;
	}
	
	public double getBKosten() {
		return anzahl*bKosten;
	}
	
	public double getFlaeche() {
		return anzahl*flaeche;
	}
	
	public double getLeistung() {
		return anzahl*leistung;
	}
	
	public double getLadestand() {
		return ladestand;
	}
	
	/**
	 * @param diff available (positive) or needed (negative) energy amount
	 * @return (reduced) energy amount afterwards
	 */
	protected double laden(double diff) {
		//System.out.print(" "+this+" "+getKapazitaet()+" "+diff+" "+ladestand+" ");
		double ladung=0;
		double delta=0;
		double leistung=0;
		double alt=ladestand;
		if(diff>0) {//Aufladen: ladung und leistung positiv
			ladung=diff*ETA;
			leistung=getLeistung();
		}
		else {//Entladen: ladung und leistung negativ
			ladung=diff/ETA;
			leistung=-getLeistung();
		}
		if(Math.abs(ladung)<=Math.abs(leistung)) {//Abfrage, ob Ladung kleiner als Leistung der Speicherstufe
			ladestand+=ladung; 
			//System.out.println("Leistung der Speicher "+i+" reicht, "+ladestand+", "+kapazitaet[i]);
		}else {
			ladestand+=leistung;
			delta=ladung-leistung;
			//System.out.println("Leistung der Speicher "+i+" reicht nicht, "+ladestand+", "+kapazitaet[i]);
		}
		if(ladestand>getKapazitaet()) {
			//System.out.print("full "+delta);
			delta+=ladestand-getKapazitaet();
			ladestand=getKapazitaet();
			return delta/ETA;
		}else if(ladestand<(1-ENTLADETIEFE)*getKapazitaet()) {
			delta += ladestand;
			ladestand=0;
			return delta*ETA;
		}
		if (diff>0)umsatzGes+=ladestand-alt;
		return delta;
	}

	/**
	 * allows to regard self-drain
	 */
	protected void selbstentladen() {
		ladestand*=(1-SELBSTENTLADUNG);
	}
}


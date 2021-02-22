package objektorientiert;

/**
 *Klasse für Bestandsanlagen (!kostet) und Neuanlagen (kostet)
 *
 */
public class Windpark {
	//Attribute 
	private Landkreis lk;
	private WeaTyp typ;
	private int anzahl;
	private boolean kostet;

	//Constructor 
	public Windpark(Landkreis lk, WeaTyp typ, int d,boolean kostet) {
		super();
		this.lk = lk;
		this.typ = typ;
		this.anzahl=d;
		this.kostet=kostet;
	}

	public Landkreis getLk() {
		return lk;
	}
	
	/** converts wind speed according to turbine height
	 * @param wind wind speed
	 * @return converted wind speed
	 */
	public double windNabenhoehe(double wind) {	
		double nab=typ.getNabenhoehe(lk.getNabenlevel());
		
		//return (wind/Math.log(10/0.1))*(Math.log(nab/0.1));		//Alternative Berechnungsformel, beide sind korrekt 
		return Math.pow((nab/10), 0.16)*wind;
	}

	/**
	 * @param hour of the year
	 * @return power
	 */
	public double leistung(int hour) {
		double windnab= windNabenhoehe(lk.getWindmessstation().getWind(hour));
		if(lk.getEismanWert()<lk.getWindmessstation().getWind(hour)) {		//EisMan Fall! Wind>Abschalt Wind gesch. 
			return (0.6*anzahl*typ.leistung(windnab));		//Hier Abschaltung mit 60% muss auf Plausibilität geprüft werden. 
		}
		else {
			return anzahl*typ.leistung(windnab);
		}
	}
	
	/**
	 * @param hour of the year
	 * @return power if new-built 
	 */
	public double leistungNeu(int hour) {
		if(kostet) {
			return leistung(hour);
		}
		else {
			return 0;
		}
	}
	
	/**
	 * @return CAPEX
	 */
	public double invKosten() {		//Umsetzung Kosten nach Jannis Quellen
		if(kostet) {
			//Hauptinvestitionskosten
			double kosten=0;
			if(typ.getNabenhoehe(lk.getNabenlevel())<120) {
				kosten+=(double)anzahl*(double)typ.getNennleistung()*1120;			//Hohe Zahl um auf ende des Arrays zu kommen und Nennleistung zurück zu geben
			}
			else {					//Jetzt wird nur zwischen <120 und >120 unterschieden. Kann noch geändert werden, jenachdem was Janni für weitere Anlagen raus sucht. 
				kosten+=(double)anzahl*(double)typ.getNennleistung()*1180;
			}
			//Nebeninvestitionskosten
			kosten+=(double)anzahl*(double)typ.getNennleistung()*387;
			kosten=kosten/1000000;			
			return kosten;		//Gibt Windpark Investitionskosten in Mio zurück 
		}
		else {
			return 0;
		}
	}
	
	/**
	 * @return OPEX
	 */
	public double bKosten() {
		if(kostet) {
			//Betriebskosten
			double kosten=(double)anzahl*(double)typ.getNennleistung()*54;					
			return kosten/1000000;		//Gibt Windpark Betriebskosten in Mio zurück 
		}
		else {
			return 0;
		}
	}

	/**
	 * @return area
	 */
	public double flaeche() {//in ha
		if(kostet) {
			double temp=Math.PI*(typ.getRotordurchmesser()*5)*(typ.getRotordurchmesser()*3);
			return (anzahl*(temp/5))/10000;		//Ob durch 5 oder durch 3 muss noch irgendwie herausgefunden werden! 
		}
		else {
			return 0;
		}
	}
	
}

package objektorientiert;

/**
 * 
 *		Jedes DSM Potential ist ein Objekt dieser Klasse 
 *		Weiß P+ und t+max 
 *		Gibt Positive Regelleistung und benötigte Ausgleichsleistung an DSMPortfolio 
 *		Debugged. Müsste Funktionieren 
 */

public class DsmPotential {
	//Attribute
	private double leistungsAbsenkung;
	private int maxZeit;
	private double ausgleichsLeistung;
	private String name;
	private int startzeit=-1;
	private int tOn=0;
	private boolean on=false;

	//Construktor 
	public DsmPotential(double leistungsAbsenkung, int maxZeit, String name) {
		super();
		this.leistungsAbsenkung = leistungsAbsenkung;
		this.maxZeit = maxZeit;
		this.name = name;
	}

	//Methoden 
	public double getPotential(int hour) {	//Modus sagt aus ob DSM Leistung absenken, oder ausgleichen soll
		//	System.out.println(name);
		if(on) tOn= 1+hour-startzeit;
		if(!on&&startzeit==-1) {		//Neuer DSM einsatz, weil aus 
			startzeit=hour;
			on=true;
			return leistungsAbsenkung;
		}
		if(on){			//DSM ist schon am laufen
			if(hour<(startzeit+maxZeit)) {
				if(hour==(startzeit+maxZeit)-1) {
					stop();
				}
				return leistungsAbsenkung;	
			}
			else {
				stop();
			}
		}
		
		return 0;
	}

	public void stop() {
		//System.out.println("Stop "+ name);	
		on=false;
		ausgleichsLeistung= (leistungsAbsenkung*tOn)/(24-tOn);
	}

	public double getAusgleich(int hour) {			//ganze Methode neu ? 
		if(on||startzeit==-1) {		//Noch kein DSM aktiv oder noch in LeitungsAbsenkungsteil
			return 0; 
		}else {
			if(hour<startzeit+24) {
				return ausgleichsLeistung;
			}
			else {
				startzeit=-1;
				return 0;
			}
		}
	}

	//Test Main
	public static void main(String[] args){
		DsmPotential d= new DsmPotential(57,6,"Fernwärmenetz");
		double Ausgleichsleistung=0;
		double pPlus=0;
		for(int i=0;i<28;i++) {
			Ausgleichsleistung=d.getAusgleich(i);
			pPlus=d.getPotential(i);
			System.out.println("Stunde "+i+": Leistungs absenkung "+pPlus+" Ausgleichsleistung "+Ausgleichsleistung);
		}
	}
}

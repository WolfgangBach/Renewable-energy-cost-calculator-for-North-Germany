package objektorientiert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
/**
 * Klasse Bilanz
 * Funktion Stündliches bilanzierung von Verbrauch und Erzeugung
 * Hält Erzeugung, Verbrauch und Konto
 * 
 */
public class Bilanz {
	private static final int N = 8760; //Anzahl Zeitpunkte
	//Attribute
	private Szenario szenario;
	private ErzeugerPortfolio ep;
	private SpeicherPortfolio sp;
	private DsmPortfolio dp;
	private VerbraucherPortfolio vp;
	private Boerse boerse;
	private double[] erzeugung = new double[N];
	private double[] erzeugungKorr = new double[N];
	private double[] verbrauchKorr = new double[N];
    private double[] status = new double[N+1];
    private double[] kavStatus = new double[N];
    private boolean[] abgedeckt = new boolean[N];
    private double abdeckung=0;
    private double zugekauft=0;

    /** loads the portfolios and boerse from szenario
     * @param sz instance of Szenario
     */
    public Bilanz(Szenario sz) {
        szenario= sz;
        ep = sz.getEp();
        sp = sz.getSp();
        dp = sz.getDp();
        vp = sz.getVp();
        boerse= sz.getBoerse();
    }

	//Getter und Setter 
	public double getErzeugung(int hour) {
		return erzeugung[hour];
	}

	public double getErzeugungKorr(int hour) {
		return erzeugungKorr[hour];
	}

	public double getVerbrauchKorr(int hour) {
		return verbrauchKorr[hour];
	}

	public double getVerbrauch(int hour) {
		return vp.getVerbrauch(hour);
	}

	public double getAbdeckung() {
		return abdeckung;
	}

	public double getResidualGes() {
		return zugekauft;
	}

	public double getStatus(int hour) {
		return status[hour];
	}

	public double getKavStatus(int hour) {
		return kavStatus[hour];
	}

	public boolean getAbgedeckt(int hour) {
		return abgedeckt[hour];
	}

	/**
	 * @return maximum power of the year
	 */
	public double getMaxErzeugung() {
		double max = 0;
		for (double e:erzeugung) {
			if (max<e) max=e;
		}
		return max;
	}

    /**runs through the year, compares power and load and manages energy flow
     * @return true if all conditions in last line are fulfilled.
     * Edit last line conditions to use Optimizer more exactly
     */
    public boolean simulate() {
        szenario.getFenster().setbilanz(this);
        int count=0;
        double delta=0;
        double dsmPot=0;
        double dsmAusgleich=0;
        double temp=0;
        status[0]=sp.getStatusGesamt();
      	boerse.terminmarkt(vp.getBaseLoad(), vp.getPeakLoad());
        for(int hour=0; hour<N; hour++) {
            abgedeckt[hour]=false;
            erzeugungKorr[hour]=erzeugung[hour]=ep.leistung(hour);
            dsmAusgleich= dp.dsmAusgleich(hour);
            delta = erzeugung[hour]- vp.getVerbrauch(hour)+dsmAusgleich; 
            //System.out.print(hour);
            dsmPot=dp.dsmPot(delta, hour);
            delta+=dsmPot;		//verringert die Differenz, falls delta negativ
            if(hour%24==0)sp.selbstentladen();//einmal täglich selbst entladen 
            if(delta>=0) {
            	double quote=8/Landkreise.get("Dithmarschen").windPrognose(hour);
                if(sp.getStatusGesamt()>=quote&&boerse.getPreis(hour)>=0)delta=boerse.spotmarkt(delta, hour);
                //System.out.println(hour+" "+Landkreise.get("Dithmarschen").windPrognose(hour)+" "+(sp.getStatusGesamt()>=quote));
            	delta=sp.ladestand(delta);
            	delta=vp.powerToGas(delta); //produziert Wasserstoff aus Überschuss delta bis Speicher voll, was übrig bleibt, wird zurückgegeben
            	boerse.spotmarkt(delta,hour); 
                count++;
                abgedeckt[hour]=true;
                //System.out.println("count++");
            }else {
            	temp = delta;
            	if(Landkreise.get("Dithmarschen").windPrognose(hour)>6) { //es wird windig -> Speicher bevorzugen //der Einfachheit halber Prognose von nur einem Lk.
            		delta=sp.ladestand(delta);
            		delta=ep.gasToPower(delta);
            	} else { //es wird weniger windig -> Speicher schonen
            		delta=ep.gasToPower(delta);
            		delta=sp.ladestand(delta);
            	}
            	erzeugungKorr[hour]=erzeugung[hour]-temp+delta;//korrigierte Erzeugung mit Leistung der Speicher und Gas-To-Power
            	if(delta==0) {
            		count++;
            		abgedeckt[hour]=true;
            	}
            	else {
            		zugekauft+=delta;
            		boerse.kaufen(delta, hour);
            	}
            }
            //System.out.println(ep.getKav().getLadestand());
            status[hour+1]=sp.getStatusGesamt();
            kavStatus[hour]=ep.getKav().getStatus();
            verbrauchKorr[hour]=vp.getVerbrauch(hour)-dsmPot-dsmAusgleich; 
            
        }
        abdeckung=(double)count/N;
        System.out.println(abdeckung+" Abdeckung");
        System.out.println(-zugekauft+" kWh zugekauft übers Jahr");
        System.out.println("Erzeugung: "+ep.getWeages()+" GWh aus Wind, "+ep.getPvges()+" GWh aus Sonne");
        
        double gesErzeugung=0;
        for(double d:erzeugung) {
            gesErzeugung+=d;
        }
        System.out.println("Erlöse: "+boerse.getGewinne()+" €, verkauft-gekauft: "+boerse.getEnergieGes()+" kWh, Durchschnittspreis: "+boerse.getGewinne()/boerse.getEnergieGes()+" €/kWh");
        System.out.println("Die Gesamte Erzeugung in 2019 beträgt: "+gesErzeugung+ "in kWh");
        double flaecheGes=ep.flaecheWeaGes()+ep.flaechePvGes()+ep.flaecheGtp()+sp.flaeche()+vp.flaechePtg()+ep.getKav().flaeche();
        System.out.println("Gesamtfläche: "+flaecheGes);
        return abdeckung==1.0&&ep.getKav().getStatus()>=Kaverne.ANFANG&&sp.getStatusGesamt()>=Speicher.ANFANG&&ep.flaecheWeaGes()<=19000;
    }

}


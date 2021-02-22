package objektorientiert;
import java.io.File;
import java.io.IOException;

/**
 * Szenario ist Modellklasse, die sich die Nutzereingaben holt, wenn sie von den Szenarioeingaben der GUI angetriggert wird
 * Szenario sollte die Portfolios halten und entsprechend der Nutzereingaben anpassen
 * Szenario soll Bilanz erzeugen, wenn Simulation gefordert, und an diesen übergeben.
 */
public class Szenario { 

    private SpeicherPortfolio sp; 
    private ErzeugerPortfolio ep; 
    private DsmPortfolio dp;
    private VerbraucherPortfolio vp;
    private Boerse b;
    private Gui fenster;  
 
    public Szenario(Gui fenster) {
        this.fenster = fenster;
    }
    
    public Gui getFenster() {
        return fenster;
    }

    /** called by GUI when user wants to simulate 
     * Changes parameters and runs simulate method
     * @throws IOException
     */
    public void notifyChange() throws IOException {
        int anlagenTyp1=fenster.getAnlagenTyp1();
        int anlagenTyp2=fenster.getAnlagenTyp2();
        int anlagenTyp3=fenster.getAnlagenTyp3();
        int freiflaechen=fenster.getFreiflaechen();
        int batteriespeicher1=fenster.getBatteriespeicher1();
        int batteriespeicher2=fenster.getBatteriespeicher2();
        int druckluftspeicher=fenster.getDruckluftspeicher();
        int elektrolyseure=fenster.getElektrolyseure();
        int brennstoffzellen=fenster.getBrennstoffzellen();
        int kavernen=fenster.getKavernen();
        boolean pumpspeicherkraftwerk = fenster.getPumpspeicherNeu();
        boolean dsmJa = fenster.getDsm(); 
        simulate(anlagenTyp1,anlagenTyp2,anlagenTyp3,freiflaechen,batteriespeicher1,batteriespeicher2,druckluftspeicher,brennstoffzellen,elektrolyseure,kavernen,10,pumpspeicherkraftwerk,dsmJa);        
        fenster.showResults(b.getGewinne(),b.getGewinne()/b.getEnergieGes(),anlagenTyp1+anlagenTyp2+anlagenTyp3,freiflaechen,
        		batteriespeicher1+batteriespeicher2+druckluftspeicher,brennstoffzellen,elektrolyseure,kavernen);
    }

    /** initiates portfolios, Boerse and Bilanz
     * Finally runs simulate method of bilanz
     * @param all parameters entered by user in GUI or by Optimzer
     * @return true if all conditions in simulate method of bilanz are fulfilled
     */
    public boolean simulate(int anlagenTyp1, int anlagenTyp2, int anlagenTyp3, int freiflaechen, int batteriespeicher1, int batteriespeicher2, int druckluftspeicher, int brennstoffzellen, int elektrolyseure, int kavernen, int wind, boolean pumpspeicherkraftwerk, boolean dsmJa){
    	ep = new ErzeugerPortfolio(anlagenTyp1,anlagenTyp2,anlagenTyp3,freiflaechen,brennstoffzellen,kavernen);
        sp = new SpeicherPortfolio(batteriespeicher1,batteriespeicher2,druckluftspeicher,pumpspeicherkraftwerk);
        dp = new DsmPortfolio(dsmJa);
        vp = new VerbraucherPortfolio(ep.getKav(),elektrolyseure);
        b= new Boerse(ep);
        Bilanz bilanz= new Bilanz(this);
        boolean abdeckung=bilanz.simulate();
        return abdeckung;
    }
    
    public ErzeugerPortfolio getEp(){
        return ep;
    }

    public SpeicherPortfolio getSp(){
        return sp;
    }
    
    public DsmPortfolio getDp() {
    	return dp;
    }

	public VerbraucherPortfolio getVp() {
		return vp;
	}

	public Boerse getBoerse() {
		return b;
	}

}

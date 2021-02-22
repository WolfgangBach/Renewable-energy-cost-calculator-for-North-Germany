package objektorientiert;
import java.util.ArrayList;

/** Hält alle Power-to-Power Speicherformen
 * Reihenfolge in arraylist bestimmt Reihenfolge der Ansprechung
 * 
 */
public class SpeicherPortfolio {
	private ArrayList<Speicher> speicher;
	private double kapGes;
	private double Pges=0;
	
	/** initiates and fills the arraylists
	 * @param bsp1 number inserted by user
	 * @param bsp2 number inserted by user
	 * @param dsp number inserted by user
	 * @param pskwNew boolean inserted by user
	 */
	public SpeicherPortfolio(int bsp1, int bsp2, int dsp, boolean pskwNew) {
		speicher = new ArrayList<Speicher>();
		
		add(new Batteriespeicher1(1000,287024,10850,43,775,bsp1)); //2025 Li-Ionen-Batterie

		add(new Batteriespeicher2(1000,258225,10500,65,250,bsp2)); // 2025 Redox-Flow-Batterie

		if(pskwNew == true) { 
			add(new Pumpspeicher(3500000,100000000,16800000/20,0,70000,1));//Lägerdorf
		}
		add(new Pumpspeicher(534000,0,0,0,120000,1));//Geesthacht
		add(new Druckluftspeicher(1000000,120194735,5500000,56000,200000,dsp));   
		
		kapGes = 0;
		for(int i=0;i<speicher.size();i++) {
			kapGes+=speicher.get(i).getKapazitaet();
		}
	}

	/**
	 * @return total battery status just in this moment
	 */
	public double getStatusGesamt() {
		double enGesamt = 0; 
		for(int i=0;i<speicher.size();i++) {
			enGesamt+=speicher.get(i).getLadestand();
		}
		//System.out.println(" "+enGesamt+" "+kapGes+" "+enGesamt/kapGes);
		return enGesamt/kapGes;
	}
	
	public void add(Speicher s) {
		speicher.add(s);
	}
	
	/** asks each storage in the list to take or give some energy
	 * @param diff available (positive) or needed (negative) energy amount
	 * @return (reduced) energy amount afterwards
	 */
	public double ladestand(double diff) {//diff ist Überschuss wenn positiv und Defizit wenn negativ	
		//System.out.println();
		//System.out.println(diff);
		double temp=diff;
		for(int i=0;i<speicher.size();i++) {
			diff=speicher.get(i).laden(diff);
		}
		temp-=diff;
		Pges+=temp;
		return diff; 
	}

	/**
	 * allows to regard self-drain
	 */
	public void selbstentladen() {
		for(int i=0;i<speicher.size();i++) {
			speicher.get(i).selbstentladen();
		}
	}
	
	/**
	 * @return total storage CAPEX
	 */
	public double invKosten() {
		double kosten=0;
		for(int i=0;i<speicher.size();i++) {
			kosten+=speicher.get(i).getInvKosten();
		}
		return kosten/1000000; //in Mio. €
	}
	
	/**
	 * @return total storage OPEX
	 */
	public double bKosten() {
		double kosten=0;
		for(int i=0;i<speicher.size();i++) {
			kosten+=speicher.get(i).getBKosten();
		}
		return kosten/1000000; //in Mio. €
	}
	
	/**
	 * @return total storage area
	 */
	public int flaeche() {
		double flaeche=0;
		for(int i=0;i<speicher.size();i++) {
			flaeche+=speicher.get(i).getFlaeche();
		}
		return (int) Math.round((flaeche)/10000);//gibt ganze Zahl in ha zurück
	}
	
	/**
	 * @return total energy difference of the year
	 */
	public double getPges() {
		double PgesTWh = Pges/1000000;  // kWh in TWh umrechnen
		return Math.round(PgesTWh*10)/10;
	}
}

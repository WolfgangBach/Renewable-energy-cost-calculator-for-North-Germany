package objektorientiert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

/** Liest Lastdaten ein, hält Lastdaten in Arrays und hält Elektrolyseure
 *
 */
public class VerbraucherPortfolio {
	private static final int N = 8760;
	private double[] verbrauch = new double[N];
	private ArrayList<Elektrolyseur> ptg;
	public static File srcHH=new File("./src\\objektorientiert\\HHLast.csv");;
	public static File srcSH=new File("./src\\objektorientiert\\SHLast_Einlesen.csv");;


	/**
	 * @param kav Kaverne instance
	 * @param elektrolyseure number inserted by user
	 */
	public VerbraucherPortfolio(Kaverne kav, int elektrolyseure) {
		readLoad();
		ptg=new ArrayList<Elektrolyseur>();
		ptg.add(new Elektrolyseur(1000,450000,189000/20,50,kav, elektrolyseure));//TODO Leistung, Kosten Fläche nur vorläufig
	}
	
	/** calculates baseload of the year, understood as everything under 2,5 GW
	 * @return amount of energy [kWh]
	 */
	public double getBaseLoad() {
		double BaseLoad=0;
		for(int i=0;i<N;i++) {
			if(verbrauch[i]>2500000) {
				BaseLoad+=2500000;
			}
			else {
				BaseLoad+=verbrauch[i];
			}
		}
		return BaseLoad;
	}
	
	/** calculates peakload of the year, understood as everything over 2,5 GW
	 * @return amount of energy [kWh]
	 */
	public double getPeakLoad() {
		double PeakLoad=0;
		for(int i=0;i<N;i++) {
			if(verbrauch[i]>2500000) {
				PeakLoad+=verbrauch[i]-2500000;
			}
		}
		return PeakLoad;
	}
	
	/**
	 * reads the files and saves load data in arrays
	 */
	public void readLoad() {
		File srcHH=new File("./src\\objektorientiert\\HHLast.csv");  
		File srcSH=new File("./src\\objektorientiert\\SHLast_Einlesen.csv");            
		double[] hochspannung= new double[N];
		double[] mittelspannung= new double[N];
		double[] niederspannung= new double[N];
		verbrauch=new double[N];
		for(int i=0;i<verbrauch.length;i++) {
			verbrauch[i]=0;
		}
		hochspannung=new double[N*4];
		for(int i=0;i<hochspannung.length;i++) {
			hochspannung[i]=0;
		}
		mittelspannung=new double[N*4];
		for(int i=0;i<mittelspannung.length;i++) {
			mittelspannung[i]=0;
		}
		niederspannung=new double[N*4];
		for(int i=0;i<niederspannung.length;i++) {
			niederspannung[i]=0;
			String line=" ";
			String csvSplitBy = ";";
			BufferedReader in=null;
			try {           //Einlesen LastDaten HH gespeichert in Verbrauch
				i = 0;
				in=new BufferedReader(new FileReader(srcHH));
				while((line=in.readLine())!=null) {
					//System.out.println("Durchlauf "+i+" letzter Wert "+line);
					String[] dat=line.split(csvSplitBy);
					verbrauch[i]=(Double.parseDouble(dat[1]))*1000;
					i++;
				}

			}catch(Exception e){
				System.err.println(e);
			}finally {
				if(in!= null) {
					// in.close();
				}
			}

			try {               //Einlesen Lastdaten SH
				i = 0;
				in=new BufferedReader(new FileReader(srcSH));
				while((line=in.readLine())!=null) {
					String[] dat=line.split(csvSplitBy);
					//System.out.println("Durchlauf "+i+" letzter Wert "+line);
					niederspannung[i]= Double.parseDouble(dat[7]);
					mittelspannung[i]= Double.parseDouble(dat[5]);
					hochspannung[i]= Double.parseDouble(dat[3]) ;
					i++;
				}
				for(int j=0;j<verbrauch.length;j++) {
					double tempNS=0;
					double tempMS=0;
					double tempHS=0;
					for(int z=0;z<4;z++) {
						tempNS+=((niederspannung[(j*4+z)]*1000)*(1-0.0357));        //mal 1000 weil komischer dezimalPunkt von NetzAG
						tempMS+=((mittelspannung[(j*4+z)]*1000)*(1-0.017));
						tempHS+=((hochspannung[(j*4+z)]*1000)*(1-0.0184));
					}
					verbrauch[j]+=tempNS+tempMS+tempHS;     //Hier Plus gleich damit Last addiert wird 
				}

			}catch(Exception e){
				System.err.println(e);
			}finally {
				if(in!= null) {
					// in.close();
				}
			}
		}
	}


	public double getVerbrauch(int hour) {
		return verbrauch[hour];
	}

	/** asks all electrolyzers to take some energy
	 * @param delta available energy amount
	 * @return afterwards available energy amount
	 */
	public double powerToGas(double delta) {
		for(Elektrolyseur e:ptg) {
			delta=e.elektrolyse(delta);
		}
		return delta;
	}
	
/*
	public static void setSrcHH(File srcHH) {
		VerbraucherPortfolio.srcHH = srcHH;
	}

	public static void setSrcSH(File srcSH) {
		VerbraucherPortfolio.srcSH = srcSH;
	}
	
	public int kostenPtg() {		//Gibt gesamtkosten in Mio zurück 
*/
	/**sums up power-to-gas CAPEX
	 * @return total power-to-gas CAPEX
	 */
	public double invKostenPtg() {
		double kosten=0;
		for(int i=0;i<ptg.size();i++) {
			kosten+=ptg.get(i).invKosten();
		}
		return kosten; //in Mio. €
	}
	
	/**sums up power-to-gas OPEX
	 * @return total power-to-gas OPEX
	 */
	public double bKostenPtg() {
		double kosten=0;
		for(int i=0;i<ptg.size();i++) {
			kosten+=ptg.get(i).bKosten();
		}
		return kosten; //in Mio. €
	}

	/**sums up power-to-gas area
	 * @return total power-to-gas area
	 */
	public double flaechePtg() {//in ha
		double flaeche=0;
		for(int i=0;i<ptg.size();i++) {
			flaeche+=(ptg.get(i).flaeche());
		}
		return flaeche;
	}
}

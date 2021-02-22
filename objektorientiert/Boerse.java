package objektorientiert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/** ermöglicht den Handel mit Strom am Spotmarkt und am Terminmarkt
 * 
 *
 */
public class Boerse {
	//Attribute
	private double[] preise;
	private double gewinne;
	double energieGes;
	ErzeugerPortfolio ep;
	public static File srcBoerse=new File("./src\\objektorientiert\\Strompreis.csv");

	//Constructor 
	/**
	 * @param ep ErzeugerPortfolio instance held by szenario
	 */
	public Boerse(ErzeugerPortfolio ep) {
		preise = new double[8760];
		try {
			readPrice();
		} catch (IOException e) {
			e.printStackTrace();
		}
		gewinne=0;
		energieGes=0;
		this.ep=ep;
	}

	/**
	 * @return the profits until now this year
	 */
	public double getGewinne() {
		return gewinne;
	}
	
	/**
	 * @return sold minus bought energy amount until now this year
	 */
	public double getEnergieGes() {
		return energieGes;
	}

	//Methoden

	/** allows to sell on spot market
	 * @param delta available energy amount
	 * @param hour of the year
	 * @return afterwards available energy amount
	 */
	public double spotmarkt(double delta, int hour) {
		if(delta>ep.leistungNeu(hour)) {
			delta=ep.leistungNeu(hour);
		}
		gewinne+=delta*(preise[hour]/1000+0.032/*vorläufig*/); //in €	
		energieGes+=delta;
		return 0;
	}
	
	/** allows to sell on futures market
	 * @param base energy amount of baseload
	 * @param peak energy amount of peakload
	 * @return afterwards available energy amount
	 */
	public double terminmarkt(double base,double peak) {
		gewinne+=base* 0.052;		//Hier BaseLoad Preis einfügen
		gewinne+=peak*0.063;			//Hier PeakLoad Preis einfügen
		energieGes+=base;
		energieGes+=peak;
		System.out.println("Terminmarkt Erlöse: "+gewinne);
		System.out.println("Terminmarkt verkauft: "+(base+peak));
		return 0;
	}

	/** allows to buy on spot market
	 * @param delta needed energy amount
	 * @param hour of the year
	 * @return afterwards needed energy amount
	 */
	public double kaufen(double delta, int hour) {
		gewinne+=delta*(preise[hour]/1000+0.032); //in €	
		energieGes+=delta;
		return 0;		
	}

	private void readPrice() throws IOException {
		String line=" ";
		String csvSplitBy = ";";
		int loc=0;
		int z = 0;
		BufferedReader in=null;
		try {
			//System.out.println("Src "+src);
			in= new BufferedReader(new FileReader(srcBoerse));

			while((line=in.readLine())!=null) {

				String[] str = line.split(csvSplitBy);
				if(z>0&&z<8760) {
					//System.out.println("Preis: "+str[2]);
					preise[z-1]=Double.parseDouble(str[2]); 
				}
				z++;
			}
		}finally {
			if(in!= null) {
				in.close();
			}
		}
	}

	public static void setSrcBoerse(File srcBoerse) {
		Boerse.srcBoerse = srcBoerse;
	}

	public double getPreis(int hour) {
		return preise[hour];
	}

	
	/*public static void main(String[] args) throws IOException {
		Boerse b= new Boerse();
	}*/

}

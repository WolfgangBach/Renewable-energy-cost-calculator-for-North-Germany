package objektorientiert;
 

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Src ist Speicherstelle von Winddaten
 * gibt Windgeschwindigkeit von Stunde als double an Landkreis 
 * Lieﬂt Windgeschw. ein 
 *
 */
public class Windmessstation {
	//Attribute 
	
	private double[] wind;
	private int station;
	private File src;
	
	//Constructor 
	public Windmessstation(int station,File src) throws IOException {
		super();
		this.station = station;
		this.src=src;
		wind=new double[8760];
		for(int i=0;i<wind.length;i++) {
			wind[i]=0;
		}
		this.readWind();
	}
	
	//Methoden
	public double getWind(int hour) {
		return wind[hour];
	}
	
	/** reads files and saves wind data in arrays
	 * @throws IOException
	 */
	private void readWind() throws IOException {
		String line=" ";
		String csvSplitBy = ";";
		int loc=0;
		int z = 0;
		BufferedReader in=null;
		try {
			in= new BufferedReader(new FileReader(src));
			
			while((line=in.readLine())!=null) {
				String[] str = line.split(csvSplitBy);
				//System.out.println("Location: "+str[0]+ "Wind "+str[2]);
				loc=Integer.parseInt(str[0]);
				if(loc==station) {
					wind[z]=Double.parseDouble(str[2]);
					z++;
				}
				}
		}finally {
			if(in!= null) {
				in.close();
			}
		}

	}
	/*public static void main(String[] args) throws IOException {
		String src="C:\\Users\\Lennart\\git\\repository\\IPJ\\src\\objektorientiert\\WindEinlesen.csv";
		Windmessstation wm= new Windmessstation(src,4393);
		for(int i=0;i<8760;i++) {
			System.out.println("Durchlauf "+i+" Wind: "+wm.getWind(i));
		}
	}*/
}

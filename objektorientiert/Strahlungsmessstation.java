package objektorientiert;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


/**Src ist Speicherstelle von Globalstrahlungsdaten
 * gibt Globalstrahlung von Stunde als double an PVPark 
 * Ließt Globalstrahlungswerte ein 
 *
 */
public class Strahlungsmessstation {
	public static File srcGlobal=new File("./src\\objektorientiert\\Global&DiffusstrahlungEinlesen.txt");
    private static Strahlungsmessstation einzige= new Strahlungsmessstation();
    private double[] global;
    

 
    //Constructor 
    public Strahlungsmessstation() {
        global= new double[8760];
        try {
            readSolar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Strahlungsmessstation get()
    {
        return einzige;
    }
    
    /**
     * notify change
     */
    public void changed() {
    	try {
			readSolar();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    //Methoden
    public double getSolar(int hour) {
        return global[hour];
    }

    /** reads radiation values
     * @throws IOException
     */
    private void readSolar() throws IOException {
        String line=" ";
        String csvSplitBy = ";";
        int loc=0;
        int z = 0;
        BufferedReader in=null;
        try {
            //System.out.println("Src "+src);
            in= new BufferedReader(new FileReader(srcGlobal));

            while((line=in.readLine())!=null) {

                String[] str = line.split(csvSplitBy);
                if(z>0) {
                    global[z-1]=Double.parseDouble(str[5]); //NullPointerException
                }
                z++;
            }
        }finally {
            if(in!= null) {
                in.close();
            }
        }
    }

}

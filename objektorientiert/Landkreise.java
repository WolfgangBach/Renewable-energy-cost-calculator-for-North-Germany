package objektorientiert;
import java.util.Hashtable;
import java.io.File;
import java.io.IOException;

/**
 * Hält alle Landkreis Instanzen
 * 
 */
public class Landkreise
{
    private static Landkreise land= new Landkreise(); // WR: executed by class loader, Singleton !
    
    // instance variables - replace the example below with your own
    private Hashtable<String,Landkreis> landkreise;
    private Windmessstation[] wm;
    public static  File srcWind;

    /**
     * private Constructor for single object of class Landkreise, executed by class loader
     * 
     */
    private Landkreise() {
    	srcWind=new File("./src\\objektorientiert\\WindEinlesen.csv");
        initWindmess();
        initLandkreise();
    }
    
    public static Landkreise get() {
    	return land;
    }
    
    /**
     * notify change
     */
    public void changed() {
    	initWindmess();
    	initLandkreise();
    }

    /**
     * initiates Landkreis instances
     */
    public void initLandkreise() {   //Debugged (Works)
        landkreise= new Hashtable<String,Landkreis>();
        landkreise.put("Nordfriesland",new Landkreis("Nordfriesland", wm[0],8.23,0));   //wm[0]=4393
        landkreise.put("Dithmarschen",new Landkreis("Dithmarschen",wm[0],10.7,0));
        //landkreise.put("Flensburg",new Landkreis("Flensburg",wm[1],7.37,1));        //wm[1]=1379
        landkreise.put("Schleswig-Flensburg", new Landkreis("Schleswig-Flensburg",wm[1],7.37,0));
        //landkreise.put("Kiel",new Landkreis("Kiel",wm[2],5.87,1));              //wm[2]=2429
        landkreise.put("Rendsburg-Eckernförde", new Landkreis("Rendsburg-Eckernförde",wm[2],7.21,1));
        landkreise.put("Steinburg", new Landkreis("Steinburg",wm[2],7.43,1));
        landkreise.put("Ostholstein",new Landkreis("Ostholstein",wm[3],5.4,1));//wm[3]=5516
        landkreise.put("Herzogtum Lauenburg",new Landkreis("Herzogtum Lauenburg",wm[4],5.82,1));    //wm[4]=3086
        //landkreise.put("Neumünster", new Landkreis("Neumünster",wm[4],5.82,1));
        //landkreise.put("Lübeck",new Landkreis("Lübeck",wm[4],5.4,1));
        landkreise.put("Pinneberg",new Landkreis("Pinneberg",wm[4],7.55,1));
        landkreise.put("Plön",new Landkreis("Plön",wm[4],5.87,1));
        landkreise.put("Segeberg",new Landkreis("Segeberg",wm[4],5.82,1));
        landkreise.put("Stormarn", new Landkreis("Stormarn",wm[4],5.82,1));
    }

    /**
     * initiates Windmessstation instances
     */
    private void initWindmess() {		//Debugged (Works)
        wm=new Windmessstation[5];
        try{
            wm[0]= new Windmessstation(4393,srcWind);
            wm[1]= new Windmessstation(1379,srcWind);
            wm[2]= new Windmessstation(2429,srcWind);
            wm[3]= new Windmessstation(5516,srcWind);
            wm[4]= new Windmessstation(3086,srcWind);
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
    
    public static Landkreis get(String name) {
        return land.landkreise.get(name);
    }
    
    public static int number() {
        return land.landkreise.size();
    }
}

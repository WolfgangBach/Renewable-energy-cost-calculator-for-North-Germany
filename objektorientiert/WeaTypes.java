package objektorientiert;
import java.io.IOException;
import java.util.Hashtable;

/**
 * Hält alle WeaTypen
 */
public class WeaTypes
{
    private static WeaTypes types= new WeaTypes(); // WR: executed by class loader, Singleton !

    private Hashtable<String,WeaTyp> weaTyp;    

    /**
     * Constructor for objects of class WeaTypes
     */
    private WeaTypes() {
        // initialise instance variables
        initWeaTypen();
    }

    /**
     * initiates all instances of WeaTyp
     */
    public  void initWeaTypen() {
        weaTyp=new Hashtable<String,WeaTyp>();
        
        double[] tempLeistung= new double[]{0,0,0,47.2,124.4,250.1,437.4,696.4,1034.8,1449.3,1904.1,2285.5,2472.1,2499.4,2500};
        int[] tempnabe= new int[]{100,100};
        weaTyp.put("vensys100",new WeaTyp("vensys100",tempLeistung,tempnabe,25));
        tempLeistung= new double[]{0,0,0,17,104,251,470,772,1170,1656,2120,2441,2661,2768,2780};
        tempnabe= new int[]{85,85};
        weaTyp.put("ge275", new WeaTyp("ge275",tempLeistung,tempnabe,25));
        tempLeistung= new double[]{0,0,2,18,56,127,240,400,626,892,1223,1590,1900,2080,2230,2300,2310};
        tempnabe= new int[]{70,70};
        weaTyp.put("enerconE70", new WeaTyp("enerconE70",tempLeistung,tempnabe,25));
        tempLeistung= new double[] {0,0,0,0,24,86,188,326,526,728,1006,1271,1412,1500};
        tempnabe= new int[]{65,65};
        weaTyp.put("nordexN70", new WeaTyp("nordexN70",tempLeistung,tempnabe,25));
        tempLeistung=new double[] {0,0,0,23,139,330,604,990,1502,2102,2679,3098,3200};  
        tempnabe= new int[]{119,119};
        weaTyp.put("senvion32", new WeaTyp("senvion32",tempLeistung,tempnabe,22));
        tempLeistung=new double[] {0,0,0,0,20,78.2,170.67,278,465.85,673.3,913.6,1054.4,1242.9,1439.351567,1620.67,1639,1650};
        tempnabe= new int[]{78,78};
        weaTyp.put("vestasV66", new WeaTyp("vestasV66",tempLeistung,tempnabe,22));
        tempLeistung=new double[] {0,0,3,25,82,174,321,532,815,1180,1580,1810,1980,2050};
        tempnabe= new int[]{78,78};
        weaTyp.put("enerconE82", new WeaTyp("enerconE82",tempLeistung,tempnabe,25));
        tempLeistung=new double[] {0,0,0,17,131,344,638,1038,1562,2188,2846,3308,3540,3600};
		tempnabe= new int[]{106,141};
		weaTyp.put("Nordex N117/3600 Delta", new WeaTyp("Nordex N117/3600 Delta",tempLeistung,tempnabe,25,3600,117));
		tempLeistung=new double[] {0,0,0,53,185,400,745,1200,1790,2450,3120,3660,4000,4150,4200};
		tempnabe= new int[]{99,144};
		weaTyp.put("Enercon E-126", new WeaTyp("Enercon E-126",tempLeistung,tempnabe,25,4200,127));
		tempLeistung=new double[] {0,0,0,22,150,340,617,1006,1522,2178,2905,3374,3448};
		tempnabe= new int[]{117,142};
		weaTyp.put("vestasV117", new WeaTyp("vestasV117",tempLeistung,tempnabe,25,3448,117));
		//Debugging Basisszenario 
		tempLeistung=new double[] {0,0,0,0,15,120,248,429,662,964,1306,1658,1984,2264,2450,2500};
		tempnabe= new int[]{80,100};
		weaTyp.put("NordexN80Alpha", new WeaTyp("NordexN80Alpha",tempLeistung,tempnabe,25,3448,117));
		tempLeistung=new double[] {0,0,0,22,134,302,552,906,1370,1950,2586,3071,3266,3300};
		tempnabe= new int[]{84,94};
		weaTyp.put("VestasV112", new WeaTyp("VestasV112",tempLeistung,tempnabe,25,3448,117));
        
    }

    public static WeaTyp get(String typ) {
        return types.weaTyp.get(typ);
    }
}

package objektorientiert;
  
/**
 * Klasse enthält Leistungskurven für Anlagetypen
 * public Methoden:getLeistung[wind]
 *
 */
public class WeaTyp {
	//Attribute
	private double[] Leistungskurve;
	private String name;
	private int[] nabenhoehe;
	private int abschaltwert;
	private int nennleistung;
	private int rotordurchmesser;
	
	
	//Constructor 
	public WeaTyp(String name,double[] Leistungskurve,int[] nabenhoehe,int abschaltwert) {
		super();
		this.name=name;
		this.Leistungskurve=Leistungskurve;
		this.nabenhoehe=nabenhoehe;
		this.abschaltwert=abschaltwert;
	}
	public WeaTyp(String name,double[] Leistungskurve,int[] nabenhoehe,int abschaltwert,int nennleistung,int rotordurchmesser) {
		super();
		this.name=name;
		this.Leistungskurve=Leistungskurve;
		this.nabenhoehe=nabenhoehe;
		this.abschaltwert=abschaltwert;
		this.nennleistung=nennleistung;
		this.rotordurchmesser=rotordurchmesser;
	}
	
	//Methoden
	/** reads values out of power curve arrays
	 * @param wind speed
	 * @return power according to wind speed
	 */
	public double leistung(double wind) {
		int windRounded = (int) Math.round(wind);
		if(windRounded>=Leistungskurve.length) {
			if(windRounded>abschaltwert) {
				return 0;
			}
			else {
				return Leistungskurve[Leistungskurve.length-1];
			}
		}
		else{
			return Leistungskurve[windRounded];
		}
	}

	public int getNabenhoehe(int i) {
		return nabenhoehe[i];
	}
	
	public int getNennleistung() {
		return nennleistung;
	}
	
	public int getRotordurchmesser() {
		return rotordurchmesser;
	}
	

}

package objektorientiert;
import java.util.ArrayList;
import java.util.Iterator;
/**
 * ErzeugerPortfolio hält alle Erzeuger
 * Erzeugerportfolio gibt Leistung stündlich an Bilanz 
 */
public class ErzeugerPortfolio {
	//Attribute 
	private ArrayList<Windpark> weaErzeuger;		 
	private ArrayList<PVPark> pvErzeuger;		
	private ArrayList<BiogasKWK> biogas;
	private ArrayList<GasToPower> gtp;
	private Kaverne kav;
	private double weages;
	private double pvges;

	//Constructor
	/**
	 * initiates Arraylists
	 */
	public ErzeugerPortfolio() {
		weaErzeuger = new ArrayList<Windpark>() ;
		pvErzeuger = new ArrayList<PVPark>();
		biogas = new ArrayList<BiogasKWK>();
		gtp = new ArrayList<GasToPower>();
	}

	/**
	 * starts init-methods
	 * 
	 * @param anlagenTyp1 number inserted by user
	 * @param anlagenTyp2 number inserted by user
	 * @param anlagenTyp3 number inserted by user
	 * @param freiflaechen number inserted by user
	 * @param brennstoffzellen number inserted by user
	 * @param kavernen number inserted by user
	 */
	public ErzeugerPortfolio(int anlagenTyp1, int anlagenTyp2, int anlagenTyp3, int freiflaechen, int brennstoffzellen,int kavernen) {
		this();
		initWeaAlt();
		initPValt();
		initWeaNeu(anlagenTyp1, anlagenTyp2, anlagenTyp3);
		initPVneu(freiflaechen);
		initBiogas(344863);//Leistung in kW (=3021GWh/8760h)
		initGasToPower(brennstoffzellen,kavernen);
	}

	/**
	 * @param brennstoffzellen number inserted by user
	 * @param kavernen number inserted by user
	 */
	private void initGasToPower(int brennstoffzellen, int kavernen) {
		kav=new Kaverne(kavernen+2,kavernen*16950000.0,kavernen*23500);
		gtp.add(new GuDK(393000,kav));
		gtp.add(new Brennstoffzelle(1000,500000,500000/20,50,brennstoffzellen, kav)); 
	}

	private void initWeaAlt() {
		//Anzahl der WEA inkl. Inbetriebnahme und in Gen. Verf. 
		/*weaErzeuger.add(new Windpark(Landkreise.get("Dithmarschen"),WeaTypes.get("vensys100"),974,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Herzogtum Lauenburg"),WeaTypes.get("enerconE82"),81,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Nordfriesland"),WeaTypes.get("ge275"),1023,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Ostholstein"),WeaTypes.get("enerconE70"),421,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Pinneberg"),WeaTypes.get("enerconE82"),17,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Plön"),WeaTypes.get("nordexN70"),46,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Rendsburg-Eckernförde"),WeaTypes.get("senvion32"),279,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Schleswig-Flensburg"),WeaTypes.get("ge275"),550,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Segeberg"),WeaTypes.get("ge275"),112,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Steinburg"),WeaTypes.get("vensys100"),362,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Stormarn"),WeaTypes.get("vestasV66"),39,false));*/
		
		
		//Debugging Basiszenario nur Bestandsanlagen 
		/*weaErzeuger.add(new Windpark(Landkreise.get("Dithmarschen"),WeaTypes.get("enerconE70"),829,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Herzogtum Lauenburg"),WeaTypes.get("enerconE82"),60,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Nordfriesland"),WeaTypes.get("NordexN80Alpha"),814,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Ostholstein"),WeaTypes.get("enerconE70"),138,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Pinneberg"),WeaTypes.get("enerconE82"),13,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Plön"),WeaTypes.get("nordexN70"),39,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Rendsburg-Eckernförde"),WeaTypes.get("VestasV112"),151,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Schleswig-Flensburg"),WeaTypes.get("NordexN80Alpha"),399,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Segeberg"),WeaTypes.get("NordexN80Alpha"),50,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Steinburg"),WeaTypes.get("NordexN80Alpha"),290,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Stormarn"),WeaTypes.get("vestasV66"),38,false));*/
		
		//Basiszenario alle Anlagen angepasste Nennleistung
		weaErzeuger.add(new Windpark(Landkreise.get("Dithmarschen"),WeaTypes.get("enerconE70"),974,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Herzogtum Lauenburg"),WeaTypes.get("enerconE82"),81,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Nordfriesland"),WeaTypes.get("NordexN80Alpha"),1023,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Ostholstein"),WeaTypes.get("enerconE70"),421,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Pinneberg"),WeaTypes.get("enerconE82"),17,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Plön"),WeaTypes.get("nordexN70"),46,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Rendsburg-Eckernförde"),WeaTypes.get("VestasV112"),279,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Schleswig-Flensburg"),WeaTypes.get("NordexN80Alpha"),550,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Segeberg"),WeaTypes.get("NordexN80Alpha"),112,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Steinburg"),WeaTypes.get("NordexN80Alpha"),362,false));
		weaErzeuger.add(new Windpark(Landkreise.get("Stormarn"),WeaTypes.get("vestasV66"),39,false));
	}

	/**
	 * initiates existing PV
	 */
	private void initPValt() {
		pvErzeuger.add(new PVPark(Strahlungsmessstation.get(),1667000.0));
	}

	/**initiates new-built PV
	 * @param freiflaechen number inserted by user
	 */
	private void initPVneu(int freiflaechen) {
		pvErzeuger.add(new PVPark(Strahlungsmessstation.get(),freiflaechen));
	}

	/**
	 * @param instLeistungEff constant bio energy power
	 */
	private void initBiogas(double instLeistungEff) {
		biogas.add(new BiogasKWK(instLeistungEff));
	}

	/**
	 * @param anlagenTyp1 number inserted by user
	 * @param anlagenTyp2 number inserted by user
	 * @param anlagenTyp3 number inserted by user
	 */
	private void initWeaNeu(int anlagenTyp1, int anlagenTyp2, int anlagenTyp3) {
		double verteilVektor[] = {0.14992,0.02466,0.25296,0.14261,0.00184,0.0259,0.12868,0.10933,0.04789,0.10962,0.00654};
		int verteilMatrix[][]= new int[Landkreise.number()][3];
		WeaTyp neueTypen[] = {WeaTypes.get("Nordex N117/3600 Delta"),WeaTypes.get("Enercon E-126"),WeaTypes.get("vestasV117")};
		for ( int i=0; i<Landkreise.number(); i++){
			verteilMatrix[i][0]=(int) Math.round(anlagenTyp1* verteilVektor[i]);
			verteilMatrix[i][1]=(int) Math.round(anlagenTyp2* verteilVektor[i]);
			verteilMatrix[i][2]=(int) Math.round(anlagenTyp3* verteilVektor[i]);
		}
		for(int i=0;i<3;i++) {
			weaErzeuger.add(new Windpark(Landkreise.get("Dithmarschen"),neueTypen[i],verteilMatrix[0][i],true)); //Beispiele
			weaErzeuger.add(new Windpark(Landkreise.get("Herzogtum Lauenburg"),neueTypen[i],verteilMatrix[1][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Nordfriesland"),neueTypen[i],verteilMatrix[2][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Ostholstein"),neueTypen[i],verteilMatrix[3][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Pinneberg"),neueTypen[i],verteilMatrix[4][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Plön"),neueTypen[i],verteilMatrix[5][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Rendsburg-Eckernförde"),neueTypen[i],verteilMatrix[6][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Schleswig-Flensburg"),neueTypen[i],verteilMatrix[7][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Segeberg"),neueTypen[i],verteilMatrix[8][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Steinburg"),neueTypen[i],verteilMatrix[9][i],true));
			weaErzeuger.add(new Windpark(Landkreise.get("Stormarn"),neueTypen[i],verteilMatrix[10][i],true));
		}
	}

	/**
	 * Method getWeages returns that energy that has been summed up by calls to leistung (int hour)
	 * @return wind power in the whole year [GWh]
	 */
	public double getWeages() {
		double weagesGWh = weages/1000000;  // kWh in GWh umrechnen
		return Math.round(weagesGWh*10)/10;
	}
	
	/**
	 * Method getWeages returns that energy that has been summed up by calls to leistung (int hour)
	 * @return wind power in the whole year [GWh]
	 */
	public double getPvges() {
		double pvgesGWh = pvges/1000000;  // kWh in GWh umrechnen
		return Math.round(pvgesGWh*10)/10;
	}
	
	/**
	 * @return instance of the cavern
	 */
	public Kaverne getKav() {
		return kav;
	}

	//Methoden
	/**
	 * @param hour of the year
	 * @return power of all renewables in this hour
	 */
	public double leistung(int hour) {
		double leistung=0;
		double temp=0;
		for(int i=0;i<weaErzeuger.size();i++) {
			temp=weaErzeuger.get(i).leistung(hour);
			leistung+=temp;
			weages+=temp;
			//System.out.println(weaErzeuger.get(i).getLk().toString()+" "+leistung);
		}
		for(int i=0;i<pvErzeuger.size();i++) {
			leistung+=pvErzeuger.get(i).leistung(hour);
			pvges+=pvErzeuger.get(i).leistung(hour);
		}
		for(int i=0;i<biogas.size();i++) {
			leistung+=biogas.get(i).leistung();
		}
		return leistung;
		
	}
	
	/**
	 * @param hour of the year
	 * @return power of all new-built renewables in this hour
	 */
	public double leistungNeu(int hour) {
		double leistung=0;
		double temp=0;
		for(int i=0;i<weaErzeuger.size();i++) {
			temp=weaErzeuger.get(i).leistungNeu(hour);
			leistung+=temp;
			//System.out.println(weaErzeuger.get(i).getLk().toString()+" "+leistung);
		}
		for(int i=0;i<pvErzeuger.size();i++) {
			temp=pvErzeuger.get(i).leistungNeu(hour);
			leistung+=temp;
		}
		return leistung;
	}
	
	/**
	 * @param delta Available energy amount
	 * @return afterwards available energy amount
	 */
	public double gasToPower(double delta) {
		for(GasToPower g:gtp) {
			delta=g.gasToPower(delta);
		}
		return delta;
	}

	/*public void addWindpark(Windpark w) {
		weaErzeuger.add(w);
	}

	public void addPVPark(PVPark p) {
		pvErzeuger.add(p);
	}

	public void addBiogas(double instLeistungEff) {
		biogas.add(new BiogasKWK(instLeistungEff));
	}*/

	/**
	 * @return wind CAPEX
	 */
	public double invKostenWeaGes() {		//Gibt gesamtkosten in Mio zurück 
		double kosten=0;
		for(int i=0;i<weaErzeuger.size();i++) {
			kosten+=(weaErzeuger.get(i).invKosten());
		}
		return kosten;
	}
	
	/**
	 * @return wind OPEX
	 */
	public double bKostenWeaGes() {		//Gibt gesamtkosten in Mio zurück 
		double kosten=0;
		for(int i=0;i<weaErzeuger.size();i++) {
			kosten+=(weaErzeuger.get(i).bKosten());
		}
		return kosten;
	}

	/**
	 * @return wind area
	 */
	public double flaecheWeaGes() { //in ha
		double flaeche=0;
		for(int i=0;i<weaErzeuger.size();i++) {
			flaeche+=(weaErzeuger.get(i).flaeche());
		}
		return flaeche;
	}

	/**
	 * @return PV CAPEX
	 */
	public double invKostenPvGes() {		//Gibt gesamtkosten in Mio zurück 
		double kosten=0;
		for(int i=0;i<pvErzeuger.size();i++) {
			kosten+=(pvErzeuger.get(i).invKosten());
		}
		return kosten;
	}
	
	/**
	 * @return PV OPEX
	 */
	public double bKostenPvGes() {		//Gibt gesamtkosten in Mio zurück 
		double kosten=0;
		for(int i=0;i<pvErzeuger.size();i++) {
			kosten+=(pvErzeuger.get(i).bKosten());
		}
		return kosten;
	}

	/**
	 * @return PV area
	 */
	public double flaechePvGes() {//in ha
		double flaeche=0;
		for(int i=0;i<pvErzeuger.size();i++) {
			flaeche+=(pvErzeuger.get(i).flaeche());
		}
		return flaeche;
	}
	
	/**
	 * @return Gas-to-power CAPEX
	 */
	public double invKostenGtpGes() {		//Gibt gesamtkosten in Mio zurück 
		double kosten=0;
		for(int i=0;i<gtp.size();i++) {
			kosten+=(gtp.get(i).invKosten());
		}
		return kosten;
	}
	
	/**
	 * @return Gas-to-power OPEX
	 */
	public double bKostenGtpGes() {		//Gibt gesamtkosten in Mio zurück 
		double kosten=0;
		for(int i=0;i<gtp.size();i++) {
			kosten+=(gtp.get(i).bKosten());
		}
		return kosten;
	}

	/**
	 * @return Gas-to-power area
	 */
	public double flaecheGtp() {//in ha
		double flaeche=0;
		for(int i=0;i<gtp.size();i++) {
			flaeche+=(gtp.get(i).flaeche());
		}
		return flaeche;
	}
}

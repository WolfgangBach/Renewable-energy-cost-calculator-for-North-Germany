package objektorientiert;

/**Klasse für Bestandsanlagen (bilden zusammen eine Instanz) und Neuanlagen (bilden zusammen eine Instanz)
 *
 */
public class PVPark {

    private Strahlungsmessstation sm;
    private double instLeistung=900; //Standardleistung eines PV-Parks
    private int freiflaechen=1; //Anzahl PV-Parks

    /** constructor for existing PV
     * @param sm Strahlungsmessstation instance
     * @param instLeistung nominal power of existing PV
     */
    public PVPark(Strahlungsmessstation sm, double instLeistung) { //Konstruktor 1x für alle Bestandsanlagen
        this.sm=sm;
        this.instLeistung=instLeistung; //wird mit P=1667000kW überschrieben für Bestandsanlagen
    }

    /**
     * @param sm Strahlungsmessstation instance
     * @param freiflaechen number inserted by user
     */
    public PVPark(Strahlungsmessstation sm, int freiflaechen) { //Konstruktor 1x für alle Neuanlagen
        this.sm=sm;
        this.freiflaechen=freiflaechen; //wird mit Parameter für Anzahl PV-Parks überschrieben für Neuanlagen
    }

    /**
     * @param hour of the year
     * @return power
     */
    public double leistung(int hour) {
        double nkf[] = {1.44,1.4,1.17,1.08,1,0.96,0.97,1.03,1.17,1.3,1.47,1.42};
        int monat = (int)Math.floor(hour/730);
        double strahlung=sm.getSolar(hour)*10000/3600;
        double leistung = freiflaechen*instLeistung * strahlung/1000*nkf[monat]*0.92;
        return leistung;
    }
    
    /**
     * @param hour of the year
     * @return power if new-built PV else 0
     */
    public double leistungNeu(int hour) {
    	if(instLeistung==900) {
    		return leistung(hour);
    	}
    	else {
    		return 0;
    	}
    }

    /**
     * @return PV area
     */
    public double flaeche() {//in ha
        if(instLeistung==900) return freiflaechen;
        else return 0;
    }

    /**
     * @return PV CAPEX
     */
    public double invKosten() {
        if(instLeistung==900) return freiflaechen*0.861500;
        else return 0;
    }
    
    /**
     * @return PV OPEX
     */
    public double bKosten() {
        if(instLeistung==900) return invKosten()*0.01;
        else return 0;
    }

}

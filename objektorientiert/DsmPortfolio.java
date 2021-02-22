package objektorientiert;

import java.util.ArrayList;

/**
 * 
 * 		Klasse verwaltet die DSM Potentiale 
 * 		Wird von Bilanz aufgerufen 
 * 
 *		Debugged, mit simplem Test. Muss später wenn in Bilanz mit unterschiedlichen Differenzen mal schauen. 
 */

public class DsmPortfolio {
	//Attribute 
	private ArrayList <DsmPotential> dsmPort;


	//Constructor 
	public DsmPortfolio(boolean ja) {
		super();
		dsmPort=new ArrayList <DsmPotential>();
		if(ja) {
			initDSM();
		}
		else {
			initEmpty();
		}
	}

	//Methoden 
	public double dsmPot(double diff,int hour) {
		double RegelLeistung=0;
		int i=0;
		double temp=0;
		if(diff<0) {
			while(diff<0 && i<(dsmPort.size())) {		//Alle DSM Potentiale druchgehen Solange benötigt 
				temp=dsmPort.get(i).getPotential(hour);
				diff+=temp;
				RegelLeistung+=temp;
				i++;
			}
		}else {
			for(i=0;i<dsmPort.size();i++) {
				dsmPort.get(i).stop();
			}
		}
		//System.out.println(" Positive Regelleistung in MW : "+RegelLeistung);
		return 1000*RegelLeistung;
	}

	public double dsmAusgleich(int hour) {		//Wir zu Verbrauch addiert, um Ausgleich zu tuen
		double RegelLeistung=0;
		int i=0;
		for(i=0;i<dsmPort.size();i++) {
			RegelLeistung-=dsmPort.get(i).getAusgleich(hour);
		}
		//System.out.print("Ausgleichsleistung in MW : "+RegelLeistung);
		return 1000*RegelLeistung;
	}
	private void initDSM() {
		dsmPort.add(new DsmPotential(50,12,"Aluminium-Elektrolyse"));
		dsmPort.add(new DsmPotential(180,6,"KühlundGefriergeräteHH"));
		dsmPort.add(new DsmPotential(64,12,"WarmwasserHH"));
		dsmPort.add(new DsmPotential(64,12,"WarmwasserSH"));
		dsmPort.add(new DsmPotential(19,9,"Elektrolichtbogenofen"));
		dsmPort.add(new DsmPotential(7,2,"Wärmepumpen"));
		dsmPort.add(new DsmPotential(11,4,"Luftzerlegung"));
		dsmPort.add(new DsmPotential(6,6,"Zementwerk"));
		dsmPort.add(new DsmPotential(5,4,"Kupfer-Elektrolyse"));
		dsmPort.add(new DsmPotential(57,6,"Fernwärmenetz"));
		dsmPort.add(new DsmPotential(180,6,"KühlundGefriergeräteSH"));
		dsmPort.add(new DsmPotential(43,8,"Wärmespeicher"));
		dsmPort.add(new DsmPotential(305,3,"Chlor-Alkali-Elektrolyse"));
		dsmPort.add(new DsmPotential(25,22,"NachtspeicherheizungHH"));
		dsmPort.add(new DsmPotential(25,22,"NachtspeicherheizungSH"));
	}

	private void initEmpty(){
		dsmPort.add(new DsmPotential(0,12," "));
	}
	//Test Main
	/*public static void main(String[] args){
		DsmPortfolio d= new DsmPortfolio(false);
		double Ausgleichsleistung=0;
		double pPlus=0;
		for(int i=0;i<8760;i++) {
			pPlus=d.dsmPot(-1000,i,3);
			Ausgleichsleistung=d.dsmAusgleich(i);
			if(Ausgleichsleistung!=0)
			System.out.println("Stunde "+i+": Leistungs absenkung "+pPlus/1000+": Ausgleichsleistung "+ Ausgleichsleistung/1000);
		}
		System.out.println("Fertig");
	}*/
}

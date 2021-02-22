package objektorientiert;

/** Hilft bei der Optimierung einzelner Parameter
 *
 */
public class Optimizer {
	Szenario sz;


	public Optimizer(Szenario szenario) {
		this.sz=szenario;
		run();
	}

	/**
	 * iterates every possibility, compares costs without simulating and eventually checks for conditions fulfillment by simulating
	 * finally starts the best option normally or if nothing found: basic szenario
	 */
	public void run() {
		final long starttime = System.currentTimeMillis(); // start timing
		int[][] param = {{0}/*Anlage1*/,{0}/*Anlage2*/,{700,800,900,1000}/*Anlage3*/,{3000,5000,1000,7000}/*PV*/,{0}/*LI-Batterie*/,{0}/*Redoxflow*/,{0,50,100}/*Druckluft*/,{2500,3000,3500,4000}/*Brennstoffzellen*/,{2500,3000,3500,4000}/*Elektrolyseure*/,{20,30,10}/*Kavernen*/};
		double kbest = 50000000000.0; //new double[8];
		int paramBest[]=new int[11];
		for(int i=0;i<param[0].length;i++) {
			for(int j=0;j<param[1].length;j++) {
				for(int k=0;k<param[2].length;k++) {
					for(int l=0;l<param[3].length;l++) {
						for(int m=0;m<param[4].length;m++) {
							for(int n=0;n<param[5].length;n++) {
								for(int o=0;o<param[6].length;o++) {
									for(int p=0;p<param[7].length;p++) {
										for(int q=0;q<param[8].length;q++) {
											for(int r=0;r<param[9].length;r++) {

												double cost=kostenAbschaetzen(param[0][i], param[1][j], param[2][k], param[3][l], param[4][m], param[5][n], param[6][o], param[7][p], param[8][q], param[9][r]);
												//System.out.println((cost<kbest[copyI])+" "+sz.simulate(param[0][i], param[1][j], param[2][k], param[3][l], param[4][m], param[5][n], param[6][o], param[7][p], param[8][q], param[9][r], param[10][copyI], false, true));
												if(cost<kbest) {
													boolean a=sz.simulate(param[0][i], param[1][j], param[2][k], param[3][l], param[4][m], param[5][n], param[6][o], param[7][p], param[8][q], param[9][r], 6, true, true);
													if(a) {
														kbest=cost;
														//System.out.print("Überschreiben: " +a+" "+sz.simulate(param[0][i], param[1][j], param[2][k], param[3][l], param[4][m], param[5][n], param[6][o], param[7][p], param[8][q], param[9][r], 10, false, true)+" ");
														paramBest[0]=param[0][i];
														paramBest[1]=param[1][j];
														paramBest[2]=param[2][k];
														paramBest[3]=param[3][l];
														paramBest[4]=param[4][m];
														paramBest[5]=param[5][n];
														paramBest[6]=param[6][o];
														paramBest[7]=param[7][p];
														paramBest[8]=param[8][q];
														paramBest[9]=param[9][r];
														paramBest[10]=6;

													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}

		sz.simulate(paramBest[0], paramBest[1], paramBest[2], paramBest[3], paramBest[4], paramBest[5], paramBest[6], paramBest[7], paramBest[8], paramBest[9], paramBest[10], true, true);
		sz.getFenster().showResults(sz.getBoerse().getGewinne(),sz.getBoerse().getGewinne()/sz.getBoerse().getEnergieGes(),paramBest[0]+paramBest[1]+paramBest[2],
				paramBest[3],paramBest[4]+paramBest[5]+paramBest[6],paramBest[7],paramBest[8],paramBest[9]);
		//System.out.println("Thread "+t);
		for(int i=0;i<paramBest.length;i++) {
			System.out.println(paramBest[i]);
		}
		System.out.println(kostenAbschaetzen(paramBest[0], paramBest[1], paramBest[2], paramBest[3], paramBest[4], paramBest[5], paramBest[6], paramBest[7], paramBest[8], paramBest[9]));
		//System.out.println(sz.simulate(paramBest[t][0], paramBest[t][1], paramBest[t][2], paramBest[t][3], paramBest[t][4], paramBest[t][5], paramBest[t][6], paramBest[t][7], paramBest[t][8], paramBest[t][9], paramBest[t][10], false, true));
		//System.out.println();

		long duration = System.currentTimeMillis() - starttime;
		System.out.println("Die Berechnung hat " + (duration / 1000.0) + " Sekunden gedauert");
	}

	/**
	 * @param all int parameters appearing in the GUI
	 * @return estimated costs
	 */
	private static double kostenAbschaetzen(int anlagenTyp1, int anlagenTyp2, int anlagenTyp3, int freiflaechen, int batteriespeicher1, int batteriespeicher2, int druckluftspeicher, int brennstoffzellen, int elektrolyseure, int kavernen) {
		double kosten = 0;
		double erloese=0;
		kosten+=anlagenTyp1*3600.0*(1150+387+54*20);
		kosten+=anlagenTyp2*4200.0*(1150+387+54*20);
		kosten+=anlagenTyp3*3500.0*(1150+387+54*20);
		kosten+=freiflaechen*861500.0*1.2;
		kosten+=brennstoffzellen*1000000.0;
		kosten+=kavernen*(16950000+20000000);
		kosten+=elektrolyseure*639000.0;
		kosten+=batteriespeicher1*1336776.0;
		kosten+=batteriespeicher2*4107720.0;
		kosten+=druckluftspeicher*181000000.0;
		kosten+=116800000;
		System.out.println("Kosten: "+kosten);
		erloese+=20*(1.04*10000000.0*anlagenTyp1+1.12*10000000.0*anlagenTyp2+1.04*10000000.0*anlagenTyp3/*+0.91*1000000.0*freiflaechen*/-26870000000.0/*Verbrauch*/+24700000000.0/*Bestand*/)*0.75*0.062;
		System.out.println("Erlöse Spot: "+erloese);
		erloese+=29121000000.0;
		System.out.println("Erlöse gesamt: "+erloese);
		return kosten-erloese;
	}
	public static void main(String[] args) {
		System.out.println("Gesamt: "+kostenAbschaetzen(0,0,1450,10000,0,0,0,4385,4200,25));
	}
}

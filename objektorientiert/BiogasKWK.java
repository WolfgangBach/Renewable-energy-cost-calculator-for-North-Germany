package objektorientiert;
public class BiogasKWK {
	
	private double instLeistungEff;
	
	public BiogasKWK(double instLeistungEff) {
		this.instLeistungEff=instLeistungEff;
	}
	public double leistung() {
		return instLeistungEff; //basiert auf j�hrlicher Erzeugung von 3021 GWh
	}
}

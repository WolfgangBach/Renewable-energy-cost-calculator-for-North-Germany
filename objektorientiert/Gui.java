package objektorientiert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * GUI bekommt eingabe vom User, was neu gebaut wird und übergibt Werte an Szenario
 * Public funktionen: Getter für Parameter
 */
public class Gui extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;

	private Szenario szenario;
	private Bilanz bilanz; 
	private PlotFenster pf;

	private JMenuBar bar;
	private JMenu menu;
	private JMenuItem lastHH;
	private JMenuItem lastSH;
	private JMenuItem wind;
	private JMenuItem boerse;
	private JMenuItem globalstrahlung;

	private JPanel[] layoutPanel;
	private JRadioButton calcAuto;
	private JRadioButton calcManuell;
	private JCheckBox pumpspeicherNeu;
	private JCheckBox dsmJa;
	private JTextField anlagenTyp1; 
	private JTextField anlagenTyp2;
	private JTextField anlagenTyp3;
	private JTextField batteriespeicher1;
	private JTextField batteriespeicher2;
	private JTextField druckluftspeicher;
	private JTextField freiflaechen;
	private JTextField brennstoffzellen;
	private JTextField elektrolyseure;
	private JTextField kavernen;
	private JButton berechne;
	private JButton speichern;
	private JButton laden;
	private JButton weaInfo1;
	private JButton weaInfo2;
	private JButton weaInfo3;
	private JButton pvInfo;
	private JButton liInfo; //Li-batterien 
	private JButton rfInfo; //RedoxFlow Batterien 
	private JButton dlsInfo;   //Druckluftspeicher
	private JButton pskInfo; 
	private JButton bszInfo; 
	private JButton elekInfo; 
	private JButton kavInfo; 
	private JButton dsmInfo; 

	private JTable table;
	private JLabel capexGesamt;
	private JLabel opexGesamt;
	private JLabel ErloeseGesamt;

	private int leftborder;
	private File dir;

	private String[] infoString= {
			"Nennleistung 3.600,0 kW\r\n" + 
					"Einschaltgeschwindigkeit: 3,0 m/s\r\n" + 
					"Nenngeschwindigkeit: 12,0 m/s\r\n" + 
					"Abschaltgeschwindigkeit: 25,0 m/s\r\n" + 
					"Windklasse (IEC): IIa\r\n" + 
					"Capex: 5,425 / 5,641 Mio. € je nach Nabenhöhe\r\n" + 
					"Opex: 194.400€ pro Jahr \r\n" + 
					"Nabenhöhe: 106 m/ 141 m\r\n",
			"Nennleistung: 4.200,0 kW\r\n" + 
			"Einschaltgeschwindigkeit: 3,0 m/s\r\n" + 
			"Nenngeschwindigkeit: 13,5 m/s\r\n" + 
			"Abschaltgeschwindigkeit: 34,0 m/s\r\n" + 
			"Windklasse (IEC): IIa\r\n" + 
			"Capex: 6,329 / 6,581 Mio. € je nach Nabenhöhe\r\n" + 
			"Opex:226.800€ pro Jahr \r\n" + 
			"Nabenhöhe: 135m /144m\r\n",
			
			"Nennleistung: 3.450,0kW\r\n" + 
					"Einschaltgeschwindigkeit: 3,0 m/s\r\n" + 
					"Nenngeschwindigkeit: 12,5 m/s\r\n" + 
					"Abschaltgeschwindigkeit: 25,0 m/s\r\n" + 
					"Windklasse (IEC): S\r\n" + 
					"Capex: 5,199 / 5,406 Mio. €\r\n" + 
					"Opex: 193.200€ pro Jahr\r\n" + 
					"Nabenhöhe:  116.5m / 140m \r\n",
			
			"Fläche: 1 ha\r\n" + 
			"Leistung: 0,9 MWp\r\n" + 
			"Investitionskosten: 861.500€\r\n" + 
			"Modul: AXIpower AC-270-280P/60S\r\n" +
			"Kosten pro Jahr: 8.615€\r\n",
			"Kenndaten einer Einheit:\r\n"
					+ "Speicherkapazität: 1000kWh\r\n"
					+ "el. Nennleistung: 775kW\r\n"
					+ "Wirkungsgrad: 95%\r\n"
					+ "Modulgröße: 43m²\r\n"
					+ "CAPEX: 287 024€\r\n"
					+ "OPEX jährlich: 10 850€\r\n",
			"Kenndaten einer Einheit:\r\n"
					+ "Speicherkapazität: 1000kWh\r\n"
					+ "el. Nennleistung:750kW\r\n"
					+ "Wirkungsgrad: 75%\r\n"
					+ "Modulgröße: 65m^2\r\n"
					+ "CAPEX: 258 225€\r\n"
					+ "OPEX jährlich: 10 500€\r\n",
			"Kenndaten einer Einheit:\r\n"
					+ "Speicherkapazität: 1000MWh\r\n"
					+ "el. Nennleistung: 200MW\r\n"
					+ "Wirkungsgrad: 70%\r\n"
					+ "Modulgröße: 5,6 ha\r\n"
					+ "CAPEX: 120 194 735€ (Netzanschlusskosten nicht inbegriffen*)\r\n"
					+ "          Anbindungskosten je Standort: a) 1320000€, b) 1800000€, c) 2400000€, d) 4200000€\r\n"
					+ "OPEX jährlich: 10 850€ (+Verschleißkosten 2€/MWh el)\r\n",
			"Kenndaten:\r\n"
					+ "Speicherkapazität: 3500MWh\r\n"
					+ "el. Nennleistung: 70MW\r\n"
					+ "Wirkungsgrad: 70%\r\n"
					+ "CAPEX: 100 Mio.€ \r\n"
					+ "OPEX jährlich: 840 000 € \r\n",
			"Kenndaten einer Einheit:\r\n"
					+"Name: Ballard's ClearGen 1MW\r\n"
					+"el. Nennleistung: 1MW\r\n"
					+"Wirkungsgrad: 40%\r\n"
					+"CAPEX: 500 000€\r\n"
					+"OPEX jährlich: 25 000€\r\n"
					+"Fläche: 50 m²\r\n",
			"Kenndaten einer Einheit:\r\n"
					+"Name: H-TEC SERIES-ME: ME 450/1400\r\n"
					+"el. Nennleistung: 1MW\r\n"
					+"Wirkungsgrad: 74%\r\n"
					+"CAPEX: 450 000€\r\n"
					+"OPEX jährlich: 9 450€\r\n"
					+"Fläche: 50 m²\r\n",
			"Kenndaten einer Einheit:\r\n"
					+"Wasserstoff-Kavernenspeicher nach Vorbild Erdgaskaverne Kiel-Rönne\r\n"
					+"Kapazität: 100 GWh\r\n"
					+"CAPEX: 16,95 Mio. €\r\n"
					+"OPEX jährlich: 423 750 € + 2 €/umgesetzte MWh\r\n"
					+"Fläche: 2,35 ha\r\n",
			"Die Lastmanagement Potentiale Schleswig-Holsteins \r\n"
					+"und Hamburgs wurden mit 1041 MW bewertet. \r\n"
					+"Die Verschobene Last wird binnen 24 Stunden ausgeglichen. \r\n",};

	/**
	 * 
	 */
	public Gui() {
		super("Energiewende-Simulator 2020");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container c = getContentPane();
		c.setBackground(Color.WHITE);

		layoutPanel = new JPanel[5];
		for (int i = 0; i < layoutPanel.length; i++) {
			layoutPanel[i] = new JPanel();

		}

		GridBagLayout gridbag = new GridBagLayout();
		gridbag.columnWidths 
		= new int[] { 540};
		gridbag.rowHeights   
		= new int[] { 50,550, 200,50};
		c.setLayout(gridbag);

		mainLayoutConstruct(gridbag, layoutPanel[0], 0, 0, 1, 1);
		mainLayoutConstruct(gridbag, layoutPanel[1], 0, 1, 1, 1);
		//        mainLayoutConstruct(gridbag, layoutPanel[2], 1, 1, 1, 2);
		mainLayoutConstruct(gridbag, layoutPanel[3], 0, 2, 1, 2);
		//        mainLayoutConstruct(gridbag, layoutPanel[4], 1, 3, 1, 1);

		buildMenu();

		//p[1]
		//JPanel mainPanel2 = new JPanel(new BorderLayout());
		//JPanel overMainPanel1 = new JPanel(new GridLayout(6,0));
		JPanel mainPanel1 = new JPanel(new GridLayout(19,0));
		JPanel subPanel1 = new JPanel(new BorderLayout());
		JPanel subPanel2 = new JPanel(new BorderLayout());
		JPanel subPanel3 = new JPanel(new BorderLayout());
		JPanel subPanel4 = new JPanel(new BorderLayout());
		JPanel subPanel5 = new JPanel(new BorderLayout());
		JPanel subPanel6 = new JPanel(new BorderLayout());
		JPanel subPanel7 = new JPanel(new BorderLayout());
		JPanel subPanel8 = new JPanel(new BorderLayout());
		JPanel subPanel9 = new JPanel(new BorderLayout());
		JPanel subPanel10 = new JPanel(new BorderLayout());
		JPanel subPanel11 = new JPanel(new BorderLayout());
		JPanel subPanel12 = new JPanel(new BorderLayout());
		JPanel mainPanelTable = new JPanel(new GridLayout(2,0));
		JPanel subPanelTable = new JPanel(new BorderLayout());
		JPanel subPanelTable2 = new JPanel(new GridLayout(0,1));

		ButtonGroup bg = new ButtonGroup();
		bg.add(calcAuto = new JRadioButton("Automatisch günstigste Option berechnen"));
		bg.add(calcManuell= new JRadioButton("Manuell: Was möchten Sie Ausbauen?",true));


		layoutPanel[0].add(new JLabel("Lighthouse International"));	//Panel ganz Oben "Titel"

		mainPanel1.add(calcAuto);
		mainPanel1.add(new JLabel());
		mainPanel1.add(calcManuell);
		subPanel1.add(anlagenTyp1 = new JTextField("0",10),BorderLayout.WEST);
		subPanel1.add(new JLabel("  WEA Typ Nordex N117"));
		subPanel1.add(weaInfo1= new JButton("Info"),BorderLayout.EAST);
		weaInfo1.setContentAreaFilled(false);
		weaInfo1.setBorderPainted(false);
		weaInfo1.setForeground(Color.BLUE);
		mainPanel1.add(subPanel1);
		subPanel2.add(anlagenTyp2 = new JTextField("0",10),BorderLayout.WEST);
		subPanel2.add(new JLabel("  WEA Typ Enercon E-126"));
		subPanel2.add(weaInfo2= new JButton("Info"),BorderLayout.EAST);
		weaInfo2.setContentAreaFilled(false);
		weaInfo2.setBorderPainted(false);
		weaInfo2.setForeground(Color.BLUE);
		mainPanel1.add(subPanel2);
		subPanel3.add(anlagenTyp3 = new JTextField("0",10),BorderLayout.WEST);
		subPanel3.add(new JLabel("  WEA Typ Vestas V117"));
		subPanel3.add(weaInfo3= new JButton("Info"),BorderLayout.EAST);
		weaInfo3.setContentAreaFilled(false);
		weaInfo3.setBorderPainted(false);
		weaInfo3.setForeground(Color.BLUE);
		mainPanel1.add(subPanel3);
		subPanel4.add(freiflaechen = new JTextField("0",10),BorderLayout.WEST);
		subPanel4.add(new JLabel("  PV-Parks à 900 kWp"));
		subPanel4.add(pvInfo= new JButton("Info"),BorderLayout.EAST);
		pvInfo.setContentAreaFilled(false);
		pvInfo.setBorderPainted(false);
		pvInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel4);
		subPanel5.add(batteriespeicher1 = new JTextField("0",10),BorderLayout.WEST);
		subPanel5.add(new JLabel("  LI-Batterien à 1 MWh"));
		subPanel5.add(liInfo= new JButton("Info"),BorderLayout.EAST);
		liInfo.setContentAreaFilled(false);
		liInfo.setBorderPainted(false);
		liInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel5);
		subPanel6.add(batteriespeicher2 = new JTextField("0",10),BorderLayout.WEST);
		subPanel6.add(new JLabel("  Redox-Flow-Batterien à 1 MWh"));
		subPanel6.add(rfInfo= new JButton("Info"),BorderLayout.EAST);
		rfInfo.setContentAreaFilled(false);
		rfInfo.setBorderPainted(false);
		liInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel6);
		subPanel7.add(druckluftspeicher = new JTextField("0",10),BorderLayout.WEST);
		subPanel7.add(new JLabel("  GWh Druckluft-Speicher"));
		subPanel7.add(dlsInfo =new JButton("Info"),BorderLayout.EAST);
		dlsInfo.setContentAreaFilled(false);
		dlsInfo.setBorderPainted(false);
		dlsInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel7);		
		subPanel8.add(pumpspeicherNeu = new JCheckBox("Pumpspeicherkraftwerk Lägerdorf"),BorderLayout.WEST);
		subPanel8.add(pskInfo= new JButton("Info"),BorderLayout.EAST);
		pskInfo.setContentAreaFilled(false);
		pskInfo.setBorderPainted(false);
		pskInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel8);
		subPanel9.add(brennstoffzellen = new JTextField("0",10),BorderLayout.WEST);
		subPanel9.add(new JLabel("  Brennstoffzellen"));
		subPanel9.add(bszInfo= new JButton("Info"),BorderLayout.EAST);
		bszInfo.setContentAreaFilled(false);
		bszInfo.setBorderPainted(false);
		bszInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel9);
		subPanel10.add(elektrolyseure = new JTextField("0",10),BorderLayout.WEST);
		subPanel10.add(new JLabel("  Elektrolyseure"));
		subPanel10.add(elekInfo= new JButton("Info"),BorderLayout.EAST);
		elekInfo.setContentAreaFilled(false);
		elekInfo.setBorderPainted(false);
		elekInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel10);
		subPanel11.add(kavernen = new JTextField("0",10),BorderLayout.WEST);
		subPanel11.add(new JLabel("  Kavernen à 100 GWh"));
		subPanel11.add(kavInfo= new JButton("Info"),BorderLayout.EAST);
		kavInfo.setContentAreaFilled(false);
		kavInfo.setBorderPainted(false);
		kavInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel11);
		subPanel12.add(dsmJa = new JCheckBox("Demandside Management",true),BorderLayout.WEST);
		subPanel12.add(dsmInfo= new JButton("Info"),BorderLayout.EAST);
		dsmInfo.setContentAreaFilled(false);
		dsmInfo.setBorderPainted(false);
		dsmInfo.setForeground(Color.BLUE);
		mainPanel1.add(subPanel12);
		mainPanel1.add(new JLabel());
		mainPanel1.add(berechne = new JButton("Berechnen"));
		berechne.addActionListener(this);
		mainPanel1.add(speichern = new JButton("Speichern"));
		speichern.addActionListener(this);
		mainPanel1.add(laden = new JButton("Laden"));
		laden.addActionListener(this);
		layoutPanel[1].add(mainPanel1);		//Eingabe Panel Mitte Links

		String[][] data =  {{"WEA","0","0","0","0"},  
				{"PV", "0","0","0","0"},
				{"Speicher", "0","0","0","0"},
				{"Brennstoffzellen", "0","0","0","0"},
				{"Elektrolyseure", "0","0","0","0"},
				{"Kaverne", "0","0","0","0"}};

		String[] colheads = { "Position", "Anzahl", "CAPEX in Mio. €", "OPEX in Mio. €","Fläche in ha"};
		table = new JTable(data, colheads);
		table.setEnabled(false);			
		table.setRowSelectionAllowed(false);
		capexGesamt = new JLabel("  CAPEX: 0,0 € gesamt");
		opexGesamt = new JLabel("  OPEX: 0,0 € gesamt jährlich");
		ErloeseGesamt=new JLabel("  Erlöse: 0,0 € gesamt jährlich");
		subPanelTable.add(table.getTableHeader(), BorderLayout.NORTH);
		subPanelTable.add(table, BorderLayout.CENTER);
		subPanelTable2.add(capexGesamt);
		subPanelTable2.add(opexGesamt);
		subPanelTable2.add(ErloeseGesamt);
		mainPanelTable.setMinimumSize(layoutPanel[3].getSize());
		mainPanelTable.add(subPanelTable);
		mainPanelTable.add(subPanelTable2);
		mainPanelTable.setPreferredSize(new Dimension(530, 235));  
		layoutPanel[3].add(mainPanelTable);  
		weaInfo1.addActionListener(this);
		weaInfo2.addActionListener(this);
		weaInfo3.addActionListener(this);
		pvInfo.addActionListener(this);
		liInfo.addActionListener(this);
		rfInfo.addActionListener(this);
		dlsInfo.addActionListener(this);
		pskInfo.addActionListener(this);
		bszInfo.addActionListener(this);
		elekInfo.addActionListener(this);
		kavInfo.addActionListener(this);
		dsmInfo.addActionListener(this);

		pack(); // organise placement
		setSize(560,910);
		setResizable(true);
		setVisible(true); 


	}



	public void setSzenario(Szenario sz) {
		this.szenario= sz;
	}

	public void setbilanz(Bilanz bilanz) {
		this.bilanz= bilanz;
	}

	public int getAnlagenTyp1() {
		try {
			anlagenTyp1.setBackground(Color.white);
			return Integer.parseInt(anlagenTyp1.getText());
		} catch (Exception e1) {
			anlagenTyp1.setBackground(Color.red);
			return Integer.parseInt(anlagenTyp1.getText());
		}
	}

	public int getAnlagenTyp2() {
		try {
			anlagenTyp2.setBackground(Color.white);
			return Integer.parseInt(anlagenTyp2.getText());
		} catch (Exception e2) {
			anlagenTyp2.setBackground(Color.red);
			return Integer.parseInt(anlagenTyp2.getText());
		}
	}

	public int getAnlagenTyp3() {
		try {
			anlagenTyp3.setBackground(Color.white);
			return Integer.parseInt(anlagenTyp3.getText());
		} catch (Exception e3) {
			anlagenTyp3.setBackground(Color.red);
			return Integer.parseInt(anlagenTyp3.getText());
		}
	}

	public int getFreiflaechen() {
		try {
			freiflaechen.setBackground(Color.white);
			return Integer.parseInt(freiflaechen.getText());
		} catch (Exception e4) {
			freiflaechen.setBackground(Color.red);
			return Integer.parseInt(freiflaechen.getText());
		}

	}

	public int getBatteriespeicher1() {
		try {
			batteriespeicher1.setBackground(Color.white);
			return Integer.parseInt(batteriespeicher1.getText());
		} catch (Exception e5) {
			batteriespeicher1.setBackground(Color.red);
			return Integer.parseInt(batteriespeicher1.getText());
		}
	}

	public int getBatteriespeicher2() {
		try {
			batteriespeicher2.setBackground(Color.white);
			return Integer.parseInt(batteriespeicher2.getText());
		} catch (Exception e6) {
			batteriespeicher2.setBackground(Color.red);
			return Integer.parseInt(batteriespeicher2.getText());
		}
	}

	public int getDruckluftspeicher() {
		try {
			druckluftspeicher.setBackground(Color.white);
			return Integer.parseInt(druckluftspeicher.getText());
		} catch (Exception e7) {
			druckluftspeicher.setBackground(Color.red);
			return Integer.parseInt(druckluftspeicher.getText());
		}
	}


	public int getElektrolyseure() {
		try {
			elektrolyseure.setBackground(Color.white);
			return Integer.parseInt(elektrolyseure.getText());
		} catch (Exception e8) {
			elektrolyseure.setBackground(Color.red);
			return Integer.parseInt(elektrolyseure.getText());
		}
	}

	public int getBrennstoffzellen() {
		try {
			brennstoffzellen.setBackground(Color.white);
			return Integer.parseInt(brennstoffzellen.getText());
		} catch (Exception e9) {
			brennstoffzellen.setBackground(Color.red);
			return Integer.parseInt(brennstoffzellen.getText());
		}
	}

	public int getKavernen() {
		try {
			kavernen.setBackground(Color.white);
			return Integer.parseInt(kavernen.getText());
		} catch (Exception e10) {
			kavernen.setBackground(Color.red);
			return Integer.parseInt(kavernen.getText());
		}
	}

	/**
	 * @return true if Lägerdorf is selected
	 */
	public boolean getPumpspeicherNeu() {
		return  pumpspeicherNeu.isSelected();
	}

	/**
	 * @return true if DSM is selected
	 */
	public boolean getDsm() {
		return dsmJa.isSelected();
	}

	//Layout
	private void mainLayoutConstruct(GridBagLayout gridbag, 
			JPanel layoutPanel, 
			int x, int y,
			int w, int h) {
		GridBagConstraints constr = new GridBagConstraints();
		constr.insets = new Insets(2, 2, 2, 2); // gaps
		constr.gridx = x;
		constr.gridy = y;
		constr.gridwidth = w;
		constr.gridheight = h;
		constr.fill = GridBagConstraints.BOTH;
		if (x == 1) {
			constr.weightx = 1;
		} else {
			constr.weightx = 0;
		}
		constr.weighty = 1; 
		gridbag.setConstraints(layoutPanel, constr); 
		add(layoutPanel);

	}

	/**
	 * @param erloese Gewinne auf dem Strommarkt
	 * @param preis erzielter Durchschnittspreis
	 * @param wea sum of WEA numbers inserted by user
	 * @param pv number inserted by user
	 * @param speicher sum of storage numbers inserted by user
	 * @param bsz number inserted by user
	 * @param elek number inserted by user
	 * @param kav number inserted by user
	 */
	public void showResults(Double erloese,Double preis,Integer wea,Integer pv,Integer speicher,Integer bsz,Integer elek,Integer kav) {//WB: Variable der jeweiligen Anzahl fehlen noch 
		double capex = szenario.getEp().invKostenWeaGes()+szenario.getEp().invKostenPvGes()+szenario.getSp().invKosten()+szenario.getEp().invKostenGtpGes()+szenario.getVp().invKostenPtg()+szenario.getEp().getKav().invKosten();
		DecimalFormat d= new DecimalFormat("0.000");		
		double opex = szenario.getEp().bKostenWeaGes()+szenario.getEp().bKostenPvGes()+szenario.getSp().bKosten()+szenario.getEp().bKostenGtpGes()+szenario.getVp().bKostenPtg()+szenario.getEp().getKav().bKosten();		
		double erl=erloese/1000000;		
		capexGesamt.setText("  CAPEX: "+d.format(capex)+" Mio. € gesamt    Kosten nach 20 Jahren: "+d.format(capex+20*(opex-erl))+"Mio. €");
		opexGesamt.setText("  OPEX: "+d.format(opex)+" Mio. € gesamt jährlich");
		ErloeseGesamt.setText("  Erlöse: "+d.format(erl)+" Mio. € gesamt jährlich, Durchschnittspreis: "+d.format(preis)+" €/kWh");
		table.setValueAt(wea.toString(), 0, 1);
		table.setValueAt(pv.toString(), 1, 1);
		table.setValueAt(speicher.toString(), 2, 1);
		table.setValueAt(bsz.toString(), 3, 1);
		table.setValueAt(elek.toString(), 4, 1);
		table.setValueAt(kav.toString(), 5, 1);
		table.setValueAt(d.format(szenario.getEp().invKostenWeaGes()), 0, 2);
		table.setValueAt(d.format(szenario.getEp().invKostenPvGes()), 1, 2);
		table.setValueAt(d.format(szenario.getSp().invKosten()), 2, 2);
		table.setValueAt(d.format(szenario.getEp().invKostenGtpGes()), 3, 2);
		table.setValueAt(d.format(szenario.getVp().invKostenPtg()), 4, 2);
		table.setValueAt(d.format(szenario.getEp().getKav().invKosten()), 5, 2);
		table.setValueAt(d.format(szenario.getEp().bKostenWeaGes()), 0, 3);
		table.setValueAt(d.format(szenario.getEp().bKostenPvGes()), 1, 3);
		table.setValueAt(d.format(szenario.getSp().bKosten()), 2, 3);
		table.setValueAt(d.format(szenario.getEp().bKostenGtpGes()), 3, 3);
		table.setValueAt(d.format(szenario.getVp().bKostenPtg()), 4, 3);
		table.setValueAt(d.format(szenario.getEp().getKav().bKosten()), 5, 3);
		table.setValueAt(d.format(szenario.getEp().flaecheWeaGes()), 0, 4);
		table.setValueAt(d.format(szenario.getEp().flaechePvGes()), 1, 4);
		table.setValueAt(d.format(szenario.getSp().flaeche()), 2, 4);
		table.setValueAt(d.format(szenario.getEp().flaecheGtp()), 3, 4);
		table.setValueAt(d.format(szenario.getVp().flaechePtg()), 4, 4);
		table.setValueAt(d.format(szenario.getEp().getKav().flaeche()), 5, 4);

		//System.out.println(bilanz==null);
		pf = new PlotFenster(bilanz,leftborder);
		//System.out.println(Integer.parseInt(druckluftspeicher.getText()));
	}

	private File chooseFile() {
		JFileChooser fc = new JFileChooser();
		fc.setCurrentDirectory(new File(".")); 
		fc.setFileSelectionMode(
				JFileChooser.FILES_ONLY);
		fc.setMultiSelectionEnabled(false);

		if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) { // user pressed OK
			String fname = fc.getSelectedFile().getAbsolutePath();
			File d = new File(fname);
			System.out.println("File "+d);
			return d;
		}
		return null;
	}

	private void buildMenu() {
		bar = new JMenuBar();
		setJMenuBar(bar);
		menu= new JMenu("Datenquellen");
		lastHH=new JMenuItem("Last HH");
		lastSH=new JMenuItem("Last SH");
		wind= new JMenuItem("Windgeschwindigkeit");
		boerse=new JMenuItem("Strombörse");
		globalstrahlung= new JMenuItem("Globalstrahlung");
		lastHH.addActionListener(this);
		lastSH.addActionListener(this);
		wind.addActionListener(this);
		boerse.addActionListener(this);
		globalstrahlung.addActionListener(this);

		menu.add(lastHH);
		menu.add(lastSH);
		menu.add(wind);
		menu.add(boerse);
		menu.add(globalstrahlung);

		bar.add(menu);
	}


	@Override
	public void actionPerformed(ActionEvent e) {


		if(e.getSource()== weaInfo1) {
			JOptionPane.showMessageDialog(this, infoString[0],"Nordex N117",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== weaInfo2) {
			JOptionPane.showMessageDialog(this, infoString[1],"Enercon E-126",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== weaInfo3) {
			JOptionPane.showMessageDialog(this, infoString[2],"Vestas V117",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== pvInfo) {
			JOptionPane.showMessageDialog(this, infoString[3],"PV-Parks",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== liInfo) {
			JOptionPane.showMessageDialog(this, infoString[4],"Litium-Ionen Akkumulatoren",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== rfInfo) {
			JOptionPane.showMessageDialog(this, infoString[5],"Redox-Flow Akkumulatoren",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== dlsInfo) {
			JOptionPane.showMessageDialog(this, infoString[6],"Druckluftspeicher",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== pskInfo) {
			JOptionPane.showMessageDialog(this, infoString[7],"Pumpspeicherkraftwerk Lägerdorf ",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== bszInfo) {
			JOptionPane.showMessageDialog(this, infoString[8],"Brennstoffzellen",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== elekInfo) {
			JOptionPane.showMessageDialog(this, infoString[9],"Elektrolyseure",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== kavInfo) {
			JOptionPane.showMessageDialog(this, infoString[10],"Kavernen",JOptionPane.QUESTION_MESSAGE);
		}
		if(e.getSource()== dsmInfo) {
			JOptionPane.showMessageDialog(this, infoString[11],"Demandside Managment",JOptionPane.QUESTION_MESSAGE);
		}


		if(e.getSource()==berechne) {
			try {	
				if(calcManuell.isSelected()) {
					try {
						szenario.notifyChange(); 
						pf.getPlotter().repaint();
					} catch (IOException e8) {
						e8.printStackTrace();
					}
				}
				if(calcAuto.isSelected()) {
					new Optimizer(szenario);
				}
			} catch (NumberFormatException e9) {
				System.out.println("In Gui markierte Eingabe vom System nicht verwendbar!");
				//				throw new NumberFormatException();
			}

		}
		if(e.getSource() == speichern) {
			dir=chooseFile();
			try {
				FileOutputStream out = new FileOutputStream(dir);   //open the file 
				ObjectOutputStream schreiber = new ObjectOutputStream(out); //save the data 
				//HD
				schreiber.writeObject(pumpspeicherNeu.isSelected());
				schreiber.writeObject(dsmJa.isSelected());
				schreiber.writeObject(anlagenTyp1.getText());
				schreiber.writeObject(anlagenTyp2.getText());
				schreiber.writeObject(anlagenTyp3.getText());
				schreiber.writeObject(freiflaechen.getText());
				schreiber.writeObject(batteriespeicher1.getText());
				schreiber.writeObject(batteriespeicher2.getText());
				schreiber.writeObject(druckluftspeicher.getText());
				schreiber.writeObject(brennstoffzellen.getText());
				schreiber.writeObject(elektrolyseure.getText());
				schreiber.writeObject(kavernen.getText());
				schreiber.flush();
				schreiber.close();
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == laden) { 
			dir=chooseFile();
			System.out.println("Dir"+dir.toString());
			try {
				FileInputStream in = new FileInputStream(dir);  //open the file 
				ObjectInputStream lesen = new ObjectInputStream(in); //get the data 
				//HD
				pumpspeicherNeu.setSelected((boolean)lesen.readObject());
				dsmJa.setSelected((boolean)lesen.readObject());
				anlagenTyp1.setText((String)lesen.readObject());
				anlagenTyp2.setText((String)lesen.readObject());
				anlagenTyp3.setText((String)lesen.readObject());
				freiflaechen.setText((String)lesen.readObject());
				batteriespeicher1.setText((String)lesen.readObject());
				batteriespeicher2.setText((String)lesen.readObject());
				druckluftspeicher.setText((String)lesen.readObject());
				brennstoffzellen.setText((String)lesen.readObject());
				elektrolyseure.setText((String)lesen.readObject());
				kavernen.setText((String)lesen.readObject());
				lesen.close();

			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		if(e.getSource() == lastHH) { 
			VerbraucherPortfolio.srcHH=chooseFile();
		}
		if(e.getSource() == lastSH) { 
			VerbraucherPortfolio.srcSH=chooseFile();
		}
		if(e.getSource() == wind) { 
			Landkreise.srcWind=chooseFile();
			System.out.println("Wind SRC "+Landkreise.srcWind);
			Landkreise.get().changed();
		}
		if(e.getSource() == boerse) { 
			Boerse.srcBoerse=chooseFile();
		}
		if(e.getSource() == globalstrahlung) { 
			Strahlungsmessstation.srcGlobal=chooseFile();
			Strahlungsmessstation.get().changed();
		}
	}


	public static void main(String[] args){
		Gui gui = new Gui();
		Szenario sz= new Szenario(gui); 
		gui.setSzenario(sz);   
	}

}

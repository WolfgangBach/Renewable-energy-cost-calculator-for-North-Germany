package objektorientiert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/** Fenster mit Plottergui und Bearbeitungsmöglichkeiten
 *
 */
public class PlotFenster extends JFrame implements ChangeListener{
	private int leftborder;
	private PlotterGui plotter;
	private JPanel mainPanel2;
	private JSlider scrollSlider;
	private JPanel subPanelplotter1;
	private JCheckBox flaechenAusmalen;
	private JCheckBox speicherAnzeigen;
	private JCheckBox erzeugungKorrigieren;
	private JCheckBox verbrauchKorrigieren;
	private JCheckBox kaverneStatus;
	
	public PlotterGui getPlotter() {
		return plotter;
	}
	
	public PlotFenster(Bilanz bilanz, int leftborder) {
		super("Energiebilanz");
		this.leftborder=leftborder;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container c = getContentPane();
		c.setBackground(Color.white);

		plotter= new PlotterGui(bilanz, leftborder, this);
        mainPanel2 = new JPanel(new BorderLayout());
        speicherAnzeigen=new JCheckBox("Speicherstand",true);
        speicherAnzeigen.addChangeListener(this);
        flaechenAusmalen=new JCheckBox("durch Speicher kompensierte Energie",false);
        flaechenAusmalen.addChangeListener(this);
        verbrauchKorrigieren=new JCheckBox("Verbrauch mit DSM",false);
        verbrauchKorrigieren.addChangeListener(this);
        erzeugungKorrigieren=new JCheckBox("Erzeugung mit Speichern und Power-To-Gas",false);
        erzeugungKorrigieren.addChangeListener(this);
        kaverneStatus=new JCheckBox("Kaverne",false);
        kaverneStatus.addChangeListener(this);
        subPanelplotter1= new JPanel(new FlowLayout());
        subPanelplotter1.add(speicherAnzeigen);
        subPanelplotter1.add(flaechenAusmalen);	
        subPanelplotter1.add(verbrauchKorrigieren);
        subPanelplotter1.add(erzeugungKorrigieren);
        subPanelplotter1.add(kaverneStatus);
        mainPanel2.add(subPanelplotter1,BorderLayout.NORTH);							
        mainPanel2.add(plotter,BorderLayout.CENTER);
        scrollSlider = new JSlider(JSlider.HORIZONTAL,0,8760-1000,0); 
        scrollSlider.addChangeListener(this);
        mainPanel2.add(scrollSlider,BorderLayout.SOUTH);
        c.add(mainPanel2);

		pack(); // organise placement
		setSize(1075,650);
		setLocation(410,100);
		setResizable(false);
		setVisible(true); 
	}
	
	public boolean getFlaechenAusmalen() {
		return flaechenAusmalen.isSelected();
	}
	
	public boolean getSpeicherAnzeigen() {
		return speicherAnzeigen.isSelected();
	}
	
	public boolean getErzeugungKorrigieren() {
		return erzeugungKorrigieren.isSelected();
	}
	
	public boolean getVerbrauchKorrigieren() {
		return verbrauchKorrigieren.isSelected();
	}
	
	public boolean getKaverneStatus() {
		return kaverneStatus.isSelected();
	}
	
    @Override
    public void stateChanged(ChangeEvent e) {
        if(e.getSource()==scrollSlider) {

            leftborder=scrollSlider.getValue();
            plotter.setLeftBorder(leftborder);

        }
        plotter.repaint();

    }
}


package objektorientiert;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/** draws the diagram
 *
 */
public class PlotterGui extends JPanel {
	private int leftborder;
	private Bilanz bilanz;
	private PlotFenster pf;
	public PlotterGui(Bilanz bilanz, int leftborder, PlotFenster pf) {
		super();
		this.leftborder=leftborder;
		this.bilanz=bilanz;
		this.pf=pf;
		setBackground(Color.white);
		setPreferredSize(new Dimension(1075,600));
	}
	
	void setLeftBorder(int leftborder) {
		this.leftborder = leftborder;
	}
	
	void setBilanz(Bilanz bz){
	    this.bilanz= bz;
	   }

	public void paintComponent(Graphics g) {
		super.paintComponent(g); // never forget
		int margin=20; // pixel Rand
		//System.out.println(bilanz==null);
		int zoom1=(int)(500/(bilanz.getMaxErzeugung()/1000000)); // pixel/GW
		//System.out.println(zoom1);
		int max=Math.round(600/zoom1);
		//System.out.println(max);
		int zoom2=4; // pixel/TWh
				
		Dimension d = getSize();
		String[] month = {"Jan","Feb","Mar","Apr","Mai","Jun","Jul","Aug","Sep","Okt","Nov","Dez"};
		// draw the axis
		g.drawLine(margin, margin, margin, d.height-margin); //linke y-Achse
		g.drawLine(margin, d.height-margin, d.width-2*margin, d.height-margin); //x-Achse
		for(int i=5;i<=max;i+=5) {
			//System.out.println(d.height-margin-i*zoom1);
			g.drawLine(margin-5, d.height-margin-i*zoom1, margin+5, d.height-margin-i*zoom1); //GW-Markierungen
			g.drawString(i+"", 2, d.height-margin-i*zoom1+5);
		}
		Font std = g.getFont();
		g.setFont(new Font("X",Font.PLAIN,20));
		g.drawString("\u2191 P/GW", margin-5, margin);
		DecimalFormat f=new DecimalFormat("0.00 %");
		g.drawString("Abdeckungsrate: "+f.format(bilanz.getAbdeckung()), margin+100, margin); //Abdeckungsrate anzeigen
		//Legende:
		g.drawLine(margin+350, margin-5, margin+360, margin-5); 
		g.drawString("Erzeugung", margin+365, margin);
		g.setColor(Color.red);
		g.drawLine(margin+470, margin-5, margin+480, margin-5);
		g.setColor(Color.black);
		g.drawString("Verbrauch", margin+485, margin);
		g.setColor(Color.blue);
		g.drawLine(margin+590, margin-5, margin+600, margin-5);
		g.setColor(Color.black);
		g.drawString("Speicherstatus relativ", margin+605, margin);
		g.setColor(Color.green);
		g.drawLine(margin+810, margin-5, margin+820, margin-5);
		g.setColor(Color.black);
		g.drawString("Kaverne relativ", margin+825, margin);
		g.setFont(std);

		int oldx = 0; // previous x in the panel
		int oldy1a = 0; 
		int oldy1b = 0; 
		int oldy2a = 0;
		int oldy2b = 0; 
		int oldy3 = 0; 
		int oldy4 = 0;
		// now draw the function
		for (int x = leftborder; x < leftborder+1000; x++) { 
			int posx = margin+x-leftborder;
			int posy1a;
			int posy1b;
			int posy2a;
			int posy2b;
			posy1a = d.height-margin-(int)bilanz.getErzeugungKorr(x)/(1000000/zoom1)-1;
			posy1b = d.height-margin-(int)bilanz.getErzeugung(x)/(1000000/zoom1); 
			posy2a = d.height-margin-(int)bilanz.getVerbrauchKorr(x)/(1000000/zoom1);
			posy2b = d.height-margin-(int)bilanz.getVerbrauch(x)/(1000000/zoom1);
			int posy3 = d.height-margin-(int)(bilanz.getStatus(x)*(d.height-3*margin));
			//System.out.println(x+" "+posy3+" "+Bilanz.getStatus(x)+" "+Bilanz.getAbgedeckt(x));
			int posy4 = d.height-margin-(int)(bilanz.getKavStatus(x)*(d.height-3*margin));
			if (x > leftborder) {
				if(pf.getErzeugungKorrigieren()) g.drawLine(oldx,oldy1a,posx,posy1a);
				else g.drawLine(oldx,oldy1b,posx,posy1b);
				g.setColor(Color.red);
				if(pf.getVerbrauchKorrigieren()) g.drawLine(oldx,oldy2a,posx,posy2a);
				else g.drawLine(oldx,oldy2b,posx,posy2b);
				if(pf.getSpeicherAnzeigen()) {
					g.setColor(Color.blue);
					g.drawLine(oldx,oldy3,posx,posy3);
				}
				if(bilanz.getAbgedeckt(x)&&bilanz.getStatus(x)!=1&&pf.getFlaechenAusmalen()) {//durch Speicher kompensierte Flächen markieren
					if(posy1b>posy2b)g.setColor(Color.orange); //Entladung
					else g.setColor(Color.green); //Aufladung
					g.drawLine(posx,posy1b,posx,posy2b);
				}
				if(pf.getKaverneStatus()) {
				g.setColor(Color.green);
				g.drawLine(oldx,oldy4,posx,posy4);
				}
				g.setColor(Color.black);
			}
			// remember last point
			oldx = posx; 
			oldy1a = posy1a;
			oldy1b = posy1b;
			oldy2a = posy2a;
			oldy2b = posy2b;
			oldy3 = posy3;
			oldy4 = posy4;

			if(x % 730 == 0) {
				g.drawString(month[(int)x/730], posx, d.height-5);

			}
		}

	}

}

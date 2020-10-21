package simulation;


import java.awt.Color;

import briques.*;
import briques.DonneesSimulation;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import robots.Robot;
import java.util.LinkedList;




public class Simulateur implements Simulable {

	/** L'interface graphique associée */
    private GUISimulator gui;	

    /** La couleur de dessin du contour */
    private Color contourColor;	
    
    private DonneesSimulation donnees;
    

    
	public Simulateur(GUISimulator gui, Color contourColor, DonneesSimulation donnees) {
		this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.contourColor = contourColor;
        this.donnees = donnees;
        //planCoordinates();
        draw();
	}
	
	public void next() {
		
	}
	
	public void restart() {
		
	}
	
	
	public Color getColorTerrain(NatureTerrain nature) {
		switch (nature) {
		case EAU: return Color.decode("#0a82f4");
		case FORET: return Color.decode("#12a21b");
		case ROCHE: return Color.decode("#bbbab8");
		case HABITAT: return Color.decode("#9b7325");
		default: return Color.decode("#f7e7d8"); //HABITAT_LIBRE
		}
	}
	//Color.decode("#f2ff28")
	
	public Color getColorRobot(Type type) {
		switch (type) {
		case DRONE: return Color.decode("#f53dde");
		case ROUES: return Color.decode("#ba3df5");
		case PATTES: return Color.decode("#ffc5f0");
		default: return Color.decode("#ecc5ff");
		}
	}
	
	public void draw() {
		//attention, un rectangle trac� est d�j� d�cal� par raport au coin, d'ou les calculs un peu zarbi dans les param�tre
		gui.reset();	// clear the window
		Carte carte = this.donnees.getCarte();
		LinkedList<Incendie> incendies = this.donnees.getIncendies();
		LinkedList<Robot> robots = this.donnees.getRobots();
		int taille = Math.min(750,  550)/Math.max(carte.getNbColonnes(), carte.getNbLignes());
		int xMin = taille/2 + 10;
		int yMin = taille/2 + 10;
		
        //gui.addGraphicalElement(new Rectangle(60, 60, this.contourColor, Color.BLACK, 100));
		//
        for (int x=0; x< carte.getNbColonnes(); x ++) {
        	for (int y=0 ; y< carte.getNbLignes(); y ++) {
        		gui.addGraphicalElement(new Rectangle(x*taille +xMin, y*taille + yMin, this.contourColor,  this.getColorTerrain(carte.getCase(y, x).getNature()), taille));
        	}
        }
        
 /*       while (incendies != null) {
        	
        	gui.addGraphicalElement(new Rectangle(incendies.getPosition().getColonne()*taille +xMin,
        											incendies.getPosition().getLigne()*taille + yMin,
        											this.contourColor,
        											Color.decode("#ff0000"),
        											taille/2));
        	incendies = incendies.getSuivant();
        }*/
        
        for (Incendie i : incendies) {
        	gui.addGraphicalElement(new Rectangle(i.getPosition().getColonne()*taille +xMin,
					i.getPosition().getLigne()*taille + yMin,
					this.contourColor,
					Color.decode("#ff0000"),
					taille/2));
        }
        
  /*      while (robots != null) {
        	
        	gui.addGraphicalElement(new Rectangle(robots.getPosition().getColonne()*taille +xMin,
        											robots.getPosition().getLigne()*taille + yMin,
        											this.contourColor,
        											this.getColorRobot(robots.getType()),
        											taille/3));
        	robots = robots.getSuivant();
        }*/
        
        for (Robot r : robots) {
        	gui.addGraphicalElement(new Rectangle(r.getPosition().getColonne()*taille +xMin,
					r.getPosition().getLigne()*taille + yMin,
					this.contourColor,
					this.getColorRobot(r.getType()),
					taille/3));
        }
        //System.out.println("DONNEES : "+this.donnees.getRobots().allToString());
	}
}


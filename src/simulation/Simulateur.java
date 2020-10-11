package simulation;


import java.awt.Color;

import briques.*;
import briques.DonneesSimulation;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;




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
	
	
	public Color getColor(NatureTerrain nature) {
		switch (nature) {
		case EAU: return Color.decode("#0a82f4");
		case FORET: return Color.decode("#12a21b");
		case ROCHE: return Color.decode("#bbbab8");
		case HABITAT: return Color.decode("#9b7325");
		default: return Color.decode("#f7e7d8"); //HABITAT_LIBRE
		}
	}
	//Color.decode("#f2ff28")
	
	public void draw() {
		gui.reset();	// clear the window
		Carte carte = this.donnees.getCarte();

		int xMin = carte.getTailleCases()/2 + 10;
		int yMin = carte.getTailleCases()/2 + 10;
		int taille = carte.getTailleCases();
        //gui.addGraphicalElement(new Rectangle(60, 60, this.contourColor, Color.BLACK, 100));
		//
        for (int x=0; x< carte.getNbColonnes(); x ++) {
        	for (int y=0 ; y< carte.getNbLignes(); y ++) {
        		gui.addGraphicalElement(new Rectangle(x*taille +xMin, y*taille + yMin, this.contourColor,  this.getColor(carte.getCase(y, x).getNature()), taille));
        	}
        }
	}
}


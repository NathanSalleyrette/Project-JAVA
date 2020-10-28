package simulation;


import java.awt.Color;

import briques.*;
import gui.GUISimulator;
import gui.Rectangle;
import gui.Simulable;
import robots.Robot;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import evenements.*;



public class Simulateur implements Simulable {

	/** L'interface graphique associée */
    private GUISimulator gui;	
    /** La couleur de dessin du contour */
    private Color contourColor;
    private DonneesSimulation donnees;
    private long DateSimulation; 
    private LinkedList<Evenement> evenements;
    
    
    
	public Simulateur(GUISimulator gui, Color contourColor, DonneesSimulation donnees) {
		this.gui = gui;
        gui.setSimulable(this);				// association a la gui!
        this.contourColor = contourColor;
        this.donnees = donnees;
        this.evenements = new LinkedList<Evenement>();
        this.DateSimulation = 0;
        //planCoordinates();
        draw();
	}
	
	public DonneesSimulation getDonnees() {
		return this.donnees;
	}
	
	
	
	public void next() {
		IncrementeDate();
		int flag = 0;
		LinkedList<Evenement> copieEvenement = (LinkedList<Evenement>)this.evenements.clone();
		for (Evenement e : copieEvenement) {
			if (e.getDate() == this.DateSimulation) {
				flag = 1;
				e.execute();
				System.out.println(e);
				this.evenements.remove(e);
				
			}
		}
		if (flag == 1) {
			draw();
		}
	}
	
	// Restart pas encore fini, il faut réinitialiser la map
	public void restart() {
		this.DateSimulation = 0;
	}
	
	public void ajouteEvenement(Evenement e) {
		if (e.getDate() >= this.DateSimulation) {
			evenements.add(e);
		}
	}
	
	public void IncrementeDate() {
		DateSimulation += 1;
	}
	
	public long getDateSimulation() {
		return DateSimulation;
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
	
	public void BougerRobotUnitaire(Direction dir, Robot robot) {
		this.ajouteEvenement(new BougerRobotUnitaire(dir, robot, this));
	}
	
	public void BougerRobotUnitaire(Case positionCible, Robot robot) {
		this.ajouteEvenement(new BougerRobotUnitaire(positionCible, robot, this));
	}
	
	public void BougerRobot(Robot robot, Case positionCible) {
		List<Case> chemin = robot.getChemin(positionCible);
		for (Case c : chemin) {
			this.BougerRobotUnitaire(c, robot);
		}
	}
	
	public void EteindreIncendieUnitaire(Incendie incendie, Robot robot) {
		this.ajouteEvenement(new EteindreIncendieUnitaire(incendie, robot, this));
	}
	
	
	public void EteindreIncendie(Incendie incendie, Robot robot) {
		this.ajouteEvenement(new EteindreIncendie(incendie, robot, this));
	}
	
	public void RemplirRobot(Robot robot) {
		this.ajouteEvenement(new RemplirRobot(robot, this));
	}
	
	public void SendMessage(String message) {
		this.ajouteEvenement(new SendMessage(message, this));
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


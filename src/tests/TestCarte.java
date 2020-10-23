package tests;

import java.awt.Color;

import briques.*;
import gui.GUISimulator;
import io.LecteurDonnees;
import robots.*;
import simulation.Simulateur;
import java.util.LinkedList;
import evenements.*;

public class TestCarte {

	public static void main(String[] args) {
		// Test pour la carte et les cases
		Case c = new Case(9,5);
		//System.out.println("case_1" + c);
		Carte ct1 = new Carte(2, 4); //matrice 2x4
		//System.out.println("carte_1" + ct1);
		NatureTerrain m[][] = {
				{NatureTerrain.EAU, NatureTerrain.FORET, NatureTerrain.EAU},
				{NatureTerrain.ROCHE, NatureTerrain.EAU, NatureTerrain.TERRAIN_LIBRE}
		};
		Carte ct2 = new Carte(m);
		//System.out.println("carte_2" + ct2);
		//System.out.println(ct2.getVoisin(ct2.getCase(0, 0), Direction.OUEST));
		
		c = ct2.getCase(0, 0);
		//System.out.println("case_1 : " + c);
		//c.setTerrain(NatureTerrain.FORET);
		//System.out.println("case_1 : " + c);
		//c = ct2.getCase(0, 0);
		//System.out.println("case_1 : " + c);
		
		LinkedList<Robot> robots = new LinkedList<Robot>();
		LinkedList<Incendie> incendies = new LinkedList<Incendie>();
		Drone drone = new Drone(c);
		//c.setTerrain(NatureTerrain.EAU);
		c = ct2.getCase(1, 0);
		Roues roues = new Roues(c);
		robots.add(drone);
		robots.add(roues);
		//drone.pushQueue(roues);
		//c.setTerrain(NatureTerrain.FORET);
		roues.remplirReserve(30000);
		drone.remplirReserve(10);
		//System.out.println(drone.allToString());
		roues.deverserEau(500);
		drone.deverserEau(-10);
		//System.out.println(drone.allToString());
		
		
		c = ct2.getCase(0, 2);
		Incendie incendie1 = new Incendie(c, 50);
		incendies.add(incendie1);
		c = ct2.getCase(0, 0);
		Incendie incendie2 = new Incendie(c, 10);
		incendies.add(incendie2);
		//incendie1.pushQueue(incendie2);
		//System.out.println(incendie1.allToString());
		incendie1.eteindre(60);
		incendie2.eteindre(8);
		//System.out.println(incendie1.allToString());
		
		c = ct2.getCase(0, 2);
		//System.out.println("case_1 : " + c);
		//System.out.println("carte_2 " + ct2);
		
		System.out.println();
		System.out.println();
		DonneesSimulation donnees = new DonneesSimulation(ct2, incendies, robots);
		donnees.bougerRobot(Direction.EST, drone);
		donnees.bougerRobot(Direction.EST, drone);
		donnees.bougerRobot(Direction.SUD, drone);
		donnees.eteindreIncendie(drone, incendie2, 2);
		System.out.println(donnees);
		
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
		Simulateur simulateur = new Simulateur(gui, Color.decode("#ffffff"), donnees);
		
		simulateur.ajouteEvenement(new BougerRobot(1, Direction.EST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(2, Direction.EST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(3, Direction.EST, drone, ct2));
		//simulateur.ajouteEvenement(new BougerRobot(4, Direction.EST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(4, Direction.NORD, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(5, Direction.OUEST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(6, Direction.OUEST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(7, Direction.SUD, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(8, Direction.EST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(9, Direction.EST, drone, ct2));
		//simulateur.ajouteEvenement(new BougerRobot(3, Direction.EST, drone, ct2));
		//simulateur.ajouteEvenement(new BougerRobot(4, Direction.EST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(10, Direction.NORD, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(11, Direction.OUEST, drone, ct2));
		simulateur.ajouteEvenement(new BougerRobot(12, Direction.OUEST, drone, ct2));
		
		
		
		
		
		
		
		
	}

}

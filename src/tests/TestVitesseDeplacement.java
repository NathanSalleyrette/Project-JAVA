package tests;

import java.awt.Color;
import java.util.LinkedList;

import briques.Carte;
import briques.Direction;
import briques.DonneesSimulation;
import briques.Incendie;
import briques.NatureTerrain;
import evenements.BougerRobotUnitaire;
import evenements.RemplirRobot;
import gui.GUISimulator;
import robots.*;
import simulation.Simulateur;

public class TestVitesseDeplacement {
	public static void main(String[] args) {
		
		
		int tailleCases = 1000;
		int longueur = 5;
		NatureTerrain m[][] = new NatureTerrain[5][longueur];
		LinkedList<NatureTerrain> liste = new LinkedList<NatureTerrain>();
		liste.add(NatureTerrain.EAU);
		liste.add(NatureTerrain.FORET);
		liste.add(NatureTerrain.ROCHE);
		liste.add(NatureTerrain.TERRAIN_LIBRE);
		liste.add(NatureTerrain.HABITAT);
		
		for (int lig = 0; lig < 5; lig++) {
			NatureTerrain type = liste.get(lig);
			for (int col = 0; col < longueur; col++) {
				m[lig][col] = type;
			}
		}
	
		Carte carte = new Carte(m, tailleCases);
		
		LinkedList<Robot> robots = new LinkedList<Robot>();
		LinkedList<Incendie> incendies = new LinkedList<Incendie>();
		
		for (int lig = 0; lig < 5; lig++) {
			robots.add(new Chenilles(carte.getCase(lig, 0), carte));
			robots.add(new Drone(carte.getCase(lig, 0), carte));
			robots.add(new Pattes(carte.getCase(lig, 0), carte));
			robots.add(new Roues(carte.getCase(lig, 0), carte));
		}
			
		DonneesSimulation donnees = new DonneesSimulation(carte, incendies, robots);
		
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
		
		
		Simulateur simulateur = new Simulateur(gui, Color.decode("#ffffff"), donnees);
	
		
		for (int col = 1; col < longueur; col++) {
			for (int lig = 0; lig < 5; lig++) {
				//simulateur.BougerRobot(Direction.EST, robots.get(0 + lig*4));
				simulateur.RemplirRobot(robots.get(1 + lig*4));
				simulateur.BougerRobotUnitaire(Direction.EST, robots.get(1 + lig*4));
				//simulateur.BougerRobot(Direction.EST, robots.get(2 + lig*4));
				//simulateur.BougerRobot(Direction.EST, robots.get(3 + lig*4));
			}
		}
	}
}

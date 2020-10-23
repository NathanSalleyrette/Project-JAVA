package tests;


import java.awt.Color;

import briques.*;
import gui.GUISimulator;

import simulation.Simulateur;
import evenements.*;
import java.util.LinkedList;


public class TestPINGPONG {

	public static void main(String[] args) {
		DonneesSimulation donnees = new DonneesSimulation();
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
		Simulateur simulateur = new Simulateur(gui, Color.decode("#ffffff"), donnees);
		
		for (int i = 2; i <=10; i+=2) {
			simulateur.ajouteEvenement(new EvenementMessage(i, "[PING]"));
		}
		
		for (int i = 3; i <= 9; i+=3) {
			simulateur.ajouteEvenement(new EvenementMessage(i, "[PONG]"));
		}
	}

}

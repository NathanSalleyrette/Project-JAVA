package tests;

import briques.Carte;
import briques.Case;
import briques.NatureTerrain;

public class TestCarte {

	public static void main(String[] args) {
		// Test pour la carte et les cases
		Case c = new Case(9,5);
		System.out.println("case_1" + c);
		Carte ct1 = new Carte(2, 4); //matrice 2x4
		System.out.println("carte_1" + ct1);
		NatureTerrain m[][] = {
				{NatureTerrain.EAU, NatureTerrain.FORET, NatureTerrain.EAU},
				{NatureTerrain.ROCHE, NatureTerrain.EAU, NatureTerrain.TERRAIN_LIBRE}
		};
		Carte ct2 = new Carte(m);
		System.out.println("carte_2" + ct2);
		//System.out.println(ct2.getVoisin(ct2.getCase(0, 0), Direction.OUEST));
		
		c = ct2.getCase(0, 0);
		System.out.println("case_1 : " + c);
		c.setTerrain(NatureTerrain.FORET);
		System.out.println("case_1 : " + c);
		c = ct2.getCase(0, 0);
		System.out.println("case_1 : " + c);
		
		/*
		Drone drone = new Drone(c);
		c.setTerrain(NatureTerrain.EAU);
		Roues roues = new Roues(c);
		drone.pushQueue(roues);
		c.setTerrain(NatureTerrain.FORET);
		roues.remplirReserve(30000);
		drone.remplirReserve(4);
		//System.out.println(drone.allToString());
		roues.deverserEau(500);
		drone.deverserEau(-10);
		//System.out.println(drone.allToString());
		*/
		
		/*
		Incendie incendie1 = new Incendie(c, 50);
		Incendie incendie2 = new Incendie(c, 10);
		incendie1.pushQueue(incendie2);
		System.out.println(incendie1.allToString());
		incendie1.eteindre(60);
		incendie2.eteindre(4);
		System.out.println(incendie1.allToString());
		*/
		
		c = ct2.getCase(0, 2);
		System.out.println("case_1 : " + c);
		System.out.println("carte_2 " + ct2);
		
		
		
		
	}

}

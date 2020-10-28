package tests;

import java.awt.Color;
import java.util.*;
import pluscourtchemin.*;
import briques.*;
import gui.GUISimulator;
import robots.*;
import simulation.Simulateur;
import java.util.LinkedList;
import evenements.*;

public class TestCarte {

	public static void main(String[] args) {
		// Test pour la carte et les cases
		//Case c = new Case(9,5);
		Case c;
		//System.out.println("case_1" + c);
		//Carte ct1 = new Carte(2, 4); //matrice 2x4
		//System.out.println("carte_1" + ct1);
		NatureTerrain m[][] = {
				{NatureTerrain.EAU, NatureTerrain.FORET, NatureTerrain.EAU},
				{NatureTerrain.ROCHE, NatureTerrain.HABITAT, NatureTerrain.TERRAIN_LIBRE}
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
		System.out.println("voisins case 0, 0 : " + ct2.getVoisins(ct2.getCase(0, 0)));
		
		LinkedList<Robot> robots = new LinkedList<Robot>();
		LinkedList<Incendie> incendies = new LinkedList<Incendie>();
		Drone drone = new Drone(c, ct2);
		//c.setTerrain(NatureTerrain.EAU);
		c = ct2.getCase(1, 0);
		Roues roues = new Roues(c, ct2);
		robots.add(drone);
		robots.add(roues);
		//drone.pushQueue(roues);
		//c.setTerrain(NatureTerrain.FORET);
		roues.remplirReserve();
		drone.remplirReserve();
		//System.out.println(drone.allToString());
		roues.deverserEau(500);
		drone.deverserEau(-10);
		//System.out.println(drone.allToString());
		
		
		c = ct2.getCase(0, 2);
		Incendie incendie1 = new Incendie(c, 50, incendies);
		c = ct2.getCase(0, 0);
		Incendie incendie2 = new Incendie(c, 10, incendies);
		//incendie1.pushQueue(incendie2);
		//System.out.println(incendie1.allToString());
		incendie1.eteindre(60);
		incendie2.eteindre(8);
		//System.out.println(incendie1.allToString());
		
		c = ct2.getCase(0, 2);
		//System.out.println("case_1 : " + c);
		//System.out.println("carte_2 " + ct2);
		System.out.println("carte_2 " + drone.getCarte());
		
		System.out.println();
		System.out.println();
		DonneesSimulation donnees = new DonneesSimulation(ct2, incendies, robots);
		/*
		donnees.deplacer(Direction.EST, drone);
		donnees.bougerRobot(Direction.EST, drone);
		donnees.bougerRobot(Direction.SUD, drone);
		*/
		//donnees.eteindreIncendie(drone, incendie2, 2);
		System.out.println(donnees);
		
		GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
		Simulateur simulateur = new Simulateur(gui, Color.decode("#ffffff"), donnees);
		
		/* ATTENTION : on ne peut pas definir plusieur bouger case pour un meme robot d'affili=�e : le robot n'a pas encore bouger au momem
		 * ou le deuxi�me mouvement et defenit, ses voisins disponible ont changer
		 * DONC le test suivant est faux, pour le faire marcher j'ai bidouill� les dates
		 */
		
		
		
		int V = 6; 
        int source = 4; 
  
        // Adjacency list representation of the  
        // connected edges 
        List<List<Noeud> > adj = new ArrayList<List<Noeud> >(); 
  
        // Initialize list for every Noeud 
        for (int i = 0; i < V; i++) { 
            List<Noeud> item = new ArrayList<Noeud>(); 
            adj.add(item); 
        } 
  
        // Inputs for the DPQ graph 
        adj.get(0).add(new Noeud(10, ct2.getCase(0, 1), 1)); 
        adj.get(0).add(new Noeud(10, ct2.getCase(1, 0), 3)); 
        
        //adj.get(1).add(new Noeud(20, ct2.getCase(0, 0), 0)); 
        //adj.get(1).add(new Noeud(20, ct2.getCase(1, 1), 4)); 
        //adj.get(1).add(new Noeud(20, ct2.getCase(0, 2), 2)); 
       
        adj.get(2).add(new Noeud(Integer.MAX_VALUE, ct2.getCase(0, 1), 1)); 
        adj.get(2).add(new Noeud(Integer.MAX_VALUE, ct2.getCase(0, 1), 5)); 
        
        adj.get(3).add(new Noeud(5, ct2.getCase(0, 0), 0)); 
        adj.get(3).add(new Noeud(5, ct2.getCase(1, 1), 4)); 
        
        adj.get(4).add(new Noeud(1, ct2.getCase(0, 1), 1)); 
        adj.get(4).add(new Noeud(1, ct2.getCase(1, 0), 3)); 
        adj.get(4).add(new Noeud(1, ct2.getCase(1, 2), 5)); 
        
        //adj.get(5).add(new Noeud(100, ct2.getCase(0, 2), 2)); 
        //adj.get(5).add(new Noeud(100, ct2.getCase(1, 1), 4)); 
        
        // Calculate the single source shortest path 
        Dijkstra dpq = new Dijkstra(new Noeud(0, ct2.getCase(0, 0), 0), adj, ct2); 
        dpq.dijkstra(); 
  
        // Print the shortest path to all the Noeuds 
        // from the source Noeud 
        System.out.println("The shorted path from Noeud :"); 
        for (int i = 0; i < dpq.getDist().length; i++) {
        	if (dpq.getDist()[i] == Integer.MAX_VALUE)
        		System.out.println("Inatteignable");
        	else
            System.out.println(source + " to " + i + " is "
                               + dpq.getDist()[i]); 
			
            dpq.printPath(i, dpq.getparents());
            System.out.println("\n");
        }
        for (int e : dpq.getparents()) {
        	System.out.println(e + " ");
        }
       
		
		
		
		
		
		
		
		
		
		
		
		simulateur.BougerRobotUnitaire(Direction.EST, drone);
		simulateur.BougerRobotUnitaire(Direction.EST, drone);
		simulateur.BougerRobotUnitaire(Direction.EST, drone);
		//simulateur.ajouteEvenement(new BougerRobot(4, Direction.EST, drone, ct2));
		simulateur.BougerRobotUnitaire(Direction.SUD, drone);
		simulateur.BougerRobotUnitaire(Direction.OUEST, drone);
		simulateur.BougerRobotUnitaire(Direction.OUEST, drone);
		simulateur.BougerRobotUnitaire(Direction.NORD, drone);
		simulateur.BougerRobotUnitaire(Direction.EST, drone);
		simulateur.BougerRobotUnitaire(Direction.EST, drone);
		//simulateur.ajouteEvenement(new BougerRobot(3, Direction.EST, drone, ct2));
		//simulateur.ajouteEvenement(new BougerRobot(4, Direction.EST, drone, ct2));
		simulateur.BougerRobotUnitaire(Direction.SUD, drone);
		simulateur.BougerRobotUnitaire(Direction.OUEST, drone);
		simulateur.BougerRobotUnitaire(Direction.OUEST, drone);
		
		
		
		
		
		
		
		
	}

}

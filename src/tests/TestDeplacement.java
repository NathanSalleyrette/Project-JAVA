package tests;

import java.awt.Color;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;


import briques.DonneesSimulation;
import gui.GUISimulator;
import io.LecteurDonnees;
import simulation.Simulateur;
import briques.*;
import robots.*;

public class TestDeplacement {
	public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
            System.out.println(donnees);
            //cree la fenetre,  augmenter les valeurs dans les champs ne fait pas apparaitre la cible en plus petit
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            //GUISimulator gui = new GUISimulator(10000, 10000, Color.BLACK); //
            // cree la carte avec les incendies et les robots
            Simulateur simulateur = new Simulateur(gui, Color.decode("#ffffff"), LecteurDonnees.lire(args[0]));
            
            Incendie incendie = simulateur.getDonnees().getIncendies().get(0);
            Robot robot1 = simulateur.getDonnees().getRobots().get(1);
            System.out.println(robot1.getCapacity());
            System.out.println(incendie.getIntensite());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
            simulateur.BougerRobot(robot1, incendie.getPosition());
            //simulateur.simuwait();
            simulateur.EteindreIncendie(incendie, robot1);
            //simulateur.simuwait();
            simulateur.printEvenements();
            System.out.println(incendie.getIntensiteApresAction());
            while (incendie.getIntensite() > 0) {
            	simulateur.BougerRobotEau(robot1);
            	//simulateur.simuwait();
            	simulateur.RemplirRobot(robot1);
            	//simulateur.simuwait();
            	simulateur.BougerRobot(robot1, incendie.getPosition());
            	//simulateur.simuwait();
            	simulateur.EteindreIncendie(incendie, robot1);
            	//simulateur.simuwait();
            	System.out.println(incendie.getIntensiteApresAction());
            	while (!simulateur.EvenementsRealises()) {
            		 try {
                         Thread.sleep(2000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
            		 System.out.println(incendie.getIntensite());
            	}
            	 
            }
            simulateur.BougerRobot(robot1, robot1.getCaseDepart());	
           // simulateur.printEvenements();
           // System.out.println("fin");
           // simulateur.BougerRobot(robot1, incendie.getPosition());
           // simulateur.printEvenements();
           // System.out.println("fin");
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }
}

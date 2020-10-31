package tests;

import java.awt.Color;

import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;
import java.util.*;


import briques.DonneesSimulation;
import gui.GUISimulator;
import io.LecteurDonnees;
import simulation.Simulateur;
import briques.*;
import robots.*;

public class TestEteindreIncendies{
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
            
            Incendie incendie = simulateur.getDonnees().getIncendies().get(12);
            Robot robot1 = simulateur.getDonnees().getRobots().get(1);
            System.out.println(robot1.getCapacity());
            System.out.println(incendie.getIntensite());
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            
       /*     simulateur.BougerRobot(robot1, incendie.getPosition());
            simulateur.EteindreIncendie(incendie, robot1);
            simulateur.simuwait();
            simulateur.printEvenements();
            System.out.println(incendie.getIntensiteApresAction());
            while (incendie.getIntensite() > 0) {
            	simulateur.BougerRobotEau(robot1);
            	simulateur.simuwait();
            	simulateur.RemplirRobot(robot1);
            	simulateur.simuwait();
            	simulateur.BougerRobot(robot1, incendie.getPosition());
            	//simulateur.simuwait();
            	simulateur.EteindreIncendie(incendie, robot1);
            	simulateur.simuwait();
            	System.out.println(incendie.getIntensiteApresAction());
            	while (!simulateur.EvenementsRealises()) {
            		 try {
                         Thread.sleep(2000);
                     } catch (InterruptedException e) {
                         e.printStackTrace();
                     }
            		 System.out.println(incendie.getIntensite());
            	}
            	 
            }*/
            LinkedList<Robot> robots = new LinkedList<>();
            robots.add(simulateur.getDonnees().getRobots().get(1));
            robots.add(simulateur.getDonnees().getRobots().get(3));
            LinkedList<Incendie> incendies = simulateur.getDonnees().getIncendies();
            //Robot r = simulateur.getDonnees().getRobots().get(1);
            //Incendie ie;
            while (!simulateur.getDonnees().getIncendies().isEmpty()) {
            	for (Robot r : simulateur.getDonnees().getRobots()) {
            		if (r.getDateDisponible() <= simulateur.getDateSimulation()) {
            			Incendie i = incendies.getFirst();
            		/*for (Incendie i : incendies) {
            			if (i.getIntensiteApresAction() != 0) {
            				ie = i;
            				break;
            			}
            			
            		}*/
            			if (i.getIntensite() != 0) {
            				simulateur.BougerRobotFeu(r, i);
            				simulateur.EteindreIncendie(i, r);
            			}
            			if (r.getReserve() == 0) {
            				simulateur.BougerRobotEau(r);
            				simulateur.RemplirRobot(r);
            			}
            		
            		}
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
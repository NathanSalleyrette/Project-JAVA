package tests;

import io.LecteurDonnees;
import simulation.Simulateur;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.zip.DataFormatException;

import briques.DonneesSimulation;
import gui.GUISimulator;

public class TestLecteurDonnees {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Syntaxe: java TestLecteurDonnees <nomDeFichier>");
            System.exit(1);
        }

        try {
            DonneesSimulation donnees = LecteurDonnees.lire(args[0]);
            System.out.println(donnees);
            // crée la fenêtre graphique dans laquelle dessiner
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            // crée l'invader, en l'associant à la fenêtre graphique précédente
            Simulateur simulateur = new Simulateur(gui, Color.decode("#ffffff"), LecteurDonnees.lire(args[0]));
            
            
        } catch (FileNotFoundException e) {
            System.out.println("fichier " + args[0] + " inconnu ou illisible");
        } catch (DataFormatException e) {
            System.out.println("\n\t**format du fichier " + args[0] + " invalide: " + e.getMessage());
        }
    }

}


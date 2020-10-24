package io;


import java.io.*;
import java.util.*;
import java.util.zip.DataFormatException;
import briques.*;
import robots.Drone;
import robots.Pattes;
import robots.Robot;
import robots.Roues;



/**
 * Lecteur de cartes au format spectifié dans le sujet.
 * Les données sur les cases, robots puis incendies sont lues dans le fichier,
 * puis simplement affichées.
 * A noter: pas de vérification sémantique sur les valeurs numériques lues.
 *
 * IMPORTANT:
 *
 * Cette classe ne fait que LIRE les infos et les afficher.
 * A vous de modifier ou d'ajouter des méthodes, inspirées de celles présentes
 * (ou non), qui CREENT les objets au moment adéquat pour construire une
 * instance de la classe DonneesSimulation à partir d'un fichier.
 *
 * Vous pouvez par exemple ajouter une méthode qui crée et retourne un objet
 * contenant toutes les données lues:
 *    public static DonneesSimulation creeDonnees(String fichierDonnees);
 * Et faire des méthode creeCase(), creeRobot(), ... qui lisent les données,
 * créent les objets adéquats et les ajoutent ds l'instance de
 * DonneesSimulation.
 */
public class LecteurDonnees {


    /**
     * Lit et affiche le contenu d'un fichier de donnees (cases,
     * robots et incendies).
     * Ceci est méthode de classe; utilisation:
     * LecteurDonnees.lire(fichierDonnees)
     * @param fichierDonnees nom du fichier à lire
     */
    public static DonneesSimulation lire(String fichierDonnees)
        throws FileNotFoundException, DataFormatException {
    	
        System.out.println("\n == Lecture du fichier" + fichierDonnees);
        LecteurDonnees lecteur = new LecteurDonnees(fichierDonnees);
        Carte carte = lecteur.lireCarte();
        LinkedList<Incendie> incendies = lecteur.lireIncendies(carte);
        LinkedList<Robot> robots = lecteur.lireRobots(carte);
        scanner.close();
        System.out.println("\n == Lecture terminee");
        DonneesSimulation donnees = new DonneesSimulation();
        donnees.setCarte(carte);
        donnees.setIncendies(incendies);
        donnees.setRobots(robots);
        return donnees;
    }




    // Tout le reste de la classe est prive!

    private static Scanner scanner;

    /**
     * Constructeur prive; impossible d'instancier la classe depuis l'exterieur
     * @param fichierDonnees nom du fichier a lire
     */
    private LecteurDonnees(String fichierDonnees)
        throws FileNotFoundException {
        scanner = new Scanner(new File(fichierDonnees));
        scanner.useLocale(Locale.US);
    }

    /**
     * Lit et affiche les donnees de la carte.
     * @throws ExceptionFormatDonnees
     */
    private Carte lireCarte() throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbLignes = scanner.nextInt();
            int nbColonnes = scanner.nextInt();
            int tailleCases = scanner.nextInt();	// en m
            NatureTerrain[][] m = new NatureTerrain[nbLignes][nbColonnes];
            System.out.println("Carte " + nbLignes + "x" + nbColonnes
                    + "; taille des cases = " + tailleCases);

            for (int lig = 0; lig < nbLignes; lig++) {
                for (int col = 0; col < nbColonnes; col++) {
                    m[lig][col] = lireCase(lig, col);
                }
            }
            return new Carte(m, tailleCases);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbLignes nbColonnes tailleCases");
        }
        // une ExceptionFormat levee depuis lireCase est remontee telle quelle
        
    }




    /**
     * Lit et affiche les donnees d'une case.
     */
    private NatureTerrain lireCase(int lig, int col) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Case (" + lig + "," + col + "): ");
        String chaineNature = new String();
        //		NatureTerrain nature;

        try {
            chaineNature = scanner.next();
            // si NatureTerrain est un Enum, vous pouvez recuperer la valeur
            // de l'enum a partir d'une String avec:
            //			NatureTerrain nature = NatureTerrain.valueOf(chaineNature);

            verifieLigneTerminee();

            System.out.print("nature = " + chaineNature);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de case invalide. "
                    + "Attendu: nature altitude [valeur_specifique]");
        }

        System.out.println();
        return NatureTerrain.valueOf(chaineNature);
    }


    /**
     * Lit et affiche les donnees des incendies.
     */
 /*   private Incendie lireIncendies(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbIncendies = scanner.nextInt();
            System.out.println("Nb d'incendies = " + nbIncendies);
            Incendie incendies = lireIncendie(carte, 0);
            for (int i = 1; i < nbIncendies; i++) {
                incendies.pushQueue(lireIncendie(carte, i));
            }
            return incendies;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbIncendies");
        }
    }*/
    
    private LinkedList<Incendie> lireIncendies(Carte carte) throws DataFormatException {
    	ignorerCommentaires();
    	LinkedList<Incendie> incendies = new LinkedList<Incendie>();
    	try {
    		int nbIncendies = scanner.nextInt();
    		System.out.println("Nb d'incendies = " + nbIncendies);
    		for (int i = 0; i < nbIncendies; i++) {
    			lireIncendie(carte, i, incendies);
    		}
    	
    		return incendies;
    	} catch (NoSuchElementException e) {
    		throw new DataFormatException("Format invalide. "
                + "Attendu: nbIncendies");
    	
    	}
    }


    /**
     * Lit et affiche les donnees du i-eme incendie.
     * @param i
     */
    private void lireIncendie(Carte carte, int i, LinkedList<Incendie> incendies) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Incendie " + i + ": ");

        try {
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            int intensite = scanner.nextInt();
            if (intensite <= 0) {
                throw new DataFormatException("incendie " + i
                        + "nb litres pour eteindre doit etre > 0");
            }
            verifieLigneTerminee();

            System.out.println("position = (" + lig + "," + col
                    + ");\t intensite = " + intensite);
            
            new Incendie(carte.getCase(lig, col), intensite, incendies);

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format d'incendie invalide. "
                    + "Attendu: ligne colonne intensite");
        }
    }


    /**
     * Lit et affiche les donnees des robots.
     */
   /* private Robot lireRobots(Carte carte) throws DataFormatException {
        ignorerCommentaires();
        try {
            int nbRobots = scanner.nextInt();
            System.out.println("Nb de robots = " + nbRobots);
            Robot robots = lireRobot(carte, 0);
            for (int i = 1; i < nbRobots; i++) {
                robots.pushQueue(lireRobot(carte, i));
                
            }
            return robots;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("Format invalide. "
                    + "Attendu: nbRobots");
        }
    }
    */
    private LinkedList<Robot> lireRobots(Carte carte) throws DataFormatException {
    	ignorerCommentaires();
    	LinkedList<Robot> robots = new LinkedList<Robot>();
    	try {
    		int nbRobots = scanner.nextInt();
    		System.out.println("Nb de robots = " + nbRobots);
    		for (int i = 0; i < nbRobots; i++) {
    			robots.add(lireRobot(carte, i));
    		}
    	
    		return robots;
    	} catch (NoSuchElementException e) {
    		throw new DataFormatException("Format invalide. "
                + "Attendu: nbRobots");
    	}
}

    /**
     * Lit et affiche les donnees du i-eme robot.
     * @param i
     */
    private Robot lireRobot(Carte carte,int i) throws DataFormatException {
        ignorerCommentaires();
        System.out.print("Robot " + i + ": ");

        try {
        	Robot robot;
            int lig = scanner.nextInt();
            int col = scanner.nextInt();
            System.out.print("position = (" + lig + "," + col + ");");
            String chainType = scanner.next();
            System.out.print("\t type = " + chainType);
            Type type = Type.valueOf(chainType);

            


            // lecture eventuelle d'une vitesse du robot (entier)
            System.out.print("; \t vitesse = ");
            String s = scanner.findInLine("(\\d+)");	// 1 or more digit(s) ?
            // pour lire un flottant:    ("(\\d+(\\.\\d+)?)");

            if (s == null) {
                System.out.print("valeur par defaut");
                switch(type) {
                case DRONE: {
                	robot = new Drone(carte.getCase(lig, col));
                	break;
                }
                case ROUES: {
                	robot = new Roues(carte.getCase(lig, col));
                	break;
                }
                case PATTES: {
                	robot = new Pattes(carte.getCase(lig, col));
                	break;
                }
                //case CHENILLES: robot = new Chenilles(carte.getCase(lig, col));
                default: throw new DataFormatException("format de robot invalide. "
                        + "type inconnu");
                
                }
                
            } else {
                int vitesse = Integer.parseInt(s);
                System.out.print(vitesse);
                switch(type) {
                case DRONE: {
                	robot = new Drone(carte.getCase(lig, col), vitesse);
                	break;
                }
                case ROUES: {
                	robot = new Roues(carte.getCase(lig, col), vitesse);
                	break;
                }
                //case CHENILLES: robot = new Chenilles(carte.getCase(lig, col), vitesse);
                default: throw new DataFormatException("format de robot invalide. "
                        + "type inconnu");
                
                }
            }
            verifieLigneTerminee();

            System.out.println();
            return robot;

        } catch (NoSuchElementException e) {
            throw new DataFormatException("format de robot invalide. "
                    + "Attendu: ligne colonne type [valeur_specifique]");
        }
    }




    /** Ignore toute (fin de) ligne commencant par '#' */
    private void ignorerCommentaires() {
        while(scanner.hasNext("#.*")) {
            scanner.nextLine();
        }
    }

    /**
     * Verifie qu'il n'y a plus rien a lire sur cette ligne (int ou float).
     * @throws ExceptionFormatDonnees
     */
    private void verifieLigneTerminee() throws DataFormatException {
        if (scanner.findInLine("(\\d+)") != null) {
            throw new DataFormatException("format invalide, donnees en trop.");
        }
    }
}

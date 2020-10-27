package robots;

import briques.Carte;
import briques.Case;
import briques.Direction;
import briques.NatureTerrain;
import briques.Type;
import pluscourtchemin.*;
import java.util.*;

public abstract class Robot {
	private Type type;
	private Case position;
	private int capacity; // quantit� qu'il peut porter au max
	private int reserve; // quantit� courante
	private double vitesse; //vitesse par default en km/h
	private double tempsRemplissage; // en minute
	private double tempsExtinctionUnitaire; // en seconde
	private int interventionUnitaire; // quantit� lib�r� (remplissage complet pour tout les robot)
	private long dateDisponible;
	private Carte carte;
	private Case positionApresAction;
	private List<List<Noeud>> adj;
	private Dijkstra dpq;

	
	
	//Constructeurs du robot
	/**
	 * cree un robot situ� en position, transporte reserve litres, peut transporter jusqu'� capacity litres
	 * @param position
	 * @param capacity
	 * @param reserve
	 */
	public Robot(Case position, int capacity, int reserve, double vitesse, double rempli, double vide, int interventionUnitaire, Carte carte) {
		this.position = position;
		this.capacity = capacity;
		this.reserve = reserve;
		this.vitesse = vitesse;
		this.tempsRemplissage = rempli;
		this.tempsExtinctionUnitaire = vide;
		this.interventionUnitaire = interventionUnitaire;
		this.carte = carte;
		this.dateDisponible = 0; // date � laquel le robot sera � nouveau disponible pour r�aliser une action
		this.positionApresAction = position; // position du robot une fois tous les �venement d�j� definis termin�s
		this.adj = this.createGraph(carte);
		
	}
	
	
	/**
	 * robot en (0,0) terrain libre, capacity de 0, vitesse 0
	 */
	/*
	public Robot() {
		this(new Case(), 0, 0, 0.0, 0.0, 0.0, 0);
	}
	 */
	
	
	
	//acc�s aux donn�es du robot
	public Case getPosition() {
		return this.position;
	}
	
	private void setPosition(Case nouvelle_position) {
		this.position = nouvelle_position;
	}
	
	public Case getPositionApresAction() {
		return this.positionApresAction;
	}
	
	public void setPositionApresAction(Case c) {
		this.positionApresAction = c;
	}
	
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getReserve() {
		return this.reserve;
	}
	
	public Type getType() {
		return this.type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	public int getInterventionUnitaire() {
		return this.interventionUnitaire;
	}
	
	public long getDateDisponible() {
		return this.dateDisponible;
	}
	
	public void setDateDisponible(long date) {
		this.dateDisponible = date;
	}
	
	public Carte getCarte() {
		return this.carte;
	}
	
	
	
	/**
	 * rempli le reservoir du robot avec volume positive, sans d�passer la capacity
	 * @param vol
	 */
	public void remplirReserve() {
		if (this.eauVoisine()) {
		this.reserve = this.capacity;
		}
	}
	
	
	/**
	 * renvoie si le robot peut se r�aprovisionner en eau (il faut que de l'eau lui soit adjacente)
	 * @return
	 */
	public Boolean eauVoisine() {
		return this.getCarte().eauVoisine(this.getPosition());
	}
	
	/**
	 * le robot deverse une partie de l'eau qu'il a en reserve sur le feu
	 * @param vol
	 * return volume d�vers� r�ellement
	 */
	public int deverserEau(int vol) {
		vol = Math.min(this.getReserve(), Math.max(0, vol)); // on ne peut pas deverser plus que ce su'il y a dans le r�servoir
		this.reserve = this.reserve - vol ;
		return vol;
		
	}
	
	/**
	 * vitesse du robot sur le terrain de nature "nature"
	 * On consid�re que le robot bouge � la vitesse de sa case actuelle pour aller sur une autre case
	 * @param nature
	 * @return
	 */
	public double getVitesse(NatureTerrain nature) {
		if (this.isCompatible(nature)) return this.getVitesse();
		return 0.0;
		
		/*
		switch (nature) {
		case EAU: return 0.0;
		case FORET: return 0.0;
		case ROCHE: return 0.0;
		case HABITAT: return 0.0;
		default: return 0.0; //HABITAT_LIBRE
		}
		*/
	}
	
	
	/**
	 * vitesse du robot sur laquelle il est apr�s toutes les actions
	 * permet d'avoir le temps pour bouger le robot 
	 * @return
	 */
	public double getVitesseCourante() {
		return this.getVitesse(this.getPositionApresAction().getNature());
	}
	
	public double getVitesse() {
		return this.vitesse;
	}
	
	public double getTempsRemplissage() {
		return this.tempsRemplissage;
	}
	
	public double getTempsExtinctionUnitaire() {
		return this.tempsExtinctionUnitaire;
	}
	
	public int getTempsExtinction(int vol) {
		int nbVolUbitaire = (int)Math.ceil(vol/this.interventionUnitaire);
		return nbVolUbitaire;
	}
	
	public Dijkstra getDijkstra() {
		return dpq;
	}
	
	public void setDijkstra(Dijkstra d) {
		this.dpq = d;
	}
	
	public List<List<Noeud>> getMadj() {
		return adj;
	}
	
	

	
	
	/**
	 * le robot ne peut pas forcement marcher n'importe o�
	 * @param nature
	 * @return Le robot peut aller sur une case de nature "nature"
	 */
	public Boolean isCompatible(NatureTerrain nature) {
		return true;
		/*
		switch (nature) {
		case EAU: return true;
		case FORET: return true;
		case ROCHE: return true;
		case HABITAT: return true;
		default: return true; //TERRAIN_LIBRE
		}
		*/
	}
	
	/**
	 * verifie si une autre case est voisine de la case du robot, cela peut etre un voisin lat�ral ou vertical
	 * @param autre_case
	 * @return true si la case est voisine du robot
	 */
	public Boolean isVoisine(Case autre_case) {
		Boolean voisinLateral = Math.abs(this.position.getLigne() - autre_case.getLigne()) == 1
						   && 
						   this.position.getColonne() - autre_case.getColonne() == 0;
		Boolean voisinHorizontal = (Math.abs(this.position.getColonne() - autre_case.getColonne()) == 1) 
							&& 
							(this.position.getLigne() - autre_case.getLigne() == 0);
		return voisinLateral || voisinHorizontal;
	}
	
	/**
	 * d�place le robot uniquement si la nouvelle case est voisine de celle du robot et accessible
	 * @param nouvelle_position
	 */
	public void deplacer(Case nouvelle_position) {
		if (this.deplacementPossible(nouvelle_position)) {
			this.setPosition(nouvelle_position);
		}	
	}
	
	public Boolean deplacementPossible(Case nouvelle_position) {
		return this.isCompatible(nouvelle_position.getNature()) && this.isVoisine(nouvelle_position);
	}
	
	
	/**
	 * si la nouvelle position est voisine de la case du robot, ainsi que compatible avec le 
	 * robot, ce dernier va sur la nouvelle case
	 * @param newPosition
	 * @param robot
	 */
	
	
	public void deplacer(Direction dir) {
	    try {
	    	Case newPosition = (this.getCarte()) . getVoisin(this.getPosition(), dir);
	    	this.deplacer(newPosition);
		} catch (Exception e) {
			System.out.println("On ne peut pas déplacer le robot par l� : " + dir.toString() + this.toString());
		}
	}
	
	/**
	 * 
	 * @param c
	 * Crée la matrice d'adjacence utile pour l'algorithme de Dijkstra
	 */
	
	public List<List<Noeud>> createGraph(Carte c) {
		
		// V for vertex
		int V = c.getNbColonnes()*c.getNbLignes(); 
		
		int distancecase = c.getTailleCases();
        // Adjacency list representation of the  
        // connected edges 
        List<List<Noeud> > adj = new ArrayList<List<Noeud> >(); 
  
        // Initialize list for every Noeud 
        for (int i = 0; i < V; i++) { 
            List<Noeud> item = new ArrayList<Noeud>(); 
            adj.add(item); 
        } 
		
		for (int i = 0; i < c.getNbLignes(); i ++) {
			for (int j = 0; j < c.getNbColonnes(); j++) {
				Case currentcase = c.getCase(i, j);
				int caseint = i * c.getNbColonnes() + j;
				double vitessedanscase = this.getVitesse(currentcase.getNature());
				if (vitessedanscase != 0) {
					int temps = distancecase / (int) vitessedanscase;
					for (Case e : c.getVoisins(currentcase)) {
						int eint = e.getLigne() * c.getNbColonnes() + e.getColonne();
						adj.get(caseint).add(new Noeud(temps, e, eint)); 
					}
				}
				
			}
		}
		
		return adj;
	}
	
	
	// les robots sont en listes
/*	public Robot getSuivant() {
		return this.suivant;
	}
	
	private void setSuivant(Robot next) {
		this.suivant = next;
	}*/
	
	/**
	 * met le nvxrobot en fin de liste dont this est la tete
	 * @param nvxRobot
	 */
/*	public void pushQueue(Robot nvxRobot) {
		//met le robot � la fin de la liste de robot
		Robot next = this.getSuivant();
		Robot suiv;
		if (next == null) this.setSuivant(nvxRobot); // un seul robot dans la liste
		else {
			suiv = next.getSuivant();
			while (suiv != null) {
				next = next.getSuivant();
				suiv = suiv.getSuivant();
			}
			next.setSuivant(nvxRobot);
		}
		
	
	}*/
	
	
	//les robots print�s
	/**
	 * exemple: 
	 * (2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 * 
	 */
	public String toString() {
		return this.position.toString() + ", " + reserve + "/" + capacity + " litres, vitesse " + this.vitesse;
	}
	
	
	/**
	 * to string generalis� pour afficher toute la liste � partir de this
	 * exemple :
	 * (0,9) ROCHE, 1/9 litres
	 * (2,3) TERRAIN_LIBRE, 4/6 litres
	 */
/*	public String allToString() {
		if (this.suivant != null) return this.toString() + "\n" + this.suivant.allToString();
		return this.toString();
	}*/

}

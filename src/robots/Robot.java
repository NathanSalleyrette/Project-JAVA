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
	private int reserveApresAction;
	private double vitesse; //vitesse par default en km/h
	private double tempsRemplissage; // en minute
	private double tempsExtinctionUnitaire; // en seconde
	private int interventionUnitaire; // quantit� lib�r� (remplissage complet pour tout les robot)
	private long dateDisponible;
	private Carte carte;
	private Case positionApresAction;
	private List<List<Noeud>> graphAdjacence;
	private List<Noeud> graph;
	private Dijkstra dpq; // dijkstra associ� au futur chemin du robot

	// Dans le cas d'un restart
	private Case positionDépart;
	
	//Constructeurs du robot
	/**
	 * cree un robot situ� en position, transporte reserve litres, peut transporter jusqu'� capacity litres
	 * @param position
	 * @param capacity
	 * @param reserve
	 */
	public Robot(Case position, int capacity, double vitesse, double rempli, double vide, int interventionUnitaire, Carte carte) {
		this.position = position;
		this.positionDépart = position;
		this.capacity = capacity;
		this.reserve = capacity;
		this.reserveApresAction = this.reserve;
		this.vitesse = vitesse;
		this.tempsRemplissage = rempli;
		this.tempsExtinctionUnitaire = vide;
		this.interventionUnitaire = interventionUnitaire;
		this.carte = carte;
		this.dateDisponible = 0; // date � laquel le robot sera � nouveau disponible pour r�aliser une action
		this.positionApresAction = position; // position du robot une fois tous les �venement d�j� definis termin�s
		this.graph = this.createGraph();
		this.graphAdjacence = this.createGraphAdjacence();
		
		
	}
	
	public void restartRobot() {
		this.position = this.positionDépart;
		this.dateDisponible = 0;
		this.positionApresAction = this.positionDépart;
		this.reserve = this.capacity;
	}
	
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
	
	public int getReserveApresAction() {
		return this.reserveApresAction;
	}
	
	public void setReserve(int reserve) {
		this.reserve = reserve;
	}
	
	public void setReserveApresAction(int reserve) {
		this.reserveApresAction = reserve;
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
	
	public Case getCaseDepart() {
		return this.positionDépart;
	}
	
	
	/**
	 * rempli le reservoir du robot avec volume positive, sans d�passer la capacity
	 * @param vol
	 */
	public void remplirReserve() {
		if (this.eauAccessible()) {
			this.reserve = this.capacity;
		}
	}
	
	
	/**
	 * renvoie si le robot peut se r�aprovisionner en eau (il faut que de l'eau lui soit adjacente)
	 * @return
	 */
	public Boolean eauAccessible() {
		return this.getCarte().eauVoisine(this.getPosition());
	}
	
	public Boolean eauAccessible(Case c) {
		return this.getCarte().eauVoisine(c);
	}
	
	public Boolean eauAccessibleApresAction() {
		return this.getCarte().eauVoisine(this.getPositionApresAction());
	}
	
	/**
	 * le robot deverse une partie de l'eau qu'il a en reserve (on ne test pas si la case contient un feu, cela est test� plus haut)
	 * @param vol
	 * return volume que l'on deverse reelement
	 */
	public int deverserEau(int vol) {
		vol = Math.min(this.getReserve(), Math.max(0, vol)); // on ne peut pas deverser plus que ce su'il y a dans le r�servoir
		this.reserve = this.reserve - vol ;
		this.reserveApresAction = reserve;
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
	
	
	/*
	public int getTempsExtinction(int vol) {
		int nbVolUbitaire = (int)Math.ceil(vol/this.interventionUnitaire);
		return nbVolUbitaire;
	}
	*/
	
	public Dijkstra getDijkstra() {
		return dpq;
	}
	
	public void setDijkstra(Dijkstra d) {
		this.dpq = d;
	}
	
	public List<List<Noeud>> getMadj() {
		return graphAdjacence;
	}
	
	 public List<Case> getChemin(Case c) {
		 	Noeud depart = graph.get(carte.transformeNombreCase(this.positionApresAction));
		 	int arrivee = this.carte.transformeNombreCase(c);
		 	this.dpq = new Dijkstra(depart, arrivee, this.graphAdjacence, this.graph, this.carte);
		 	this.dpq.dijkstra();
		 	//this.printGraph();
		 	//this.printGraphAdjacence();
		 	//this.dpq.printDistance();
		 	//this.dpq.printParents();
	    	return this.dpq.getChemin();
	 }
	
	public List<Case> getCheminEau() {
		Noeud depart = graph.get(carte.transformeNombreCase(this.positionApresAction));
	 	this.dpq = new Dijkstra(depart, this.graphAdjacence, this.graph, this.carte);
	 	this.dpq.dijkstra();
	 	//this.printGraph();
	 	//this.printGraphAdjacence();
	 	//this.dpq.printDistance();
	 	//this.dpq.printParents();
    	return this.dpq.getChemin();
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
	 * verifie si une autre case est voisine de la case actuelle du robot, cela peut etre un voisin lat�ral ou vertical
	 * @param autre_case
	 * @return true si la case est voisine du robot
	 */
	public Boolean isVoisin(Case autre_case) {
		return this.getPosition().isVoisine(autre_case);
	}
	
	public Boolean isVoisinApresAction(Case autre_case) {
		return this.getPositionApresAction().isVoisine(autre_case);
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
		Boolean a = this.isCompatible(nouvelle_position.getNature());
		Boolean b = this.isVoisin(nouvelle_position);
		return this.isCompatible(nouvelle_position.getNature()) && this.isVoisin(nouvelle_position);
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
	 * @param carte
	 * Crée la matrice d'adjacence utile pour l'algorithme de Dijkstra
	 */
	
	public List<List<Noeud>> createGraphAdjacence() {
		
		int nbNoeuds = carte.getNbColonnes()*carte.getNbLignes(); 
		
        // Adjacency list representation of the connected edges 
        List<List<Noeud> > adj = new ArrayList<List<Noeud> >(); 
  
        // Initialize list for every Noeud 
        for (int i = 0; i < nbNoeuds; i++) { 
            List<Noeud> item = new ArrayList<Noeud>(); 
            adj.add(item); 
        } 
		
		for (int i = 0; i < carte.getNbLignes(); i ++) {
			for (int j = 0; j < carte.getNbColonnes(); j++) {
				Case currentCase = carte.getCase(i, j);
				int numeroCase = i * carte.getNbColonnes() + j;
				double vitesseDansCase = this.getVitesse(currentCase.getNature()); //redondance de calcul voir create graphe
				if (vitesseDansCase != 0) {
					for (Case c : carte.getVoisins(currentCase)) {
						if (this.getVitesse(c.getNature()) != 0) {
							adj.get(numeroCase).add(graph.get(this.carte.transformeNombreCase(c))); 
						}
					}
				}
			}
		}
		return adj;
	}
	
	public List<Noeud> createGraph() {
		List<Noeud> liste = new ArrayList<Noeud>();
		for (int lig = 0; lig < carte.getNbLignes(); lig++) {
			for (int col = 0; col < carte.getNbColonnes(); col++) {
				Case c = carte.getCase(lig, col);
				double vitesse = this.getVitesse(c.getNature());
				int temps;
				if (vitesse != 0) {
					temps = (int) Math.ceil(this.carte.getTailleCases()/vitesse);
				} else {
					temps = Integer.MAX_VALUE;
				}
				liste.add(new Noeud(temps, carte.transformeNombreCase(c), this.eauAccessible(c)));
			}
		}
		return liste;
	}
	

	
	
	 public void printGraph() {
		 for (Noeud noeud : this.graph) {
			 System.out.println(noeud);
		 }
	 }
	 
	 public void printGraphAdjacence() {
		 StringJoiner stringJoiner2 =  new StringJoiner(", ",  "(",  ")") ;
		for (int i=0; i<this.graphAdjacence.size(); i++) {
			StringJoiner stringJoiner1 =  new StringJoiner(", ",  "(",  ")") ;
			for (int j=0; j<this.graphAdjacence.get(i).size(); j++) {
				stringJoiner1.add(this.graphAdjacence.get(i).get(j).toString());
			}
			stringJoiner2.add("\n"+ stringJoiner1.toString());
		}
		System.out.println(stringJoiner2.toString());
	 }
	 
	//les robots print�s
	/**
	 * exemple: 
	 * (2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 * 
	 */
	public String toString() {
		return this.position.toString() + ", " + reserve + "/" + capacity + " litres, vitesse " + this.vitesse;
	}
}
	
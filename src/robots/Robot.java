package robots;

import briques.Carte;
import briques.Case;
import briques.Direction;
import briques.NatureTerrain;
import briques.Type;


public abstract class Robot {
	private Type type;
	private Case position;
	private int capacity; // quantitï¿½ qu'il peut porter au max
	private int reserve; // quantitï¿½ courante
	private double vitesse; //vitesse par default en km/h
	private double tempsRemplissage; // en minute
	private double tempsExtinctionUnitaire; // en seconde
	private int interventionUnitaire; // quantitï¿½ libï¿½rï¿½ (remplissage complet pour tout les robot)
	private long dateDisponible;
	private Carte carte;
	private Case positionApresAction;

	
	
	//Constructeurs du robot
	/**
	 * cree un robot situï¿½ en position, transporte reserve litres, peut transporter jusqu'ï¿½ capacity litres
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
		this.dateDisponible = 0; // date à laquel le robot sera à nouveau disponible pour réaliser une action
		this.positionApresAction = position; // position du robot une fois tous les évenement déjà definis terminés
	}
	
	
	/**
	 * robot en (0,0) terrain libre, capacity de 0, vitesse 0
	 */
	/*
	public Robot() {
		this(new Case(), 0, 0, 0.0, 0.0, 0.0, 0);
	}
	 */
	
	
	
	//accï¿½s aux donnï¿½es du robot
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
	 * rempli le reservoir du robot avec volume positive, sans dï¿½passer la capacity
	 * @param vol
	 */
	public void remplirReserve() {
		if (this.eauVoisine()) {
		this.reserve = this.capacity;
		}
	}
	
	
	/**
	 * renvoie si le robot peut se réaprovisionner en eau (il faut que de l'eau lui soit adjacente)
	 * @return
	 */
	public Boolean eauVoisine() {
		return this.getCarte().eauVoisine(this.getPosition());
	}
	
	public Boolean eauVoisineApresAction() {
		return this.getCarte().eauVoisine(this.getPositionApresAction());
	}
	
	/**
	 * le robot deverse une partie de l'eau qu'il a en reserve sur le feu
	 * @param vol
	 * return volume dï¿½versï¿½ rï¿½ellement
	 */
	public int deverserEau(int vol) {
		vol = Math.min(this.getReserve(), Math.max(0, vol)); // on ne peut pas deverser plus que ce su'il y a dans le rï¿½servoir
		this.reserve = this.reserve - vol ;
		return vol;
		
	}
	
	/**
	 * vitesse du robot sur le terrain de nature "nature"
	 * On considère que le robot bouge à la vitesse de sa case actuelle pour aller sur une autre case
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
	 * vitesse du robot sur laquelle il est après toutes les actions
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
	
	
	
	

	
	
	/**
	 * le robot ne peut pas forcement marcher n'importe oï¿½
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
	 * verifie si une autre case est voisine de la case actuelle du robot, cela peut etre un voisin latï¿½ral ou vertical
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
	 * dï¿½place le robot uniquement si la nouvelle case est voisine de celle du robot et accessible
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
			System.out.println("On ne peut pas dÃ©placer le robot par là : " + dir.toString() + this.toString());
		}
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
		//met le robot ï¿½ la fin de la liste de robot
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
	
	
	//les robots printï¿½s
	/**
	 * exemple: 
	 * (2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 * 
	 */
	public String toString() {
		return this.position.toString() + ", " + reserve + "/" + capacity + " litres, vitesse " + this.vitesse;
	}
	
	
	/**
	 * to string generalisï¿½ pour afficher toute la liste ï¿½ partir de this
	 * exemple :
	 * (0,9) ROCHE, 1/9 litres
	 * (2,3) TERRAIN_LIBRE, 4/6 litres
	 */
/*	public String allToString() {
		if (this.suivant != null) return this.toString() + "\n" + this.suivant.allToString();
		return this.toString();
	}*/

}

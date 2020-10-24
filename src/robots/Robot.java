package robots;

import briques.Case;
import briques.NatureTerrain;
import briques.Type;


public abstract class Robot {
	private Type type;
	private Case position;
	private int capacity; // quantit� qu'il peut porter au max
	private int reserve; // quantit� courante
	private double vitesse; //vitesse par default en km/h
	private double vitesseRemplissage; // en minute
	private double vitesseExtinction; // en seconde
	private int interventionUnitaire; // quantit� lib�r� (remplissage complet pour tout les robot)
	private long dateDisponible;

	
	
	//Constructeurs du robot
	/**
	 * cree un robot situ� en position, transporte reserve litres, peut transporter jusqu'� capacity litres
	 * @param position
	 * @param capacity
	 * @param reserve
	 */
	public Robot(Case position, int capacity, int reserve, double vitesse, double rempli, double vide, int interventionUnitaire) {
		this.position = position;
		this.capacity = capacity;
		this.reserve = reserve;
		this.vitesse = vitesse;
		this.vitesseRemplissage = rempli;
		this.vitesseExtinction = vide;
		this.interventionUnitaire = interventionUnitaire;
		this.dateDisponible = 0;
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
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getReserve() {
		return this.reserve;
	}
	
	public Type getType() {
		return this.type;
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
	
	public void setType(Type type) {
		this.type = type;
	}
	
	/**
	 * rempli le reservoir du robot avec volume positive, sans d�passer la capacity
	 * @param vol
	 */
	public void remplirReserve(int vol) {
		//si vol <0 alors vol = 0
		
		if (position.getNature() == NatureTerrain.EAU) {
			//this.reserve = this.capacity;
			this.reserve = Math.min(Math.max(0, vol) + this.reserve, this.capacity);
		}
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
	
	public double getVitesseCourante() {
		return this.getVitesse(this.getPosition().getNature());
	}
	
	public double getVitesse() {
		return this.vitesse;
	}
	
	public double getVitesseRemplissage() {
		return this.vitesseRemplissage;
	}
	
	public double getVitesseExtinction() {
		return this.vitesseExtinction;
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

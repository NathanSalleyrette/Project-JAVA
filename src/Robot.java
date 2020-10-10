
public abstract class Robot {
	private Case position;
	private int capacity; // quantité qu'il peut porter au max
	private int reserve; // quantité courante
	private double vitesse; //vitesse par default en km/h
	private double vitesseRemplissage; // en minute
	private double vitesseExtinction; // en seconde
	private int interventionUnitaire; // quantité libéré (remplissage complet pour tout les robot)
	private Robot suivant;
	
	
	//Constructeurs du robot
	/**
	 * cree un robot situé en position, transporte reserve litres, peut transporter jusqu'à capacity litres
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
	}
	
	
	/**
	 * robot en (0,0) terrain libre, capacity de 0, vitesse 0
	 */
	public Robot() {
		this(new Case(), 0, 0, 0.0, 0.0, 0.0, 0);
	}
	
	
	
	//accès aux données du robot
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
	 */
	public void deverserEau(int vol) {
		//si vol <0 alors vol = 0
		this.reserve = Math.max(this.reserve - Math.max(0, vol), 0);
		// TODO éteindre incendie 
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
	 * le robot ne peut pas forcement marcher n'importe où
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
	 * verifie si une autre case est voisine de la case du robot, cela peut etre un voisin latéral ou vertical
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
	 * déplace le robot uniquement si la nouvelle case est voisine de celle du robot et accessible
	 * @param nouvelle_position
	 */
	public void deplacer(Case nouvelle_position) {
		if (this.isCompatible(nouvelle_position.getNature()) && this.isVoisine(nouvelle_position)) {
			this.setPosition(nouvelle_position);
		}	
	}
	
	
	// les robots sont en listes
	public Robot getSuivant() {
		return this.suivant;
	}
	
	private void setSuivant(Robot next) {
		this.suivant = next;
	}
	
	/**
	 * met le nvxrobot en fin de liste dont this est la tete
	 * @param nvxRobot
	 */
	public void pushQueue(Robot nvxRobot) {
		//met le robot à la fin de la liste de robot
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
	}
	
	
	//les robots printés
	/**
	 * exemple: 
	 * (2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 * 
	 */
	public String toString() {
		return this.position.toString() + ", " + reserve + "/" + capacity + " litres, vitesse " + this.vitesse;
	}
	
	
	/**
	 * to string generalisé pour afficher toute la liste à partir de this
	 * exemple :
	 * (0,9) ROCHE, 1/9 litres
	 * (2,3) TERRAIN_LIBRE, 4/6 litres
	 */
	public String allToString() {
		if (this.suivant != null) return this.toString() + "\n" + this.suivant.allToString();
		return this.toString();
	}

}

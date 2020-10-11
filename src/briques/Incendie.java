package briques;

public class Incendie {
	private Case position;
	private int intensite;
	private Incendie suivant; // pour faire une liste d'incendie

	
	
	//constructeurs
	
	/**
	 * Incendie par defaut en position 0, 0, terrain libre, il faut 1 litre pour l'éteindre
	 */
	public Incendie() {
		this(new Case(), 1);
	}
	
	/**
	 * Incendie en position "position", terrain libre, il faut "intensite" litres pour l'éteindre
	 * @param position
	 * @param litre
	 */
	public Incendie(Case position, int intensite) {
		// copie legère pour pouvoir pointer sur une case de la carte
		// pas de suivant
		this.position = position;
		this.setIntensite(intensite);
	}
	
	
	//accès aux données
	public Case getCase() {
		return this.position;
	}
	
	public int getIntensite() {
		return this.intensite;
	}
	
	public void setIntensite(int intensite) {
		this.intensite = Math.max(0, intensite);
	}
	
	public Incendie getSuivant() {
		return this.suivant;
	}
	
	private void setSuivant(Incendie next) {
		this.suivant = next;
	}
	
	
	/**
	 * met nvxIncendie en fin de la liste dont this est la tete
	 * @param nvxIncendie
	 */
	public void pushQueue(Incendie nvxIncendie) {
		//met l'incendie à la fin de la liste d'incendie
		Incendie next = this.getSuivant();
		Incendie suiv;
		if (next == null) this.setSuivant(nvxIncendie); // un seul incendie dans la liste
		else {
			suiv = next.getSuivant();
			while (suiv != null) {
				next = next.getSuivant();
				suiv = suiv.getSuivant();
			}
			next.setSuivant(nvxIncendie);
		}
	}
	
	
	public void eteindre(int vol) {
		this.setIntensite(this.intensite - Math.max(0,vol));
	}
	
	/**
	 * exemple:
	 * Incendie taille 4, (2,3) TERRAIN_LIBRE
	 */
	public String toString() {
		return "Incendie taille " + this.intensite + ", " + this.position.toString();
	}
	
	/**
	 * to string general
	 * exemple:
	 * Incendie taille 4, (2,3) TERRAIN_LIBRE
	 * Incendie taille 10, (0,0) EAU
	 * ...
	 */
	public String allToString() {
		if (this.suivant != null) return this.toString() + "\n" + this.suivant.allToString();
		return this.toString();
	}
}

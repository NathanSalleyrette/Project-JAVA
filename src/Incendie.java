
public class Incendie {
	private Case position;
	private int litre;
	private Incendie suivant; // pour faire une liste d'incendie

	
	
	//constructeurs
	
	/**
	 * Incendie par defaut en position 0, 0, terrain libre, il faut 1 litre pour l'éteindre
	 */
	public Incendie() {
		this(new Case(), 1);
	}
	
	/**
	 * Incendie en position "position", terrain libre, il faut "litre" litres pour l'éteindre
	 * @param position
	 * @param litre
	 */
	public Incendie(Case position, int litre) {
		// copie legère pour pouvoir pointer sur une case de la carte
		// pas de suivant
		this.position = position;
		this.setLitre(litre);
	}
	
	//accès aux données
	public Case getCase() {
		return this.position;
	}
	
	public int getLitre() {
		return this.litre;
	}
	
	public void setLitre(int litre) {
		this.litre = Math.max(0, litre);
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
		this.setLitre(this.litre - Math.max(0,vol));
	}
	
	/**
	 * exemple:
	 * Incendie taille 4, (2,3) TERRAIN_LIBRE
	 */
	public String toString() {
		return "Incendie taille " + this.litre + ", " + this.position.toString();
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

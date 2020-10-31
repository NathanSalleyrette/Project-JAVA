package briques;

import java.util.LinkedList;

/**
 * un incendie est determiné par sa position, son intensite,
 * l'incendie qui le suit dans la chaine des incendies
 *
 */
public class Incendie {
	private Case position;
	private int intensite;
	private int intensiteApresAction;
	private LinkedList<Incendie> incendies;
	private LinkedList<Incendie> incendiesEteint;
	

	
	
	//constructeurs
	
	/**
	 * Incendie par defaut en position 0, 0, terrain libre, il faut 1 litre pour l'�teindre
	 */
	/*
	public Incendie() {
		this(new Case(), 1);
	}
	*/
	
	/**
	 * Incendie en position "position", terrain libre, il faut "intensite" litres pour l'�teindre
	 * @param position
	 * @param litre
	 */
	public Incendie(Case position, int intensite, LinkedList<Incendie> incendies) {
		// copie leg�re pour pouvoir pointer sur une case de la carte
		// pas de suivant
		this.position = position;
		this.incendies = incendies;
		this.intensiteApresAction = intensite;
		this.setIntensite(intensite);
		incendies.add(this);
	}
	
	
	//acc�s aux donn�es
	public Case getPosition() {
		return this.position;
	}
	
	public int getIntensite() {
		return this.intensite;
	}
	
	public int getIntensiteApresAction() {
		return this.intensiteApresAction;
	}
	
	public void setIntensiteApresAction(int intensite) {
		this.intensiteApresAction = Math.max(0, intensite);
		//if (this.intensiteApresAction == 0) {
		//	this.incendies.remove(this);
		//}
	}
	
	public void setIntensite(int intensite) {
		this.intensite = Math.max(0, intensite);
		
	}
	
	public LinkedList<Incendie> getIncendies(){
		return this.incendies;
	}
	

	
	/**
	 * met nvxIncendie en fin de la liste dont this est la tete
	 * @param nvxIncendie
	 */
/*	public void pushQueue(Incendie nvxIncendie) {
		//met l'incendie � la fin de la liste d'incendie
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
	} */
	
	
	/**
	 * diminue l'intensit� de vol, mais l'intensit� ne peut etre n�gative
	 * @param vol
	 */
	public void eteindre(int vol) {
		this.setIntensite(Math.max(this.intensite - Math.max(0,vol),0)); //Math.max(0,vol) gere le cas ou vol est <0
		if (this.getIntensite() == 0) {
			this.getIncendies().remove(this);
		}	
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
/*	public String allToString() {
		if (this.suivant != null) return this.toString() + "\n" + this.suivant.allToString();
		return this.toString();
	}*/
}

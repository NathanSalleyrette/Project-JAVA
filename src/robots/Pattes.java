package robots;

import briques.Case;
import briques.NatureTerrain;
import briques.Type;



/**
 * classe de robots à pattes
 */
public class Pattes extends Robot {
	/*
	public Pattes() {
		this(new Case());	
	}
	*/
	
	/**
	 * fonction inutile mais notée par soucis d'harmonisation avec les autres classes de robots
	 * vitesse de base 30km/h
	 * appelle du consutructeur plus complet
	 * @param position
	 */
	public Pattes(Case position) {
		this(position, 30);	
	}

	/**
	 * reservoir 42 litres (normalement infini mais gérer autrepart, remplissage en 5min
	 * intervention unitaire 100 litres en 5sec
	 * @param position
	 * @param vitesse
	 */
	public Pattes(Case position, int vitesse) {
		super(position, 42, 0, 30, 10, 5, 100);	
		super.setType(Type.PATTES);
	}
	
	
	/**
	 * le robot à pattes ne se deplace pas sur
	 * eau
	 */
	@Override
	public Boolean isCompatible(NatureTerrain nature) {
		switch (nature) {
		case EAU: return false;
		default: return true;
		}
	}
	
	
	/**
	 * la vitesse du robot à pattes est de 10 sur les rochets
	 * vitesse de base qur les autres terrains (compatibles)
	 * 0.0 sinon
	 */
	@Override
	public double getVitesse(NatureTerrain nature) {
		if (this.isCompatible(nature)) {
			switch (nature) {
			case ROCHE: return 10;
			default: return this.getVitesse(); //HABITAT_LIBRE
			}
		}
		return 0.0;
	}
	
	
	/**
	 * exemple:
	 * PATTES(2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 */
	public String toString() {
		return super.getType() + super.toString();
	}
	

}

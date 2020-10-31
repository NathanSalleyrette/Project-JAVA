package robots;

import briques.*;



/**
 * classe de robots � pattes
 */
public class Pattes extends Robot {
	/*
	public Pattes() {
		this(new Case());	
	}
	*/
	
	/**
	 * fonction inutile mais not�e par soucis d'harmonisation avec les autres classes de robots
	 * vitesse de base 30km/h
	 * appelle du consutructeur plus complet
	 * @param position
	 */
	public Pattes(Case position, Carte carte) {
		this(position, 30, carte);	
	}

	/**
	 * reservoir 42 litres (normalement infini mais g�rer autrepart, remplissage en 5min
	 * intervention unitaire 100 litres en 5sec
	 * @param position
	 * @param vitesse
	 */
	public Pattes(Case position, int vitesse, Carte carte) {
		super(position, 42, 30, 0, 1, 10, carte);	
		super.setType(Type.PATTES);
	}
	
	
	/**
	 * le robot � pattes ne se deplace pas sur
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
	 * la vitesse du robot � pattes est de 10 sur les rochets
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
	
	public void remplirReserve() {} //le robot n'a pas besoin de se remplir
	
	@Override
	public int deverserEau(int vol) { //le robot ne se vide pas
		return vol;	
	}
	
	
	
	/**
	 * exemple:
	 * PATTES(2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 */
	public String toString() {
		return super.getType() + super.toString();
	}
	

}

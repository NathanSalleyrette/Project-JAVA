package robots;

import briques.*;


/**
 * class de robots � roues
 */
public class Roues extends Robot{
	
	
	/*
	public Roues() {
		this(new Case());	
	}
	*/
	
	/**
	 * vitesse de base 80 km/h
	 * @param position
	 */
	public Roues(Case position, Carte carte) {
		this(position, 80, carte);	
	}

	
	/**
	 * reservoir 5000 litres, remplissage en 10min
	 * intervention unitaire : 100litres en 5sec
	 * vitesse du robot "vitesse" pas de max
	 * @param position
	 * @param vitesse
	 */
	public Roues(Case position, int vitesse, Carte carte) {
		super(position, 5000, 5000, vitesse, 10, 5, 100, carte);	
		super.setType(Type.ROUES);
	}
	
	
	/**
	 * le robot � roues ne se deplace que sur
	 * terrain libre et habitat
	 */
	@Override
	public Boolean isCompatible(NatureTerrain nature) {
		switch (nature) {
		case TERRAIN_LIBRE: return true;
		case HABITAT: return true;
		default: return false;
		}
	}
	
	
	/**
	 * exemple:
	 * Roues(2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 */
	@Override
	public String toString() {
		return super.getType() + super.toString();
	}
	
}

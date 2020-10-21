package robots;

import briques.Case;
import briques.NatureTerrain;
import briques.Type;


/**
 * class de robots � roues
 */
public class Roues extends Robot{
	
	
	
	public Roues() {
		this(new Case());	
	}
	
	/**
	 * vitesse de base 80 km/h
	 * @param position
	 */
	public Roues(Case position) {
		this(position, 80);	
	}

	
	/**
	 * reservoir 5000 litres, remplissage en 10min
	 * intervention unitaire : 100litres en 5sec
	 * vitesse du robot "vitesse" pas de max
	 * @param position
	 * @param vitesse
	 */
	public Roues(Case position, int vitesse) {
		super(position, 5000, 0, vitesse, 10, 5, 100);	
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

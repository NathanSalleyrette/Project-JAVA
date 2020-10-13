package robots;

import briques.Case;
import briques.Type;


/**
 * class de robots drone
 */
public class Drone extends Robot{


	public Drone() {
		this(new Case());	
	}
	
	/**
	 * vitesse de base 100 km/h
	 * @param position
	 */
	public Drone(Case position) {
		this(position, 100);	
	}

	/**
	 * reservoir 10000 litres, remplissage en 30min
	 * intervention unitaire : tout le reservoir en 30sec
	 * vitesse du robot "vitesse" mais max = 150km/h
	 * @param position
	 * @param vitesse
	 */
	public Drone(Case position, int vitesse) {
		super(position, 10000, 0, Math.min(150, vitesse), 30, 30, 10000);
		super.setType(Type.DRONE);
		
	}
	
	
	/**
	 * exemple:
	 * DRONE(2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 */
	@Override
	public String toString() {
		return super.getType() + super.toString();
	}
}

package robots;

import briques.*;


/**
 * class de robots drone
 */
public class Drone extends Robot{

/*
	public Drone() {
		this(new Case());	
	}
	*/
	
	/**
	 * vitesse de base 100 km/h
	 * @param position
	 */
	public Drone(Case position, Carte carte) {
		this(position, 100, carte);	
	}

	/**
	 * reservoir 10000 litres, remplissage en 30min
	 * intervention unitaire : tout le reservoir en 30sec
	 * vitesse du robot "vitesse" mais max = 150km/h
	 * @param position
	 * @param vitesse
	 */
	public Drone(Case position, int vitesse, Carte carte) {
		super(position, 10000, Math.min(150, vitesse), 30, 30, 10000, carte);
		super.setType(Type.DRONE);
		
	}
	
	
	@Override
	public Boolean eauAccessible() {
		return this.getPosition().getNature() == NatureTerrain.EAU;
	}
	
	public Boolean eauAccessibleApresAction() {
		return this.getPositionApresAction().getNature() == NatureTerrain.EAU;
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

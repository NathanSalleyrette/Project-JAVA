package robots;

import briques.*;


/**
 * class de robots à chenilles
 */
public class Chenilles extends Robot {

/*
	public Chenilles() {
		this(new Case());	
	}
	*/
	
	/**
	 * vitesse de base 60 km/h
	 * @param position
	 */
	public Chenilles(Case position, Carte carte) {
		
		this(position, 60, carte);	
	}

	/**
	 * reservoir 2000 litres, remplissage en 5min
	 * intervention unitaire 100 litres en 8sec
	 * vitesse du robot "vitesse" mais max = 80km/h
	 * @param position
	 * @param vitesse
	 */
	public Chenilles(Case position, int vitesse, Carte carte) {
		super(position, 2000, 2000, Math.min(80, vitesse), 5, 8, 100, carte);	
		super.setType(Type.CHENILLES);
	}
	
	
	
	/**
	 * robot chenille ne peut pas se deplasser sur
	 * eau ou sur rocher
	 */
	@Override
	public Boolean isCompatible(NatureTerrain nature) {
		
		switch (nature) {
		case EAU: return false;
		case ROCHE: return false;
		default: return true;
		}
	}
	
	

	/**
	 * la vitesse du robot de 50% en foret
	 * vitesse de base sur terrains compatibles
	 * 0.0 sinon
	 */
	@Override
	public double getVitesse(NatureTerrain nature) {
		if (this.isCompatible(nature)) {
			switch (nature) {
			case FORET: return this.getVitesse()*0.5;
			default: return this.getVitesse();
			}
		}
		return 0.0;
	}
	
	
	/**
	 * exemple:
	 * CHENILLES(2,3) TERRAIN_LIBRE, 4/6 litres, vitesse 6
	 */
	@Override
	public String toString() {
		return super.getType() + super.toString();
	}
	


}

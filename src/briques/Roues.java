package briques;

public class Roues extends Robot{

	private Type type = Type.ROUES;
	
	public Roues() {
		this(new Case());	
	}
	
	public Roues(Case position) {
		this(position, 80);	
	}

	public Roues(Case position, int vitesse) {
		super(position, 5000, 0, Math.min(80, vitesse), 10, 5, 100);	
	}
	
	public Boolean isCompatible(NatureTerrain nature) {
		switch (nature) {
		case TERRAIN_LIBRE: return true;
		case HABITAT: return true;
		default: return false;
		}
	}
	
	public String toString() {
		return this.type + super.toString();
	}
	
}

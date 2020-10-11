package briques;

public class Pattes extends Robot {


	private Type type = Type.PATTES;
	
	public Pattes() {
		this(new Case());	
	}
	
	public Pattes(Case position) {
		this(position, 30);	
	}

	public Pattes(Case position, int vitesse) {
		super(position, 5000, 0, 30, 10, 5, 100);	
	}
	
	public Boolean isCompatible(NatureTerrain nature) {
		switch (nature) {
		case EAU: return false;
		default: return true;
		}
	}
	
	public double getVitesse(NatureTerrain nature) {
		if (this.isCompatible(nature)) {
			switch (nature) {
			case ROCHE: return 10;
			default: return this.getVitesse(); //HABITAT_LIBRE
			}
		}
		return 0.0;
	}
	
	public String toString() {
		return this.type + super.toString();
	}
	

}

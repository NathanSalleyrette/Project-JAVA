
public abstract class Robot {
	private Case position;
	private int capacity;
	private int reserve;
	
	
	//Constructeurs du robot
	public Robot(Case position, int capacity, int reserve) {
		this.position = position;
		this.capacity = capacity;
		this.reserve = reserve;
	}
	
	public Robot() {
		this(new Case(), 0, 0);
	}
	
	//accès aux données du robot
	public Case getPosition() {
		return this.position;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public int getReserve() {
		return this.reserve;
	}
	
	
	//method qui sera override
	public double getVitesse(NatureTerrain nature) {
		return 0.0;
		/*
		switch (nature) {
		case EAU: return 0.0;
		case FORET: return 0.0;
		case ROCHE: return 0.0;
		case HABITAT: return 0.0;
		default: return 0.0; //HABITAT_LIBRE
		}
		*/
	}
	
	public void deverserEau(int vol) {
		this.reserve = max(this.reserve - vol, 0);
		// TODO éteindre incendie 
	}

	
	//fonction auxiliaire
	public int max(int i, int j) {
		if (i > j) return i;
		return j;
	}
	
	public int min(int i, int j) {
		if (i < j) return i;
		return j;
	}
	
}

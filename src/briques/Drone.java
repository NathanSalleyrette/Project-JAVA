package briques;

public class Drone extends Robot{
	private Type type = Type.DRONE;

	public Drone() {
		this(new Case());	
	}
	
	public Drone(Case position) {
		this(position, 100);	
	}

	public Drone(Case position, int vitesse) {
		super(position, 10000, 0, Math.min(150, vitesse), 30, 30, 10000);
		
	}
	
	public String toString() {
		return this.type + super.toString();
	}
}

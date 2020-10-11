package briques;


public class DonneesSimulation {
	private Carte carte;
	private Incendie incendies;
	private Robot robots;
	
	public DonneesSimulation(Carte carte, Incendie incendies, Robot robots) {
		this.carte = carte;
		this.incendies = incendies;
		this.robots = robots;
	}
	
	public DonneesSimulation() {
		this(new Carte(), new Incendie(), new Drone());
	}
	
	
	
	//get
	public Carte getCarte() {
		return this.carte;
	}
	
	public Incendie getIncendies() {
		return this.incendies;
	}
	
	public Robot getRobots() {
		return this.robots;
	}
	
	
	//set
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	
	public void setIncendies(Incendie incendies) {
		this.incendies = incendies;
	}
	
	public void setRobots(Robot robots) {
		this.robots = robots;
	}
	
	
	public void eteindreIncendie(Robot robot, Incendie incendie, int vol) {
		robot.deverserEau(vol); // BOOL ou INT ??? s'il ne reste pas assez d'eau
		incendie.eteindre(vol);
	}
	
	public void bougerRobot(Case newPosition, Robot robot) {
		
	}
	
	
	public String toString() {
		return carte + "\n" + incendies.allToString() + "\n" + robots.allToString(); 
	}
	
	
}

package briques;

import java.util.LinkedList;

import robots.Robot;


/**
 * Les donn�es d'une simulation sont :
 * une carte (avec des terrains),
 * une chaine d'incendies,
 * une chaine de robots (de toutes sortes)
 * Pour les chaines on utilise les LinkedList, il y a toute sorte de collection dans Java
 * j'ai choisi celle la car elle me semble être la plus simple d'utilisation
 * De nombreuses fonctions sont implementées dans ces listes comme add, remove, firstadd etc
 */
public class DonneesSimulation {
	
	
	private Carte carte;
	private LinkedList<Incendie> incendies;
	private LinkedList<Incendie> incendiesEteint;
	private LinkedList<Robot> robots;
	
	
	public DonneesSimulation(Carte carte, LinkedList<Incendie> incendies, LinkedList<Robot> robots) {
		this.carte = carte;
		this.incendies = incendies;
		this.robots = robots;
		this.incendiesEteint = new LinkedList<Incendie>();
		
	
	}
	
	public DonneesSimulation() {
		this(new Carte(), new LinkedList<Incendie>(), new LinkedList<Robot>());
	}
	
	
	
	//get

	
	public Carte getCarte() {
		return this.carte;
	}
	
	public LinkedList<Incendie> getIncendies() {
		return this.incendies;
	}
	
	public LinkedList<Robot> getRobots() {
		return this.robots;
	}
	
	
	//set
	public void setCarte(Carte carte) {
		this.carte = carte;
	}
	
	public void setIncendies(LinkedList<Incendie> incendies) {
		this.incendies = incendies;
	}
	
	public void setRobots(LinkedList<Robot> robots) {
		this.robots = robots;

	}
	
	//restart
	public void restartRobots() {
		for (Robot r : this.robots) {
			r.restartRobot();
		}
	}
	
	/**
	 * si la nouvelle position est voisine de la case du robot, ainsi que compatible avec le 
	 * robot, ce dernier va sur la nouvelle case
	 * @param newPosition
	 * @param robot
	 */
	/*
	public void bougerRobot(Case newPosition, Robot robot) {
		robot.deplacer(newPosition);
	}
	*/
	
	/**
	 * si la case voisine dans la direction dir de celle du robot est compatible avec le 
	 * robot, ce dernier va sur la nouvelle case
	 * @param newPosition
	 * @param robot
	 */
	/*
	public void bougerRobot(Direction dir, Robot robot) {
		try {
		Case newPosition = this.carte.getVoisin(robot.getPosition(), dir);
		bougerRobot(newPosition, robot);
		} catch (IllegalArgumentException e) {
			
		}
	}
	*/
	
	/* Ne pas oublier l'affichage des robots*/
	public String toString() {
		//return carte + "\n" + incendies.allToString() + robots.allToString();
		return "donneesSimuation";
	}
	
	
}

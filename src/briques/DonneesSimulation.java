package briques;

import java.util.NoSuchElementException;

import robots.Drone;
import robots.Robot;


/**
 * Les données d'une simulation sont :
 * une carte (avec des terrains),
 * une chaine d'incendies,
 * une chaine de robots (de toutes sortes)
 */
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
	
	
	/**
	 * action d'éteindre un incendie, enlève l'eau du réservoir du robot
	 * pour la déverser sur le feu
	 * @param robot
	 * @param incendie
	 * @param vol
	 */
	public void eteindreIncendie(Robot robot, Incendie incendie, int vol) {
		vol = robot.deverserEau(vol);
		incendie.eteindre(vol);
		if (incendie.getIntensite() == 0) {
			incendie = incendie.getSuivant();
		}
	}
	
	
	/**
	 * si la nouvelle position est voisine de la case du robot, ainsi que compatible avec le 
	 * robot, ce dernier va sur la nouvelle case
	 * @param newPosition
	 * @param robot
	 */
	public void bougerRobot(Case newPosition, Robot robot) {
		robot.deplacer(newPosition);
	}
	
	
	/**
	 * si la case voisine dans la direction dir de celle du robot est compatible avec le 
	 * robot, ce dernier va sur la nouvelle case
	 * @param newPosition
	 * @param robot
	 */
	public void bougerRobot(Direction dir, Robot robot) {
		try {
		Case newPosition = this.carte.getVoisin(robot.getPosition(), dir);
		bougerRobot(newPosition, robot);
		} catch (IllegalArgumentException e) {
			
		}
	}
	
	public String toString() {
		return carte + "\n" + incendies.allToString() + "\n" + robots.allToString(); 
	}
	
	
}

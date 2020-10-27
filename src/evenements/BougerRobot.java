package evenements;

import java.util.LinkedList;

import briques.Case;
import robots.Robot;
import simulation.Simulateur;

public class BougerRobot extends EvenementRobot{
	
	Case positionCible;
	
	BougerRobot(Robot robot, Case positionCible, Simulateur simulateur){
		super(robot, simulateur);
		this.positionCible = positionCible;
		LinkedList<Case> chemin = new LinkedList<Case>();
		for (Case c : chemin) {
			simulateur.BougerRobotUnitaire(c, robot);
		}
	}
	
	public int tempsActionRobot() {
		return 0; //--- partie entiere sup de la dur�e de l'action : t = d/v;
	}
	
	public void execute() {
	}
	
	
}

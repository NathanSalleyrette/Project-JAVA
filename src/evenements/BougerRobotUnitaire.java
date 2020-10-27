package evenements;
import briques.*;
import java.util.*;
import robots.*;
import simulation.Simulateur;


/**
 * 
 * @author nicolas
 * C'est l'evenement qui fait bouger le robot, il a comme argument le robot qui bouge
 * la direction dans laquelle il bouge et la carte 
 * sa fonction execute() fait bouger le robot
 */
public class BougerRobotUnitaire extends EvenementRobot {

	private Case position;
	
	/**
	 * peut etre appel� que sur une case accessible
	 * @param date
	 * @param direction
	 * @param robot
	 */
	public BougerRobotUnitaire(Direction dir, Robot robot, Simulateur simulateur) {
		super(robot, simulateur);
		
		try {
			Case futurePosition = robot.getCarte().getVoisin(robot.getPositionApresAction(), dir);
			annexeConstrutor(futurePosition, robot);			
		} catch (Exception e){
			System.out.println("erreur");
			super.setDateActionImpossible();
		}	
	}
	
	public BougerRobotUnitaire(Case futurePosition, Robot robot, Simulateur simulateur) {
		super(robot, simulateur);
		annexeConstrutor(futurePosition, robot);
	}
	
	//robot.isVoisine(futurePosition) &
	
	private void annexeConstrutor(Case futurePosition, Robot robot) {
		NatureTerrain futurTerrain = futurePosition.getNature();
		
		if (robot.isVoisinApresAction(futurePosition) && robot.isCompatible(futurTerrain)) { //sinon il n'y a pas de deplacement + pas besoin d'attendre pour le savoir
			super.setDateActionRobot();
			super.robot.setPositionApresAction(futurePosition);
		} else {
			super.setDateActionImpossible();
		}
		this.position = futurePosition;
		
	}
	
	
	
	/**
	 * temps que met le robot pour realiser l'action de bouger
	 */
	public int tempsActionRobot() {
		return (int)Math.ceil(robot.getCarte().getTailleCases()/robot.getVitesseCourante()); //--- partie entiere sup de la dur�e de l'action : t = d/v;
	}
	
	
	
	public void execute() {
		this.robot.deplacer(this.position);
		System.out.println(robot.getPosition() + " " + this.getDate());
	}
	
	
	public String toString() {
		return this.getDate() + " bouger robot vers : " + this.position;
	}

}

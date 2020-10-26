package evenements;
import briques.*;
import robots.*;


/**
 * 
 * @author nicolas
 * C'est l'evenement qui fait bouger le robot, il a comme argument le robot qui bouge
 * la direction dans laquelle il bouge et la carte 
 * sa fonction execute() fait bouger le robot
 */
public class BougerRobot extends EvenementRobot {

	private Direction direction;
	
	/**
	 * peut etre appelé que sur une case accessible
	 * @param date
	 * @param direction
	 * @param robot
	 */
	public BougerRobot(long date, Direction dir, Robot robot) {
		super(date, robot);
		
		try {
			Case futureCase = robot.getCarte().getVoisin(robot.getPositionApresAction(), dir);
			NatureTerrain futurTerrain = futureCase.getNature();
		
			if (robot.isCompatible(futurTerrain)) { //sinon il n'y a pas de deplacement + pas besoin d'attendre pour le savoir
				super.setDateActionRobot();
				super.robot.setPositionApresAction(futureCase);
			} else {
				super.setDateActionImpossible();
			}
		} catch (Exception e){
			System.out.println("erreur");
			super.setDateActionImpossible();
		}
		this.direction = dir;
		
	}
	
	
	/**
	 * temps que met le robot pour realiser l'action de bouger
	 */
	public int tempsActionRobot() {
		return (int)Math.ceil(robot.getCarte().getTailleCases()/robot.getVitesseCourante()); //--- partie entiere sup de la durée de l'action : t = d/v;
	}
	
	
	
	public void execute() {
		this.robot.deplacer(this.direction);
		System.out.println(robot.getPosition() + " " + this.getDate());
	}
	
	public String toString() {
		return this.getDate() + " " + "bouger robot vers : " + direction;
	}

}

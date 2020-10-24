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
public class BougerRobot extends Evenement {

	private Direction direction;
	private Robot robot;
	
	/**
	 * peut etre appelé que sur une case accessible
	 * @param date
	 * @param direction
	 * @param robot
	 */
	public BougerRobot(long date, Direction direction, Robot robot) {
		super(date);
		
		if (robot.getVitesseCourante() != 0.0) { //sinon il n'y a pas de deplacement, pas besoin d'attendre pour le savoir
			
			int last = (int)Math.ceil(robot.getPosition().getCarte().getTailleCases()/robot.getVitesseCourante()); //--- partie entiere sup de la durée de l'action : t = d/v
			
			long nvlleDate = Math.max(date, robot.getDateDisponible()) + last;
			
			super.setDate(nvlleDate);
			System.out.println(nvlleDate);
			robot.setDateDisponible(nvlleDate);
		}
		this.direction = direction;
		this.robot = robot;
		
	}
	
	// le try ne fonctionne pas (alors que dans DonneesSimulation il fonctionne)
	// Je catch toutes les exceptions qui arrivent, ca fonctionne
	public void execute() {
		try {
			Case newPosition = (this.robot.getPosition().getCarte()) . getVoisin(this.robot.getPosition(), this.direction);
			this.robot.deplacer(newPosition);
			} catch (Exception e) {
				System.out.println("On ne peut pas dÃ©placer le robot par là : " + direction.toString() + robot.toString() + " " + super.getDate());
			}
	}

}

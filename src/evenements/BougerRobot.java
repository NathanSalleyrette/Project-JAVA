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
	private Carte carte;
	
	public BougerRobot(long date, Direction direction, Robot robot,Carte carte) {
		super(date);
		this.direction = direction;
		this.robot = robot;
		this.carte = carte;
	}
	
	// le try ne fonctionne pas (alors que dans DonneesSimulation il fonctionne)
	// Je catch toutes les exceptions qui arrivent, ca fonctionne
	public void execute() {
		try {
			Case newPosition = this.carte.getVoisin(this.robot.getPosition(), this.direction);
			this.robot.deplacer(newPosition);
			} catch (Exception e) {
				System.out.println("On ne peut pas d√©placer le robot par l‡ : " + direction.toString() + robot.toString() + " " + super.getDate());
			}
	}

}

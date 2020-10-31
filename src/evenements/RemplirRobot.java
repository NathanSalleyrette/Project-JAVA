package evenements;

import robots.Robot;
import simulation.Simulateur;


/**
 * 
 * @author Lucie
 *le robot se rempli pr�s d'une case d'eau adjacente � lui
 */
public class RemplirRobot extends EvenementRobot {

	public RemplirRobot(Robot robot, Simulateur simulateur) {
		super(robot, simulateur);
		if (this.robot.eauAccessibleApresAction()) {
			super.setDateActionRobot();
			this.robot.setReserveApresAction(this.robot.getCapacity());
		} else {	
			super.setDateActionImpossible();
		}
		System.out.println("remplir en " + this.getDate());
	}
	
	/**
	 * temps d'un remplissage complet
	 */
	public int tempsActionRobot() {
		int last = (int)Math.ceil(super.robot.getTempsRemplissage()*60);
		System.out.println("last " + last);
		return last;
	}
	
	public void execute() {
		//System.out.println(this.robot);
		this.robot.remplirReserve();
		//System.out.println(this.robot);
	}
	
	public String toString() {
		return this.getDate() + " remplir robot";
	}
}

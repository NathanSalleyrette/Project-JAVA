package evenements;

import robots.Robot;
import simulation.Simulateur;


/**
 * 
 * @author Lucie
 *evenement qui concerne les robots
 */
public abstract class EvenementRobot extends Evenement{
	protected Robot robot;
	
	public EvenementRobot(Robot robot, Simulateur simulateur) {
		super(simulateur);
		this.robot = robot;
	}
	
	
	/**
	 * update la date de diponibilit� du robot + met l'evenment au bon moment
	 */
	public void setDateActionRobot() {
		int last = this.tempsActionRobot();
		// System.out.println("last " + last);
		long nvlleDate = Math.max(super.getDate(), this.robot.getDateDisponible()) + last;
		
		super.setDate(nvlleDate);
		this.robot.setDateDisponible(nvlleDate);
	}
	
	public void setDateActionImpossible() {
		super.setDate(super.getDate() - 1);
	}
	
	public abstract int tempsActionRobot();
}

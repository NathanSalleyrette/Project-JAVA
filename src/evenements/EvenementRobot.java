package evenements;

import robots.Robot;


/**
 * 
 * @author Lucie
 *evenement qui concerne les robots
 */
public abstract class EvenementRobot extends Evenement{
	protected Robot robot;
	
	public EvenementRobot(long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
	
	/**
	 * update la date de diponibilité du robot + met l'evenment au bon moment
	 */
	public void setDateActionRobot() {
		int last = this.tempsActionRobot();
		System.out.println("last " + last);
		long nvlleDate = Math.max(super.getDate(), this.robot.getDateDisponible()) + last;
		
		super.setDate(nvlleDate);
		this.robot.setDateDisponible(nvlleDate);
	}
	
	public abstract int tempsActionRobot();
}

package evenements;

import robots.Robot;

public abstract class EvenementRobot extends Evenement{
	protected Robot robot;
	
	public EvenementRobot(long date, Robot robot) {
		super(date);
		this.robot = robot;
	}
	
	public void setDateActionRobot() {
		int last = this.tempsActionRobot();
		long nvlleDate = Math.max(super.getDate(), this.robot.getDateDisponible()) + last;
		
		super.setDate(nvlleDate);
		this.robot.setDateDisponible(nvlleDate);
	}
	
	public abstract int tempsActionRobot();
}

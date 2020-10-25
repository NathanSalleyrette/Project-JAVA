package evenements;

import robots.Robot;

public class RemplirRobot extends EvenementRobot {
	int vol;

	public RemplirRobot(long date, int vol, Robot robot) {
		super(date, robot);
		this.vol = vol;
	}
	
	public int tempsActionRobot() {
		return (int)Math.ceil(super.robot.getTempsRemplissage());
	}
	
	public void execute() {
		this.robot.remplirReserve();
	}
}

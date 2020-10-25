package evenements;

import robots.Robot;


/**
 * 
 * @author Lucie
 *le robot se rempli près d'une case d'eau adjacente à lui
 */
public class RemplirRobot extends EvenementRobot {
	int vol;

	public RemplirRobot(long date, int vol, Robot robot) {
		super(date, robot);
		this.vol = vol;
	}
	
	/**
	 * temps d'un remplissage complet
	 */
	public int tempsActionRobot() {
		return (int)Math.ceil(super.robot.getTempsRemplissage());
	}
	
	public void execute() {
		this.robot.remplirReserve();
	}
}

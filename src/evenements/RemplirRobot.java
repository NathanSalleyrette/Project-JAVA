package evenements;

import robots.Robot;


/**
 * 
 * @author Lucie
 *le robot se rempli près d'une case d'eau adjacente à lui
 */
public class RemplirRobot extends EvenementRobot {

	public RemplirRobot(long date, Robot robot) {
		super(date, robot);
		if (this.robot.eauVoisineApresAction()) {
			super.setDateActionRobot();
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
		System.out.println(this.robot);
		this.robot.remplirReserve();
		System.out.println(this.robot);
	}
	
	public String toString() {
		return this.getDate() + " remplir robot";
	}
}

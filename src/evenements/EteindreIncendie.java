package evenements;


import briques.Incendie;
import robots.Robot;


/**
 * �venement pour �teindre les incendies
 * @author Lucie
 *
 */
public class EteindreIncendie extends EvenementRobot {
	private Incendie incendie;
	private int vol;
	
	
	/**
	 * creation de l'vent : un incendie est eteint par un robot gr�ce � un certain vol d'eau
	 * hyp : robot et incendie sont sur la meme case
	 * @param incendie
	 * @param robot
	 * @param vol
	 */
	public EteindreIncendie(long date, Incendie incendie, Robot robot, int vol) {
		super(date, robot);
		super.setDateActionRobot();
		this.incendie = incendie;
		this.vol = vol;
	}
	
	public EteindreIncendie(long date, Incendie incendie, Robot robot) {
		this(date, incendie, robot, robot.getInterventionUnitaire());
	}
	
	
	/**
	 * temps pour r�aliser l'action d'eteindre l'incendie d'une quantit� vol (converti en intervention unitaire)
	 */
	public int tempsActionRobot() {
		return this.robot.getTempsExtinction(vol);
	}
	
	
	/**
	 * action d'�teindre un incendie, enl�ve l'eau du r�servoir du robot
	 * pour la d�verser sur le feu
	 * @param robot
	 * @param incendie
	 * @param vol
	 */
	public void execute() {
		this.eteindreIncendie();
	}
	
	/*on peut mettre ce block dans incendie ou robot*/
	public void eteindreIncendie() {
		this.vol = this.robot.deverserEau(this.vol);
		this.incendie.eteindre(this.vol);
		System.out.println(this.incendie);
		if (this.incendie.getIntensite() == 0) {
			this.incendie.getIncendies().remove(incendie);
		}	
	}
	
	
	
}

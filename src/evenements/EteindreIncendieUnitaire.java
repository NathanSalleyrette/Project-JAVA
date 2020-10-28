package evenements;


import briques.Incendie;
import robots.Robot;
import simulation.Simulateur;


/**
 * évenement pour éteindre les incendies
 * @author Lucie
 *
 */
public class EteindreIncendieUnitaire extends EvenementRobot {
	private Incendie incendie;
	private int vol;
	
	
	/**
	 * creation de l'e
	 * vent : un incendie est eteint par un robot grâce à un certain vol d'eau
	 * hyp : robot et incendie sont sur la meme case
	 * @param incendie
	 * @param robot
	 * @param vol
	 */
	/*
	public EteindreIncendieUnitaire(Incendie incendie, Robot robot, int vol, Simulateur simulateur) {
		super(robot, simulateur);
		this.incendie = incendie;
		this.vol = vol;
		super.setDateActionRobot();
	}
	*/
	
	public EteindreIncendieUnitaire(Incendie incendie, Robot robot, Simulateur simulateur) {
		super(robot, simulateur);
		this.incendie = incendie;
		this.vol = robot.getInterventionUnitaire();
		if (incendie.getIntensite() != 0 && robot.getReserve() !=0 && robot.getPositionApresAction() == incendie.getPosition()) {
			int last = this.tempsActionRobot();
			long nvlleDate = super.getDate() + last; //le robot est disponible car cet event est issu de l'appel d'un plus gros incendie
			super.setDate(nvlleDate);
			
		} else {
			super.setDateActionImpossible();
			System.out.println("l'incendie est deja eteint ou le robot et l'incendie ne sont pas sur la meme case ou le robot est vide");
		}
	} 
	
	
	/**
	 * temps pour réaliser l'action d'eteindre l'incendie d'une quantité vol (converti en intervention unitaire)
	 */
	public int tempsActionRobot() {
		return (int)Math.ceil(this.robot.getTempsExtinctionUnitaire());
	}
	
	
	/**
	 * action d'ï¿½teindre un incendie, enlï¿½ve l'eau du rï¿½servoir du robot
	 * pour la dï¿½verser sur le feu
	 * @param robot
	 * @param incendie
	 * @param vol
	 */
	public void execute() {
		this.eteindreIncendie();
		if (this.incendie.getIntensite() != 0) {
			System.out.println("again !");
			this.getSimulateur().EteindreIncendieUnitaire(this.incendie, this.robot);
		} else {
			this.robot.setDateDisponible(this.getDate()); //le robot est de nouveau disponible, peut-etre prematurement, on sait pas
		}
	}
	
	/*on peut mettre ce block dans incendie ou robot*/
	public void eteindreIncendie() {
		this.vol = this.robot.deverserEau(this.vol);
		this.incendie.eteindre(this.vol);
		System.out.println(this.incendie);
 

	}
	
	public String toString() {
		return this.getDate() + " eteindre incendie avec " + this.vol + " litres";
	}
	
	
	
}

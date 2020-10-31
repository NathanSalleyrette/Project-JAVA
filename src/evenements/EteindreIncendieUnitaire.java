package evenements;


import briques.Incendie;
import briques.Type;
import robots.Robot;
import simulation.Simulateur;


/**
 * �venement pour �teindre les incendies
 * @author Lucie
 *
 */
public class EteindreIncendieUnitaire extends EvenementRobot {
	private Incendie incendie;
	private int vol;
	
	
	/**
	 * creation de l'e
	 * vent : un incendie est eteint par un robot gr�ce � un certain vol d'eau
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
		if (incendie.getIntensite() != 0 && robot.getReserve() !=0 && robot.getPosition() == incendie.getPosition()) {
			int last = this.tempsActionRobot();
			// la reserve après l'execution sera la reserve - l'intervention unitaire
			// si le robot est de type PATTES, la reserve ne change jamais
			if (robot.getType() != Type.PATTES)
				robot.setReserveApresAction(Math.max(0, robot.getReserveApresAction() - robot.getInterventionUnitaire()));
			long nvlleDate = super.getDate() + last; //le robot est disponible car cet event est issu de l'appel d'un plus gros incendie
			super.setDate(nvlleDate);
			
		} else {
			System.out.println(incendie.getIntensite());
			System.out.println(robot.getPositionApresAction().toString());
			System.out.println(incendie.getPosition().toString());
			System.out.println(robot.getReserve());
			super.setDateActionImpossible();
			System.out.println("l'incendie est deja eteint ou le robot et l'incendie ne sont pas sur la meme case ou le robot est vide");
		}
	} 
	
	
	/**
	 * temps pour r�aliser l'action d'eteindre l'incendie d'une quantit� vol (converti en intervention unitaire)
	 */
	public int tempsActionRobot() {
		return (int)Math.ceil(this.robot.getTempsExtinctionUnitaire());
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
		// Lignes de code assez bizarre, si on définit un chemin après avoir verser de l'eau
		// le fait que le robot soit liberer et meme le fait de ré éteindre l'incendie est bizarre
		if (this.robot.getReserve() != 0 && this.incendie.getIntensite() != 0) {
			this.getSimulateur().EteindreIncendieUnitaire(this.incendie, this.robot);
		} else {
			this.robot.setDateDisponible(this.getDate());
		}
	}
		/**	if (this.incendie.getIntensite() != 0) {
			System.out.println("again !");
			this.getSimulateur().EteindreIncendieUnitaire(this.incendie, this.robot);
		} else {
			this.robot.setDateDisponible(this.getDate()); //le robot est de nouveau disponible, peut-etre prematurement, on sait pas
		}*/
	
	
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

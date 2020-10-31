package evenements;

import briques.Incendie;
import briques.*;
import robots.Robot;
import simulation.Simulateur;

public class EteindreIncendie extends EvenementRobot {
	private Incendie incendie;
	private int vol;
	
	public EteindreIncendie(Incendie incendie, Robot robot, Simulateur simulateur) {
		super(robot, simulateur);
		this.incendie = incendie;
		if (robot.getType() == Type.PATTES) this.vol = incendie.getIntensite();
		else
			this.vol = Math.min(incendie.getIntensite(), robot.getReserveApresAction());
		//le robot sera occup� tout le temps de d'eteindre le feu, on le tiens occup� tout ce temps
		if (incendie.getIntensite() != 0 && robot.getReserveApresAction() !=0 && robot.getPositionApresAction() == incendie.getPosition()) {
			int last = this.tempsActionRobot();
			long nvlleDate = Math.max(super.getDate(), this.robot.getDateDisponible()) + last;
			this.setDate(Math.max(super.getDate(), this.robot.getDateDisponible()));
			this.robot.setDateDisponible(nvlleDate);
			// Lorsque l'evenement sera executé, l'incendie aura cette intensité
			// On n'utilise pas cette donnée
			this.incendie.setIntensiteApresAction(incendie.getIntensiteApresAction() - robot.getCapacity());
	
			
			
		} else {
			super.setDateActionImpossible();
			System.out.println("l'incendie est deja eteint ou le robot et l'incendie ne sont pas sur la meme case ou le robot est vide");
		}
	} 
	
	
	/**
	 * temps pour r�aliser l'action d'eteindre l'incendie d'une quantit� vol (converti en intervention unitaire)
	 */
	public int tempsActionRobot() {
		if (robot.getType() == Type.DRONE) return 30;
		return (int)Math.ceil(this.robot.getTempsExtinctionUnitaire() * this.vol/ this.robot.getInterventionUnitaire());
	}
	
	
	
	/**
	 * action d'�teindre un incendie, enl�ve l'eau du r�servoir du robot
	 * pour la d�verser sur le feu
	 * @param robot
	 * @param incendie
	 * @param vol
	 */
	public void execute() {
		this.getSimulateur().EteindreIncendieUnitaire(this.incendie, this.robot);
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

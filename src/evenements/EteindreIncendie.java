package evenements;

import briques.Incendie;
import robots.Robot;
import simulation.Simulateur;

public class EteindreIncendie extends EvenementRobot {
	private Incendie incendie;
	private int vol;
	
	public EteindreIncendie(Incendie incendie, Robot robot, Simulateur simulateur) {
		super(robot, simulateur);
		this.incendie = incendie;
		this.vol = Math.min(incendie.getIntensite(), robot.getReserve());
		//le robot sera occupé tout le temps de d'eteindre le feu, on le tiens occupé tout ce temps
		if (incendie.getIntensite() != 0 && robot.getReserve() !=0 && robot.getPositionApresAction() == incendie.getPosition()) {
			int last = this.tempsActionRobot();
			long nvlleDate = Math.max(super.getDate(), this.robot.getDateDisponible()) + last;
			this.setDate(Math.max(super.getDate(), this.robot.getDateDisponible()));
			this.robot.setDateDisponible(nvlleDate);
			
			
		} else {
			super.setDateActionImpossible();
			System.out.println("l'incendie est deja eteint ou le robot et l'incendie ne sont pas sur la meme case ou le robot est vide");
		}
	} 
	
	
	/**
	 * temps pour réaliser l'action d'eteindre l'incendie d'une quantité vol (converti en intervention unitaire)
	 */
	public int tempsActionRobot() {
		return (int)Math.ceil(this.robot.getTempsExtinctionUnitaire() * this.vol/ this.robot.getInterventionUnitaire());
	}
	
	
	
	/**
	 * action d'ï¿½teindre un incendie, enlï¿½ve l'eau du rï¿½servoir du robot
	 * pour la dï¿½verser sur le feu
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

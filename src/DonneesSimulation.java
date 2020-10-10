
public class DonneesSimulation {
	Carte carte;
	Incendie incendies;
	Robot robots;
	
	
	public void eteindreIncendie(Robot robot, Incendie incendie, int vol) {
		robot.deverserEau(vol); // BOOL ou INT ??? s'il ne reste pas assez d'eau
		incendie.eteindre(vol);
	}
	
	public void bougerRobot(Case newPosition, Robot robot) {
		
	}
	
	
}


public class TestCarte {

	public static void main(String[] args) {
		// Test pour la carte et les cases
		Case c = new Case(9,5);
		System.out.println("case_1" + c);
		Carte ct1 = new Carte(2, 4); //matrice 2x4
		System.out.println("carte_1" + ct1);
		NatureTerrain m[][] = {
				{NatureTerrain.EAU, NatureTerrain.FORET, NatureTerrain.EAU},
				{NatureTerrain.ROCHE, NatureTerrain.EAU, NatureTerrain.TERRAIN_LIBRE}
		};
		Carte ct2 = new Carte(m);
		System.out.println("carte_2" + ct2);
		System.out.println(ct2.getVoisin(ct2.getCase(0, 0), Direction.OUEST));
	}

}

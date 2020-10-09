
public class Incendie {
	private Case position;
	private int litre;
	
	
	//constructeurs
	public Incendie() {
		this.position = new Case();
		this.litre = 0;
	}
	
	public Incendie(Case position, int litre) {
		// copie legère pour pouvoir pointer sur une case de la carte
		this.position = position;
		this.litre = litre;
	}
	
	//accès aux données
	public Case getCase() {
		return this.position;
	}
	
	public int getLitre() {
		return this.litre;
	}
}


public class Case {
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	
	public Case() {
		this(0,0);
	}
	
	public Case(int ligne, int colonne, NatureTerrain terrain) {
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = terrain;
	}
	public Case(int ligne, int colonne) {
		this(ligne, colonne, NatureTerrain.TERRAIN_LIBRE);
	}
	
	
	//changer terrain
	public void setTerrain(NatureTerrain terrain) {
		this.nature = terrain;
	}
	
	
	// acéder aux valeur
	public int getLigne() {
		return this.ligne;
	}
	
	public int getColonne() {
		return this.colonne;
	}
	
	public NatureTerrain getNature() {
		return this.nature;
	}
	
	
	//print
	public String toString() {
		//return "(" + this.ligne + "," + this.colonne + "), terrain : " + this.terrain;
		return this.nature.toString();
	}
}

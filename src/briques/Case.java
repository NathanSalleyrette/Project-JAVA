package briques;

/**
 * 
 * Une case est definie par sa position, ie sa ligne et sa colonne.
 * Une case represente un terrain, cd NatureTerrain
 *
 */
public class Case{
	private int ligne;
	private int colonne;
	private NatureTerrain nature;
	private Carte carte;
	
	
	/**
	 * case par defaut en 0,0, terrain libre
	 */
	/*
	public Case() {
		this(0,0);
	}
	*/
	/**
	 * Case en position ligne et colonne, elle est de nature terrain
	 * @param ligne
	 * @param colonne
	 * @param terrain
	 */
	public Case(int ligne, int colonne, NatureTerrain terrain, Carte carte) {
		if (ligne < 0 || colonne < 0) {
			throw new IllegalArgumentException("dimension invalide");
		}
		this.ligne = ligne;
		this.colonne = colonne;
		this.nature = terrain;
		this.carte = carte;
	}
	
	/**
	 * Case en position ligne et colonne, terrain libre
	 * @param ligne
	 * @param colonne
	 */
	public Case(int ligne, int colonne, Carte carte) {
		this(ligne, colonne, NatureTerrain.TERRAIN_LIBRE, carte);
	}
	
	
	/**
	 * change le terrain de la case
	 * @param terrain
	 */
	public void setTerrain(NatureTerrain terrain) {
		this.nature = terrain;
	}
	
	
	// accéder aux valeur
	public int getLigne() {
		return this.ligne;
	}
	
	public int getColonne() {
		return this.colonne;
	}
	
	public NatureTerrain getNature() {
		return this.nature;
	}
	
	public Carte getCarte() {
		return this.carte;
	}
	
	
	/**
	 * exemple:
	 * (2,3) TERRAIN_LIBRE
	 */
	public String toString() {
		return "(" + this.ligne + "," + this.colonne + ") " + this.nature;
		//return this.nature.toString();
	}
}

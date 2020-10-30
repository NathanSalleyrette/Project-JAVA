package briques;
import java.util.LinkedList;
import java.util.StringJoiner;


import robots.Robot;

public class Carte {
	private int nbLignes;
	private int nbColonnes;
	private int tailleCases; //distance � parcourir que repr�sente une case
	private Case[][] m;
	
	//variables globales
	
	public static int tailleCasesDefault = 10000;
	//constructeurs
	
	public Carte() {
		this(1, 1, 1);
	}
	
	/**
	 * creer une carte avec nbLignes lignes, nbColonnes colonnes, longueur de la case tailleCases
	 * rempli la carte avec des terrains libres
	 * @param nbLignes
	 * @param nbColonnes
	 * @param tailleCases
	 */
	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCases = tailleCases;
		if (nbLignes <= 0 || nbColonnes <= 0) {
			throw new IllegalArgumentException("dimension invalide");
		}
		this.m = new Case[nbLignes][nbColonnes];
		for (int i = 0; i < m.length; i++) {
			for(int j = 0; j <m[0].length; j++) {
				this.m[i][j] = new Case(i, j);
			}
		}
		//Case.setCarte(this);
	}
	
	
	/**
	 * creer une carte avec nbLignes lignes, nbColonnes colonnes et cot� par default, terrains libres
	 * @param nbLignes
	 * @param nbColonnes
	 */
	public Carte(int nbLignes, int nbColonnes) {
		this(nbLignes, nbColonnes, tailleCasesDefault);
	}
	
	
	/**
	 * creer une carte � partir d'une matrice de terrain (meme dim que la carte) , cote tailleCases
	 * @param t
	 * @param tailleCases
	 */
	public Carte(NatureTerrain t[][], int tailleCases) {
		this.nbLignes = t.length;
		this.nbColonnes = t[0].length;
		this.tailleCases = tailleCases;
		this.m = new Case[t.length][t[0].length];
		
		for (int i = 0; i < t.length; i++) {
			for(int j = 0; j <t[0].length; j++) {
				this.m[i][j] = new Case(i, j, t[i][j]);
			}
		}
	}
	
	
	/**
	 * creer une carte � partir d'une matrice de terrain (meme dim que la carte) , cote par defaut de 2
	 * @param t
	 */
	public Carte(NatureTerrain t[][]) {
		this(t, tailleCasesDefault);
	}
	
	/**
	 * remplace la matrice de terrain d'une carte dej� existante. OBSOLETE ?
	 * @param t
	 */
	public void copie_terrain(NatureTerrain t[][]) {
		if (t.length != this.m.length || t[0].length != this.m.length) {
			throw new IllegalArgumentException("la matrice propos�e n'a pas les bonne dimension");
		}
		for (int i = 0; i < m.length; i++) {
			for(int j = 0; j <m[0].length; j++) {
				this.m[i][j].setTerrain(t[i][j]);;
			}
		}
	}
	
	
	//acc�s aux donn�es
	public int getNbLignes() {
		return this.nbLignes;
	}
	
	public int getNbColonnes() {
		return this.nbColonnes;
	}
	
	public int getTailleCases() {
		return this.tailleCases;
	}

	public Case getCase(int lig, int col) {
		//copie l�g�re
		return this.m[lig][col];
	}
	
	public Case[][] getCarte() {
		return m;
	}
	
	
	/**
	 * renvoie true si la cases src poss�de un voisin dans la direction  dir
	 * @param src
	 * @param dir
	 * @return la case poss�de une voisin ?
	 */
	public Boolean voisinExiste(Case src, Direction dir) {
		switch (dir) {
		case NORD:
			return !(src.getLigne() == 0);
		case SUD:
			return !(src.getLigne() == this.nbLignes);
		case EST:
			return !(src.getColonne() == this.nbColonnes);
		case OUEST:
			return !(src.getColonne() == 0);
		default:
			throw new IllegalArgumentException("euuuh quelle direction ?");
		}
	}
	
	/**
	 * renvoie la case voisine de src dans la direction dir, "pas de voisin" s'il n'y en a pas
	 * @param src
	 * @param dir
	 * @return case voisine
	 */
	public Case getVoisin(Case src, Direction dir) {
		//axe y invers� !!
		if (!(this.voisinExiste(src, dir))) throw new IllegalArgumentException("pas de voisin dans la direction : "+ dir);
		int x = 0;
		int y = 0;
		switch (dir) {
		case NORD:
			y = -1;
			break;
		case SUD:
			y = 1;
			break;
		case EST:
			x = 1;
			break;
		case OUEST:
			x = -1;
			break;
		default:
			throw new IllegalArgumentException("euuuh quelle direction ?");
		}
		return this.m[src.getLigne() + y][src.getColonne() + x];
	}

	
	
	/**
	 * renvoie l'ensemble des cases adjacentes � la case src
	 * @param src
	 * @return
	 */
	public LinkedList<Case> getVoisins(Case src) {  //cases sera non null
		LinkedList<Case> cases = new LinkedList<Case>();
		try {
		cases.add(this.getVoisin(src, Direction.NORD));
		} catch (Exception e) {}
		try {
		cases.add(this.getVoisin(src, Direction.SUD));
		} catch (Exception e) {}
		try {
		cases.add(this.getVoisin(src, Direction.EST));
		} catch (Exception e) {}
		try {
		cases.add(this.getVoisin(src, Direction.OUEST));
		} catch (Exception e) {}
		return cases;
	}
	
	
	/**
	 * dit si de l'eau est adjacente � la case
	 * @param src
	 * @return
	 */
	public Boolean eauVoisine(Case src) {
		for (Case c : this.getVoisins(src)) {
			if (c.getNature() == NatureTerrain.EAU) {
				return true;
			}
		}
		return false;
	}
	
	
	public Case tranformeNombreCase(int numeroCase) {
    	int ligne = (int) numeroCase / this.getNbColonnes();
    	int colonne = (int) numeroCase - ligne * this.getNbColonnes();
    	return this.getCase(ligne, colonne);
    }
    
    public int transformeNombreCase(Case c) {
    	return this.getNbColonnes() * c.getLigne() + c.getColonne();
    }
	
	/**
	 * On consid�re que le robot n'apparait pas sur une case qui n'est pas compatible avec lui, ie vitesse =0.0
	 * @param robot
	 * @param dir
	 * @return
	 */
	public int getTempsDeplacement(Robot robot, Direction dir){
		return (int)Math.ceil(this.getTailleCases()/robot.getVitesseCourante());
	}
	
	
	
	/**
	 * exemple:
	 * dim: 2x3, cot�: 2
	 * (
	 * Libre, Libre, Libre,
	 * Roche, Libre, Eau)
	 */
	public String toString() {
		StringJoiner stringJoiner2 =  new StringJoiner(", ",  "(",  ")") ;
		for (int i=0; i<this.m.length; i++) {
			StringJoiner stringJoiner1 =  new StringJoiner(", ",  "(",  ")") ;
			for (int j=0; j<this.m[0].length; j++) {
				stringJoiner1.add(this.m[i][j].toString());
				
			}
			stringJoiner2.add("\n"+ stringJoiner1.toString());
		}
		return "dim: " + this.nbLignes + "x" + this.nbColonnes + ", cot� : " + this.tailleCases + "\n " + stringJoiner2.toString();
	}
}

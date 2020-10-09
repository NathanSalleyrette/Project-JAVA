import java.util.StringJoiner;

public class Carte {
	private int nbLignes;
	private int nbColonnes;
	private int tailleCases;
	private Case[][] m;
	
	
	//constructeurs
	public Carte(int nbLignes, int nbColonnes, int tailleCases) {
		//rempli la carte avec des terrain libre, le coté et la dimension sont donnée
		this.nbLignes = nbLignes;
		this.nbColonnes = nbColonnes;
		this.tailleCases = tailleCases;
		this.m = new Case[nbLignes][nbColonnes];
		if (nbLignes <= 0 || nbColonnes <= 0) {
			throw new IllegalArgumentException("dimension invalide");
		}
		for (int i = 0; i < m.length; i++) {
			for(int j = 0; j <m[0].length; j++) {
				this.m[i][j] = new Case(i, j);
			}
		}
	}
	
	public Carte(int nbLignes, int nbColonnes) {
		//rempli la carte avec des terrain libre, la dimension est donnée
		this(nbLignes, nbColonnes, 2);
	}
	
	public Carte(NatureTerrain t[][]) {
		//faire une carte à partir d'une matrice de terrain, coté par defaut de 2
		this.nbLignes = t.length;
		this.nbColonnes = t[0].length;
		this.tailleCases = 2;
		this.m = new Case[t.length][t[0].length];
		
		for (int i = 0; i < t.length; i++) {
			for(int j = 0; j <t[0].length; j++) {
				this.m[i][j] = new Case(i, j, t[i][j]);
			}
		}
	}
	
	
	public void copie_terrain(NatureTerrain t[][]) {
		// on copie un terrain dans une matrice deja existante, peut-etre OBSOLETE
		if (t.length != this.m.length || t[0].length != this.m.length) {
			throw new IllegalArgumentException("la matrice proposée n'a pas les bonne dimension");
		}
		for (int i = 0; i < m.length; i++) {
			for(int j = 0; j <m[0].length; j++) {
				this.m[i][j].setTerrain(t[i][j]);;
			}
		}
	}
	
	
	//accès aux données
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
		//copie légère
		return this.m[lig][col];
	}
	
	
	public Boolean voisinExiste(Case src, Direction dir) {
		switch (dir) {
		case NORD:
			return !(src.getLigne() == this.nbLignes);
		case SUD:
			return !(src.getLigne() == 0);
		case EST:
			return !(src.getColonne() == this.nbColonnes);
		case OUEST:
			return !(src.getColonne() == 0);
		default:
			throw new IllegalArgumentException("euuuh quelle direction ?");
		}
	}
	
	
	public Case getVoisin(Case src, Direction dir) {
		//axe y inversé !!
		if (!(this.voisinExiste(src, dir))) throw new IllegalArgumentException("pas de voisin");
		int x = 0;
		int y = 0;
		switch (dir) {
		case NORD:
			y = 1;
			break;
		case SUD:
			y = -1;
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
	
	//fonction pour print
	public String toString() {
		StringJoiner stringJoiner2 =  new StringJoiner(", ",  "(",  ")") ;
		System.out.println(this.m.length + "x" + this.m[0].length);
		for (int i=0; i<this.m.length; i++) {
			StringJoiner stringJoiner1 =  new StringJoiner(", ",  "(",  ")") ;
			for (int j=0; j<this.m[0].length; j++) {
				stringJoiner1.add(this.m[i][j].toString());
				
			}
			stringJoiner2.add("\n"+ stringJoiner1.toString());
		}
		return "dim: " + this.nbLignes + "x" + this.nbColonnes + ", coté : " + this.tailleCases + "\n " + stringJoiner2.toString();
	}
}

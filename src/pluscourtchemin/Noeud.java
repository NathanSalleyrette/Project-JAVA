package pluscourtchemin;

import briques.*;
import java.util.*;

/**
 * 
 * On se sert de la structure de noeud pour l'algorithme de
 * Dijkstra, il comporte le temps qu'on met pour aller d'une case
 * à la case numérotée numbergraph
 * 
 * L'argument Case c n'est peut etre pas indispensable (a changer)
 */

public class Noeud implements Comparable<Noeud>{
		
	private int time;
	private Case c;
	private int numeroCase;
	
	public Noeud(int time, Case c, int numeroCase) {
		this.time = time;
		this.c = c;
		this.numeroCase = numeroCase;
	}

	public int getTime() {
		return time;
	}
	
	public Case getCase() {
		return c;
	}
	
	public int getNumeroCase() {
		return numeroCase;
	}
	
	@Override
	public int compareTo(Noeud other) {
		if (this.getTime() < other.getTime()) return -1;
		if (this.getTime() > other.getTime()) return 1;
		return 0;
	}
}

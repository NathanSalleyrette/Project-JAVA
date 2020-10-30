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

public class Noeud{
		
	private int time; // temps pour parcourir la case
	private int numeroCase;
	
	public Noeud(int time, int numeroCase) {
		this.time = time;
		this.numeroCase = numeroCase;
	}

	public int getTime() {
		return time;
	}
	
	public void setTime(int time) { 
		this.time = time;
	}
	
	public int getNumeroCase() {
		return numeroCase;
	}
	
	public String toString() {
		return "(" + time + ", num : " + numeroCase + ")";
	}
	
	
}

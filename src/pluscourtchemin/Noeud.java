package pluscourtchemin;

import briques.*;
import java.util.*;

public class Noeud implements Comparable<Noeud>{
		
	private int time;
	private Case c;
	private int numbergraph;
	
	public Noeud(int time, Case c, int numbergraph) {
		this.time = time;
		this.c = c;
		this.numbergraph = numbergraph;
	}

	public int getTime() {
		return time;
	}
	
	public Case getCase() {
		return c;
	}
	
	public int getNumberGraph() {
		return numbergraph;
	}
	
	@Override
	public int compareTo(Noeud other) {
		if (this.getTime() < other.getTime()) return -1;
		if (this.getTime() > other.getTime()) return 1;
		return 0;
	}
}

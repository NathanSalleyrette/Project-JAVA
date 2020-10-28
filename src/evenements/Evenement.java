package evenements;

import simulation.Simulateur;

/**
 * 
 * @author nicolas
 * Evenement est une classe abstraite, chaque evenement a une date d'execution
 * et une fonction execute() qui est définie dans chaque sous-classe
 */
public abstract class Evenement {
	private long date;
	private Simulateur simulateur;
	
	public Evenement(Simulateur simulateur) {
		this.date = simulateur.getDateSimulation();
		this.simulateur = simulateur;
	}
	
	public abstract void execute();
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}
	
	public Simulateur getSimulateur() {
		return simulateur;
	}
	
	public String toString() {
		return this.getDate() + " evenement : ";
	}
	
	

}

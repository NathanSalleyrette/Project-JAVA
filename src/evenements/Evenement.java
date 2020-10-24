package evenements;


/**
 * 
 * @author nicolas
 * Evenement est une classe abstraite, chaque evenement a une date d'execution
 * et une fonction execute() qui est définie dans chaque sous-classe
 */
public abstract class Evenement {
	private long date;
	
	public Evenement(long date) {
		this.date = date;
	}
	
	public abstract void execute();
	
	public long getDate() {
		return date;
	}
	
	public void setDate(long date) {
		this.date = date;
	}

}

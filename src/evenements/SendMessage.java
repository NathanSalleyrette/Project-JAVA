package evenements;

import simulation.Simulateur;

/**
 * 
 * @author nicolas
 * EvenementMessage est l'evenement qui envoie un message dans le terminal
 * c'est l'evenement "test" défini dans le sujet
 */
public class SendMessage extends Evenement {
	private String message;
	
	public SendMessage(String message, Simulateur simulateur) {
		super(simulateur);
		this.message = message;
	}

	public void execute() {
		System.out.println(this.getDate() + this.message);
	}
}

package evenements;

/**
 * 
 * @author nicolas
 * EvenementMessage est l'evenement qui envoie un message dans le terminal
 * c'est l'evenement "test" défini dans le sujet
 */
public class EvenementMessage extends Evenement {
	private String message;
	
	public EvenementMessage(long date, String message) {
		super(date);
		this.message = message;
	}

	public void execute() {
		System.out.println(this.getDate() + this.message);
	}
}

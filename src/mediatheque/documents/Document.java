package mediatheque.documents;

import mediatheque.Abonne;
import mediatheque.IDocument;

public abstract class Document implements IDocument{
	
	private int num;
	protected Abonne emprunteur;
	protected Abonne reserveur;
	protected Etat etat;

	public Document(int num) {
		this.num = num;
		this.emprunteur = null;
		this.reserveur = null;
		this.etat = Etat.Disponible;
	}

	@Override
	public int numero() {
		return this.num;
	}

	@Override
	public Abonne emprunteur() {
		return emprunteur;
	}

	@Override
	public Abonne reserveur() {
		return reserveur;
	}

	@Override
	public abstract void reservationPour(Abonne ab);
	
	@Override
	public  abstract void empruntPar(Abonne ab);
	
	@Override
	public abstract void retour();
	
}

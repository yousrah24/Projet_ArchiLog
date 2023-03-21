package mediatheque.object;


import java.sql.Date;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public class ConcurrentAbonne implements IAbonne {

	private static final long serialVersionUID = 1L;
	private IAbonne abonne;
	
	

	public ConcurrentAbonne(IAbonne abonne) {
		this.abonne = abonne;
	}

	@Override
	public int numero() {
		return abonne.numero();
	}

	@Override
	public String getNom() {
		return abonne.getNom();
	}

	@Override
	public Date getDateNaiss() {
		return abonne.getDateNaiss();
	}

	@Override
	public boolean estAdulte() {
		return abonne.estAdulte();
	}

	@Override
	public void reserver(IDocument doc) {
		synchronized (abonne) {
			abonne.reserver(doc);
		}
	}

	@Override
	public void emprunter(IDocument doc) {
		synchronized (abonne) {
			abonne.emprunter(doc);
		}
	}

	@Override
	public void retourner(IDocument doc) {
		synchronized (abonne) {
			abonne.retourner(doc);
		}
	}

	@Override
	public void retour(IDocument doc) {
		abonne.retour(doc);
	}

}

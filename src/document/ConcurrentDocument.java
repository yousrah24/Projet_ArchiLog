package document;


import java.sql.Date;
import java.time.LocalDateTime;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public class ConcurrentDocument implements IDocument{
	
	private IDocument doc;
	
	

	public ConcurrentDocument(IDocument doc) {
		this.doc = doc;
	}

	@Override
	public int numero() {
		return doc.numero();
	}

	@Override
	public IAbonne emprunteur() {
		return doc.emprunteur();
	}

	@Override
	public IAbonne reserveur() {
		return doc.reserveur();
	}

	@Override
	public void reservationPour(IAbonne ab) throws RestrictionException {
		synchronized (doc) {
			doc.reservationPour(ab);
		}
	}

	@Override
	public void empruntPar(IAbonne ab) throws RestrictionException {
		synchronized (doc) {
			doc.empruntPar(ab);
		}
	}

	@Override
	public void retour() {
		synchronized (doc) {
			doc.retour();
		}
	}

	@Override
	public String toString() {
		return doc.toString();
	}

	@Override
	public boolean equals(Object obj) {
		return doc.equals(obj);
	}

	@Override
	public LocalDateTime getTime() {
		return doc.getTime();
	}

	@Override
	public Date getDateRetour() {
		return doc.getDateRetour();
	}
	

}

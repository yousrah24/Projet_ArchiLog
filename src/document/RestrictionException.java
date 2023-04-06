package document;

import mediatheque.IDocument;

public class RestrictionException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private IDocument doc;
	
	public RestrictionException() {
		this(null);
	}
	
	public RestrictionException(IDocument doc) {
		this.doc = doc;
	}
	
	@Override
	public String toString() {
		if(doc!= null) {
			if(doc.reserveur() != null)
				return "Ce document est réservé jusqu’à " + doc.getTime().toString();
			else if(doc.emprunteur() != null)
				return "Ce document est déjà emprunté, Il sera disponible le " + doc.getDateRetour().toString();
			else
				return "Vous n’avez pas l’âge pour emprunter ce document";
		}
		return "Document non existant ou Abonné non existant";
	}

}

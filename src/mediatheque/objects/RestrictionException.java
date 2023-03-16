package mediatheque.objects;

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
				return "Ce document est déjà réservé, il sera libre à ";
			else if(doc.emprunteur() != null)
				return "Ce document n'est pas disponible";
			else
				return "Ce document n'est pas de votre âge";
		}
		
		return "Document non existant";
	}

}

package mediatheque;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import mediatheque.objects.RestrictionException;
import mediatheque.repository.AbonneeRepository;
import mediatheque.repository.DocumentRepository;

public class Mediatheque {
	private static DocumentRepository documents;
	private static AbonneeRepository abonnees;
	
	public Mediatheque(String user, String password) throws ClassNotFoundException, RestrictionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		documents = new DocumentRepository(user, password);
		abonnees = new AbonneeRepository(user, password);
		
	}
	

	public List<IAbonne> getListeAbonnee() {
		return abonnees.getListeAbonnee();
	}

	
	public static IDocument getDocument(int numDoc) {
		return documents.findDocumentByNum(numDoc);
	}
	public static IAbonne getAbonne(int numAb) {
		return abonnees.findAbonneByNum(numAb);
	}
	
	public boolean estEmprunte(int numDoc) {
		IDocument doc = getDocument(numDoc);
		return doc.emprunteur() == null;
	}
	
	

	public void emprunt(int numAb, int numDoc) throws ClassNotFoundException, RestrictionException, SQLException {
		IAbonne ab = Mediatheque.getAbonne(numAb);
		IDocument doc = getDocument(numDoc);
		if(ab != null && doc != null) {
			doc.empruntPar(ab);
			documents.update(doc.numero(), ab.numero());
			documents.update(doc.numero(),"emprunte");
		}
			
		throw new RuntimeException("Numéro d'abonné incorrect ou numéro de document incorrect");
	}
	
	
	public void retour(int numDoc) throws ClassNotFoundException, RestrictionException, SQLException {
		IDocument doc = getDocument(numDoc);
		if(doc != null) {
			doc.retour();
			documents.update(doc.numero(),"disponible");
		}
			
		throw new RestrictionException();
	}
	
	
	public void reservation(int numAb, int numDoc) throws ClassNotFoundException, RestrictionException {
		IAbonne ab = Mediatheque.getAbonne(numAb);
		IDocument doc = getDocument(numDoc);
		if(ab != null && doc != null) {
			doc.reservationPour(ab);
		}
			
		throw new RestrictionException();
	}
	
	public void close() throws SQLException {
		 abonnees.close();
		 documents.close();
	 }
	

}

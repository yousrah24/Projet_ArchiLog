package mediatheque;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

import mediatheque.document.RestrictionException;
import mediatheque.repository.AbonneeRepository;
import mediatheque.repository.DocumentRepository;

public class Mediatheque implements Serializable{
	private static final long serialVersionUID = 1L;
	private static DocumentRepository lesDocuments;
	private static AbonneeRepository lesAbonnees;
	
	public Mediatheque(String user, String password) throws ClassNotFoundException, RestrictionException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException {
		lesAbonnees = new AbonneeRepository(user, password);
		lesDocuments = new DocumentRepository(user, password);		
	}
	

	public static List<IAbonne> getListeAbonnee() {
		return lesAbonnees.getListeAbonnee();
	}
	
	public static List<IDocument> getListeDocument() {
		return lesDocuments.getListeDocuments();
	}

	
	public static IDocument getDocument(int numDoc) {
		return lesDocuments.findDocumentByNum(numDoc);
	}
	public static IAbonne getAbonne(int numAb) {
		return lesAbonnees.findAbonneByNum(numAb);
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
			lesDocuments.update(numDoc, numAb);
			lesDocuments.updateEtat(numDoc,0);
		}
		else
			throw new RestrictionException(doc);
	}
	
	
	public void retour(int numDoc) throws ClassNotFoundException, RestrictionException, SQLException {
		IDocument doc = getDocument(numDoc);
		if(doc != null) {
			doc.retour();
			lesDocuments.updateEtat(doc.numero(),1);
		}
		else
			throw new RestrictionException(doc);
	}
	
	
	public void reservation(int numAb, int numDoc) throws ClassNotFoundException, RestrictionException {
		IAbonne ab = Mediatheque.getAbonne(numAb);
		IDocument doc = getDocument(numDoc);
		if(ab != null && doc != null) {
			doc.reservationPour(ab);
		}
		else
			throw new RestrictionException(doc);
	}
	
	public static void close() throws SQLException {
		 lesAbonnees.close();
		 lesDocuments.close();
	 }
	

}

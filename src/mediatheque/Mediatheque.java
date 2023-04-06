package mediatheque;



import java.util.List;

import database.FabriqueRepos;
import document.RestrictionException;

public class Mediatheque {
	private static Repository lesDocuments;
	private static Repository lesAbonnees;
	
	public Mediatheque(String user, String password) throws Exception {
		lesAbonnees = FabriqueRepos.creerRepository(user, password, "database.AbonneeRepository");
		lesDocuments = FabriqueRepos.creerRepository(user, password, "database.DocumentRepository");		
	}
	
	/**
	 * @brief donne la liste des abonnées
	 * @return un abonne
	 */
	public static List<?> getListeAbonnee() {
		return lesAbonnees.getRepository();
	}
	/**
	 * @brief donne la liste des documents
	 * @return un abonne
	 */
	public static List<?> getListeDocument() {
		return lesDocuments.getRepository();
	}

	/**
	 * @brief donne un document selon son numero
	 * @param numAb
	 * @return un document
	 */
	public static Object getDocument(int numDoc) {
		return lesDocuments.findByNum(numDoc);
	}
	
	/**
	 * @brief donne un abonne selon son numero
	 * @param numAb
	 * @return un abonne
	 */
	public static Object getAbonne(int numAb) {
		return lesAbonnees.findByNum(numAb);
	}
	
	/**
	 * @brief verifie si un document est emprunté
	 * @param numDoc
	 * @return true ou false
	 */
	public boolean estEmprunte(int numDoc) {
		IDocument doc = (IDocument) getDocument(numDoc);
		return doc.emprunteur() == null;
	}
	
	
	/**
	 * @brief emprunte un document et met à jour en temps reel la database
	 * @param numAb
	 * @param numDoc
	 * @throws Exception
	 */
	public void emprunt(int numAb, int numDoc) throws Exception{
		IAbonne ab = (IAbonne) Mediatheque.getAbonne(numAb);
		IDocument doc = (IDocument) getDocument(numDoc);
		if(ab != null && doc != null) {
			doc.empruntPar(ab);
			lesDocuments.update(numDoc, numAb);
			lesDocuments.updateEtat(numDoc,0);
		}
		else
			throw new RestrictionException(doc);
	}
	
	/**
	 * @brief rend disponible un documentet met à jour en temps reel la database
	 * @param numDoc
	 * @throws Exception
	 */
	public void retour(int numDoc) throws Exception {
		IDocument doc = (IDocument) Mediatheque.getDocument(numDoc);
		if(doc != null) {
			doc.retour();
			lesDocuments.updateEtat(doc.numero(),1);
		}
		else
			throw new RestrictionException(doc);
	}
	
	/**
	 * @brief fait la reservation d'un document 
	 * @param numAb
	 * @param numDoc
	 * @throws Exception
	 */
	public void reservation(int numAb, int numDoc) throws Exception {
		IAbonne ab = (IAbonne) Mediatheque.getAbonne(numAb);
		IDocument doc = (IDocument) Mediatheque.getDocument(numDoc);
		if(ab != null && doc != null) {
			doc.reservationPour(ab);
		}
		else
			throw new RestrictionException(doc);
	}
	
	

}

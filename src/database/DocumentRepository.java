package database;



import java.lang.reflect.Constructor;



import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import document.ConcurrentDocument;
import document.Document;
import mediatheque.IAbonne;
import mediatheque.IDocument;
import mediatheque.Mediatheque;
import mediatheque.Repository;

public class DocumentRepository implements Repository{
	private DatabaseConnection connection;
	private List<IDocument> listeDocuments;
	
	public DocumentRepository(String user, String password) throws Exception{
		listeDocuments = new LinkedList<>();
		connection = new DatabaseConnection(user, password);
		init();
	}
	
	/**
	 * @brief initilaise le repertoir de documents
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	private void init() throws Exception{
		var res = connection.up("Document");
		while (res.next()) {
			String className = "document." + res.getString("Type");
			Class< ? extends Document> c = (Class<? extends Document>)Class.forName(className);
			Date date = res.getDate("dateRetour");
			 // Récupération du constructeur paramètres d'un document
            Constructor<?> constructor = c.getConstructor(
            		int.class, String.class,
            		Date.class, int.class);
			IDocument d = (IDocument) constructor.newInstance(res.getInt("idDoc"), res.getString("Titre"), date, res.getInt("Adulte"));
			//verification si le document a été emprunté
			if(res.getInt("idAbonne") != 0)
				d.empruntPar((IAbonne) Mediatheque.getAbonne(res.getInt("idAbonne")));
			listeDocuments.add(new ConcurrentDocument(d));
		}
		res.close();
		
	}
	/**
	 * @brief renvoie la liste des documents
	 * @return la liste de documents
	 */
	public List<IDocument> getRepository() {
		return listeDocuments;
	}
	
	/**
	 * @brief cherche un document selon son numéro
	 *@param numDoc le numero de document
	 */
	public IDocument findByNum(int numDoc) {
		for (IDocument doc : listeDocuments) {
			if(doc.numero() == numDoc)
				return doc;
			
		}
		return null;
	}
	/**
	 * @brief met à jour l'etat d'un document
	 * @param numDoc
	 * @param etat
	 */
	 public void updateEtat(int numDoc, int etat) throws SQLException {
		 connection.updateEtat(numDoc, etat);
	 }
	 /**
	  * @brief met à jour l'emprunteur d'un document
	  * @param numDoc
	  * @param numAb
	  */
	 public void update(int numDoc, int numAb) throws SQLException{
		 connection.update(numDoc, numAb);
	 }	

}

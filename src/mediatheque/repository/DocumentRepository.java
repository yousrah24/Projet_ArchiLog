package mediatheque.repository;



import java.lang.reflect.Constructor;

import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mediatheque.IDocument;
import mediatheque.Mediatheque;
import mediatheque.document.ConcurrentDocument;
import mediatheque.document.Document;
import mediatheque.document.RestrictionException;

public class DocumentRepository {
	private DatabaseConnection connection;
	private List<IDocument> listeDocuments;
	
	public DocumentRepository(String user, String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, RestrictionException{
		listeDocuments = new LinkedList<>();
		connection = new DatabaseConnection(user, password);
		init();
	}
	
	@SuppressWarnings("unchecked")
	private void init() throws RestrictionException, SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException{
		var res = connection.up("Document");
		while (res.next()) {
			String className = "mediatheque.document." + res.getString("Type");
			Class< ? extends Document> c = (Class<? extends Document>)Class.forName(className);
			Date date = res.getDate("dateRetour");
			 // Récupération du constructeur paramètres
            Constructor<?> constructor = c.getConstructor(
            		int.class, String.class, 
            		int.class, Date.class);
			IDocument d = (IDocument) constructor.newInstance(res.getInt("idDoc"), res.getString("Titre"), res.getInt("Adulte"),date);
			
			if(res.getInt("idAbonne") != 0)
				d.empruntPar(Mediatheque.getAbonne(res.getInt("idAbonne")));
			listeDocuments.add(new ConcurrentDocument(d));
		}
		res.close();
		
	}

	public List<IDocument> getListeDocuments() {
		return listeDocuments;
	}
	
	public IDocument findDocumentByNum(int numDoc) {
		for (IDocument doc : listeDocuments) {
			if(doc.numero() == numDoc)
				return doc;
			
		}
		return null;
	}
	
	 public void updateEtat(int numDoc, int etat) throws SQLException {
		 connection.updateEtat(numDoc, etat);
	 }
	 
	 public void update(int numDoc, int numAb) throws SQLException{
		 connection.update(numDoc, numAb);
	 }
	 
	 
	 public void close() throws SQLException {
		 connection.close();
	 }
	

}

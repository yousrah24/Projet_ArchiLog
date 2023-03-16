package mediatheque.repository;



import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mediatheque.IDocument;
import mediatheque.Mediatheque;
import mediatheque.objects.Document;
import mediatheque.objects.RestrictionException;

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
		ResultSet res = connection.up("Document");
		while (res.next()) {
			String className = res.getString(2);
			Class< ? extends Document> c = (Class<? extends Document>)Class.forName(className);
			IDocument d = c.getConstructor(Document.class)
					.newInstance(
						res.getInt(1), 
						res.getString(3),
						res.getBoolean(4), 
						res.getString(5)
					);
			if(res.getInt(5) != 0)
				d.empruntPar(Mediatheque.getAbonne(res.getInt(5)));
			
			listeDocuments.add(d);
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
	
	 public void update(int numDoc, String etat) throws SQLException {
		 connection.update(numDoc, etat);
	 }
	 
	 public void update(int numDoc, int numAb) throws SQLException{
		 connection.update(numDoc, numAb);
	 }
	 
	 
	 public void close() throws SQLException {
		 connection.close();
	 }
	

}

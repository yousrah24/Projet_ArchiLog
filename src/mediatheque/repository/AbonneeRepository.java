package mediatheque.repository;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import mediatheque.IAbonne;
import mediatheque.objects.Abonne;

public class AbonneeRepository {
	
	private List<IAbonne> listeAbonnee;
	
	private DatabaseConnection connection;

	public AbonneeRepository(String user, String password) throws ClassNotFoundException, SQLException {
		this.listeAbonnee = new LinkedList<>();
		connection = new  DatabaseConnection(user, password);
		init();
	}

	private void init() throws ClassNotFoundException, SQLException {
		ResultSet res = connection.up("Abonne");
		while (res.next()) {
			Date d = res.getDate(2);
			Abonne ab = new Abonne(res.getInt(1), res.getString(3),d);
			listeAbonnee.add(ab);
		}
		res.close();
	}

	public IAbonne findAbonneByNum(int numAb) {
		for (IAbonne abonne : listeAbonnee) {
			if(abonne.numero() == numAb)
				return abonne;
		}
		return null;
	}

	public List<IAbonne> getListeAbonnee() {
		return listeAbonnee;
	}
	
	public void close() throws SQLException {
		 connection.close();
	 }
}

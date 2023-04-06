package database;


import java.sql.Date;


import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;

import client.Abonne;
import client.ConcurrentAbonne;
import mediatheque.IAbonne;
import mediatheque.Repository;

public class AbonneeRepository implements Repository {
	
	private List<IAbonne> listeAbonnee;
	
	private DatabaseConnection connection;

	public AbonneeRepository(String user, String password) throws Exception{
		this.listeAbonnee = new LinkedList<>();
		connection = new  DatabaseConnection(user, password);
		init();
	}

	/**
	 * @brief recuperation des abonnée dans la database
	 * @throws Exception
	 */
	private void init() throws Exception{
		ResultSet res = connection.up("Abonne");
		while (res.next()) {
			Date d = res.getDate("DateNaissance");
			IAbonne ab = new Abonne(res.getInt("idAbonne"), res.getString("nom"),d);
			listeAbonnee.add(new ConcurrentAbonne(ab));
		}
		res.close();
	}

	@Override
	/**
	 * @brief cherche un abonne selon son numero
	 * @param numAb
	 */
	public IAbonne findByNum(int numAb) {
		for (IAbonne abonne : listeAbonnee) {
			if(abonne.numero() == numAb)
				return abonne;
		}
		return null;
	}

	@Override
	/**
	 * @brief renvoie le repertoir des abonnés
	 */
	public List<IAbonne> getRepository() {
		return listeAbonnee;
	}
	
	@Override
	public void update(int numDoc, int numAb) {
		
	}

	@Override
	public void updateEtat(int numDoc, int i) {
		
	}
	
}

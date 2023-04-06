package database;

import java.sql.Connection;


import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseConnection {

    private static Connection connection;

    public DatabaseConnection(String user, String password) throws Exception{
		Class.forName("oracle.jdbc.OracleDriver");
        if(connection != null) return;
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",user,password);
            
    }
    
    /**
     * @brief renvoie la connexion de la base de donnée
     * @return la connexion
     */
    public Connection getConnection() {
        return connection;
    }
    /**
     * @brief renitialise la base de donné
     * @throws SQLException
     */
    public void resetDatabase() throws SQLException {
    	var preparedStatement = connection.prepareStatement("delete from document");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("delete from abonnee");
        preparedStatement.execute();
        connection.commit();
    }

    /**
     * @brief renvoie la liste des elements de la table document
     * @param table
     * @return
     * @throws SQLException
     */
    public ResultSet up(String table) throws SQLException {
			var stmt = connection.createStatement();
			ResultSet res = stmt.executeQuery("select * from " + table);
			return res;
	}

    /**
     * @brief ferme la connexion à la base de donnée
     * @throws SQLException
     */
    public static void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }
    
    /**
     * @brief met à jour la table document dés que un document est retourne ou emprunté
     * @param numDoc
     * @param etat
     * @throws SQLException
     */
    public void updateEtat(int numDoc, int etat) throws SQLException {
		var stmt = connection.prepareStatement("UPDATE Document SET Disponible = ? WHERE idDoc = ?");
		stmt.setInt(1,etat);
		stmt.setInt(2, numDoc);
		stmt.execute();
		connection.commit();
			
	}
	/**
	 * @brief met à jour la table document en mettant l'identifiant de l'abonné ayant emprunte un document
	 * @param numDoc
	 * @param numAb
	 * @throws SQLException
	 */
	public void update(int numDoc, int numAb) throws SQLException{
		var stmt = connection.prepareStatement("UPDATE Document SET idAbonne = ? WHERE idDoc = ?");
		stmt.setInt(1,numAb);
		stmt.setInt(2, numDoc);
		stmt.execute();
		connection.commit();
	}
}

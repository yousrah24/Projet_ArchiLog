package mediatheque.repository;

import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseConnection {

    private static Connection connection;

    public DatabaseConnection(String user, String password) throws SQLException, ClassNotFoundException {
		Class.forName("oracle.jdbc.OracleDriver");
        if(connection != null) return;
        connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE",user,password);
            
    }

    public Connection getConnection() {
        return connection;
    }

    public void resetDatabase() throws SQLException {
    	var preparedStatement = connection.prepareStatement("delete from document");
        preparedStatement.execute();
        preparedStatement = connection.prepareStatement("delete from abonnee");
        preparedStatement.execute();
        connection.commit();
    }

    public ResultSet up(String table) throws SQLException {
			var stmt = connection.createStatement();
			var res = stmt.executeQuery("SELECT * FROM " + table);
			connection.commit();
			return res;
	}

    
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
        connection = null;
    }
    
    
    public void update(int numDoc, String etat) throws SQLException {
		var stmt = connection.prepareStatement("UPDATE Document SET etat = ?, set idAbonne = NULL WHERE idDoc = ?");
		stmt.setString(1,etat);
		stmt.setInt(2, numDoc);
		stmt.execute();
		connection.commit();
			
	}
	
	public void update(int numDoc, int numAb) throws SQLException{
		var stmt = connection.prepareStatement("UPDATE Document SET idAbonnee = ? WHERE idDoc = ?");
		stmt.setInt(1,numAb);
		stmt.setInt(2, numDoc);
		stmt.execute();
		connection.commit();
	}
}

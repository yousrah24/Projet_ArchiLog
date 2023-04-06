package mediatheque;

import java.sql.SQLException;

import java.util.List;

public interface Repository {

	Object findByNum(int numAb);

	List<?> getRepository();

	void update(int numDoc, int numAb) throws SQLException;

	void updateEtat(int numDoc, int i) throws SQLException;

}
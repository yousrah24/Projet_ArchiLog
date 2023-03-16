package mediatheque;

import java.sql.Date;

public interface IAbonne {

	int numero();

	String getNom();

	Date getDateNaiss();

	boolean estAdulte();

	void reserver(IDocument doc);

	void emprunter(IDocument doc);

	void retourner(IDocument doc);

}
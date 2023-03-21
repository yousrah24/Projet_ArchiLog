package mediatheque;

import java.io.Serializable;
import java.sql.Date;

public interface IAbonne extends Serializable{

	int numero();

	String getNom();

	Date getDateNaiss();

	boolean estAdulte();

	void reserver(IDocument doc);

	void emprunter(IDocument doc);

	void retourner(IDocument doc);

	void retour(IDocument doc);

}
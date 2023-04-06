package mediatheque;

import java.sql.Date;
import java.util.List;

public interface IAbonne {

	int numero();

	String getNom();

	Date getDateNaiss();

	boolean estAdulte();

	void reserver(IDocument doc);

	void emprunter(IDocument doc);

	void retourner(IDocument doc);


	List<IDocument> getListeEmprunt();

	List<IDocument> getListeReservation();

}
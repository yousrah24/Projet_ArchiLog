package mediatheque;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

import mediatheque.document.RestrictionException;

public interface IDocument extends Serializable{
	int numero();
	
	LocalDateTime getTime();
	
	Date getDateRetour();
	/**
	 * @return null  si pas emprunté ou pas réservé
	 */
	IAbonne emprunteur() ; // Abonné qui a emprunté ce document
	IAbonne reserveur() ; // Abonné qui a réservé ce document
	/**
	 * @pre  ni réservé ni emprunté
	 * @param ab
	 */
	void reservationPour(IAbonne ab) throws RestrictionException ;
	/**
	 * @pre libre ou réservé par l’abonné qui vient emprunter
	 * @param ab
	 */
	void empruntPar(IAbonne ab) throws RestrictionException;
	/**
	 * @brief retour d’un document ou annulation d‘une réservation
	 */
	void retour();
	
	
	
}

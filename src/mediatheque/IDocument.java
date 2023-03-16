package mediatheque;

import mediatheque.objects.RestrictionException;

public interface IDocument {
	int numero();
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

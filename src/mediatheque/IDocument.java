package mediatheque;


public interface IDocument {
	int numero();
	/**
	 * @return null  si pas emprunté ou pas réservé
	 */
	Abonne emprunteur() ; // Abonné qui a emprunté ce document
	Abonne reserveur() ; // Abonné qui a réservé ce document
	/**
	 * @pre  ni réservé ni emprunté
	 * @param ab
	 */
	void reservationPour(Abonne ab) ;
	/**
	 * @pre libre ou réservé par l’abonné qui vient emprunter
	 * @param ab
	 */
	void empruntPar(Abonne ab);
	/**
	 * @brief retour d’un document ou annulation d‘une réservation
	 */
	void retour(); 
}

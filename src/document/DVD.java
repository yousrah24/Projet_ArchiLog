package document;

import java.sql.Date;

import mediatheque.IAbonne;

public class DVD extends Document{	
	
	public DVD(int num, String titre, Date dateRetour, int adulte) {
		super(num, titre, dateRetour, adulte);
	}
	
	@Override
	public void reservationPour(IAbonne ab) throws RestrictionException {
		super.reservationPour(ab);
	}
	
	@Override
	public void empruntPar(IAbonne ab) throws RestrictionException {
		super.empruntPar(ab);

	}


	@Override
	public String toString() {
		return "DVD - " + getTitre() + " - Reférence numéro : " + numero();
	}


}

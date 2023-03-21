package mediatheque.document;

import java.sql.Date;

import mediatheque.IAbonne;

public class DVD extends Document{
	private static final long serialVersionUID = 1L;
	
	private boolean adulte;
	
	public DVD(int num, String titre, int adulte, Date dateRetour) {
		super(num, titre, dateRetour);
		this.adulte  = adulte == 1 ? true : false;
	}
	
	@Override
	public void reservationPour(IAbonne ab) throws RestrictionException {
		if(adulte && !ab.estAdulte()) {
			throw new RestrictionException(this);
		}
		super.reservationPour(ab);
	}
	
	@Override
	public void empruntPar(IAbonne ab) throws RestrictionException {
		if(adulte && !ab.estAdulte()) {
			throw new RestrictionException(this);
		}
		super.empruntPar(ab);

	}


	@Override
	public String toString() {
		return "DVD - " + getTitre() + " - Reférence numéro : " + numero();
	}


}

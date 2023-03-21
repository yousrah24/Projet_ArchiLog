package mediatheque.objects;

import mediatheque.IAbonne;

public class DVD extends Document{
	
	private String titre;
	private boolean adulte;
	
	public DVD(int num, String titre, boolean adulte, String etat) {
		super(num, etat);
		this.titre = titre;
		this.adulte  = adulte;
	}
	
	@Override
	public void reservationPour(IAbonne ab) throws RestrictionException {
		if(estDisponible() && !estReservePar(ab) && (adulte && ab.estAdulte())) {
			ab.reserver(this);
			setReserveur(ab);
			setEtat(Etat.Reservé);
		}
		throw new RestrictionException(this);
	}
	
	@Override
	public void empruntPar(IAbonne ab) throws RestrictionException {
		if( (estDisponible() || estReservePar(ab)) && (adulte && ab.estAdulte()) ) {
			ab.emprunter(this);
			setEtat(Etat.Emprunté);
			setEmprunteur(ab);
		}
		throw new RestrictionException(this);

	}

	private boolean estDisponible() throws RestrictionException {
		if(etat() == Etat.Disponible)
			return true;
		return false;
	}

	private boolean estReservePar(IAbonne ab) throws RestrictionException {
		if(etat() == Etat.Reservé && reserveur() == ab)
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "DVD " + titre;
	}

}

package mediatheque.documents;

import mediatheque.Abonne;

public class DVD extends Document{
	
	private String titre;
	private boolean adulte;
	public DVD(int num, String titre, boolean adulte) {
		super(num);
		this.titre = titre;
		this.adulte  = adulte;
	}
	
	@Override
	public void reservationPour(Abonne ab) {
		
	}
	@Override
	public void empruntPar(Abonne ab) {
		
	}
	@Override
	public void retour() {
		
	}

}

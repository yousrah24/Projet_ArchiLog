package mediatheque.objects;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public abstract class Document implements IDocument{

	private int num;
	private IAbonne emprunteur;
	private IAbonne reserveur;
	private Etat etat;

	public Document(int num, String etat) {
		this.num = num;
		this.emprunteur = null;
		this.reserveur = null;
		this.etat = Etat.get(etat);
	}

	@Override
	public int numero() {
		return this.num;
	}

	@Override
	public IAbonne emprunteur() {
		return emprunteur;
	}

	@Override
	public IAbonne reserveur() {
		return reserveur;
	}
	
	protected Etat etat() {
		return etat;
	}
	
	protected void setEmprunteur(IAbonne ab) {
		emprunteur = ab;
	}

	protected void setReserveur(IAbonne ab) {
		reserveur = ab;
	}

	@Override
	public abstract void reservationPour(IAbonne ab) throws RestrictionException;
	
	@Override
	public  abstract void empruntPar(IAbonne ab) throws RestrictionException;
	
	@Override
	public void retour() {
		etat = Etat.Disponible;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Document other = (Document) obj;
		return num == other.num;
	}

	protected void setEtat(Etat e) {
		this.etat = e;
	}

	@Override
	public String toString() {
		return "Document {num= " + num + ", type= "+ this.getClass().getName() + ", etat= " + etat + "}";
	}

	
	
	
}

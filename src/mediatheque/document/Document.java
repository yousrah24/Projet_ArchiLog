package mediatheque.document;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import mediatheque.IAbonne;
import mediatheque.IDocument;

public abstract class Document implements IDocument{

	private static final long serialVersionUID = 1L;
	private int num;
	private String titre;
	private IAbonne emprunteur;
	private IAbonne reserveur;
	private Etat etat;
	private Date dateRetour;
	private LocalDateTime time;

	public Document(int num, String titre, Date dateRetour) {
		this.num = num;
		this.emprunteur = null;
		this.reserveur = null;
		this.titre = titre;
		this.etat = Etat.Disponible;
		this.dateRetour = dateRetour;
	}
	
	protected String getTitre() { 
		return titre;
	}
	
	@Override
	public Date getDateRetour() {
		return dateRetour;
	}
	
	@Override
	public LocalDateTime getTime() {
		return time;
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
	

	@Override
	public  void reservationPour(IAbonne ab) throws RestrictionException{
		if(estDisponible() && !estReservePar(ab)) {
			ab.reserver(this);
			reserveur = ab;
			etat = Etat.Reservé;
			time = LocalDateTime.now().plusHours(2);
		}
		else
			throw new RestrictionException(this);

	}
	@Override
	public   void empruntPar(IAbonne ab) throws RestrictionException{
		if( estDisponible() || estReservePar(ab)) {
			ab.emprunter(this);
			etat = Etat.Emprunté;
			emprunteur = ab;
			dateRetour = Date.valueOf(LocalDate.now().plusDays(30));
		}
		else
			throw new RestrictionException(this);

	}
	
	@Override
	public void retour() {
		etat = Etat.Disponible;
	}
	
	
	private boolean estDisponible() throws RestrictionException {
		return etat == Etat.Disponible;
	}

	private boolean estReservePar(IAbonne ab) throws RestrictionException {
		return etat == Etat.Reservé && reserveur() == ab;
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


	@Override
	public String toString() {
		StringBuilder str = new StringBuilder();
		str.append("{num= " + num + ", titre= "+ titre + ", etat= " + etat);
		if(emprunteur() != null)
			str.append("emprunté par " + emprunteur.getNom() + "}");
		else
			str.append("}");
		return  str.toString();
	}
	
}

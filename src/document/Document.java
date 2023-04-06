package document;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public abstract class Document implements IDocument{

	private int num;
	private String titre;
	private IAbonne emprunteur;
	private IAbonne reserveur;
	private Etat etat;
	private Date dateRetour;
	private boolean adulte;
	private LocalDateTime time;

	public Document(int num, String titre, Date dateRetour, int adulte) {
		this.num = num;
		this.emprunteur = null;
		this.reserveur = null;
		this.titre = titre;
		this.etat = Etat.Disponible;
		this.dateRetour = dateRetour;
		this.adulte = adulte == 1 ? true : false;
	}
	
	/**
	 * @brief renvoie le titre d'un document
	 * @return le titre
	 */
	protected String getTitre() { 
		return titre;
	}
	
	/**
	 * @brief renvoir la date de retour d'un document
	 * @return la date
	 */
	@Override
	public Date getDateRetour() {
		return dateRetour;
	}
	
	@Override
	/**
	 * @brief renvoie l'heure de fin de reservation d'un livre
	 * @return l'heure
	 */
	public LocalDateTime getTime() {
		return time;
	}

	@Override
	/**
	 * renvoie le numero d'un document
	 * @return le numero
	 */
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
	
	/**
	 * @brief reserve un document pour un abonné
	 */
	@Override
	public  void reservationPour(IAbonne ab) throws RestrictionException{
		if(adulte) 
			assert(ab.estAdulte());
		if(estDisponible() || estReservePar(ab)) {
			ab.reserver(this);
			reserveur = ab;
			etat = Etat.Reservé;
			time = LocalDateTime.now().plusHours(2);
		}
		else
			throw new RestrictionException(this);

	}
	@Override
	/**
	 * @brief emprunt d'un document par un abonné
	 */
	public   void empruntPar(IAbonne ab) throws RestrictionException{
		if(adulte) 
			assert(ab.estAdulte());
		if(estDisponible() || estReservePar(ab)) {
			ab.emprunter(this);
			etat = Etat.Emprunté;
			emprunteur = ab;
			dateRetour = Date.valueOf(LocalDate.now().plusDays(30));
		}
		else
			throw new RestrictionException(this);

	}
	
	/**
	 * retour d'un document
	 */
	@Override
	public void retour() {
		etat = Etat.Disponible;
		emprunteur.retourner(this);
		emprunteur = null;
		reserveur = null;
	}
	
	/**
	 * @brief verfie si un document est disponible
	 * @return true ou false
	 * @throws RestrictionException
	 */
	private boolean estDisponible() throws RestrictionException {
		return etat == Etat.Disponible;
	}

	/**
	 * @brief verifie si un document est reservé
	 * @param ab l'abonne
	 * @return true ou false
	 * @throws RestrictionException
	 */
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

package client;


import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public class Abonne implements IAbonne{
	
	private int num;
	private String nom;
	private Date dateNaiss;
	private List<IDocument> listeReservation;
	private List<IDocument> listeEmprunt;
	
	public Abonne(int num, String nom, Date dateNaiss) {
		this.num = num;
		this.nom = nom;
		this.dateNaiss = dateNaiss;
		listeEmprunt = new ArrayList<>();
		listeReservation = new ArrayList<>();
	}
	
	
	/**
	 * @brief renvoie le numero d'un abonné
	 */
	@Override
	public int numero() {
		return num;
	}

	/**
	 * @brief renvoie le nom d'un abonné
	 */
	@Override
	public String getNom() {
		return nom;
	}
	/**
	 * @brief renvoie la date de naissance d'un abonné
	 */
	@Override
	public Date getDateNaiss() {
		return dateNaiss;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Abonne other = (Abonne) obj;
		return Objects.equals(nom, other.nom) && num == other.num;
	}
	
	/**
	 * @brief verifie si un abonné a plus de 16ans
	 */
	@SuppressWarnings("deprecation")
	@Override
	public boolean estAdulte() {
		// Date de naissance
		LocalDate birthdate = LocalDate.of(dateNaiss.getYear(), dateNaiss.getMonth(), dateNaiss.getDay());
		
        // Date actuelle
        LocalDate now = LocalDate.now();

        // Calcul de la différence entre les deux date
        Period diff = Period.between(birthdate, now);
        
		return diff.getYears() >= 16;
	}

	
	/**
	 * @brief reserve un document et l'ajoute dans sa liste
	 * @param doc le document
	 */
	@Override
	public void reserver(IDocument doc) {
		listeReservation.add(doc);
	}
	/**
	 * @brief emprunte un document et l'ajoute dans sa liste 
	 * @param doc le document
	 */
	@Override
	public void emprunter(IDocument doc) {
		if(listeReservation.contains(doc)) 
			listeReservation.remove(doc);
		listeEmprunt.add(doc);
	}
	
	/**
	 * @brief retoune un document en le supprimant dans sa liste de document empruntés
	 * @param doc le document
	 */
	@Override
	public void retourner(IDocument doc) {
		listeEmprunt.remove(doc);
	}


	@Override
	public String toString() {
		return "Abonne {num= " + num + ", nom= " + nom + ", dateNaiss= " + dateNaiss + "}";
	}

	/**
	 * @brief renvoie la liste des documents emprunté 
	 */
	@Override
	public List<IDocument> getListeEmprunt() {
		return listeEmprunt;
	}

	/**
	 * @brief renvoie la liste des documents reservé
	 */
	@Override
	public List<IDocument> getListeReservation() {
		return listeReservation;
	}
	
	
	
	
}

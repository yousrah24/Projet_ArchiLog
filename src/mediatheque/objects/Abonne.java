package mediatheque.objects;

import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public class Abonne implements IAbonne {
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
	

	@Override
	public int numero() {
		return num;
	}


	@Override
	public String getNom() {
		return nom;
	}
	
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

	@Override
	public boolean estAdulte() {
		// Date de naissance
		LocalDate birthdate = LocalDate.of(dateNaiss.getYear(), dateNaiss.getMonth(), dateNaiss.getDay());

        // Date actuelle
        LocalDate now = LocalDate.now();

        // Calcul de la différence entre les deux dates
        Period diff = Period.between(birthdate, now);
        
		return diff.getYears() >= 16;
	}


	@Override
	public void reserver(IDocument doc) {
		listeReservation.add(doc);
	}

	@Override
	public void emprunter(IDocument doc) {
		if(listeReservation.contains(doc)) 
			listeReservation.remove(doc);
		listeEmprunt.add(doc);
	}
	
	@Override
	public void retourner(IDocument doc) {
		listeEmprunt.remove(doc);
	}


	@Override
	public String toString() {
		return "Abonne {num= " + num + ", nom= " + nom + ", dateNaiss= " + dateNaiss + "}";
	}
	
	
	
	
}

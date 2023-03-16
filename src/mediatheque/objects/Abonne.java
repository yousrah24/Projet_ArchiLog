package mediatheque;

import java.util.Objects;

public class Abonne {
	private int num;
	private String nom;
	private String dateNaiss;
	
	public Abonne(int num, String nom, String dateNaiss) {
		this.num = num;
		this.nom = nom;
		this.dateNaiss = dateNaiss;
	}
	

	public int getNum() {
		return num;
	}


	public String getNom() {
		return nom;
	}
	
	public String getDateNaiss() {
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
	
	
	
	
}

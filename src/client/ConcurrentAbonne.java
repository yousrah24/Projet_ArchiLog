package client;


import java.sql.Date;
import java.util.List;

import mediatheque.IAbonne;
import mediatheque.IDocument;

public class ConcurrentAbonne implements IAbonne {

	private IAbonne abonne;
	
	

	public ConcurrentAbonne(IAbonne abonne) {
		this.abonne = abonne;
	}
	/**
	 * renvoie le numero d'un abonné
	 */
	@Override
	public int numero() {
		return abonne.numero();
	}
	/**
	 * renvoie le nom d'un abonné
	 */
	@Override
	public String getNom() {
		return abonne.getNom();
	}
	/**
	 * renvoie la date de naissance d'un abonné
	 */
	@Override
	public Date getDateNaiss() {
		return abonne.getDateNaiss();
	}
	/**
	 * verifie si un abonné a plus de 16ans
	 */
	@Override
	public boolean estAdulte() {
		return abonne.estAdulte();
	}
	/**
	 * reserve un document et l'ajoute dans sa liste
	 */
	@Override
	public void reserver(IDocument doc) {
		synchronized (abonne) {
			abonne.reserver(doc);
		}
	}
	/**
	 * emprunte un document et l'ajoute dans sa liste en gerant les confilts
	 */
	@Override
	public void emprunter(IDocument doc) {
		synchronized (abonne) {
			abonne.emprunter(doc);
		}
	}
	/**
	 * retoune un document en le supprimant dans sa liste de document empruntés en gerant les confilts
	 */
	@Override
	public void retourner(IDocument doc) {
		synchronized (abonne) {
			abonne.retourner(doc);
		}
	}
	/**
	 * renvoie la liste des documents emprunté 
	 */
	@Override
	public List<IDocument> getListeEmprunt() {
		return abonne.getListeEmprunt();
	}
	/**
	 * renvoie la liste des documents reservé
	 */
	@Override
	public List<IDocument> getListeReservation() {
		return abonne.getListeReservation();
	}

}

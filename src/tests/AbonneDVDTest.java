package tests;
import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import client.Abonne;
import document.DVD;
import document.RestrictionException;
import mediatheque.IAbonne;
import mediatheque.IDocument;
import oracle.sql.DATE;

public class AbonneDVDTest {
	
	private IAbonne abonne;
	private IDocument dvd;
	
	@Before
	public void setUp() {
		// création d'un abonné
		abonne = new Abonne(1, "Doe", new java.sql.Date(1990, 1, 1));
		
		// création d'un DVD disponible
		dvd = new DVD(1, "Le Seigneur des anneaux", null, 0);
	}
	
	@Test
	public void testReservation() throws RestrictionException {
		// le DVD est réservé par l'abonné
		dvd.reservationPour(abonne);
		
		// vérification que le DVD est bien réservé par l'abonné
		assertEquals(abonne, dvd.reserveur());
		assertTrue(abonne.getListeReservation().contains(dvd));
	}
	
	@Test
	public void testEmprunt() throws RestrictionException {
		// l'abonné emprunte le DVD
		dvd.empruntPar(abonne);
		
		// vérification que le DVD est bien emprunté par l'abonné
		assertTrue(dvd.emprunteur() != null);
		assertEquals(abonne, dvd.emprunteur());
		assertTrue(abonne.getListeEmprunt().contains(dvd));
		assertFalse(abonne.getListeReservation().contains(dvd));
	}
	
	@Test
	public void testRetour() throws RestrictionException {
		// le DVD est réservé et emprunté par l'abonné
		dvd.reservationPour(abonne);
		dvd.empruntPar(abonne);
		
		// l'abonné retourne le DVD
		dvd.retour();
		
		// vérification que le DVD est bien retourné
		assertTrue(dvd.emprunteur() == null);
		assertFalse(dvd.reserveur() != null);
		assertFalse(abonne.getListeEmprunt().contains(dvd));
		assertFalse(abonne.getListeReservation().contains(dvd));
	}
	
	@Test
	public void testEstAdulte() {
		// l'abonné a plus de 16 ans
		assertTrue(abonne.estAdulte());
		
		// création d'un abonné mineur
		IAbonne abonneMineur = new Abonne(2, "Smith", new java.sql.Date(2010, 1, 1));
		
		// vérification que l'abonné mineur n'est pas adulte
		assertFalse(abonneMineur.estAdulte());
	}

}

package appli;

import bserveur.Serveur;

import bserveur.Service;
import mediatheque.Mediatheque;
import mediatheque.ServiceEmprunt;
import mediatheque.ServiceReservation;
import mediatheque.ServiceRetour;



public class AppliServeur {
	
	private final static int[] PORTS = {3000,4000,5000};
	@SuppressWarnings("rawtypes")
	private final static Class[] SERVICES = {ServiceReservation.class, ServiceEmprunt.class, ServiceRetour.class};

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		Service.laMediatheque(new Mediatheque("system", "root"));
		
		new Serveur(SERVICES[0], PORTS[0]).lancer();
		new Serveur(SERVICES[1], PORTS[1]).lancer();
		new Serveur(SERVICES[2], PORTS[2]).lancer();		
		
	}

}

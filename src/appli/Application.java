package appli;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;

import appli.serveur.Serveur;
import appli.serveur.Service;
import appli.serveur.ServiceEmprunt;
import appli.serveur.ServiceReservationFin;
import appli.serveur.ServiceRetour;
import mediatheque.Mediatheque;
import mediatheque.document.RestrictionException;



public class Application {
	
	private final static int[] PORTS = {3000,4000,5000};
	@SuppressWarnings("rawtypes")
	private final static Class[] SERVICES = {ServiceReservationFin.class, ServiceEmprunt.class, ServiceRetour.class};

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, SQLException, RestrictionException {
		
		Service.laMediatheque(new Mediatheque("system", "root"));
		
		new Serveur(SERVICES[0], PORTS[0]).lancer();
		new Serveur(SERVICES[1], PORTS[1]).lancer();
		new Serveur(SERVICES[2], PORTS[2]).lancer();		
		
	}

}

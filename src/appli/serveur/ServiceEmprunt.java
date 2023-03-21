package appli.serveur;


import java.io.BufferedReader;




import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.SQLException;
import java.time.LocalDate;

import mediatheque.Mediatheque;
import mediatheque.document.RestrictionException;

public class ServiceEmprunt extends Service{
	
	public ServiceEmprunt(Socket socket) {
		super(socket);
	}


	@Override
	public void run() {
		try {
			System.out.println("Connexion " + num + " demarree");
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			
			socketOut.println("Quel est le numéro de l'abonné ?");
			int numAb = Integer.parseInt(socketIn.readLine());
			System.out.println("Connexion " + num + " a reçu "+ numAb + " comme numéro d'abonné");
			
			socketOut.println("Quel est le numéro de document ?");
			int numDoc = Integer.parseInt(socketIn.readLine());
			System.out.println("Connexion " + num + " a reçu "+ numDoc + " comme numéro de document");

			try {
				synchronized (mediatheque) {
					mediatheque.emprunt(numAb, numDoc);
				}
			} catch (RestrictionException e) {
				socketOut.println(e);
			}
			
			socketOut.println("Vous devez le rendre ce document avant le " + LocalDate.now().plusDays(30).toString());
			
			socket.close();
			System.out.println("Connexion " + num + " terminee");
		} catch (IOException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	
	}


	

}

package mediatheque;


import java.io.BufferedReader;





import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDate;

import bserveur.Codage;
import bserveur.Service;
import document.RestrictionException;

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
			
			
			socketOut.println(Codage.coder("Quel est le numéro de l'abonné ?"));
			int numAb = Integer.parseInt(Codage.decoder(socketIn.readLine()));
			
			System.out.println("Connexion " + num + " a reçu "+ numAb + " comme numéro d'abonné");
			
			socketOut.println(Codage.coder("Quel est le numéro de document ?"));
			int numDoc = Integer.parseInt(Codage.decoder(socketIn.readLine()));
			
			System.out.println("Connexion " + num + " a reçu "+ numDoc + " comme numéro de document");

			try {
				synchronized (mediatheque) {
					mediatheque.emprunt(numAb, numDoc);
				}
				socketOut.println(Codage.coder("Vous devez le rendre ce document avant le " 
				+ LocalDate.now().plusDays(30).toString()));
			} catch (RestrictionException e) {
				socketOut.println(Codage.coder(e.toString()));
			}
						
			socketOut.close();
			socketIn.close();
			socket.close();
			System.out.println("Connexion " + num + " terminee");
		} catch (Exception e) {
			e.printStackTrace();
		} 
	
	}


	

}

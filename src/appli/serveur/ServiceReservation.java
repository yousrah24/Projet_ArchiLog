package appli.serveur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;

import mediatheque.Mediatheque;
import mediatheque.document.RestrictionException;

public class ServiceReservation extends Service{
	protected int numDoc;
	
	public ServiceReservation(Socket socket) {
		super(socket);
	}
	

	@Override
	public void run() {
		
		System.out.println("Connexion " + num + " demarree");
		try {
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
	        oos.writeObject(Mediatheque.getListeDocument());
			
			socketOut.println("Quel est le numéro de l'abonné ?");
			int numAb = Integer.parseInt(socketIn.readLine());
			System.out.println("Connexion " + num + " a reçu "+ numAb + " comme numéro d'abonné");
			
			socketOut.println("Quel est le numéro de document ?");
			numDoc = Integer.parseInt(socketIn.readLine());
			System.out.println("Connexion " + num + " a reçu "+ numDoc + " comme numéro de document");

			try {
				synchronized (mediatheque) {
					mediatheque.reservation(numAb, numDoc);
				}
			} catch (RestrictionException e) {
				socketOut.println(e);
			}
			socketOut.println("Vous devez aller prendre ce document avant " + LocalDateTime.now().plusHours(2).toString());
				
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

}

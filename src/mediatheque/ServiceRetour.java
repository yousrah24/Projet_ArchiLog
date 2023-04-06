package mediatheque;


import java.io.BufferedReader;



import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import bserveur.Codage;
import bserveur.Service;
import document.RestrictionException;

public class ServiceRetour extends Service{
	
	public ServiceRetour(Socket socket) {
		super(socket);
	}


	@Override
	public void run() {
		try {
			System.out.println("Connexion " + num + " demarree");
			BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			PrintWriter socketOut = new PrintWriter(socket.getOutputStream(), true);
			
			socketOut.println(Codage.coder("Quel est le numéro de document ?"));
			int numDoc = Integer.parseInt(Codage.decoder(socketIn.readLine()));
			
			System.out.println("Connexion " + num + " a reçu "+ numDoc + " comme numéro de document");

			try {
				mediatheque.retour(numDoc);
				socketOut.println(Codage.coder("Retour reussie du document " + numDoc));
			} catch (RestrictionException e) {
				socketOut.println(Codage.coder(e.toString()));
			}
			
			socketOut.close();
			socketIn.close();
			this.socket.close();
			System.out.println("Connexion " + num + " terminee");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}

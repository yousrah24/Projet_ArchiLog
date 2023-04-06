package appli;

import java.io.BufferedReader;


import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

import bserveur.Codage;


public class ServiceBTTPS implements Runnable{
	
	
    private String hote;
    private int port;
     

	public ServiceBTTPS(String hote, int port) {
		this.hote = hote;
		this.port = port;
	}



	@Override
	public void run() {
	    try {
	        Socket socket = new Socket(hote, port);
	        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
	        int num;
            System.out.println("Pour quitter l'application tapez 0\n");
	        // tube de communication sécurisé
	        Scanner sc = new Scanner(System.in);
	        while (true) {
	            // Recevoir le message du service
	            String message = in.readLine();
	            if (message == null) {
	                // Le service a fermé la connexion
	                break;
	            }

	            // Affiche le message envoyé par le service
	            System.out.println(Codage.decoder(message));
	            
	            
            	if (Codage.decoder(message).contains("?")) {
					//Saisie la réponse du client
					do {
						System.out.print("-> ");
						if (sc.hasNextInt()) {
							num = sc.nextInt();
							break;
						} else {
							System.out.println("Erreur: vous devez entrer un nombre entier.");
							sc.next(); // clear input buffer
						}
					} while (true);
					// si le client souhaite arrêter les échanges
					if (num == 0) {
						// fin des échanges
						break;
					}
					// Envoie de la réponse au service
					String response = String.valueOf(num);
					out.println(Codage.coder(response));
				}
	     
	        }

	        // Fermer la connexion
	        socket.close();
	        //Fermer le clavier
	        sc.close();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}




}

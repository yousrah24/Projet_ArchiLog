package appli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;

import mediatheque.IDocument;

public class AppliClientReservation {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ClassNotFoundException {
		try {
			try (Socket socket = new Socket("localhost", 3000)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				Scanner scanner = new Scanner(System.in);
				String question, answer;
				int request = -1;
				
				ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
				List<IDocument> docs = (List<IDocument>) ois.readObject();
		        
		        System.out.println("*".repeat(15)+ " Catalogue " + "*".repeat(35));
		        if(docs.size() == 0) System.out.println("Tous les livres sont empruntés :(");
				for (IDocument doc : docs) {
					if(doc.emprunteur() == null)
						System.out.println(doc);
				}
		        System.out.println("*".repeat(61));
		        
				
				question = in.readLine();
				System.out.println(question);
				while(true){
					System.out.print("-> ");
					if (scanner.hasNextInt()) {
						request = scanner.nextInt();
			            break;
			        } else {
			            System.out.println("Erreur: vous devez entrer un nombre entier.");
			            scanner.next(); // Ignorer l'entrée incorrecte
			        }
					
				}
				out.println(String.valueOf(request));
				
				question = in.readLine();
				System.out.println(question);
				while(true){
					System.out.print("-> ");
					if (scanner.hasNextInt()) {
						request = scanner.nextInt();
			            break;
			        } else {
			            System.out.println("Erreur: vous devez entrer un nombre entier.");
			            scanner.next(); // Ignorer l'entrée incorrecte
			        }
					
				}
				out.println(String.valueOf(request));
				
				answer = in.readLine();
				System.out.println(answer);
				
				scanner.close();
				socket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

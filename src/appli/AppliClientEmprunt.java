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

public class AppliClientEmprunt {

	public static void main(String[] args) throws ClassNotFoundException {
		try {
			try (Socket socket = new Socket("localhost", 4000)) {
				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				Scanner scanner = new Scanner(System.in);
				String question, answer;
				int request = -1;
		        
				
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

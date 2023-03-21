package appli.serveur;

import java.net.Socket;

import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import mediatheque.document.RestrictionException;

public class ServiceReservationFin extends ServiceReservation{
	private long tempsDeService = 2L * 60L * 60L * 1000L;
	private Timer timer;
	
	public ServiceReservationFin(Socket socket) {
		super(socket);
		timer = new Timer();
	    new Thread(this).start();
	    super.lancer();
	}
	
	private class FinReservation extends TimerTask{
		private Thread thread;
		
		public FinReservation(Thread currentThread) {
			thread = currentThread;
		}
		@Override
		public void run() {
			try {
				mediatheque.retour(numDoc);
			} catch (ClassNotFoundException | RestrictionException | SQLException e) {
				e.printStackTrace();
			}
			thread.interrupt();
		}
		
	}
	
	@Override
	public void run() {		
		super.run();
		this.timer.schedule(new FinReservation(Thread.currentThread()), tempsDeService);
		try {
			Thread.sleep(tempsDeService);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		timer.cancel();
		try {
			finalize();
			System.out.println("Connexion " + num + " terminee");
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}

	

}

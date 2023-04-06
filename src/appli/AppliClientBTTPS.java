package appli;



public class AppliClientBTTPS {
	public static void main(String[] args) throws ClassNotFoundException {
		if (args.length != 2) {
            System.out.println("Usage: java AppliClientBTTPS <hôte> <numéro de port>");
            System.exit(1);
        }
		String hote = args[0];
        int port = Integer.parseInt(args[1]);
        
        new Thread(new ServiceBTTPS(hote, port)).start();
        
        
	}

}

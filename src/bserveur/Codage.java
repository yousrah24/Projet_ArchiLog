package bserveur;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Codage {
	
	private static final String ALGORITHM = "AES";
    private static final String SECRET_KEY = "CeciEstUneCleSecreteConfidentiel"; // Clé secrète utilisée pour le cryptage/décryptage

    private static String crypter(String texte) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        byte[] encryptedBytes = cipher.doFinal(texte.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    private static String decrypter(String texteCrypte) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(SECRET_KEY.getBytes(), ALGORITHM);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, keySpec);
        byte[] encryptedBytes = Base64.getDecoder().decode(texteCrypte);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
    
    
	public static String coder(String message) throws Exception {
        return crypter(message.replace("\n", "##"));
    }
    
    public static String decoder(String message) throws Exception {
    	message = decrypter(message);
        return message.replace("##", "\n");
    }
}

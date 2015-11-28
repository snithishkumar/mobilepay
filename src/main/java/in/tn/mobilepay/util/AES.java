package in.tn.mobilepay.util;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class AES {
	
	
	public static void main(String[] args) throws Exception {
		String alg = "AES";
		String key = "TheBestSecreteKe";
		Key key2  = new SecretKeySpec(key.getBytes(), alg);
		Cipher cipher = Cipher.getInstance(alg);
		cipher.init(Cipher.ENCRYPT_MODE, key2);
		String data = "TestString";
		byte[] encVal =  cipher.doFinal(data.getBytes());
		//String encryptedValue = new String(encVal);
		//oWphySKdzYo6PUItshn0kQ==
		String encryptedValue  = Base64.getEncoder().encodeToString(encVal);
		System.out.println(encryptedValue);
	}
	
	private void encryption() throws Exception{
		String alg = "AES";
		String key = "TheBestSecreteKey";
		Key key2  = new SecretKeySpec(alg.getBytes(), key);
		Cipher cipher = Cipher.getInstance(alg);
		cipher.init(Cipher.ENCRYPT_MODE, key2);
		String data = "TestString";
		byte[] encVal =  cipher.doFinal(data.getBytes());
		String encryptedValue = new String(encVal);
		System.out.println(encryptedValue);
	}

}

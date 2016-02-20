package in.tn.mobilepay.services;

import java.security.Key;
import java.time.Clock;
import java.util.Base64;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.hibernate.id.UUIDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

@Service
public class ServiceUtil {

	@Autowired
	private Gson gson;
	
	private Random random = new Random();
	
	
	public <T> T fromJson(String data,Class<T> className){
		return gson.fromJson(data, className);
	}
	
	public <T> String toJson(T object){
		return gson.toJson(object);
	}
	
	public ResponseEntity<String> getSuccessResponse(HttpStatus code,Object object){
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(gson.toJson(object), code);
		return responseEntity;
	}
	
	
	public ResponseEntity<String> getErrorResponse(HttpStatus code,Object object){
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(gson.toJson(object), code);
		return responseEntity;
	}
	
	
	public String netEncryption(String data)throws Exception{
		String alg = "AES";
		String key = "MySecondProjectMSecondPhoneNexus";
		return encryption(data,key,alg);
	}
	
	
	public String netDecryption(String data) throws Exception{
		String alg = "AES";
		String key = "MySecondProjectMSecondPhoneNexus";
		return decryption(data, key, alg);
		
	}
	
	
	
	public String dbEncryption(String data) throws Exception{
		String alg = "AES";
		String key = "MySecondProjectMSecondPhoneNexus";
		return encryption(data,key,alg);
	}
	
	
	private String encryption(String data,String key,String alg)throws Exception{
		Key key2  = new SecretKeySpec(key.getBytes(), alg);
		Cipher cipher = Cipher.getInstance(alg);
		cipher.init(Cipher.ENCRYPT_MODE, key2);
		byte[] encVal =  cipher.doFinal(data.getBytes());
		String encryptedValue  = Base64.getEncoder().encodeToString(encVal);
		return encryptedValue;
	}
	
	
	public String dbDecryption(String data) throws Exception{
		String alg = "AES";
		String key = "MySecondProjectMSecondPhoneNexus";
		return decryption(data, key, alg);
		
	}
	
	private String decryption(String data,String key,String alg) throws Exception{
		Key key2  = new SecretKeySpec(key.getBytes(), alg);
		Cipher cipher = Cipher.getInstance(alg);
		cipher.init(Cipher.DECRYPT_MODE, key2);
		byte[] decryptedByte =  cipher.doFinal(Base64.getDecoder().decode(data));
        String result = new String(decryptedByte);
		return result;
	}
	
	
	public long getCurrentGmtTime(){
		return Clock.systemUTC().millis();
	}
	
	public String generateLoginToken(){
		return UUID.randomUUID().toString()+"-"+(10+random.nextInt(Integer.MAX_VALUE))+"-"+UUID.randomUUID().toString();
	}
}

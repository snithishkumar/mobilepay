package in.tn.mobilepay.services;

import java.security.Key;
import java.security.SecureRandom;
import java.time.Clock;
import java.util.Random;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import in.tn.mobilepay.response.model.ResponseData;

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
	
	public ResponseEntity<String> getResponse(int statusCode,Object object){
		ResponseData responseData = new ResponseData(statusCode, object);
		String temp = gson.toJson(responseData);
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(temp, HttpStatus.OK);
		return responseEntity;
	}
	
	
	public ResponseEntity<String> getErrorResponse(HttpStatus code,Object object){
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(gson.toJson(object), code);
		return responseEntity;
	}
	
	
	public String netEncryption(String data)throws Exception{
		String alg = "AES/CBC/PKCS5Padding";
		String key = "1234567891123456";
		return encryption(data,key,alg);
	}
	
	
	public static void main(String[] args) throws Exception {
		ServiceUtil ss = new ServiceUtil();
		//String dd = ss.netEncryption("test");
		//System.out.println(dd);
		String temp = ss.netDecryption("oBDOSZ6kheF5b39QDqOv9WxE3wKPi7TmyuUX6kYjHJDvnyi+oYQFSqX3JjW3BBF4HV6L7wZIGDm+T3NCbvmKyw==");
		System.out.println(temp);
	}
	
	public String netDecryption(String data) throws Exception{
		String alg = "AES";
		String key = "1234567891123456";
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
		String encryptedValue  = Base64.encodeBase64String(encVal);
		return encryptedValue;
	}
	
	
	public String dbDecryption(String data) throws Exception{
		String alg = "AES/CBC/PKCS5Padding";
		String key = "MySecondProjectMSecondPhoneNexus";
		return decryption(data, key, alg);
		
	}
	
	private String decryption(String data,String key,String alg) throws Exception{
		Key key2  = new SecretKeySpec(key.getBytes(), alg);
		Cipher cipher = Cipher.getInstance(alg);
		cipher.init(Cipher.DECRYPT_MODE, key2);
		byte[] decryptedByte =  cipher.doFinal(Base64.decodeBase64(data));
        String result = new String(decryptedByte);
		return result;
	}
	
	
	public static long getCurrentGmtTime(){
		//return Clock.systemUTC().millis(); -- TODO
		return System.currentTimeMillis();
	}
	
	public String uuid(){
		return UUID.randomUUID().toString();
	}
	
	public String generateLoginToken(){
		return UUID.randomUUID().toString()+"-"+(10+random.nextInt(Integer.MAX_VALUE))+"-"+UUID.randomUUID().toString();
	}
	
	public String getToken() throws Exception{
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(RandomStringUtils.randomAlphanumeric(15));
		SecureRandom secureRandom =  SecureRandom.getInstance("SHA1PRNG");
		stringBuilder.append(secureRandom.nextInt());
		stringBuilder.append(RandomStringUtils.randomAlphanumeric(5));
		return stringBuilder.toString();
	}
}

package in.tn.mobilepay.util;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

public class Test {
	
	/*public static void main(String[] args) {
		DateTime dateTime = DateTime.now(DateTimeZone.UTC);
		dateTime = dateTime.minusHours(3);
		Date time = dateTime.toDate();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.000'Z'");
		dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println(dateFormat.format(time));
	}*/
	
	public static void main(String[] args) {
		try {
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			long value = random.nextLong();
			System.out.println(value+"-"+random.nextGaussian());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}

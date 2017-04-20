package com.appsquabble.bigday.helpers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.jasypt.util.text.BasicTextEncryptor;
import org.mindrot.jbcrypt.BCrypt;

public class Utils {
	
	public static final String pw = "t7y8nv5rnv5789v4y5vn5987vq5n49v3489b5t4n39v58t34598v6q5n4985vqy5n9485vb5g9oy5789y";
	public static long fifteenMinsInMillis = 900000L;
	
	 public static String hashPassword(String Password) {
	        return BCrypt.hashpw(Password, BCrypt.gensalt());

	    }
	 public static String generateRandomKey() {
	        return UUID.randomUUID().toString();
	    }
	 
	 public static long return15MinsFromNow()
		{
			return System.currentTimeMillis() + fifteenMinsInMillis;
		}
	 
	 

	    public static boolean checkPassword(String plainPassword, String encryptedPassword) {
	        return BCrypt.checkpw(plainPassword, encryptedPassword);
	    }

	    public static String getUrlSafeEncryptedToken(String token)
		{
			try {
				return URLEncoder.encode(getEncryptedToken(token),"UTF-8");
			}
			catch(UnsupportedEncodingException e)
			{
				return token;
			}
		}
	    
	    public static String getEncryptedToken(String token)
		{
			return getReadyEncryptor().encrypt(token);
		}
	    
	    private static BasicTextEncryptor getReadyEncryptor(){
			BasicTextEncryptor textEncryptor = new BasicTextEncryptor();
			textEncryptor.setPassword(pw);
			return textEncryptor;
		}
	    
	    public static String getDecryptedToken(String cipher)
		{
			return getReadyEncryptor().decrypt(cipher);
		}
	

}

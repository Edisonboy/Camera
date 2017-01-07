package com.dgut.app.pck;


import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class Des {
	  public static byte[] encrypt(byte[] src, byte[] key)
			    throws Exception
			  {
			    SecureRandom sr = new SecureRandom();
			    DESKeySpec dks = new DESKeySpec(key);
			    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			    SecretKey securekey = keyFactory.generateSecret(dks);
			    Cipher cipher = Cipher.getInstance("DES");
			    cipher.init(1, securekey, sr);

			    return cipher.doFinal(src);
			  }

			  public static byte[] decrypt(byte[] src, byte[] key)
			    throws Exception
			  {
			    SecureRandom sr = new SecureRandom();
			    DESKeySpec dks = new DESKeySpec(key);
			    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			    SecretKey securekey = keyFactory.generateSecret(dks);
			    Cipher cipher = Cipher.getInstance("DES");
			    cipher.init(2, securekey, sr);

			    return cipher.doFinal(src);
			  }
}

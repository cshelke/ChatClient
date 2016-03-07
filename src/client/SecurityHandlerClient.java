package client;
import java.security.*;
//import org.apache.commons.codec.binary.Base64;
import java.util.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;

public class SecurityHandlerClient {
	
	public static String encryptTheMessage(String message, Key key) throws NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		Cipher cyphr = Cipher.getInstance("AES");
        cyphr.init(Cipher.ENCRYPT_MODE, key);
        byte[] byteDataToEncrypt = message.getBytes();
        byte[] byteCipherText = cyphr.doFinal(byteDataToEncrypt);
        return Base64.getEncoder().encodeToString(byteCipherText);
	}
	
	public static String decryptTheMessage(String msg,Key key) throws NoSuchAlgorithmException, 
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		byte[] data = java.util.Base64.getDecoder().decode(msg);
        Cipher cyphr = Cipher.getInstance("AES");
        cyphr.init(Cipher.DECRYPT_MODE, key);
        byte[] plainData = cyphr.doFinal(data);
        String decryptedMsg = new String(plainData);
        //System.out.println("decryptedMsg: " +decryptedMsg);
        return decryptedMsg;
	}
	
	
	public static Key extractKey(String encodedKey)
	{
		System.out.println("generating back the key: "+ encodedKey);
		byte[] decodedKey = Base64.getDecoder().decode(encodedKey);
		Key k = new SecretKeySpec(decodedKey,0, decodedKey.length,"AES");
		
		return k;
		
	}

}

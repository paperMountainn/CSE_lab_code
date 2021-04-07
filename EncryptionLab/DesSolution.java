import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.crypto.*;
import java.util.Base64;
import java.util.Arrays;

public class DesSolution {
    public static void main(String[] args) throws Exception {

        // print out shorttext.txt, store content in shortTextdata
        String fileNameShortText = "shorttext.txt";
        String shortTextdata = "";
        String line;
        BufferedReader bufferedReaderShortText = new BufferedReader( new FileReader(fileNameShortText));
        while((line= bufferedReaderShortText.readLine())!=null){
            shortTextdata = shortTextdata +"\n" + line;
        }
        System.out.println("Original content: "+ shortTextdata);

        // print out longtext.txt, store content in longTextdata
        String fileNameLongText = "longtext.txt";
        String longTextdata = "";
        String line1;
        BufferedReader bufferedReaderLongText = new BufferedReader( new FileReader(fileNameLongText));
        while((line1= bufferedReaderLongText.readLine())!=null){
            longTextdata = longTextdata +"\n" + line1;
        }
        System.out.println("Original content: "+ longTextdata);
 
        

//TODO: generate secret key using DES algorithm
KeyGenerator keyGen = KeyGenerator.getInstance("DES");
 
SecretKey desKey = keyGen.generateKey();
        
//TODO: create cipher object, initialize the ciphers with the given key, choose encryption mode as DES
Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");

//TODO: do encryption, by calling method Cipher.doFinal().

desCipher.init(Cipher.ENCRYPT_MODE, desKey);

byte[] shorttextByteArray = shortTextdata.getBytes();
byte[] longtextByteArray = longTextdata.getBytes();
 

byte[] shortTextEncryptedBytesArray = desCipher.doFinal(shorttextByteArray);
byte[] longTextEncryptedBytesArray = desCipher.doFinal(longtextByteArray);

//TODO: print the length of output encrypted byte[], compare the length of file shorttext.txt and longtext.txt
System.out.println("length of shortTextEncryptedByteArray "+ shortTextEncryptedBytesArray.length);
System.out.println("length of longTextEncryptedByteArray "+ longTextEncryptedBytesArray.length);

System.out.println("shortTextByteArray is "+ Arrays.toString(shorttextByteArray));

//TODO: do format conversion. Turn the encrypted byte[] format into base64format String using Base64
String shortTextbase64format = Base64.getEncoder().encodeToString(shorttextByteArray);
String longTextbase64format = Base64.getEncoder().encodeToString(shorttextByteArray);


//TODO: print the encrypted message (in base64format String format)
System.out.println("shortTextbase64format is "+ shortTextbase64format);
System.out.println("longTextbase64format is "+ shortTextbase64format);

//

//TODO: create cipher object, initialize the ciphers with the given key, choose decryption mode as DES
desCipher.init(Cipher.DECRYPT_MODE, desKey);

//TODO: do decryption, by calling method Cipher.doFinal().

byte[] shortTextDecryptedByteArray = desCipher.doFinal(shortTextEncryptedBytesArray);
byte[] longTextDecryptedByteArray = desCipher.doFinal(longTextEncryptedBytesArray);


//TODO: do format conversion. Convert the decrypted byte[] to String, using "String a = new String(byte_array);"
String  shorttextDecrypted = new String(shortTextDecryptedByteArray);
String longtextDecrypted = new String(longTextDecryptedByteArray);


//TODO: print the decrypted String text and compare it with original text
System.out.println("decrypted shortText is: " + shorttextDecrypted);
System.out.println("decrypted longText is: "+ longtextDecrypted);



    }
}

import java.util.Base64;
import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.*;


public class DigitalSignatureSolution {

    public static void main(String[] args) throws Exception {
//Read the text file and save to String data
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

             

 


//TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.

KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
keyGen.initialize(1024); 
KeyPair keyPair = keyGen.generateKeyPair(); 
Key publicKey = keyPair.getPublic(); 
Key privateKey = keyPair.getPrivate();



//TODO: Calculate message digest, using MD5 hash function

// Create MD object
MessageDigest mdShort = MessageDigest.getInstance("MD5");

MessageDigest mdLong = MessageDigest.getInstance("MD5");


// update MessageDigest Object
byte[] shortTextByteArray = shortTextdata.getBytes();
mdShort.update(shortTextByteArray); 

byte[] longTextByteArray = longTextdata.getBytes();
mdLong.update(shortTextByteArray); 

// compute digest
byte[] shortDigest = mdShort.digest();
byte[] longDigest = mdLong.digest();

//TODO: print the length of output digest byte[], compare the length of file shorttext.txt and longtext.txt
System.out.println("length of output shortDigest byte[] is: " + shortDigest.length);
System.out.println("length of output longDigest byte[] is: " + longDigest.length);
           
//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.

Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);

//TODO: encrypt digest message

byte[] shortEncryptedDigest = rsaCipher.doFinal(shortDigest);
byte[] longEncryptedDigest = rsaCipher.doFinal(longDigest);

System.out.println("shortText signed Digestlength: " + shortEncryptedDigest.length);
System.out.println("longText signed Digestlength: " + longEncryptedDigest.length);
 
System.out.println(longEncryptedDigest.length);


//TODO: print the encrypted message (in base64format String using Base64) 
String shortDigestBase64format = Base64.getEncoder().encodeToString(shortEncryptedDigest);
System.out.println("shortEncryptedDigest in base64 is: " + shortDigestBase64format);

//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.           
Cipher desCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
desCipher.init(Cipher.DECRYPT_MODE, publicKey);

//TODO: decrypt message
byte[] shortDecrpytedDigest = desCipher.doFinal(shortEncryptedDigest);

//TODO: print the decrypted message (in base64format String using Base64), compare with origin digest 
String shortDigestDecryptedBase64 = new String(shortDecrpytedDigest);
System.out.println("shortDigestDecrypted in base64 is: " + shortDigestDecryptedBase64);

    }

}
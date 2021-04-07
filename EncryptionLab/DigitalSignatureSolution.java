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
            String fileName = "shorttext.txt";
            String data = "";
            String line;
            BufferedReader bufferedReader = new BufferedReader( new FileReader(fileName));
            while((line= bufferedReader.readLine())!=null){
                data = data +"\n" + line;
            }
            System.out.println("Original content: "+ data);

             

 


//TODO: generate a RSA keypair, initialize as 1024 bits, get public key and private key from this keypair.

KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA"); 
keyGen.initialize(1024); 
KeyPair keyPair = keyGen.generateKeyPair(); 
Key publicKey = keyPair.getPublic(); 
Key privateKey = keyPair.getPrivate();



//TODO: Calculate message digest, using MD5 hash function

// Create MD object
MessageDigest md = MessageDigest.getInstance("MD5");


// update MessageDigest Object
byte[] shortTextByteArray = data.getBytes(StandardCharsets.UTF_8);
md.update(shortTextByteArray); 

// compute digest
byte[] digest = md.digest();

//TODO: print the length of output digest byte[], compare the length of file shorttext.txt and longtext.txt
System.out.println("length of output digest byte[] is: " + digest.length);
           
//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as encrypt mode, use PRIVATE key.

Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding"); 
rsaCipher.init(Cipher.ENCRYPT_MODE, privateKey);

//TODO: encrypt digest message

byte[] encryptedDigest = rsaCipher.doFinal(digest);


//TODO: print the encrypted message (in base64format String using Base64) 
String digestBase64format = Base64.getEncoder().encodeToString(encryptedDigest);
System.out.println("encrypted digest in base64 is: " + digestBase64format);

//TODO: Create RSA("RSA/ECB/PKCS1Padding") cipher object and initialize is as decrypt mode, use PUBLIC key.           
Cipher desCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
desCipher.init(Cipher.DECRYPT_MODE, publicKey);

//TODO: decrypt message
byte[] decrpytedDigest = desCipher.doFinal(encryptedDigest);

//TODO: print the decrypted message (in base64format String using Base64), compare with origin digest 
String digestDecryptedBase64 = new String(decrpytedDigest);
System.out.println("decrypted digest in base64 is: " + digestDecryptedBase64);

    }

}
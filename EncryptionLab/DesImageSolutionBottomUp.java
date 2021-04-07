import java.lang.Object;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.image.BufferedImage;
import java.nio.*;
import javax.crypto.*;
import java.util.Base64;


public class DesImageSolutionBottomUp {
    public static void main(String[] args) throws Exception{
        int image_width = 200;
        int image_length = 200;
        // read image file and save pixel value into int[][] imageArray
        BufferedImage img = ImageIO.read(new File("triangle.bmp"));

        image_width = img.getWidth();
        image_length = img.getHeight();

        // byte[][] imageArray = new byte[image_width][image_length];
        int[][] imageArray = new int[image_width][image_length];
        for(int idx = 0; idx < image_width; idx++) {
            for(int idy = 0; idy < image_length; idy++) {
                int color = img.getRGB(idx, idy);
                imageArray[idx][idy] = color;            
            }
        } 
// TODO: generate secret key using DES algorithm
KeyGenerator keyGen = KeyGenerator.getInstance("DES");
 
SecretKey desKey = keyGen.generateKey();
        

// TODO: Create cipher object, initialize the ciphers with the given key, choose encryption algorithm/mode/padding,
// Cipher desCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
Cipher desCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
//you need to try both ECB and CBC mode, use PKCS5Padding padding method
desCipher.init(Cipher.ENCRYPT_MODE, desKey);

        // define output BufferedImage, set size and format
        BufferedImage outImage = new BufferedImage(image_width,image_length, BufferedImage.TYPE_3BYTE_BGR);

        for(int idx = 0; idx < image_width; idx++) {
        // convert each column int[] into a byte[] (each_width_pixel)
            byte[] each_width_pixel = new byte[4*image_length];
            for(int idy = 0; idy < image_length; idy++) {
                ByteBuffer dbuf = ByteBuffer.allocate(4);
                dbuf.putInt(imageArray[idx][idy]);
                byte[] bytes = dbuf.array();
                System.arraycopy(bytes, 0, each_width_pixel, idy*4, 4);
            }
// TODO: encrypt each column or row bytes 
byte[] encryptedImageBytes = desCipher.doFinal(each_width_pixel);

IntBuffer intBuf = ByteBuffer.wrap(encryptedImageBytes).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
int[] arrayImg = new int[intBuf.remaining()];
intBuf.get(arrayImg);


// TODO: convert the encrypted byte[] back into int[] and write to outImage (use setRGB)

for (int idy = 0; idy < image_length; idy++){
    
    outImage.setRGB(idx, idy, arrayImg[image_length - idy - 1]);
}





            
            
        }
//write outImage into file
        ImageIO.write(outImage, "BMP",new File("triange_new.bmp"));
    }
}
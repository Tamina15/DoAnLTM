package Server;

//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.*;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class Client {
    private Socket socket = null; 
    public BufferedWriter out = null;
    public BufferedReader in = null;
    BufferedReader stdIn = null; 
    static String pubkey = "pubkeyclient.bin";
    static String prikey = "prikeyclient.bin";
      byte[] ServerpubKeyByte;     
    SecretKey secKey;

    public  Client(String address, int port) throws UnknownHostException, IOException, NoSuchAlgorithmException
	{ 
		socket = new Socket(address, port); 
		System.out.println("Da ket noi"); 
		out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		stdIn = new BufferedReader(new InputStreamReader(System.in));
                createAESkey();
//                System.out.println(Encrypt("test"));
//                System.out.println(Decrypt(Encrypt("test")));
//                sendpublickey();
                String exit = "";
	}     
//    public void send(String s) throws IOException
//    {
//        
//         out.write(Encrypt(s));
//         out.newLine();
//         out.flush();
//    }
//    public String receive() throws IOException
//    {
//        return Decrypt(in.readLine());
//        
//    }
    public void exit() throws IOException
    {
        in.close(); 
	out.close(); 
	socket.close(); 
    }
    

//    public void sendpublickey()
//        {
//            try {
//            String tmp = in.readLine();
//            ServerpubKeyByte = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.decode(tmp);
//            out.write(EncryptKey(secKey));
//            out.newLine();
//            out.flush();
//            } catch (IOException e) {
//                System.out.println(e);
//            
//        }
//        }
//        public String EncryptKey(SecretKey sec)
//        {
//            try {
//                     // Tạo public key
//                    X509EncodedKeySpec spec = new X509EncodedKeySpec(ServerpubKeyByte);
//                    KeyFactory factory = KeyFactory.getInstance("RSA");
//                    PublicKey ClientpubKey = factory.generatePublic(spec);
//                    // Mã hoá dữ liệu
//                    Cipher c = Cipher.getInstance("RSA");
//                    c.init(Cipher.ENCRYPT_MODE, ClientpubKey);
//                    byte encryptOut[] = c.doFinal(sec.getEncoded());
//                    String strEncrypt = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(encryptOut);
//                    System.out.println("Chuỗi sau khi mã hoá: " + strEncrypt);
//                    return strEncrypt;
//            } catch (Exception ex) {
//              ex.printStackTrace();
//              return ex.toString();
//            }
//        }
        public void createAESkey() throws NoSuchAlgorithmException
        {
            KeyGenerator generator = KeyGenerator.getInstance("AES");
            generator.init(128); // The AES key size in number of bits
            secKey = generator.generateKey();
        }
//        public String Encrypt(String valueEnc) { 
//
//            String encryptedVal = null;
//
//            try {
//                Cipher c = Cipher.getInstance("AES");
//                c.init(Cipher.ENCRYPT_MODE, secKey);
//                byte[] encValue = c.doFinal(valueEnc.getBytes());
//                encryptedVal = com.sun.org.apache.xerces.internal.impl.dv.util.Base64.encode(encValue);
//            } catch(Exception ex) {
//                System.out.println("The Exception is=" + ex);
//            }
//
//            return encryptedVal;
//        }
//        public String Decrypt(final String encryptedValue) {
//
//            String decryptedValue = null;
//
//            try {
//                final Cipher c = Cipher.getInstance("AES");
//                c.init(Cipher.DECRYPT_MODE, secKey);
//                byte[] decorVal =Base64.decode(encryptedValue);
//                final byte[] decValue = c.doFinal(decorVal);
//                decryptedValue = new String(decValue);
//            } catch(Exception ex) {
//                System.out.println("The Exception is=" + ex);
//            }
//
//            return decryptedValue;
//        }
    public static void main(String[] args) throws IOException, UnknownHostException, NoSuchAlgorithmException {
        Client client2 = new Client("127.0.0.1", 10164); 
//        client2.send("abckasjhgdaskfa");
  //      create cr = new create(client2);
    }


}

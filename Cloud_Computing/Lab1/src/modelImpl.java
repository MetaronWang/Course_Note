import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class modelImpl implements model {
    private KeyGenerator kg;
    private SecretKey key;
    private Cipher cipher;

    public modelImpl() throws NoSuchAlgorithmException, NoSuchPaddingException{
        kg = KeyGenerator.getInstance("AES");
        key = kg.generateKey();
        cipher = Cipher.getInstance("AES");
    }

    @Override
    public byte[] encrypt(byte[] b) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(b);
    }

    @Override
    public byte[] decrypt(byte[] b) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(b);
    }

    @Override
    public int register(byte[] usernameByte, byte[] passwordByte) throws Exception {
        String username = new String(decrypt(usernameByte));
        String password = new String(decrypt(passwordByte));
        return JDBC.doRegister(username,password);
    }

    @Override
    public int login(byte[] usernameByte, byte[] passwordByte) throws Exception {
        String username = new String(decrypt(usernameByte));
        String password = new String(decrypt(passwordByte));
        return JDBC.doLogin(username,password);
    }

}
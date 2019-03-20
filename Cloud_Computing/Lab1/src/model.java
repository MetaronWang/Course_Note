import java.rmi.Remote;
import java.rmi.RemoteException;

public interface model extends Remote {
    public byte[] encrypt(byte[] b)throws Exception;
    public byte[] decrypt(byte[] b) throws Exception;
    public int register(byte[] usernameByte, byte[] passwordByte) throws Exception;
    public int login(byte[] usernameByte, byte[] passwordByte) throws Exception;
}

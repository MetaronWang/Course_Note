import java.rmi.registry.Registry;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.net.InetAddress;

public class Server {

	public Server() {
    }

	public static void main(String args[]) {
		try {
			modelImpl robj = new modelImpl();
			model stub = (model) UnicastRemoteObject.exportObject(robj, 0);
			Registry registry = LocateRegistry.getRegistry();
			registry.bind("Mortgage", stub);
			System.out.print("Mortgage Server is ready to listen... ");
			System.out.println(InetAddress.getLocalHost().getHostName());
		} catch (Exception e) {
			System.err.println("Server exception thrown: " + e.toString());
			e.printStackTrace();
		}
	}
}

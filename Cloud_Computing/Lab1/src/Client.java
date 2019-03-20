import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class Client {
    private static model stub = null;
    private Client() {}

    public static void main(String[] args) throws Exception {
        System.out.println("Please Input your option, login, register or exit");
        try {
            Registry reg = LocateRegistry.getRegistry("localhost");
            stub = (model) reg.lookup("Mortgage");
        } catch (Exception e) {
            System.err.println("Client exception thrown: " + e.toString());
            e.printStackTrace();
        }
        Scanner sc = new Scanner(System.in);
        while(true){
            String a = sc.nextLine();
            if (a.equals("exit"))
                break;
            if (a.equals("register")){
                System.out.println("Please input username");
                String username = sc.nextLine();
                System.out.println("Please input password");
                String password = sc.nextLine();
                int status = stub.register(stub.encrypt(username.getBytes()), stub.encrypt(password.getBytes()));
                if (status==1)
                    System.out.println("Register Successfully");
                else{
                    System.out.println("This username has been used, please try a new one");
                }
            }
            else if(a.equals("login")){
                System.out.println("Please input username");
                String username = sc.nextLine();
                System.out.println("Please input password");
                String password = sc.nextLine();
                int status = stub.login(stub.encrypt(username.getBytes()), stub.encrypt(password.getBytes()));
                if (status==1)
                    System.out.println("Login Successfully");
                else if(status==0)
                    System.out.println("Password Wrong");
                else
                    System.out.println("User not exist");
            }
            System.out.println("Please Input your option, login, register or exit");
        }
        
    }

}
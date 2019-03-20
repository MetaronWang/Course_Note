import java.rmi.RemoteException;
import java.sql.*;

public class JDBC {
    public static int doLogin(String username, String password) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/cc_lab1", "yuan", "12345678");
        Statement stat = conn.createStatement();
        String sql = "select password from user_pass where username=\'"+username+"\';";
        ResultSet rs = stat.executeQuery(sql);
        if(rs.next()){
            String temp = rs.getString(1);
            if (BCrypt.checkpw(password, temp)){
                conn.close();
                return 1;//True
            }
            else{
                conn.close();
                return 0;//Wrong
            }

        }
        else{
            conn.close();
            return 2;//Username is not exist
        }
    }
    public static int doRegister(String username, String password) throws Exception {
        password = BCrypt.hashpw(password, BCrypt.gensalt(12));
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("JDBC:mysql://localhost:3306/cc_lab1", "yuan", "12345678");
        Statement stat = conn.createStatement();
        String sql = "select count(username) from user_pass where username=\'"+username+"\';";
        ResultSet rs = stat.executeQuery(sql);
        rs.next();
        if (rs.getInt(1)>0) {
            conn.close();
            return 0;//means this username is already in the database
        }
        else{
            sql = "insert into user_pass(username, password)value(\'"+username+"\',\'"+password+"\');";
            stat.executeUpdate(sql);
            conn.close();
            return 1;//Success
        }
    }
}

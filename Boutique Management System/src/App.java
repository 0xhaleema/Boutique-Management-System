import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App{
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/bms";
        String user = "root";
        String password = "23062712";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected successfully!");
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

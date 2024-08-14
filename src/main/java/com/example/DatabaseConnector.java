import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnector {
    public static void main(String[] args) {
        String url  = "jdbc:mysql://localhost:3306/work";
        String user = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to the database successfully!");
        } catch (SQLException e) {
            System.out.println("SQL Error: " + e.getMessage());
        }
    }
}

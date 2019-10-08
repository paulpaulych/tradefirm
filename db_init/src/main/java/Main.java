import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.postgres.Driver");
        Connection connection = DriverManager.getConnection("jdbc:postgres:localhost:5432/trade-firm", "user", "changeme");
    }
}

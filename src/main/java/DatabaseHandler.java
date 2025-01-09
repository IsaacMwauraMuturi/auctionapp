import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseHandler {
    private final String DB_URL = "jdbc:mysql://localhost:3306/test_jdbc";
    private final String DB_USERNAME = "root";
    private final String DB_PASSWORD = "";

    private Connection connection;

    /**
     * Constructor initializes the database connection.
     */
    public DatabaseHandler() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the database connection.
     * 
     * @return The database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Closes the database connection.
     */
    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

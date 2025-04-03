
package conexion;

/**
 *
 * @author Aley Cabrera D
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/quorum360?useSSL=false&serverTimezone=UTC";
    private static final String USER = "quorum_user"; // Cambiar por tus credenciales
    private static final String PASSWORD = "TuContr@$eñaSegur@2025Quorum$60";
    
    static {
        try {
            // Registrar el driver (nuevo método para JDBC 4.0+)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            throw new ExceptionInInitializerError("Error al cargar el driver JDBC: " + ex.getMessage());
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public static void closeConnection(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}

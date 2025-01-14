package hellofx;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER = "root";
    private static final String PASSWORD = "parola";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void closeConnection(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            // e.printStackTrace();
        }
    }

    public static boolean validateUser(String username, String password) {
        String query = "SELECT * FROM Customer WHERE username = ? and password = ?";
        try (Connection connection = getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query))
            {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String username, String password, String email) {
        if(validateUser(username, password)) {
            System.out.println("User already exists");
            return false;
        }
        String query = "INSERT INTO Customer (customerID, username, password, email) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, generateCustomerID());
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setString(4, email);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            // e.printStackTrace();
            return false;
        }
    }

    public static int generateCustomerID() throws SQLException
    {
        String sql = "SELECT MAX(customerID) FROM Customer";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            if (rs.next())
            {
                return rs.getInt(1) + 1;
            }
            return 1;
        }
    }


}

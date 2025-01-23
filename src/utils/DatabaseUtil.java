package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import SHA.SHA;
import java.security.NoSuchAlgorithmException;

public class DatabaseUtil {
    private static final String URL = "jdbc:mysql://localhost:3306/users";
    private static final String USER = "root";
    private static final String PASSWORD = "parola";
    private static final String CUSTOMERS_TABLE = "customers";
    private static final String CUSTOMER_ID = "customer_id";
    private static final String TRANSACTIONS_TABLE = "transactions";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static float getAmount(int customer_id)
    {
        String query = "SELECT amount FROM " + TRANSACTIONS_TABLE + " WHERE customer_id = ?"; //
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getFloat("amount");
            }
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static float deposit(int customer_id, float amount)
    {
        String query = "UPDATE " + TRANSACTIONS_TABLE + " SET amount = amount + ? WHERE customer_id = ?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setFloat(1, amount);
            preparedStatement.setInt(2, customer_id);
            preparedStatement.executeUpdate();

            return getAmount(customer_id);
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static float getAmountFromDB(int customer_id)
    {
        String query = "SELECT amount FROM " + TRANSACTIONS_TABLE + " WHERE customer_id = ?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, customer_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                return resultSet.getFloat("amount");
            }
            else return -1;
        } catch (SQLException e) {
            e.printStackTrace();
            return -1;
        }
    }


    public static boolean validateUser(String username, String password) {
        try
        {
            byte[] hashedPassword = SHA.getSHA(password);
            String hashedPasswordHex = SHA.toHexString(hashedPassword);
            String query = "SELECT * FROM " + CUSTOMERS_TABLE + " WHERE username = ? and password = ?";
            try (Connection connection = getConnection();
                    PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, hashedPasswordHex);
                ResultSet resultSet = preparedStatement.executeQuery();
                return resultSet.next();
            } catch (SQLException e) {
                // e.printStackTrace();
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean registerUser(String username, String password, String email) {
        try {
            // Assuming SHA.getSHA is used here
            byte[] hashedPassword = SHA.getSHA(password);
            String encryptedPassword = SHA.toHexString(hashedPassword);

            // Code to register the user with encryptedPassword
            String userQuery = "INSERT INTO " + CUSTOMERS_TABLE + " (username, password, email) VALUES (?, ?, ?)";
            try (Connection connection = getConnection();
                 PreparedStatement userStatement = connection.prepareStatement(userQuery)) {
                userStatement.setString(1, username);
                userStatement.setString(2, encryptedPassword);
                userStatement.setString(3, email);
                int userRowsInserted = userStatement.executeUpdate();

                if (userRowsInserted > 0) {
                    // Insert initial amount for the new user
                    double initialAmount = 100.0;
                    String transactionQuery = "INSERT INTO " + TRANSACTIONS_TABLE + " (customer_id, amount) VALUES (?, ?)";
                    try (PreparedStatement transactionStatement = connection.prepareStatement(transactionQuery)) {
                        int customerId = getUserId(username);
                        transactionStatement.setInt(1, customerId);
                        transactionStatement.setDouble(2, initialAmount);
                        return transactionStatement.executeUpdate() > 0;
                    }
                } else {
                    return false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static int getUserId(String username) {
        String query = "SELECT " + CUSTOMER_ID + " FROM " + CUSTOMERS_TABLE + " WHERE username = ?";
        try (Connection connection = getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, username);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    if(resultSet.next()) {
                        return resultSet.getInt(CUSTOMER_ID);
                    }
                    else return -1;
                } catch (SQLException e) {
                    e.printStackTrace();
                    return -1;
                }
    }




}

package db;

import model.User;

import java.sql.*;

public class UserDAO {
    public void addUser(User user) {
        // Database logic to insert user into database
        // Example using JDBC:
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library_db", "root", "");
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO members_tb (full_name, phone_number, address, password) VALUES (?, ?, ?, ?)")) {

            pstmt.setString(1, user.getFullName());
            pstmt.setString(2, user.getPhoneNumber());
            pstmt.setString(3, user.getAddress());
            pstmt.setString(4, user.getPassword());

            pstmt.executeUpdate();

            // Optionally handle success or error cases
        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
    }

    public boolean checkLogin(String username, String password) {
        boolean isValidLogin = false;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM members_tb WHERE full_name = ? AND password = ?")) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                isValidLogin = true;
            }

        } catch (SQLException e) {
            e.printStackTrace(); // Handle or log the exception appropriately
        }
        return isValidLogin;
    }
}

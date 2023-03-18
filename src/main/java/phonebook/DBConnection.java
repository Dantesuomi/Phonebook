package phonebook;

import java.sql.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/phonebook";
        String username = "root";
        String password = "12345";
        return DriverManager.getConnection(dbURL, username, password);
    }

    private void saveContactToDB(){

    }

    public static void createContact(Contact contact) {
        try {
            String sql = "INSERT INTO Phonebook (fullName, phoneNumber, email) VALUES (?,?,?)";

            Connection dbConnection = DBConnection.getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, contact.getFullName());
            preparedStatement.setString(2, contact.getPhoneNumber());
            preparedStatement.setString(3, contact.getEmail());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Contact> findContacts(Contact contact){
        try {
            String searchQuery = "";
            if(!contact.getFullName().equals("") && !contact.getPhoneNumber().equals("")){
                searchQuery = "fullName LIKE '%" + contact.getFullName() + "%' OR phoneNumber LIKE '%" + contact.getPhoneNumber() + "%'";
            } else if(!contact.getFullName().equals("")){
                searchQuery = "fullName LIKE '%" + contact.getFullName() + "%'";
            } else if (!contact.getPhoneNumber().equals("")) {
                searchQuery = "phoneNumber LIKE '%" + contact.getPhoneNumber() + "%'";
            }
            String sql = "SELECT * FROM Phonebook WHERE " + searchQuery;

            Connection dbConnection = DBConnection.getConnection();
            Statement statement = dbConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            ArrayList<Contact> contacts = new ArrayList<>();

            while  (resultSet.next()) {
                Contact dbContact = new Contact(resultSet.getString("fullName"), resultSet.getString("phoneNumber"), resultSet.getString("email"));
                contacts.add(dbContact);
            }
            return contacts;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}

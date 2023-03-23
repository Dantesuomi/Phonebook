package phonebook;

import java.sql.*;
import java.util.ArrayList;

public class DBConnection {
    public static Connection getConnection() throws SQLException {
        String dbURL = "jdbc:mysql://localhost:3306/phonebook";
        String username = "root";
        String password = "0865";
        return DriverManager.getConnection(dbURL, username, password);
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

    public static void updateContact(Contact contactToUpdate, Contact updatedContact){
        try {
            String sql = "UPDATE Phonebook SET fullName=?, phoneNumber=?, email=? WHERE fullName=? AND phoneNumber=?";

            Connection dbConnection = DBConnection.getConnection();

            PreparedStatement preparedStatement = dbConnection.prepareStatement(sql);
            preparedStatement.setString(1, updatedContact.getFullName());
            preparedStatement.setString(2, updatedContact.getPhoneNumber());
            preparedStatement.setString(3, updatedContact.getEmail());
            preparedStatement.setString(4, contactToUpdate.getFullName());
            preparedStatement.setString(5, contactToUpdate.getPhoneNumber());

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

    public static ArrayList<Contact> getAllContacts(){
        try {
            String sql = "SELECT * FROM Phonebook" ;
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

    public static void deleteContact(Contact contact){
        try {

            String sql = "DELETE FROM Phonebook WHERE fullName='" + contact.getFullName() + "' AND phoneNumber='" + contact.getPhoneNumber() + "'";

            Connection dbConnection = DBConnection.getConnection();
            Statement statement = dbConnection.createStatement();
            statement.executeUpdate(sql);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

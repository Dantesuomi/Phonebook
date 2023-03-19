package phonebook;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class MenuController {

    public void start(){
        int result = JOptionPane.showConfirmDialog(null,
                "Welcome to Phonebook" +
                        " Please choose an option on the next prompt", "Welcome to Phonebook", JOptionPane.OK_CANCEL_OPTION);
        if(result == JOptionPane.OK_OPTION){
            this.displayMainMenu();
        }
        else {
            System.exit(0);
        }
    }

    private void displayMainMenu(){

        String option = this.getUserInput(
                "Write the number of activity you want to perform\n" +
                        "1. Add new contact\n" +
                        "2. Remove contact\n" +
                        "3. Find contact\n" +
                        "4. Update contact\n" +
                        "5. Show contact list\n" +
                        "6. Export contact list into CSV\n" +
                        "7. Import contacts from CSV file\n" +
                        "8. Exit application\n"
        );

        switch (option){
            case "1" :
                this.addNewContact();
                break;
            case "2" :
                this.removeContact();
                break;
            case "3":
                this.findContact();
                break;
            case "4":
                this.updateContact();
                break;
            case "5":
                this.showContactList();
                break;
            case "6":
                this.exportToCsv();
                break;
            case "7":
                this.importFromCsv();
                break;
            case "8":
                System.exit(0);
                break;

        }
        this.displayMainMenu();

    }

    private void importFromCsv() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(System.getProperty("user.home")));
        JPanel exportToCsvPanel = new JPanel();
        int result = fileChooser.showOpenDialog(exportToCsvPanel);

        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            File selectedFile = fileChooser.getSelectedFile();
            try {
                ArrayList<Contact> contacts = Csv.readTheFile(selectedFile);
                for (Contact contact : contacts) {
                    DBConnection.createContact(contact);
                }
                JOptionPane.showMessageDialog(null, "Import successful");
            }
            catch (Exception e){
                JOptionPane.showMessageDialog(null, "Failed to import","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportToCsv() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setSelectedFile(new File(System.getProperty("user.home") + "\\export.csv"));
        JPanel exportToCsvPanel = new JPanel();
        int result = fileChooser.showOpenDialog(exportToCsvPanel);


        if (result == JFileChooser.APPROVE_OPTION) {
            // user selects a file
            File selectedFile = fileChooser.getSelectedFile();
            ArrayList<Contact> contacts = DBConnection.getAllContacts();
            boolean writeSucceeded = Csv.writeToFile(contacts, selectedFile);
            if(writeSucceeded){
                JOptionPane.showMessageDialog(null, "Export successful");
            }
            else {
                JOptionPane.showMessageDialog(null, "Failed to export","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String getUserInput(String message){
        return JOptionPane.showInputDialog(null, message);
    }

    private void showContactList() {
        ArrayList<Contact> contacts = DBConnection.getAllContacts();
        JPanel panel = generateContactPanel(contacts);

        JOptionPane.showMessageDialog(null, panel, "Your contacts", JOptionPane.INFORMATION_MESSAGE);

    }

    private void updateContact() {
        JTextField fullNameField = new JTextField(10);
        JTextField phoneNumberField = new JTextField(10);


        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Full Name: "));
        myPanel.add(fullNameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Phone number: "));
        myPanel.add(phoneNumberField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter contact details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Contact lookupContact = new Contact(fullNameField.getText(), phoneNumberField.getText());

            ArrayList<Contact> contacts = DBConnection.findContacts(lookupContact);
            if (contacts.isEmpty()){
                JOptionPane.showMessageDialog(null, "Contact not found");
                return;
            }

            Contact contactToUpdate = (Contact) JOptionPane.showInputDialog(null, "Choose, which contact to update", "Choose contact", JOptionPane.QUESTION_MESSAGE, null, contacts.toArray(), 0);

            executeContactUpdatePane(contactToUpdate);

        }
    }

    private static void executeContactUpdatePane(Contact contactToUpdate) {
        JTextField updatedFullNameField = new JTextField(contactToUpdate.getFullName(), 10);
        JTextField updatedPhoneNumberField = new JTextField(contactToUpdate.getPhoneNumber(), 10);
        JTextField updatedEmailField = new JTextField(contactToUpdate.getEmail(), 10);

        JPanel updateContactPanel = new JPanel();
        updateContactPanel.add(new JLabel("Full Name: "));
        updateContactPanel.add(updatedFullNameField);
        updateContactPanel.add(Box.createHorizontalStrut(15)); // a spacer
        updateContactPanel.add(new JLabel("Phone number: "));
        updateContactPanel.add(updatedPhoneNumberField);
        updateContactPanel.add(Box.createHorizontalStrut(15)); // a spacer
        updateContactPanel.add(new JLabel("Email(optional): "));
        updateContactPanel.add(updatedEmailField);

        int updatedResult = JOptionPane.showConfirmDialog(null, updateContactPanel,
                "Please enter contact details", JOptionPane.OK_CANCEL_OPTION);
        if (updatedResult == JOptionPane.OK_OPTION) {
            if(StringHelpers.isNullOrEmpty(updatedFullNameField.getText()) || StringHelpers.isNullOrEmpty(updatedPhoneNumberField.getText()) ) {
                JOptionPane.showMessageDialog(null, "Please enter both full name and phone number", "Error", JOptionPane.ERROR_MESSAGE);
                executeContactUpdatePane(contactToUpdate);
            }
            else {
                Contact updatedContact = new Contact(updatedFullNameField.getText(), updatedPhoneNumberField.getText(), updatedEmailField.getText());
                DBConnection.updateContact(contactToUpdate, updatedContact);

                JOptionPane.showMessageDialog(null, "Contact was successfully updated");
            }
        }
    }

    private void findContact() {

        JTextField fullNameField = new JTextField(10);
        JTextField phoneNumberField = new JTextField(10);


        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Full Name: "));
        myPanel.add(fullNameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Phone number: "));
        myPanel.add(phoneNumberField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter contact details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Contact lookupContact = new Contact(fullNameField.getText(), phoneNumberField.getText());

            ArrayList<Contact> contacts = DBConnection.findContacts(lookupContact);
            JPanel panel = generateContactPanel(contacts);

            JOptionPane.showMessageDialog(null, panel, "Search results", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void removeContact() {

        JTextField fullNameField = new JTextField(10);
        JTextField phoneNumberField = new JTextField(10);


        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Full Name: "));
        myPanel.add(fullNameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Phone number: "));
        myPanel.add(phoneNumberField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter contact details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            Contact lookupContact = new Contact(fullNameField.getText(), phoneNumberField.getText());

            ArrayList<Contact> contacts = DBConnection.findContacts(lookupContact);
            if (contacts.isEmpty()){
                JOptionPane.showMessageDialog(null, "Contact not found");
                return;
            }

            Contact contactToRemove = (Contact) JOptionPane.showInputDialog(null, "Which contact to delete", "Choose contact", JOptionPane.QUESTION_MESSAGE, null, contacts.toArray(), 0);
            DBConnection.deleteContact(contactToRemove);

            JOptionPane.showMessageDialog(null, "Contact was successfully deleted");
        }
    }

    private void addNewContact() {
        JTextField fullNameField = new JTextField(10);
        JTextField phoneNumberField = new JTextField(10);
        JTextField emailField = new JTextField(10);

        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Full Name: "));
        myPanel.add(fullNameField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Phone number: "));
        myPanel.add(phoneNumberField);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Email(optional): "));
        myPanel.add(emailField);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Please enter contact details", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            if(StringHelpers.isNullOrEmpty(fullNameField.getText()) || StringHelpers.isNullOrEmpty(phoneNumberField.getText()) ) {
                JOptionPane.showMessageDialog(null, "Please enter both full name and phone number", "Error", JOptionPane.ERROR_MESSAGE);
                addNewContact();
            }
            else {
                Contact contact = new Contact(fullNameField.getText(), phoneNumberField.getText(), emailField.getText());
                DBConnection.createContact(contact);
            }
        }

    }

    private JPanel generateContactPanel(ArrayList<Contact> contacts){
        Vector<Vector<String>> dataVector = new Vector<>();
        for (Contact contact : contacts) {
            Vector<String> rowVector = new Vector<>();
            rowVector.add(contact.getFullName());
            rowVector.add(contact.getPhoneNumber());
            rowVector.add(contact.getEmail());
            dataVector.add(rowVector);
        }

        Vector<String> columnNamesVector = new Vector<>();
        columnNamesVector.add("Full name");
        columnNamesVector.add("Phone number");
        columnNamesVector.add("Email");

        DefaultTableModel tableModel = new DefaultTableModel(dataVector, columnNamesVector);
        JTable table = new JTable(tableModel);
        JPanel panel = new JPanel();
        panel.add(new JScrollPane(table));
        return panel;
    }

}

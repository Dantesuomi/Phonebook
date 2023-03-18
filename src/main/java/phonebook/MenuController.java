package phonebook;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.TreeSet;

public class MenuController {

    public void start(){
        JOptionPane.showConfirmDialog(null,
                "Welcome to Phonebook" +
                        " Please choose an option on the next prompt", "Welcome to Phonebook", JOptionPane.OK_CANCEL_OPTION);

        this.displayMainMenu();
    }

    private void displayMainMenu(){

        String option = this.getUserInput(
                "Write the number of activity you want to perform\n" +
                        "1. Add new contact\n" +
                        "2. Remove contact\n" +
                        "3. Find contact\n" +
                        "4. Update contact\n" +
                        "5. Show contact list\n" +
                        "6. Exit application\n"
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
                System.exit(0);
                break;

        }
        this.displayMainMenu();

    }

    private String getUserInput(String message){
        return JOptionPane.showInputDialog(null, message);
    }

    private void showContactList() {
    }

    private void updateContact() {
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
            //TODO : ADD VALIDATION
            Contact contact = new Contact(fullNameField.getText(), phoneNumberField.getText());

            ArrayList<Contact> contacts = DBConnection.findContacts(contact);
            String message = "";

            if(contacts.isEmpty()){
                message = "No matches found";
            }else {
                message = contacts.toString();
            }
            JOptionPane.showMessageDialog(null, message, "Search results", JOptionPane.INFORMATION_MESSAGE);
        }



    }

    private void removeContact() {
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
            //TODO : ADD VALIDATION
            Contact contact = new Contact(fullNameField.getText(), phoneNumberField.getText(), emailField.getText());
            DBConnection.createContact(contact);
        }

    }



}

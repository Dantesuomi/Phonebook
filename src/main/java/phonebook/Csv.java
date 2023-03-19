package phonebook;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvValidationException;
import java.io.*;
import java.util.ArrayList;

public class Csv {
    public static boolean writeToFile(ArrayList<Contact> contacts, File fileToWrite){
        try (CSVWriter writer = new CSVWriter(new FileWriter(fileToWrite))) {
            String[] header = {"fullName", "phoneNumber", "email"};
            writer.writeNext(header);

            for (Contact contact : contacts) {
                String[] row = {contact.getFullName(), contact.getPhoneNumber(), contact.getEmail()};
                writer.writeNext(row);
            }
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Contact> readTheFile(File fileToRead) throws IOException, CsvValidationException {
        ArrayList<Contact> contacts = new ArrayList<>();
        CSVReader reader = new CSVReader(new FileReader(fileToRead));
        // Discard the header
        reader.readNext();

        String[] row;
        while ((row = reader.readNext()) != null) {
            Contact contact = new Contact(row[0], row[1], row[2]);
            contacts.add(contact);
        }

        return contacts;
    }
}


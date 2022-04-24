package sample.Domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.apache.commons.lang3.SerializationUtils;


// The purpose of this class is to wrap the password table in an observable list. This allows us to utilize
// the functionality from the observable list. There is also add and delete functionality for this class
public class ObservablePasswordEntryList {

    // Observable list
    private ObservableList<Node> passwordEntries = FXCollections.observableArrayList();

    // Making this a singleton
    private static ObservablePasswordEntryList instance = new ObservablePasswordEntryList();


    public void deserializeAllExistingEntries(byte[] binaryPasswordTable){
        // Deserializing the password table and filling it into the observable password table list
        PasswordTable passwordTable = SerializationUtils.deserialize(binaryPasswordTable);
        for ( PasswordEntry pe : passwordTable ) {
            this.passwordEntries.add(new EntryHBox(pe.site, pe.url, pe.username, pe.password));
        }
    }

    public byte[] serializePasswordTable() {

        // New simple password table
        PasswordTable passwordTable = new PasswordTable();

        // Filling it with entries from the password table list
        for (Node ehb : this.passwordEntries) {
            passwordTable.add(new PasswordEntry(
                    ((EntryHBox) ehb).getSite(), ((EntryHBox) ehb).getUrl(), ((EntryHBox) ehb).getUsername(), ((EntryHBox) ehb).getPassword())
            );
        }

        // Return a serialized password table
        return SerializationUtils.serialize(passwordTable);
    }

    public String addEntry(EntryHBox eHBox){

        // Adding an entry. Checking if the entry's url already exists. The url is the "primary key"
        for (int i = 0; i < this.passwordEntries.size(); i++) {
            if (((EntryHBox) this.passwordEntries.get(i)).getUrl().equals(eHBox.getUrl())) {
                this.passwordEntries.set(i, eHBox);
                return "Entry changed";
            }
        }
        this.passwordEntries.add(eHBox);
        return "Entry added!";
    }

    public void deleteEntry(EntryHBox eHBox){
        this.passwordEntries.remove(eHBox);
    }

    public ObservableList<Node> getPasswordEntries() {
        return this.passwordEntries;
    }

    public static ObservablePasswordEntryList getInstance(){
        return instance;
    }
}

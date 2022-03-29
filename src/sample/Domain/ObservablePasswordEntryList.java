package sample.Domain;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.SerializationUtils;

public class ObservablePasswordEntryList {
    private ObservableList<Node> passwordEntries;
    private static ObservablePasswordEntryList instance = new ObservablePasswordEntryList();

    private ObservablePasswordEntryList(){
        this.passwordEntries = FXCollections.observableArrayList();
    }

    public void deserializeAllExistingEntries(byte[] binaryObservablePasswordEntryList){
        PasswordTable passwordTable = SerializationUtils.deserialize(binaryObservablePasswordEntryList);
        for ( PasswordEntry pe : passwordTable ) {
            this.passwordEntries.add(new EntryHBox(pe.site, pe.url, pe.username, pe.password));
        }
    }

    public byte[] serializeObservablePasswordEntryList() {
        PasswordTable passwordTable = new PasswordTable();
        for (Node ehb : this.passwordEntries) {
            passwordTable.add(new PasswordEntry(
                    ((EntryHBox) ehb).getSite(), ((EntryHBox) ehb).getUrl(), ((EntryHBox) ehb).getUsername(), ((EntryHBox) ehb).getPassword())
            );
        }
        return SerializationUtils.serialize(passwordTable);
    }

    public String addEntry(EntryHBox eHBox){
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
        //System.out.println(this.passwordEntries.size());
        this.passwordEntries.remove(eHBox);
    }

    public ObservableList<Node> getPasswordEntries() {
        return this.passwordEntries;
    }

    public static ObservablePasswordEntryList getInstance(){
        return instance;
    }
}

package sample.Domain;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import org.apache.commons.lang3.SerializationUtils;

public class ObservablePasswordEntryList {
    private final ObservableList<Node> passwordEntries;
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

    public void changeEntry(EntryHBox eHBox) {
        this.passwordEntries.remove(eHBox);
        this.addEntry(eHBox);
    }

    public String addEntry(EntryHBox eHBox){
        for (int i = 0; i < this.passwordEntries.size(); i++) {
            if (((EntryHBox) this.passwordEntries.get(i)).getUrl().equals(eHBox.getUrl())) {
                System.out.println("Entry changed!");
                this.passwordEntries.set(i, eHBox);
            } else {
                this.passwordEntries.add(eHBox);
                return "Entry added!";
            }
        }
        return "";
    }

    public void deleteEntry(EntryHBox eHBox){
        this.passwordEntries.remove(eHBox);
    }

    public ObservableList<Node> getPasswordEntries() {
        return this.passwordEntries;
    }

    public int getSize() {
        return this.passwordEntries.size();
    }

    public static ObservablePasswordEntryList getInstance(){
        return instance;
    }
}

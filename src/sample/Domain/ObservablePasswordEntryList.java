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

    public String addEntry(EntryHBox eHBox){
        if (doesExist(eHBox)){
            return "The Site name or Url already exists";
        }else if (!doesExist(eHBox)){
            this.passwordEntries.add(eHBox);
            this.passwordEntries.forEach(System.out::println);
            System.out.println();
            return "Entry added!";
        }
        return "";
    }

    public void deleteEntry(EntryHBox eHBox){
        this.passwordEntries.remove(eHBox);
    }

    public boolean doesExist(EntryHBox eHBox) {
        for (Node ehb: this.passwordEntries) {
            return ((EntryHBox) ehb).getSite().equals(eHBox.getSite()) || ((EntryHBox) ehb).getUrl().equals(eHBox.getUrl());
        }
        return false;
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

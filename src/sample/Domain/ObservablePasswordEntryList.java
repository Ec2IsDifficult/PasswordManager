package sample.Domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Map;

public class ObservablePasswordEntryList {
    private final ObservableList<EntryHBox> passwordEntries;

    public ObservablePasswordEntryList(){
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
        for (EntryHBox ehb : this.passwordEntries) {
            passwordTable.add(new PasswordEntry(ehb.getSite(), ehb.getUrl(), ehb.getUsername(), ehb.getPassword()));
        }
        return SerializationUtils.serialize(passwordTable);
    }

    public String addEntry(EntryHBox eHBox){
        if (doesExist(eHBox)){
            return "The Site name or Url already exists";
        }else{
            this.passwordEntries.add(eHBox);
            return "Entry added!";
        }
    }

    public void deleteEntry(EntryHBox eHBox){
        this.passwordEntries.remove(eHBox);
    }

    public boolean doesExist(EntryHBox eHBox) {
        for (EntryHBox e: this.passwordEntries) {
            return e.getSite().equals(eHBox.getSite()) || e.getUrl().equals(eHBox.getUrl());
        }
        return false;
    }

    public ObservableList<EntryHBox> getPasswordEntries() {
        return this.passwordEntries;
    }

    public int getSize() {
        return this.passwordEntries.size();
    }







/*    public void setPasswordEntries(PasswordTable passwordTable) {
        HBox hbox = new HBox();
        for (PasswordEntry e : passwordTable) {
            hbox.getChildren().add(new Label(e.getSite()));
            hbox.getChildren().add(new Label(e.getUrl()));
            hbox.getChildren().add(new Label(e.getUsername()));
            hbox.getChildren().add(new Label(e.getPassword()));
            this.passwordEntries.add(createPair(e, hbox));
        }
    }

    public boolean addEntry(PasswordEntry passwordEntry){

        for (Pair<String, Node> pair: this.passwordEntries) {
            if(pair.getKey().equals(passwordEntry.getSite())){
                return false;
            }else{
                HBox hbox = new HBox();
                hbox.getChildren().add(new Label(passwordEntry.getSite()));
                hbox.getChildren().add(new Label(passwordEntry.getUrl()));
                hbox.getChildren().add(new Label(passwordEntry.getUsername()));
                hbox.getChildren().add(new Label(passwordEntry.getPassword()));
                this.passwordEntries.add(createPair(passwordEntry, hbox));
                return true;
            }
        }
        return false;
    }

    public void deleteEntry(PasswordEntry passwordEntry) {
        this.passwordEntries.removeIf(e -> e.getKey().equals(passwordEntry.getSite()));
    }

    public PasswordTable getPasswordTable(){
        PasswordTable passwordTable = new PasswordTable();
        for (Pair<String, Node> n : this.passwordEntries) {
            PasswordEntry passwordEntry = new PasswordEntry(
                    ((Label)((HBox) n.getValue()).getChildren().get(0)).getText(),
                    ((Label)((HBox) n.getValue()).getChildren().get(0)).getText(),
                    ((Label)((HBox) n.getValue()).getChildren().get(0)).getText(),
                    ((Label)((HBox) n.getValue()).getChildren().get(0)).getText()
            );
            passwordTable.add(passwordEntry);
            }
        return passwordTable;
    }

    public Pair<String, Node> createPair(PasswordEntry passwordEntry, HBox HBoxPasswordEntry){
        return new Pair<>(passwordEntry.getSite(), HBoxPasswordEntry);
    }*/
}

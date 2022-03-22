package sample.Domain;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class ObservablePasswordEntryList {
    private final ObservableList<Pair<String, Node>> passwordEntries;

    public ObservablePasswordEntryList(){
        this.passwordEntries = FXCollections.observableArrayList();
    }
    public ObservableList<Pair<String, Node>> getPasswordEntries() {
        return this.passwordEntries;
    }

    public void setPasswordEntries(PasswordTable passwordTable) {
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
    }
}

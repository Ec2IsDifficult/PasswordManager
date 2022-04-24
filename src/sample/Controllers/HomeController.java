package sample.Controllers;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sample.Domain.*;
import sample.Model.EncryptDecryptPasswordTable;
import sample.Model.MasterPasswordMasterKey;
import java.security.Security;

public class HomeController{

    Stage stage;

    // Class for logic for encrypting and decrypting the password table
    EncryptDecryptPasswordTable encDecPasswordTable;

    // The actual password table rapped in an observable list
    ObservablePasswordEntryList observablePasswordEntryList;
    MasterPasswordMasterKey masterPasswordMasterKey;

    // Vertical box for all the entries in the password table
    @FXML
    VBox entryVBox;

    public HomeController() throws Exception{

        // Adding the provider and getting the instance of the observable list
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
        this.encDecPasswordTable = new EncryptDecryptPasswordTable();
    }

    // This functions sets the stage after construction, and it displays all password table entries
    public void start(Stage stage){
        this.stage = stage;
        this.entryVBox = (VBox) this.stage.getScene().lookup("#entryVBox");
        displayAllEntries();
    }

    // Logic for binding the vbox with the singleton observable password table list
    public void displayAllEntries(){
        Bindings.bindContent(this.entryVBox.getChildren(), this.observablePasswordEntryList.getPasswordEntries());
        this.checkIfEmpty();
    }

    // Display string if the password table is empty
    public void checkIfEmpty() {
        if (this.entryVBox.getChildren().size() == 0) {
            this.entryVBox.getChildren().add(new Label("No password entries exist yet..."));
        }
    }

    // Encrypt and write the password table for the save all button
    @FXML
    public void saveAllAction() throws Exception{
        this.encDecPasswordTable.encryptPasswordTable();
    }

    // Go to another view with another controller. Specifically the view where new entries are made
    @FXML
    private void goToCreateNewEntry(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/NewEntry.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        NewEntryController newEntryController = loader.getController();
        newEntryController.start(stage.getScene());
        newEntryController.setObservablePasswordEntryList();
    }

    @FXML
    private void changeMasterPasswordAction(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/ChangeMasterPasswordView.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        ChangeMasterPasswordController changeMasterPasswordController = loader.getController();
        changeMasterPasswordController.start(stage.getScene());
    }
}

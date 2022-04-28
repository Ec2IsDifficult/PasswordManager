package sample;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.bouncycastle.util.encoders.Hex;
import sample.Controllers.LoginController;
import sample.Domain.EntryHBox;
import sample.Domain.ObservablePasswordEntryList;
import sample.Model.EncryptDecryptPasswordTable;
import sample.Model.MasterPasswordMasterKey;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/LoginView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));

        // Prepare the first controller and give the primary stage to it
        LoginController loginController = loader.getController();
        loginController.start(primaryStage);
        primaryStage.show();
    }

    @Override
    public void stop(){
        // Ensuring encryption of the password table when the program is closed
        try{
            EncryptDecryptPasswordTable encryptDecryptPasswordTable = new EncryptDecryptPasswordTable();
            encryptDecryptPasswordTable.encryptPasswordTable();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void testProgram() {
        // Create a masterkey based on the password 'HelloPassword'
        MasterPasswordMasterKey masterPasswordMasterKey = MasterPasswordMasterKey.getInstance();
        masterPasswordMasterKey.createMasterKey("HelloPassword");
        System.out.println( Hex.toHexString(masterPasswordMasterKey.getMasterKey().getEncoded()) + "<-- First making the masterkey");

        // Create an instance of a password tabel with one entry
        ObservablePasswordEntryList observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
        EntryHBox entryHBox = new EntryHBox(
                "Test",
                "Test",
                "Test",
                "Test");
        observablePasswordEntryList.addEntry(entryHBox);

        try {
            // Encrypt the password table
            EncryptDecryptPasswordTable encryptDecryptPasswordTable = new EncryptDecryptPasswordTable();
            encryptDecryptPasswordTable.encryptPasswordTable();
            // Give the program time to write the file
            Thread.sleep(3000);

            // Create the same masterkey
            masterPasswordMasterKey.createMasterKey("HelloPassword");
            System.out.println(Hex.toHexString(masterPasswordMasterKey.getMasterKey().getEncoded()) + "<-- Same masterkey as before");

            // Decrypt the table
            encryptDecryptPasswordTable.decryptPasswordTable();
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        //Test
        //testProgram();

        //Actual gui program
        launch(args);
    }
}

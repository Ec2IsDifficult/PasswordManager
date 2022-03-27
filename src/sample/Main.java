package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controllers.LoginController;
import sample.Model.EncryptDecryptPasswordTable;
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/LoginView.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        LoginController loginController = loader.getController();
        loginController.start(primaryStage);
        primaryStage.show();
    }

    @Override
    public void stop(){
        try{
            EncryptDecryptPasswordTable encryptDecryptPasswordTable = new EncryptDecryptPasswordTable();
            encryptDecryptPasswordTable.encryptPasswordTable();

        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

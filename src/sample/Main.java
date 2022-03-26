package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.Controllers.HomeController;
import sample.Domain.ObservablePasswordEntryList;

public class Main extends Application {
    //HomeController homeController = new HomeController(primaryStage);
    //loader.setController(homeController);

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Views/Home.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
        HomeController homeController = loader.getController();
        homeController.loadPasswordTableFromMemory();
        homeController.start(primaryStage);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

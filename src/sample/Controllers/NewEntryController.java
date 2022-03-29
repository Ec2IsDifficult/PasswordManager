package sample.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.Domain.EntryHBox;
import sample.Domain.ObservablePasswordEntryList;
import sample.Model.SuggestStrongPassword;

public class NewEntryController {

    Scene scene;
    ObservablePasswordEntryList observablePasswordEntryList;
    SuggestStrongPassword suggestStrongPassword;

    @FXML
    private TextField siteField;
    @FXML
    private TextField urlField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatedPasswordField;
    @FXML
    private Label equalPasswordFeedback;

    public NewEntryController(){

    }

    public void start(Scene scene) {
        this.scene = scene;
        this.siteField = (TextField) this.scene.lookup("#site");
        this.urlField = (TextField) this.scene.lookup("#url");
        this.usernameField = (TextField) this.scene.lookup("#username");
        this.passwordField = (TextField) this.scene.lookup("#password");
        this.repeatedPasswordField = (TextField) this.scene.lookup("#passwordRepeat");
        this.equalPasswordFeedback = (Label) this.scene.lookup("#equalPasswordFeedback");
        this.suggestStrongPassword = new SuggestStrongPassword();
    }

    public void setToBeChangedEntry(String site, String url, String username, String password){
        this.siteField.setText(site);
        this.urlField.setText(url);
        this.usernameField.setText(username);
        this.passwordField.setText(password);
    }

    public void setObservablePasswordEntryList(){
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
    }

    @FXML
    public void saveEntryAction(){

        if(!this.siteField.getText().equals("")
                && !this.urlField.getText().equals("")
                && !this.usernameField.getText().equals("")
                && !this.passwordField.getText().equals("")) {

            if(this.passwordField.getText().equals(this.repeatedPasswordField.getText())){
                EntryHBox entryHBox = new EntryHBox(
                        this.siteField.getText(),
                        this.urlField.getText(),
                        this.usernameField.getText(),
                        this.passwordField.getText()
                );
                this.observablePasswordEntryList.addEntry(entryHBox);
                this.equalPasswordFeedback.setText("Entry added!");
            }else{
                this.equalPasswordFeedback.setText("Passwords must match!");
            }
        }else{
            this.equalPasswordFeedback.setText("Missing information!");
        }
    }

    @FXML
    private void backToOverviewAction(ActionEvent event) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/Home.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        HomeController homeController = loader.getController();
        homeController.start(stage);
    }

    @FXML
    private void suggestStrongPasswordAction() {
        String suggestedPassword = this.suggestStrongPassword.generatePassword();
        this.repeatedPasswordField.setText(suggestedPassword);
        this.passwordField.setText(suggestedPassword);
    }

}

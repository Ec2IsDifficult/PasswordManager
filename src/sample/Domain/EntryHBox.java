package sample.Domain;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import sample.Controllers.NewEntryController;

public class EntryHBox extends HBox {

    private final Label siteLabel = new Label();
    private final Label urlLabel = new Label();
    private final Label usernameLabel = new Label();
    private final Label passwordLabel = new Label();

    // Each hbox entry needs access to logic from the singleton password table observable list
    private final ObservablePasswordEntryList observablePasswordEntryList;

    // This hbox is for displaying the entries in the home view
    public EntryHBox(String site, String url, String username, String password) {
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();

        // Setting up the labels
        this.siteLabel.setText(site);
        this.urlLabel.setText(url);
        this.usernameLabel.setText(username);
        this.passwordLabel.setText(password);

        // Setup the internal grid
        GridPane internalGrid = new GridPane();
        internalGrid.add(this.siteLabel, 0,0);
        internalGrid.add(this.urlLabel, 1,0);
        internalGrid.add(this.usernameLabel, 2,0);
        internalGrid.add(this.passwordLabel, 3,0);

        // Button for going to change the specific entry
        Button change = new Button("Change");
        change.setOnAction(e -> {
            try {
                // This button loads an entire new entry view
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/sample/Views/NewEntry.fxml"));
                Parent root = loader.load();
                Stage stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
                stage.setScene(new Scene(root));
                NewEntryController newEntryController = loader.getController();
                newEntryController.start(stage.getScene());
                newEntryController.setObservablePasswordEntryList();
                newEntryController.setToBeChangedEntry(this.getSite(), this.getUrl(), this.getUsername(), this.getPassword());
            }catch(Exception exc) {
                exc.printStackTrace();
            }

        });
        internalGrid.add(change, 4,0);

        // Button for deleting the selected entry
        Button delete = new Button("Delete");
        delete.setOnAction(e -> this.observablePasswordEntryList.deleteEntry(this));
        internalGrid.add(delete, 5,0);

        internalGrid.setPadding(new Insets(0, 0, 0, 10));
        this.setMinSize(800, 30);
        internalGrid.setMinWidth(800.0);

        // Logic for spreading out the labels inside the hbox
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(158);
        ColumnConstraints column3 = new ColumnConstraints(170);
        ColumnConstraints column4 = new ColumnConstraints(100);
        ColumnConstraints column5 = new ColumnConstraints(100);
        ColumnConstraints column6 = new ColumnConstraints(100);
        column1.setHgrow(Priority.ALWAYS);
        column2.setHgrow(Priority.ALWAYS);
        column3.setHgrow(Priority.ALWAYS);
        column4.setHgrow(Priority.ALWAYS);
        column5.setHgrow(Priority.ALWAYS);
        column6.setHgrow(Priority.ALWAYS);
        internalGrid.getColumnConstraints().addAll(column1, column2, column3, column4, column5, column6); // first

        this.getChildren().add(internalGrid);
        this.setHeight(26.3);
        internalGrid.setGridLinesVisible(true);
    }

    public void setSite(String site) {
        this.siteLabel.setText(site);
    }
    public void setUrl(String url) {
        this.urlLabel.setText(url);
    }
    public void setUserName(String username) {
        this.usernameLabel.setText(username);
    }
    public void setPassword(String password) {
        this.passwordLabel.setText(password);
    }

    public String getSite() {
        return this.siteLabel.getText();
    }
    public String getUrl() {
        return this.urlLabel.getText();
    }
    public String getUsername() {
        return this.usernameLabel.getText();
    }
    public String getPassword() {
        return this.passwordLabel.getText();
    }
}

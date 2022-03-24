package sample.Domain;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Map;

public class EntryHBox extends HBox {

    Label siteLabel = new Label();
    Label urlLabel = new Label();
    Label usernameLabel = new Label();
    Label passwordLabel = new Label();



    public EntryHBox(String site, String url, String username, String password) {
        this.siteLabel.setText(site);
        this.urlLabel.setText(url);
        this.usernameLabel.setText(username);
        this.passwordLabel.setText(password);
        this.getChildren().addAll(
                this.siteLabel,
                this.urlLabel,
                this.usernameLabel,
                this.passwordLabel
        );

        this.setSpacing(30);

        for (Node sl: this.getChildren()) {
            System.out.println(((Label) sl).getText());
        }
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

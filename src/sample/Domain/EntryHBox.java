package sample.Domain;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.io.Serializable;
import java.util.Map;

public class EntryHBox extends HBox implements Serializable {

    SLabel siteLabel = new SLabel();
    SLabel urlLabel = new SLabel();
    SLabel usernameLabel = new SLabel();
    SLabel passwordLabel = new SLabel();

    public EntryHBox(String site, String url, String username, String password) {
        this.siteLabel.setText(site);
        this.urlLabel.setText(url);
        this.usernameLabel.setText(username);
        this.passwordLabel.setText(password);
        this.getChildren().addAll(this.siteLabel, this.urlLabel, this.usernameLabel, this.passwordLabel);
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

}

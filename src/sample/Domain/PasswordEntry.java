package sample.Domain;
import java.io.Serializable;

// Class for storing each entry and its information
public class PasswordEntry implements Serializable {
    private String site;
    private String url;
    private String username;
    private String password;

    public PasswordEntry(String site, String url, String username, String password){
        this.site = site;
        this.url = url;
        this.username = username;
        this. password = password;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

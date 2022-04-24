package sample.Domain;
import java.io.Serializable;

// Class for storing each entry and its information
public class PasswordEntry implements Serializable {
    public String site;
    public String url;
    public String username;
    public String password;

    public PasswordEntry(String site, String url, String username, String password){
        this.site = site;
        this.url = url;
        this.username = username;
        this. password = password;
    }
}

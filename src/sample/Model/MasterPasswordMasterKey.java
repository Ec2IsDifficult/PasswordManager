package sample.Model;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;

public class MasterPasswordMasterKey {
    private SecretKey masterKey = null;

    public String getMasterPassword() {
        return masterPassword;
    }

    public void setMasterPassword(String masterPassword) {
        this.masterPassword = masterPassword;
    }

    private String masterPassword = null;
    private byte[] salt;

    // Only one instance of this class because it stores information
    private static MasterPasswordMasterKey  instance;

    // Singleton
    static {
        try {
            instance = new MasterPasswordMasterKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private EncryptDecryptPasswordTable encryptDecryptPasswordTable;
    public static MasterPasswordMasterKey getInstance() {
        return instance;
    }


    private MasterPasswordMasterKey() throws Exception{
        this.encryptDecryptPasswordTable = new EncryptDecryptPasswordTable();
    }

    public void createMasterKey(String masterPassword){
        // Getting the master password from the login controller
        this.masterPassword = masterPassword;
        try {
            // If no salt exists generate a new one
            if (this.encryptDecryptPasswordTable.getSalt().length == 0) {
                generateSalt();
                // Else, retrieve the existing salt
            }else{
                this.salt = encryptDecryptPasswordTable.getSalt();
            }
            // Generate master key with 1.000.000 hashing iterations and a salt
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
            this.masterKey = factory.generateSecret(new PBEKeySpec(masterPassword.toCharArray(), salt, 1000000, 256));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Generate random salt
    public void generateSalt() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        this.salt = new byte[32];
        secureRandom.nextBytes(this.salt);
    }

    // Generate new salt and a new key based on it
    public void remakeSalt() throws Exception{
        generateSalt();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
        this.masterKey = factory.generateSecret(new PBEKeySpec(masterPassword.toCharArray(), salt, 1000000, 256));
    }


    public SecretKey getMasterKey(){
        return this.masterKey;
    }
    public byte[] getSalt() {return this.salt;}
}

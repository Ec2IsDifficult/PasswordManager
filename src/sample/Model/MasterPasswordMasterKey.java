package sample.Model;

import org.bouncycastle.util.encoders.Hex;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

public class MasterPasswordMasterKey {
    //TODO: Password should be loaded from a password store rather than hardcoded

    private SecretKey masterKey = null;

    //TODO: Load the password for creating master-key. Should fill the masterPassword variable.
    public boolean checkForExistingPassword(){
        /*
        * AT APPLICATION START PASSWORD-BARRIER VIEW RUNS:
        *
        * while(!checkPassword()){
        *   The controller should ask and run the createMasterPassword which in turn runs the storePassword.
        *}
        *
        * The controller should run the askForCorrectPasswordFunction -> loads, compares with user input
        * The controller should run the createMasterKey function.
        * The controller should run the decrypt function.
        */
        return false;
    }

    //TODO: This should ideally be done inside the password-database
    public boolean comparePassword() {
        return false;
    }

    //TODO: Load password from database
    public String loadPassword(){
        return null;
    }

    public void createMasterPassword(String password){
        //TODO: Create password for creating master-key. Should fill the masterPassword variable.
    }

    //TODO: This should create the master-key based on the master-password
    public void createMasterKey(String masterPassword){
        try {
            // TODO: Get random salt
            byte[] salt = {0, 1, 2, 3, 4, 5, 6, 7}; // 32 bytes = 256 bits
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WITHHMACSHA256", "BC");
            this.masterKey = factory.generateSecret(new PBEKeySpec(masterPassword.toCharArray(), salt, 1000000, 256));
            System.out.println(Hex.toHexString(this.masterKey.getEncoded()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO: End of life store the password
    public void StorePassword(){

    }

    public SecretKey getMasterKey(){
        return this.masterKey;
    }
}

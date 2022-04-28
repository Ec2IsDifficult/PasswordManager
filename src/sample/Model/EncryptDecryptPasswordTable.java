package sample.Model;

import org.bouncycastle.util.encoders.Hex;
import sample.Domain.ObservablePasswordEntryList;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Arrays;


// Contains all encryption logic
public class EncryptDecryptPasswordTable {

    private final Cipher cipher;

    // Get system specific path
    private final Path currentDirectoryPath = Paths.get("").toAbsolutePath();
    private final File file = new File(currentDirectoryPath + "\\src\\PasswordTableFile.txt");

    // Getting the instance of the master key. This must be a singleton because it contains the master-password
    // for creating the master key based on a new salt (the program changes salt with each encryption)
    private final MasterPasswordMasterKey masterPasswordMasterKey = MasterPasswordMasterKey.getInstance();

    // Getting the single instance of the observable list
    private final ObservablePasswordEntryList observablePasswordEntryList;

    private final byte[] passwordTableFile;

    // Adding the provider, creating the cipher class instance,
    // getting the observable password table instance, and reading the specific file
    public EncryptDecryptPasswordTable() throws Exception {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        this.cipher = Cipher.getInstance("AES/CBC/PKCS5Padding", "BC");
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
        this.passwordTableFile = FileUtil.readAllBytes(this.file.toPath());
    }

    public void encryptPasswordTable() throws Exception {
        // Serializes for encrpytion
        byte[] serializedPasswordTable = this.observablePasswordEntryList.serializePasswordTable();
        System.out.println(Hex.toHexString(serializedPasswordTable) + "<-- Just before encryption password table");

        // Generating random iv
        IvParameterSpec iv = this.generateIv();
        // remakes the salt (new salt each time)
        this.masterPasswordMasterKey.remakeSalt();
        // Encryption
        this.cipher.init(Cipher.ENCRYPT_MODE, this.masterPasswordMasterKey.getMasterKey(), iv);
        byte[] encryptedBinaryPasswordTable = this.cipher.doFinal(serializedPasswordTable);
        // Writing the iv and the salt at the beginning of the encrypted table for retrieval when decypting
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(iv.getIV());
        stream.write(masterPasswordMasterKey.getSalt());
        stream.write(encryptedBinaryPasswordTable);
        FileUtil.write(this.file.toPath(), stream.toByteArray());
    }

    public void decryptPasswordTable() throws Exception {
        // Checking the password table file length
        if (this.passwordTableFile.length != 0) {
            // Retrieving the iv at the beginning of the file
            IvParameterSpec iv = this.getIv(this.passwordTableFile);
            // Getting the file itself
            byte[] ENCRYPTEDBinaryPasswordTable = this.getPasswordTable(this.passwordTableFile);
            // Decrypting
            this.cipher.init(Cipher.DECRYPT_MODE, this.masterPasswordMasterKey.getMasterKey(), iv);
            byte[] decryptedPasswordTable = this.cipher.doFinal(ENCRYPTEDBinaryPasswordTable);
            System.out.println(Hex.toHexString(decryptedPasswordTable) + "<-- Same byte string as just before encryption (This is after encryption)");

            // Deserializing
            this.observablePasswordEntryList.deserializeAllExistingEntries(decryptedPasswordTable);
        }
    }

    // Generating random iv bytes
    private IvParameterSpec generateIv() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    // Retrieving the iv at the beginning of the stored password table file
    private IvParameterSpec getIv(byte[] binary) {
        return new IvParameterSpec(Arrays.copyOfRange(binary, 0, 16));
    }

    // Retrieving the salt at the middle of the stored password table file
    public byte[] getSalt(){
        if (this.passwordTableFile.length == 0){
            return new byte[]{};
        }else{
            return Arrays.copyOfRange(this.passwordTableFile, 16, 48);
        }
    }

    // Getting the actual password table file
    private byte[] getPasswordTable(byte[] binary) {
        return Arrays.copyOfRange(binary, 48, binary.length);
    }
}

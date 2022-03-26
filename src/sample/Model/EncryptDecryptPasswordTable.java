package sample.Model;

import org.apache.commons.lang3.SerializationUtils;
import org.bouncycastle.util.encoders.Hex;
import sample.Domain.ObservablePasswordEntryList;
import sample.Domain.PasswordTable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.apache.commons.lang3.SerializationUtils.deserialize;

public class EncryptDecryptPasswordTable {

    public final Cipher cipher;
    public byte[] password = {0,1,2,3,4,5,6,7,8,9};
    File file = new File("C:\\Users\\rasse\\IdeaProjects\\PasswordManager\\src\\PasswordTableFile.txt");
    MasterPasswordMasterKey masterPasswordMasterKey = new MasterPasswordMasterKey();
    ObservablePasswordEntryList observablePasswordEntryList;


    public EncryptDecryptPasswordTable() throws Exception {
        this.cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        this.masterPasswordMasterKey.createMasterKey("HelloWorld");
        this.observablePasswordEntryList = ObservablePasswordEntryList.getInstance();
    }

    public void encryptPasswordTable() throws Exception {
        byte[] serializedPasswordTable = this.observablePasswordEntryList.serializeObservablePasswordEntryList();
        IvParameterSpec iv = this.generateIv();
        this.cipher.init(Cipher.ENCRYPT_MODE, this.masterPasswordMasterKey.getMasterKey(), iv);
        byte[] encryptedBinaryPasswordTable = this.cipher.doFinal(serializedPasswordTable);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(iv.getIV());
        stream.write(encryptedBinaryPasswordTable);
        FileUtil.write(this.file.toPath(), stream.toByteArray());
    }

    public void decryptPasswordTable() throws Exception {
        byte[] passwordTableFile = FileUtil.readAllBytes(this.file.toPath());
        if (passwordTableFile.length != 0) {
            IvParameterSpec iv = this.getIv(passwordTableFile);
            byte[] ENCRYPTEDBinaryPasswordTable = this.getPasswordTable(passwordTableFile);
            this.cipher.init(Cipher.DECRYPT_MODE, this.masterPasswordMasterKey.getMasterKey(), iv);
            byte[] decryptedPasswordTable = this.cipher.doFinal(ENCRYPTEDBinaryPasswordTable);
            this.observablePasswordEntryList.deserializeAllExistingEntries(decryptedPasswordTable);
        }
    }

    public IvParameterSpec generateIv() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public IvParameterSpec getIv(byte[] binary) {
        return new IvParameterSpec(Arrays.copyOfRange(binary, 0, 16));
    }

    public byte[] getPasswordTable(byte[] binary) {
        return Arrays.copyOfRange(binary, 16, binary.length);
    }
}

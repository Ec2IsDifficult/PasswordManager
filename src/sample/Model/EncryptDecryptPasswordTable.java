package sample.Model;

import org.apache.commons.lang3.SerializationUtils;
import org.bouncycastle.util.encoders.Hex;
import sample.Domain.PasswordTable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.ByteArrayOutputStream;
import java.security.SecureRandom;
import java.util.Arrays;

import static org.apache.commons.lang3.SerializationUtils.deserialize;

public class EncryptDecryptPasswordTable {

    public final Cipher cipher;
    public byte[] password = {0,1,2,3,4,5,6,7,8,9};

    public EncryptDecryptPasswordTable() throws Exception {
        this.cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
    }

    public byte[] encryptPasswordTable(byte[] serializedPasswordTable, SecretKey masterKey) throws Exception {
        IvParameterSpec iv = this.generateIv();
        this.cipher.init(Cipher.ENCRYPT_MODE, masterKey, iv);
        byte[] encryptedBinaryPasswordTable = this.cipher.doFinal(serializedPasswordTable);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write(iv.getIV());
        stream.write(encryptedBinaryPasswordTable);
        return stream.toByteArray();
    }

    public byte[] decryptPasswordTable(byte[] encryptedIvAndPasswordTableBinary, SecretKey masterKey) throws Exception {
        IvParameterSpec iv = getIv(encryptedIvAndPasswordTableBinary);
        byte[] ENCRYPTEDBinaryPasswordTable = getPasswordTable(encryptedIvAndPasswordTableBinary);
        this.cipher.init(Cipher.DECRYPT_MODE, masterKey, iv);
        return this.cipher.doFinal(ENCRYPTEDBinaryPasswordTable);
    }

    public IvParameterSpec generateIv() throws Exception {
        SecureRandom secureRandom = SecureRandom.getInstance("DEFAULT", "BC");
        byte[] iv = new byte[16];
        secureRandom.nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    //TODO: These two will only be part of decrypting the password table
    public IvParameterSpec getIv(byte[] binary) {
        return new IvParameterSpec(Arrays.copyOfRange(binary, 0, 16));
    }

    public byte[] getPasswordTable(byte[] binary) {
        return Arrays.copyOfRange(binary, 16, binary.length);
    }
}

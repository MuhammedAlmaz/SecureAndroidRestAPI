package com.example.mhmdalmz.SecureTheApi.Security;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public class DataCrypter implements IDataCrypter {

    private final Cipher _Chiper;
    private final SecretKeySpec _SecretKeySpec;
    private AlgorithmParameterSpec _Spec;
    private Keys CrypterKeys;
    public DataCrypter(Keys CrypterKeys) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, CrypterExceptions {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        if(CrypterKeys.GetSecretKey().equals(null))
        {
            throw new CrypterExceptions("The Secret Key is null.");

        }else if(CrypterKeys.GetSecretKey().length()!=16){
            throw new CrypterExceptions("The Secret Key character size need to be 16.");
        }
        digest.update(CrypterKeys.GetSecretKey().getBytes("UTF-8"));
        byte[] keyBytes = new byte[32];
        System.arraycopy(digest.digest(), 0, keyBytes, 0, keyBytes.length);

        _Chiper = Cipher.getInstance("AES/CBC/PKCS7Padding");
        _SecretKeySpec = new SecretKeySpec(keyBytes, "AES");
        this.CrypterKeys=CrypterKeys;
        _Spec = getIV();
    }




    @Override
    public AlgorithmParameterSpec getIV() {
        byte[] iv =this.CrypterKeys.GetIVKey();
        IvParameterSpec ivParameterSpec;
        ivParameterSpec = new IvParameterSpec(iv);

        return ivParameterSpec;
    }

    @Override
    public String EncryptData(String CryptingData) throws InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException {
        _Chiper.init(Cipher.ENCRYPT_MODE, _SecretKeySpec, _Spec);
        byte[] encrypted = _Chiper.doFinal(CryptingData.getBytes("UTF-8"));
        String encryptedText = new String(Base64.encode(encrypted,
                Base64.DEFAULT), "UTF-8");

        return encryptedText;
    }

    @Override
    public String DecryptData(String CryptedData) throws InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException, InvalidAlgorithmParameterException {
        _Chiper.init(Cipher.DECRYPT_MODE, _SecretKeySpec, _Spec);
        byte[] Bytes = Base64.decode(CryptedData, Base64.DEFAULT);
        byte[] DeCryptedBytes = _Chiper.doFinal(Bytes);
        String DeCryptedData = new String(DeCryptedBytes, "UTF-8");

        return DeCryptedData;
    }
}

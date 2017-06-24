package com.example.mhmdalmz.SecureTheApi.Security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public class Security {

     static Keys _Keys ;
     static int _StanCounter;
    public Security(String IV,String SecretKey,int StanCounter){
        _Keys=new Keys(IV,SecretKey);
    }
    public Security(byte[] IV,String SecretKey,int StanCounter){
        _Keys=new Keys(IV,SecretKey);
        _StanCounter=StanCounter;
    }

    public String EnCryptData(String Data) throws CrypterExceptions, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return new DataCrypter(_Keys).EncryptData(Data);
    }
    public String DeCryptData(String Data) throws CrypterExceptions, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        return new DataCrypter(_Keys).DecryptData(Data);
    }







}

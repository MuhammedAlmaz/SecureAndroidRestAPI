package com.example.mhmdalmz.SecureTheApi.Security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public interface IDataCrypter {
    AlgorithmParameterSpec getIV();
    String EncryptData(String CryptingData) throws InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;
    String DecryptData(String CryptedData)  throws InvalidAlgorithmParameterException, InvalidKeyException, UnsupportedEncodingException, BadPaddingException, IllegalBlockSizeException;


}

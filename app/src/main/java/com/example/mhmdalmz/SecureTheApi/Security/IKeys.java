package com.example.mhmdalmz.SecureTheApi.Security;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public interface IKeys {

     void SetSecretKey(String Key);
     String GetSecretKey();
     void SetIVKey(byte[] IV);
     byte[] GetIVKey();
     void SetIVKeyFromString(String IV);
}

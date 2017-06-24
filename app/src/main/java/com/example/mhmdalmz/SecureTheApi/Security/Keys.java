package com.example.mhmdalmz.SecureTheApi.Security;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public class Keys implements IKeys {

    public  String SecretKey;
    public  byte[] IVKey;

    public Keys(byte[] IV,String SecretKey)
    {
        SetSecretKey(SecretKey);
        SetIVKey(IV);
    }
    public Keys(String IV,String SecretKey)
    {
        SetSecretKey(SecretKey);
        SetIVKeyFromString(IV);

    }
    @Override
    public void SetSecretKey(String Key) {
        this.SecretKey=Key;
    }

    @Override
    public String GetSecretKey() {
        return this.SecretKey;
    }

    @Override
    public void SetIVKey(byte[] IV) {
        this.IVKey=IV;
    }

    @Override
    public byte[] GetIVKey() {
        return this.IVKey;
    }

    @Override
    public void SetIVKeyFromString(String IV) {
        //This String  splited the ',' if you want you can change your spliting type.
        //In my code , the IV String like that . 255,55,2,21,33,44
        String[] StringArrayIV=IV.split(",");
        byte[] IVKey=new byte[StringArrayIV.length];
        for(int i=0;i<StringArrayIV.length;i++)
        {
            IVKey[i]=(byte)(Integer.parseInt(StringArrayIV[i].toString()));
        }

        SetIVKey(IVKey);
    }
}

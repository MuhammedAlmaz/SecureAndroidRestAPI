package com.example.mhmdalmz.SecureTheApi.Security;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public class Options {
    static String _URL;
    static String _SecretKey;

    public static String get_SecretKey() {
        return _SecretKey;
    }

    public static void set_SecretKey(String _SecretKey) {
        Options._SecretKey = _SecretKey;
    }

    public static String get_IVKey() {
        return _IVKey;
    }

    public static void set_IVKey(String _IVKey) {
        Options._IVKey = _IVKey;
    }

    static String _IVKey;

    public static int get_PostCounter() {
        return _PostCounter;
    }

    public static void set_PostCounter(int _PostCounter) {
        Options._PostCounter = _PostCounter;
    }

    static int _PostCounter;

    public static String getUrl() {
        return _URL;
    }

    public static void setUrl(String Url) {
        _URL = Url;
    }
}

package com.example.mhmdalmz.SecureTheApi.Security;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 * Created by MhmdAlmz on 23.06.2017.
 */

public class SecurePostRequest extends AsyncTask<String , String,String> {

    private Context context;
    private ProgressDialog progress;
    private String ProgressMessage,ApiName;
    private String SendData;



    public SecurePostRequest(Context context, String ProgressMessage, ArrayList nameValuePairs, String ApiName, Keys Keys) throws CrypterExceptions, NoSuchAlgorithmException, NoSuchPaddingException, UnsupportedEncodingException, IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
        this.context=context;
        this.ProgressMessage=ProgressMessage;
        JSONObject studentJSON = new JSONObject();
        for (int i=0;i<nameValuePairs.size();i++)
        {

            try {
                studentJSON.put(((BasicNameValuePair)nameValuePairs.get(i)).getName(),((BasicNameValuePair)nameValuePairs.get(i)).getValue());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        this.SendData=new DataCrypter(Keys).EncryptData(studentJSON.toString());
        this.ApiName=ApiName;
    }



    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = new ProgressDialog(context);
        progress.setMessage(ProgressMessage);
        progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progress.setIndeterminate(true);
        progress.setCancelable(false);
        progress.show();


    }

    @Override
    protected String doInBackground(String... params) {

        String Result="";
        InputStream isr=null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(Options._URL+ApiName+".php");
            Log.e("Sending Data",SendData);
            ArrayList SendArray=new ArrayList();
            SendArray.add(new BasicNameValuePair("Data",SendData));
            httpPost.setEntity(new UrlEncodedFormEntity(SendArray ));
            HttpResponse response=httpClient.execute(httpPost);
            HttpEntity entity=response.getEntity();
            isr=entity.getContent();

        } catch (Exception e) {
            return e.toString();
        }
        try
        {

            BufferedReader reader=new BufferedReader(new InputStreamReader(isr,"iso-8859-1"),8);
            StringBuilder sb=new StringBuilder();
            String line=null;
            while((line=reader.readLine())!=null)
            {
                sb.append(line+"\n");
            }
            isr.close();
            Result=sb.toString();
            return Result;

        }catch(Exception e) {
            return e.getMessage();
        }

    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        progress.cancel();
    }


}

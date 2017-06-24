package com.example.mhmdalmz.SecureTheApi;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import com.example.mhmdalmz.SecureTheApi.Security.CrypterExceptions;
import com.example.mhmdalmz.SecureTheApi.Security.DataCrypter;
import com.example.mhmdalmz.SecureTheApi.Security.Keys;
import com.example.mhmdalmz.SecureTheApi.Security.Options;
import com.example.mhmdalmz.SecureTheApi.Security.SecurePostRequest;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class MainActivity extends AppCompatActivity {

    Keys LoginKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //We Have a Default Login keys. You need Hide them inner String.xml.
        //It's safe than set the variable
        LoginKey=new Keys(
                getApplicationContext().getString(R.string.String_Login_IV),
                getApplicationContext().getString(R.string.String_Login_Key)
        );
        Options.setUrl("http://localhost:8080/deneme/");
        Options.set_PostCounter(10);

        ((Button)findViewById(R.id.BtnLogin))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList LoginArray=new ArrayList();
                        LoginArray.add(new BasicNameValuePair("UserName","MhmdAlmz"));
                        LoginArray.add(new BasicNameValuePair("Password","FatihSultanMehmed1453"));
                        try {
                            new SendLoginPostTheServer(MainActivity.this,"Loading...",LoginArray,"Login"
                                    ,LoginKey).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
        ((Button)findViewById(R.id.BtnInformation))
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ArrayList SendArray=new ArrayList();
                        SendArray.add(new BasicNameValuePair("Ask","HowAreYou"));
                        SendArray.add(new BasicNameValuePair("PostCounter",Options.get_PostCounter()+""));
                        try {
                            new SendRequestExample(MainActivity.this,"Loading...",SendArray,"Information"
                                    ,new Keys(Options.get_IVKey(),Options.get_SecretKey())).execute();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });




    }

    class SendRequestExample extends SecurePostRequest{
        public SendRequestExample(Context context, String ProgressMessage, ArrayList nameValuePairs, String ApiName,Keys Key) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException, CrypterExceptions {
            super(context, ProgressMessage, nameValuePairs, ApiName, Key);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s.toString());


            try{
                DataCrypter LoginCrypter=new DataCrypter(new Keys(Options.get_IVKey(),Options.get_SecretKey()));
                String ResultJSONString=LoginCrypter.DecryptData(s);
                Log.e("JsonResult",ResultJSONString);
                JSONObject ResultJSON=new JSONObject(ResultJSONString);
                if(ResultJSON.getString("Status").equals("Successful"))
                {
                    Toast.makeText(getApplicationContext(),ResultJSON.getString("Answer"),Toast.LENGTH_LONG).show();
                }else{
                    //Token breaked we need get a new token and PostCounter.
                }


            }catch(Exception e )
            {
                e.printStackTrace();
            }
        }
    }



    class SendLoginPostTheServer extends SecurePostRequest{
        public SendLoginPostTheServer(Context context, String ProgressMessage, ArrayList nameValuePairs, String ApiName,Keys Key) throws NoSuchPaddingException, BadPaddingException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidAlgorithmParameterException, CrypterExceptions {
            super(context, ProgressMessage, nameValuePairs, ApiName, Key);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.e("Result",s.toString());

            try {
                DataCrypter LoginCrypter=new DataCrypter(LoginKey);
                String ResultJSONString=LoginCrypter.DecryptData(s);
                Log.e("JsonResult",ResultJSONString);
                JSONObject ResultJSON=new JSONObject(ResultJSONString);
                if(ResultJSON.getString("Result").equals("Successful"))
                {
                    //Login Succesfull.
                    //If the Login is succesfull you need hide the key in the application

                    Options.set_SecretKey(ResultJSON.getString("SecretKey"));
                    Options.set_IVKey(ResultJSON.getString("IV"));
                    Options.set_PostCounter(Integer.parseInt(ResultJSON.getString("PostCounter")));
                }else{
                    //Login Unsuccesfull.
                    Toast.makeText(getApplicationContext(), "Please Check UserName or Password", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            try{

            }catch(Exception e )
            {
                e.printStackTrace();
            }
        }
    }

}
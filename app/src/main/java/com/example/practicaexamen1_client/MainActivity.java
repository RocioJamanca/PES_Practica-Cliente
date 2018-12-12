package com.example.practicaexamen1_client;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void funcio(View view)
    {
        new proba (this).execute("http://192.168.1.44:9000/Application/SubscripcionsPerSubscriptor");
    }

    private class proba extends  AsyncTask<String,Void,String> {
       Context context;
       InputStream stream=null;
       String str="";
       String result=null;
       private proba (Context context){
           this.context=context;
       }
       @Override
        protected String doInBackground(String... urls){
           try{
               String query = String.format(urls[0]);
               URL url = new URL(query);
               HttpURLConnection conn =(HttpURLConnection)url.openConnection();
               conn.setReadTimeout(10000);
               conn.setConnectTimeout(15000);
               conn.setRequestMethod("GET");
               conn.setDoInput(true);
               conn.connect();
               stream=conn.getInputStream();
               BufferedReader reader=null;
               StringBuilder sb=new StringBuilder();
               reader=new BufferedReader(new InputStreamReader(stream));

               String line=null;
               while((line=reader.readLine())!=null){
                   sb.append(line);
               }
               result=sb.toString();
               Log.i("resultado",result);
               return  result;
           }
           catch (IOException e){
               return null;
           }
       }
       @Override
        protected void onPostExecute(String result){
           TextView n =(TextView) findViewById(R.id.messageText);
           n.setText(result);
       }

    }



}

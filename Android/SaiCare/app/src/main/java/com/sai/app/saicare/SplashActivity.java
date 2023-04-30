package com.sai.app.saicare;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SplashActivity extends AppCompatActivity {

    ProgressBar loader;
    String json_array;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String key;
    int update_status=0;

    static String AppIndexKey = "MHV2203APPY2018MMAYD121.0RELEASE";

    String temp[] = {
            "empty",
            "empty"
    };

    String home[] = new String[100];
    String gallery[] = new String[100];

    String doctors[] = new String[100];
    String d_name[]=new String[100];
    String d_quali[] = new String[100];
    String d_desg[] = new String[100];

    String equipments[] = new String[100];
    String e_details[] = new String[100];

    int homeind=0,galleryind=0,doctorsind=0,equipmentsind=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);



        loader = (ProgressBar) findViewById(R.id.loader);
        loader.setVisibility(View.GONE);

        initiate();
    }

    public void initiate(){
        loader.setVisibility(View.VISIBLE);
        if(!isNetworkAvailable()){
            loader.setVisibility(View.GONE);
            Intent i = new Intent(SplashActivity.this,NoNetworkActivity.class);
            startActivityForResult(i,81);
        }
        else{
            GetImages gi = new GetImages();
            gi.execute();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==81){
            if(resultCode==RESULT_CANCELED){
                finish();
            }else{
                initiate();
            }
        }
        if(requestCode==89){
            finish();
        }
    }
    public void completed(){
        if(update_status==1) {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            if (homeind > 0)
                i.putExtra("home", home);
            else
                i.putExtra("home", temp);
            if (galleryind > 0)
                i.putExtra("gallery", gallery);
            else
                i.putExtra("gallery", temp);
            if (doctorsind > 0) {
                i.putExtra("doctors", doctors);
                i.putExtra("d_name", d_name);
                i.putExtra("d_quali", d_quali);
                i.putExtra("d_desg", d_desg);
            } else
                i.putExtra("doctors", temp);
            if (equipmentsind > 0) {
                i.putExtra("equipments", equipments);
                i.putExtra("e_details", e_details);
            } else
                i.putExtra("equipments", temp);

            i.putExtra("homelen", homeind + "");
            i.putExtra("gallerylen", galleryind + "");
            i.putExtra("doctorslen", doctorsind + "");
            i.putExtra("equipmentslen", equipmentsind + "");
            startActivity(i);
            finish();
        }
        else{
            loader.setVisibility(View.GONE);
            Intent i =new Intent(SplashActivity.this,UpdateBox.class);
            startActivityForResult(i,89);
        }
    }





    class GetImages extends AsyncTask<Void , Void , String> {

        String insert_url;
        String json_url;
        String JSON_STRING="";

        @Override
        protected void onPreExecute() {

            insert_url = "https://saronic-oscillators.000webhostapp.com/mahavir/readimages.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                inputStream.close();

                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (Exception e) {}

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_array = result;
            try {
                jsonObject = new JSONObject(json_array);
                int count = 0;
                String name;
                jsonArray = jsonObject.getJSONArray("home");
                count=0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    name = jo.getString("name");
                    if(name.equals("null")){
                        break;
                    }
                    else{
                        home[homeind]=name;
                        homeind++;
                    }
                    count++;
                }

                jsonArray = jsonObject.getJSONArray("gallery");
                count=0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    name = jo.getString("name");
                    if(name.equals("null")){
                        break;
                    }
                    else{
                        gallery[galleryind]=name;
                        galleryind++;
                    }
                    count++;
                }

                jsonArray = jsonObject.getJSONArray("doctors");
                count=0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    name = jo.getString("name");
                    if(name.equals("null")){
                        break;
                    }
                    else{
                        doctors[doctorsind]=name;
                        d_name[doctorsind]=jo.getString("dr_name");
                        d_quali[doctorsind]=jo.getString("quali");
                        d_desg[doctorsind]=jo.getString("desg");
                        doctorsind++;
                    }
                    count++;
                }

                jsonArray = jsonObject.getJSONArray("equipments");
                count=0;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    name = jo.getString("name");
                    if(name.equals("null")){
                        break;
                    }
                    else{
                        equipments[equipmentsind]=name;
                        e_details[equipmentsind]=jo.getString("details");
                        equipmentsind++;
                    }
                    count++;
                }
                count =0;
                jsonArray = jsonObject.getJSONArray("update");
                JSONObject jo = jsonArray.getJSONObject(count);
                key = jo.getString("key");

                if(key.equals(AppIndexKey))
                    update_status = 1;
                completed();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loader.setVisibility(View.GONE);
                    }
                });
                Intent i = new Intent(SplashActivity.this, NoNetworkActivity.class);
                startActivityForResult(i, 81);
            }
        }
    }

}

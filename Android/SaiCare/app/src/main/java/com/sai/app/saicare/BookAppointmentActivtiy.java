package com.sai.app.saicare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class BookAppointmentActivtiy extends AppCompatActivity {

    Button date_but[] = new Button[10];
    Button slot_but[] = new Button[16];
    Button done;

    String time1[] = {"08:00","08:30","09:00","09:30","10:00","10:30","11:00","11:30",
            "12:00","12:30","04:30","05:00","05:30","06:00","06:30","07:00"};

    String time2[]={
            "AM",
            "AM",
            "AM",
            "AM",
            "AM",
            "AM",
            "AM",
            "AM",
            "PM",
            "PM",
            "PM",
            "PM",
            "PM",
            "PM",
            "PM",
            "PM"
    };

    int previd = 0;
    RelativeLayout slotrel;
    int err=0 ;
    int ind=0;
    String day[] = new String[10];
    String month[] = new String[10];
    String year[] = new String[10];
    String slots[] = new String[10];
    String minute,hour;
    int cur_date_ind=-1;
    int cur_slot_ind=-1;

    String timeslot[] = new String[16];


    String json_array;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_book_appointment_activtiy);

        date_but[0] = (Button) findViewById(R.id.one);
        date_but[1] = (Button) findViewById(R.id.two);
        date_but[2] = (Button) findViewById(R.id.three);
        date_but[3] = (Button) findViewById(R.id.four);
        date_but[4] = (Button) findViewById(R.id.five);
        date_but[5] = (Button) findViewById(R.id.six);
        date_but[6] = (Button) findViewById(R.id.seven);
        date_but[7] = (Button) findViewById(R.id.eight);
        date_but[8] = (Button) findViewById(R.id.nine);
        date_but[9] = (Button) findViewById(R.id.ten);


        slot_but[0] = (Button) findViewById(R.id.s1);
        slot_but[1] = (Button) findViewById(R.id.s2);
        slot_but[2] = (Button) findViewById(R.id.s3);
        slot_but[3] = (Button) findViewById(R.id.s4);
        slot_but[4] = (Button) findViewById(R.id.s5);

        slot_but[5] = (Button) findViewById(R.id.s6);
        slot_but[6] = (Button) findViewById(R.id.s7);
        slot_but[7] = (Button) findViewById(R.id.s8);
        slot_but[8] = (Button) findViewById(R.id.s9);
        slot_but[9] = (Button) findViewById(R.id.s10);

        slot_but[10] = (Button) findViewById(R.id.s11);
        slot_but[11] = (Button) findViewById(R.id.s12);
        slot_but[12] = (Button) findViewById(R.id.s13);
        slot_but[13] = (Button) findViewById(R.id.s14);
        slot_but[14] = (Button) findViewById(R.id.s15);

        slot_but[15] = (Button) findViewById(R.id.s16);

        slotrel = (RelativeLayout) findViewById(R.id.slotrel);
        done = (Button) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cur_date_ind!=-1 && cur_slot_ind!=-1) {
                    Intent i = new Intent(BookAppointmentActivtiy.this, AppointmentFormActivity.class);
                    i.putExtra("day",day[cur_date_ind]);
                    i.putExtra("month",month[cur_date_ind]);
                    i.putExtra("year",year[cur_date_ind]);
                    i.putExtra("slot",cur_slot_ind+"");
                    i.putExtra("slots",slots[cur_date_ind]);
                    startActivityForResult(i,170);
                }
                else {
                    Toast t = Toast.makeText(BookAppointmentActivtiy.this, "Please select your slot", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                }
            }
        });
        ReadBookings read = new ReadBookings();
        read.execute();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==170){
            if(resultCode==RESULT_CANCELED){
                finish();
            }
        }
        if(requestCode==81){
            if(resultCode==RESULT_CANCELED){
                finish();
            }
            else{
                ReadBookings read = new ReadBookings();
                read.execute();
            }
        }
    }

    public void initDateButtons(){

        for(int i=0;i<=9;i++){
            date_but[i].setTextColor(getResources().getColor(R.color.white));
            date_but[i].setBackground(getResources().getDrawable(R.drawable.date_button));
            date_but[i].setText(day[i]+"/"+month[i]+"\n"+year[i]);
        }
    }
    public void initSlotButtons(){


        for(int i=0;i<=15;i++){
            slot_but[i].setTextColor(getResources().getColor(R.color.colorPrimary));
            slot_but[i].setBackground(getResources().getDrawable(R.drawable.book_button));
            slot_but[i].setText(time1[i]+"\n"+time2[i]);
        }
    }

    public void dc(View view){
        cur_slot_ind=-1;
        int id = view.getId();
        Button bt = (Button) findViewById(id);
        initDateButtons();
        bt.setBackground(getResources().getDrawable(R.drawable.date_button_selected));
        bt.setTextColor(getResources().getColor(R.color.white));
        slotrel.setVisibility(View.VISIBLE);
        for(int i=0;i<=9;i++)
        {
            if(date_but[i].getId()==id) {
                cur_date_ind = i;
                updateSlotButtons(i);
                break;
            }
        }

    }


    public void sc(View view){
        int id = view.getId();
        Button bt = (Button) findViewById(id);
        String slt = slots[cur_date_ind];
        updateSlotButtons(cur_date_ind);
        for(int i=0;i<=15;i++){
            if(slot_but[i].getId()==id){
                if(slt.charAt(i)=='1'){
                    Toast t = Toast.makeText(BookAppointmentActivtiy.this, "This slot is not available. Please select any another slot", Toast.LENGTH_SHORT);
                    t.setGravity(Gravity.CENTER,0,0);
                    t.show();
                }
                else{
                    cur_slot_ind = i;
                    bt.setBackground(getResources().getDrawable(R.drawable.book_button_selected));
                    bt.setTextColor(getResources().getColor(R.color.white));
                }
            }
        }
    }


    public void setDetails(){
        initDateButtons();
        initSlotButtons();
    }

    public void updateSlotButtons(int ind){
        initSlotButtons();
        String slot = slots[ind];
        for(int i=0;i<=15;i++){
            if(slot.charAt(i)=='1'){
                slot_but[i].setBackground(getResources().getDrawable(R.drawable.book_button_booked));
                slot_but[i].setTextColor(getResources().getColor(R.color.white));
            }
        }
    }



    class ReadBookings extends AsyncTask<Void , Void , String> {

        String json_url;
        String JSON_STRING="";

        @Override
        protected void onPreExecute() {
            p  = new ProgressDialog(BookAppointmentActivtiy.this);
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setMessage("Loading...Please wait");
            p.setIndeterminate(true);
            p.setCancelable(false);
            p.show();
            json_url = "https://saronic-oscillators.000webhostapp.com/mahavir/ReadBooking.php";
        }

        @Override
        protected String doInBackground(Void... voids) {



            try {
                URL url = new URL(json_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                bufferedReader.close();
                inputStream.close();

                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (Exception e) {}

            return "Failed";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {

            json_array = result;
            String temp="";
            ind=0;
            try {
                jsonObject = new JSONObject(json_array);
                jsonArray = jsonObject.getJSONArray("bookings");
                int count =0;
                while(count<jsonArray.length()){
                    JSONObject jo = jsonArray.getJSONObject(count);
                    temp = jo.getString("date");
                    month[ind] = temp.substring(0,2);
                    day[ind] = temp.substring(3,5);
                    year[ind] = temp.substring(6);
                    slots[ind] = jo.getString("slots");
                    minute = jo.getString("min");
                    hour = jo.getString("hour");
                    ind++;
                    count++;
                }


                p.cancel();
                p.hide();
                setDetails();
            } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            p.cancel();
                            p.hide();
                        }
                    });
                    Intent i = new Intent(BookAppointmentActivtiy.this,NoNetworkActivity.class);
                    startActivityForResult(i,81);
            }
        }
    }
}

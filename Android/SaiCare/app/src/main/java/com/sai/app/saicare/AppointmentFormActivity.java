package com.sai.app.saicare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;

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

public class AppointmentFormActivity extends AppCompatActivity {

    Button book;
    String sname,smobile,sage,semail,saddress,sreffered,sgender;
    EditText name,mobile,age,email,reffered,address;
    Spinner gender;
    String gen_str[] = {"Gender","Male","Female"};
    Button cat[] = new Button[36];
    String cat_string = "000000000000000000000000000000000000";
    String day,month,year,slots,updatedslots,date;
    String cur_slot;
    int cur_slot_ind;

    String ser="";
    String time_slot;

    String time_slots[] = {"08:00AM-08:30AM","08:30AM-09:00AM","09:00AM-09:30AM","09:30AM-10:00AM","10:00AM-10:30AM","10:30AM-11:00AM","11:00AM-11:30AM","11:30AM-12:00PM",
            "12:00PM-12:30PM","12:30PM-01:00PM","04:30PM-05:00PM","05:00PM-05:30PM","05:30PM-06:00PM","06:00PM-06:30PM","06:30PM-07:00PM","07:00PM-07:30PM"};

    String facillities[] = {"CT SACAN-BRAIN",
            "CT SACAN-ABDOMEN",
            "CT SACAN-THORAX",
            "CT SACAN-SPINE",
            "CT SACAN-KNEE",
            "CT SACAN-FOOT",
            "CT SACAN-ELBOW",
            "CT SACAN-ORBIT",
            "CT SACAN-PELVIS",
            "CT SACAN-NECK",
            "CT SACAN-FEMUR",
            "CT SACAN-LEG",
            "CT SACAN-SHOULDER",
            "CT SACAN-HAND",
            "CT SACAN-DNS",
            "CT SACAN-NASOPHYRNX",
            "CT SACAN-MANDIBLE",
            "CT SACAN-MAXILLA",
            "CT SACAN-IAC",
            "CT SACAN-HUMERUS",
            "ULTRASOUND-WHOLE ABDOMEN",
            "ULTRASOUND-KUB ABDOMEN",
            "ULTRASOUND-FOLLICULAR STUDY",
            "ULTRASOUND-UPPER ABDOMEN",
            "ULTRASOUND-KUBP ABDOMEN",
            "ULTRASOUND-LOWER ABDOMEN",
            "ULTRASOUND-PREGNANCY",
            "X-RAY-BRAIN-SKULL",
            "X-RAY-THROX-CHEST",
            "OTHER-ECG",
            "OTHER-PFT",
            "OTHER-PATHOLOGY",
            "OTHER-ECHO",
            "OTHER-HOLTER",
            "OTHER-DIGITAL LIFE",
            "OTHER-24 HR BP"
    };

    String json_array;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProgressDialog p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_appointment_form);

        gender = (Spinner) findViewById(R.id.gender);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spiner_item,gen_str);
        gender.setAdapter(adapter);

        initCategoryButtons();

        day = getIntent().getStringExtra("day");
        month = getIntent().getStringExtra("month");
        year = getIntent().getStringExtra("year");
        cur_slot = getIntent().getStringExtra("slot");
        cur_slot_ind = Integer.parseInt(cur_slot);
        slots = getIntent().getStringExtra("slots");
        updatedslots = slots.substring(0,cur_slot_ind)+"1"+slots.substring(cur_slot_ind+1);


        date = month+"/"+day+"/"+year;

        book = (Button) findViewById(R.id.book);
        name = (EditText) findViewById(R.id.name);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        age = (EditText) findViewById(R.id.age);
        reffered = (EditText) findViewById(R.id.reffered);
        address = (EditText) findViewById(R.id.address);
        gender = (Spinner) findViewById(R.id.gender);



        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = validate();
                if(val.equals("1111111")) {
                    if(!cat_string.equals("000000000000000000000000000000000000")) {

                        for(int i=0;i<=35;i++){
                            if(cat_string.charAt(i)=='1'){
                                ser = ser+facillities[i]+",";
                            }
                        }

                        ser=ser.substring(0,ser.length()-1);
                        time_slot = time_slots[cur_slot_ind];

                        BookSlot book = new BookSlot();
                        book.execute();
                    }
                    else
                        Toast.makeText(AppointmentFormActivity.this, "Please choose services you need", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(AppointmentFormActivity.this, "Invalid inputs", Toast.LENGTH_SHORT).show();
                    name.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    mobile.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    age.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    reffered.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    address.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    gender.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    email.setBackground(getResources().getDrawable(R.drawable.formtextbox));
                    if(val.charAt(0)=='0'){
                        name.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                    if(val.charAt(1)=='0'){
                        mobile.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                    if(val.charAt(2)=='0'){
                        age.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                    if(val.charAt(3)=='0'){
                        reffered.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                    if(val.charAt(4)=='0'){
                        address.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                    if(val.charAt(5)=='0'){
                        gender.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                    if(val.charAt(6)=='0'){
                        email.setBackground(getResources().getDrawable(R.drawable.formtextbox_red));
                    }
                }
            }
        });

    }

    public void initCategoryButtons(){

        int i=0;
        cat[i] = (Button) findViewById(R.id.a1); i++;
        cat[i] = (Button) findViewById(R.id.a2); i++;
        cat[i] = (Button) findViewById(R.id.a3); i++;
        cat[i] = (Button) findViewById(R.id.a4); i++;
        cat[i] = (Button) findViewById(R.id.a5); i++;
        cat[i] = (Button) findViewById(R.id.a6); i++;
        cat[i] = (Button) findViewById(R.id.a7); i++;
        cat[i] = (Button) findViewById(R.id.a8); i++;
        cat[i] = (Button) findViewById(R.id.a9); i++;
        cat[i] = (Button) findViewById(R.id.a10);i++;
        cat[i] = (Button) findViewById(R.id.a11);i++;
        cat[i] = (Button) findViewById(R.id.a12);i++;
        cat[i] = (Button) findViewById(R.id.a13);i++;
        cat[i] = (Button) findViewById(R.id.a14);i++;
        cat[i] = (Button) findViewById(R.id.a15);i++;
        cat[i] = (Button) findViewById(R.id.a16);i++;
        cat[i] = (Button) findViewById(R.id.a17);i++;
        cat[i] = (Button) findViewById(R.id.a18);i++;
        cat[i] = (Button) findViewById(R.id.a19);i++;
        cat[i] = (Button) findViewById(R.id.a20);i++;
        cat[i] = (Button) findViewById(R.id.b1); i++;
        cat[i] = (Button) findViewById(R.id.b2); i++;
        cat[i] = (Button) findViewById(R.id.b3); i++;
        cat[i] = (Button) findViewById(R.id.b4); i++;
        cat[i] = (Button) findViewById(R.id.b5); i++;
        cat[i] = (Button) findViewById(R.id.b6); i++;
        cat[i] = (Button) findViewById(R.id.b7); i++;
        cat[i] = (Button) findViewById(R.id.c1); i++;
        cat[i] = (Button) findViewById(R.id.c2); i++;
        cat[i] = (Button) findViewById(R.id.d1); i++;
        cat[i] = (Button) findViewById(R.id.d2); i++;
        cat[i] = (Button) findViewById(R.id.d3); i++;
        cat[i] = (Button) findViewById(R.id.d4); i++;
        cat[i] = (Button) findViewById(R.id.d5); i++;
        cat[i] = (Button) findViewById(R.id.d6); i++;
        cat[i] = (Button) findViewById(R.id.d7);

        for(i=0;i<=35;i++){
            cat[i].setBackground(getResources().getDrawable(R.drawable.cat_but_normal));
            cat[i].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    public void catClicked(View v){
        int id = v.getId();
        Button bt = (Button) findViewById(id);
        for(int i=0;i<=35;i++){
            if(cat[i].getId()==id){
                if(cat_string.charAt(i)=='0'){
                    bt.setBackground(getResources().getDrawable(R.drawable.cat_but_selected));
                    bt.setTextColor(getResources().getColor(R.color.white));
                    cat_string = cat_string.substring(0,i)+'1'+cat_string.substring(i+1);
                }
                else{
                    bt.setBackground(getResources().getDrawable(R.drawable.cat_but_normal));
                    bt.setTextColor(getResources().getColor(R.color.colorPrimary));
                    cat_string = cat_string.substring(0,i)+'0'+cat_string.substring(i+1);
                }
            }
        }
    }


    public void completed(int id){
        if(id==1){
            Intent i = new Intent(AppointmentFormActivity.this,PreviewActivity.class);
            i.putExtra("name",sname);
            i.putExtra("mobile",smobile);
            i.putExtra("age",sage);
            i.putExtra("email",semail);
            i.putExtra("address",saddress);
            i.putExtra("reffered",sreffered);
            i.putExtra("gender",sgender);
            i.putExtra("services",cat_string);
            i.putExtra("date",date);
            i.putExtra("time",cur_slot_ind+"");
            i.putExtra("status","1");
            startActivityForResult(i,170);

        }else{
            Toast.makeText(AppointmentFormActivity.this, "An error occured...please try again", Toast.LENGTH_SHORT).show();

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==170){
            if(resultCode==RESULT_CANCELED){
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        }
        if(requestCode==81){
            if(resultCode==RESULT_CANCELED){
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        }
    }


    class BookSlot extends AsyncTask<Void , Void , String> {

        String insert_url;
        String json_url;
        String JSON_STRING="";

        @Override
        protected void onPreExecute() {
            p  = new ProgressDialog(AppointmentFormActivity.this);
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setMessage("Loading...Please wait");
            p.setIndeterminate(true);
            p.setCancelable(false);
            p.show();
            insert_url = "https://saronic-oscillators.000webhostapp.com/mahavir/AddBooking.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("mobile","UTF-8")+"="+URLEncoder.encode(smobile,"UTF-8")+"&"+
                        URLEncoder.encode("pname","UTF-8")+"="+URLEncoder.encode(sname,"UTF-8")+"&"+
                        URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(sgender,"UTF-8")+"&"+
                        URLEncoder.encode("age","UTF-8")+"="+URLEncoder.encode(sage,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(semail,"UTF-8")+"&"+
                        URLEncoder.encode("rby","UTF-8")+"="+URLEncoder.encode(sreffered,"UTF-8")+"&"+
                        URLEncoder.encode("services","UTF-8")+"="+URLEncoder.encode(ser,"UTF-8")+"&"+
                        URLEncoder.encode("slot","UTF-8")+"="+URLEncoder.encode(time_slot,"UTF-8")+"&"+
                        URLEncoder.encode("doa","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+
                        URLEncoder.encode("slotarr","UTF-8")+"="+URLEncoder.encode(updatedslots,"UTF-8")+"&"+
                        URLEncoder.encode("slot_ind","UTF-8")+"="+URLEncoder.encode(cur_slot_ind+"","UTF-8")+"&"+
                        URLEncoder.encode("address","UTF-8")+"="+URLEncoder.encode(saddress,"UTF-8");



                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

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
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String res;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    res = jo.getString("status");
                    count++;
                    p.cancel();
                    p.hide();
                    int resint = Integer.parseInt(res);
                    completed(resint);
                }
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p.cancel();
                        p.hide();
                    }
                });
                Intent i = new Intent(AppointmentFormActivity.this, NoNetworkActivity.class);
                startActivityForResult(i, 81);
            }
        }
    }







    public String validate(){

        int n=0,m=0,a=0,e=0,ad=0,r=1,g=0;
        sname = name.getText().toString();
        smobile = mobile.getText().toString();
        sage = age.getText().toString();
        semail = email.getText().toString();
        saddress = address.getText().toString();
        if(gender.getSelectedItemPosition()==0)
            sgender = "select";
        else if(gender.getSelectedItemPosition()==1)
            sgender = "Male";
        else
            sgender = "Female";

        if(sname.length()!=0)
        {
            n=1;
            for(int i=0;i<sname.length();i++)
            {
                if(Character.isDigit(sname.charAt(i))||((!Character.isDigit(sname.charAt(i))&&!Character.isLetter(sname.charAt(i)))&&!Character.isSpaceChar(sname.charAt(i))))
                    n=0;
            }
        }

        if(smobile.length()==10)
        {
            m=1;
            for(int i=0;i<smobile.length();i++)
            {
                if(!Character.isDigit(smobile.charAt(i)))
                    m=0;
            }
        }

        if(sage.length()>0 && sage.length() <= 2)
        {
            a = 1;
            for (int i = 0; i < sage.length(); i++) {
                if (!Character.isDigit(sage.charAt(i)))
                    a = 0;
            }
        }



        if(saddress.length()!=0)
            ad=1;

        if(sgender!="select")
            g=1;

        if(semail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"))
            e=1;

        String s = n+""+m+""+a+""+r+""+ad+""+g+""+e;
        return s;
    }

}

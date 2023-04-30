package com.sai.app.saicare;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class PreviewActivity extends AppCompatActivity {

    String name,mobile,age,email,gender,address,reffered,services,date,time,status;
    int time_ind;
    TextView tname,tage,tgender,temail,tmobile,treffered,tdate,tservices;
    ImageButton close;

    String time_slots[] = {"08:00 AM","08:30 AM","09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM",
            "12:00 PM","12:30 PM","04:30 PM","05:00 PM","05:30 PM ","06:00 PM","06:30 PM","07:00 PM"};

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

    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_preview);


        name = getIntent().getStringExtra("name");
        mobile = getIntent().getStringExtra("mobile");
        age = getIntent().getStringExtra("age");
        gender = getIntent().getStringExtra("gender");
        address = getIntent().getStringExtra("address");
        reffered = getIntent().getStringExtra("reffered");
        services = getIntent().getStringExtra("services");
        email = getIntent().getStringExtra("email");
        date = getIntent().getStringExtra("date");
        status = getIntent().getStringExtra("status");
        time_ind = Integer.parseInt(getIntent().getStringExtra("time"));
        time = time_slots[time_ind];

        close = (ImageButton) findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED, intent);
                finish();
            }
        });

        if(status.equals("1")){
            db = openOrCreateDatabase("sai", Context.MODE_PRIVATE,null);
            db.execSQL("CREATE TABLE IF NOT EXISTS appointments(name varchar2(30),mobile varchar2(10),age varchar2(3),email varchar2(80),rby varchar2(30),gender varchar2(10),services varchar2(36),slot varchar2(2),doa varchar2(15),address varchar2(100));");
            db.execSQL("INSERT INTO appointments VALUES('"+name+"','"+mobile+"','"+age+"','"+email+"','"+reffered+"','"+gender+"','"+services+"','"+time_ind+"','"+date+"','"+address+"');");
            db.close();
        }

        tname = (TextView) findViewById(R.id.name);
        tname.setText(name);

        tage = (TextView) findViewById(R.id.age);
        tage.setText("AGE : "+age);

        tgender = (TextView) findViewById(R.id.gender);
        tgender.setText("Gender : "+gender);

        temail = (TextView) findViewById(R.id.email);
        temail.setText("Email : "+email);

        tmobile = (TextView) findViewById(R.id.mobile);
        tmobile.setText("Mobile : "+mobile);

        treffered = (TextView) findViewById(R.id.reffered);
        treffered.setText("Reffered by : "+reffered);

        tdate = (TextView) findViewById(R.id.date);
        tdate.setText(date+" ~ "+time);

        tservices = (TextView) findViewById(R.id.services);
        tservices.setText(" ");
        for(int i=0;i<=35;i++){
            if(services.charAt(i)=='1'){
                tservices.setText(tservices.getText().toString()+"\n"+facillities[i]);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_CANCELED, intent);
        finish();
    }
}

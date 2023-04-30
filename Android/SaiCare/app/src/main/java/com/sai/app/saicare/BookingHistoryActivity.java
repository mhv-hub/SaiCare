package com.sai.app.saicare;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class BookingHistoryActivity extends AppCompatActivity {

    ListView listView;
    List<HashMap<String,String>> aList;
    SQLiteDatabase db;
    int ind=0;
    RelativeLayout main,temp;

    String name[] = new String[100];
    String mobile[] = new String[100];
    String age[] = new String[100];
    String email[] = new String[100];
    String rby[] = new String[100];
    String gender[] = new String[100];
    String services[] = new String[100];
    String slot[] = new String[100];
    String doa[] = new String[100];
    String address[] = new String[100];
    String time_slots[] = {"08:00 AM","08:30 AM","09:00 AM","09:30 AM","10:00 AM","10:30 AM","11:00 AM","11:30 AM",
            "12:00 PM","12:30 PM","04:30 PM","05:00 PM","05:30 PM ","06:00 PM","06:30 PM","07:00 PM"};
    String time[] = new String[100];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_booking_history);

        main = (RelativeLayout) findViewById(R.id.main);
        temp = (RelativeLayout) findViewById(R.id.temp);

        db = openOrCreateDatabase("sai", Context.MODE_PRIVATE, null);

        try {
            Cursor c = db.rawQuery("SELECT * FROM appointments", null);
            while (c.moveToNext()) {
                name[ind]=c.getString(0);
                mobile[ind]=c.getString(1);
                age[ind]=c.getString(2);
                email[ind]=c.getString(3);
                rby[ind]=c.getString(4);
                gender[ind]=c.getString(5);
                services[ind]=c.getString(6);
                slot[ind]=c.getString(7);
                time[ind]=time_slots[Integer.parseInt(slot[ind])];
                doa[ind]=c.getString(8);
                address[ind]=c.getString(9);
                ind++;
            }
        }catch(Exception e){

        }

        ind--;
        if(ind<0)
            temp.setVisibility(View.VISIBLE);
        else
            main.setVisibility(View.VISIBLE);

        listView = (ListView) findViewById(R.id.list_items);
        aList = new ArrayList<HashMap<String,String>>();



        for(int i=ind;i>=0;i--){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("name",name[i]);
            hm.put("date",doa[i]);
            hm.put("time",time[i]);
            aList.add(hm);

            String[] from = { "name","date","time" };

            int[] to = { R.id.name,R.id.date,R.id.time};

            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.list_view_layout, from, to);
            listView.setAdapter(adapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                position = ind-position;

                Intent i = new Intent(BookingHistoryActivity.this,PreviewActivity.class);
                i.putExtra("name",name[position]);
                i.putExtra("mobile",mobile[position]);
                i.putExtra("age",age[position]);
                i.putExtra("email",email[position]);
                i.putExtra("address",address[position]);
                i.putExtra("reffered",rby[position]);
                i.putExtra("gender",gender[position]);
                i.putExtra("services",services[position]);
                i.putExtra("date",doa[position]);
                i.putExtra("time",slot[position]);
                i.putExtra("status","2");
                startActivity(i);
            }
        });

    }
}

package com.sai.app.saicare;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    int st=0;
    NavigationView navigationView;
    String home[] = new String[100];
    String gallery[] = new String[100];
    String doctors[] = new String[100];

    String d_name[]=new String[100];
    String d_quali[] = new String[100];
    String d_desg[] = new String[100];

    String equipments[] = new String[100];
    String e_details[] = new String[100];

    int homelen,gallerylen,doctorslen,equipmentslen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("SAI Scan and Diagnostic Center");
        setSupportActionBar(toolbar);


        home = getIntent().getStringArrayExtra("home");
        gallery = getIntent().getStringArrayExtra("gallery");
        doctors = getIntent().getStringArrayExtra("doctors");
        d_name = getIntent().getStringArrayExtra("d_name");
        d_quali = getIntent().getStringArrayExtra("d_quali");
        d_desg = getIntent().getStringArrayExtra("d_desg");
        equipments = getIntent().getStringArrayExtra("equipments");
        e_details = getIntent().getStringArrayExtra("e_details");
        homelen = Integer.parseInt(getIntent().getStringExtra("homelen"));
        gallerylen = Integer.parseInt(getIntent().getStringExtra("gallerylen"));
        doctorslen = Integer.parseInt(getIntent().getStringExtra("doctorslen"));
        equipmentslen = Integer.parseInt(getIntent().getStringExtra("equipmentslen"));


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.getMenu().getItem(0).setChecked(true);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
        st=1;

    }

    public void closeFragment(){
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
        st=1;
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        if(st==0){
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
            st=1;
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        else
            startActivityForResult(new Intent(MainActivity.this,ExitConfirmationActivity.class),7);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.action_notice){
            st=0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new NoticeFragment()).commit();
        }

        if(id==R.id.action_book){
            startActivityForResult(new Intent(MainActivity.this,BookAppointmentActivtiy.class),170);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id==R.id.nav_home){

            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
            st=1;
            navigationView.getMenu().getItem(0).setChecked(true);
        }
        if(id==R.id.nav_docts){
            st=0;
            Intent i = new Intent(MainActivity.this,Doctors.class);
            i.putExtra("d_name",d_name);
            i.putExtra("d_desg",d_desg);
            i.putExtra("d_quali",d_quali);
            i.putExtra("doctors",doctors);
            i.putExtra("doctorslen",doctorslen+"");
            startActivityForResult(i,170);
        }

        if(id==R.id.nav_appoint){
            st=0;
            startActivityForResult(new Intent(MainActivity.this,BookingHistoryActivity.class),170);
        }

        if(id==R.id.nav_gal){
            st=0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new GalleryFragment()).commit();
        }

        if(id==R.id.nav_contact){
            st=0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ContactUsFragment()).commit();
        }

        if(id==R.id.nav_notice){
            st=0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new NoticeFragment()).commit();
        }

        if(id==R.id.nav_ser){
            st=0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new ServicesFragment()).commit();
        }

        if(id==R.id.nav_equip){
            st=0;
            Intent i = new Intent(MainActivity.this,Equipments.class);
            i.putExtra("e_details",e_details);
            i.putExtra("equipments",equipments);
            i.putExtra("equipmentslen",equipmentslen+"");
            startActivityForResult(i,170);
        }

        if(id==R.id.nav_about){
            st=0;
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame,new AboutUsFragment()).commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void openBookAppointment(){
        startActivity(new Intent(MainActivity.this,BookAppointmentActivtiy.class));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        st=1;
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content_frame,new HomeFragment()).commit();
        navigationView.getMenu().getItem(0).setChecked(true);
        if(requestCode==7){
            if(resultCode==RESULT_OK){
                finish();
            }
        }
    }
}

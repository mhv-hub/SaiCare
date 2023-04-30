package com.sai.app.saicare;

import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;
public class Doctors extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DoctorsAdapter adapter;
    private List<Doctor> albumList;

    RelativeLayout temp,main;

    String doctors[] = new String[100];
    String d_name[]=new String[100];
    String d_quali[] = new String[100];
    String d_desg[] = new String[100];
    int doctorslen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_doctors);

        temp = (RelativeLayout) findViewById(R.id.tempdoctors);
        main = (RelativeLayout) findViewById(R.id.doctorsection);
        temp.setVisibility(View.GONE);
        main.setVisibility(View.GONE);

        albumList = new ArrayList<>();
        adapter = new DoctorsAdapter(this, albumList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,0,true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if((doctorslen=Integer.parseInt(getIntent().getStringExtra("doctorslen")))>0){
            main.setVisibility(View.VISIBLE);
            doctors = getIntent().getStringArrayExtra("doctors");
            d_name = getIntent().getStringArrayExtra("d_name");
            d_quali = getIntent().getStringArrayExtra("d_quali");
            d_desg = getIntent().getStringArrayExtra("d_desg");
            prepareDoctors();
        }
        else
        {
            temp.setVisibility(View.VISIBLE);
        }

    }


    private void prepareDoctors() {

        String preurl = "https://saronic-oscillators.000webhostapp.com/";
        for(int i=0;i<doctorslen;i++){

            doctors[i]= preurl+"images/"+doctors[i];
            Doctor a = new Doctor(d_name[i],doctors[i],d_quali[i],d_desg[i]);
            albumList.add(a);
        }

        adapter.notifyDataSetChanged();
    }





    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

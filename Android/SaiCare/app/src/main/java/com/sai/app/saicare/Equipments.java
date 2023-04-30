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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Equipments extends AppCompatActivity {

    private RecyclerView recyclerView;
    private EquipmentsAdapter adapter;
    private List<Equipment> albumList;

    RelativeLayout temp,main;

    String equipments[] = new String[100];
    String e_details[]=new String[100];
    int equipmentslen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fragment_equipments);


        temp = (RelativeLayout) findViewById(R.id.tempequipments);
        main = (RelativeLayout) findViewById(R.id.equipmentsection);
        temp.setVisibility(View.GONE);
        main.setVisibility(View.GONE);

        albumList = new ArrayList<>();
        adapter = new EquipmentsAdapter(this, albumList);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(1,0,true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        if((equipmentslen=Integer.parseInt(getIntent().getStringExtra("equipmentslen")))>0){
            main.setVisibility(View.VISIBLE);
            equipments = getIntent().getStringArrayExtra("equipments");
            e_details = getIntent().getStringArrayExtra("e_details");
            prepareEquipments();
        }
        else
        {
            temp.setVisibility(View.VISIBLE);
        }

    }


    private void prepareEquipments() {

        String preurl = "https://saronic-oscillators.000webhostapp.com/";
        for(int i=0;i<equipmentslen;i++){

            equipments[i]= preurl+"images/"+equipments[i];
            Equipment a = new Equipment(e_details[i],equipments[i]);
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

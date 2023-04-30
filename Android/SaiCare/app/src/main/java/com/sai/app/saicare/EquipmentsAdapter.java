package com.sai.app.saicare;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;


public class EquipmentsAdapter extends RecyclerView.Adapter<EquipmentsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Equipment> albumList;
    Bitmap bm;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView e_details;
        public ImageView equipment;


        public MyViewHolder(View view) {
            super(view);
            e_details = (TextView) view.findViewById(R.id.e_details);
            equipment = (ImageView) view.findViewById(R.id.equipment);
        }


    }


    public EquipmentsAdapter(Context mContext, List<Equipment> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equipments_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Equipment album = albumList.get(position);
        holder.e_details.setText(album.getE_details());


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    bm = Glide.
                            with(mContext).
                            load(album.getEquipments()).
                            asBitmap().
                            into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL).
                            get();
                }catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(Void dummy) {
                if (bm!=null) {
                    try {
                        DisplayMetrics displayMetrics = new DisplayMetrics();
                        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                        wm.getDefaultDisplay().getMetrics(displayMetrics);
                        int targetWidth = displayMetrics.widthPixels;
                        double aspectRatio = (double) bm.getHeight() / (double) bm.getWidth();
                        int targetHeight = (int) (targetWidth * aspectRatio);
                        Bitmap resizedbm = Bitmap.createScaledBitmap(bm, targetWidth, targetHeight, false);
                        holder.equipment.setImageBitmap(resizedbm);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                };
            }
        }.execute();


        //Glide.with(mContext).load(album.getEquipments()).into(holder.equipment);
        /*holder.doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "position : "+position, Toast.LENGTH_SHORT).show();
            }
        });

        holder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "position : "+position, Toast.LENGTH_SHORT).show();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

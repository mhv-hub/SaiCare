package com.sai.app.saicare;


import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Doctor> albumList;
    Bitmap bm;

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public ImageView doctor;
        public TextView quali;


        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.d_name);
            doctor = (ImageView) view.findViewById(R.id.doctor);
            quali = (TextView) view.findViewById(R.id.d_quali);
        }


    }


    public DoctorsAdapter(Context mContext, List<Doctor> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final Doctor album = albumList.get(position);
        holder.name.setText(album.getName()+" ( "+album.getDesg()+" )");
        holder.quali.setText(album.getQuali());


        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                try {
                    bm = Glide.
                            with(mContext).
                            load(album.getImageurl()).
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
                        holder.doctor.setImageBitmap(resizedbm);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                };
            }
        }.execute();



        //Glide.with(mContext).load(album.getImageurl()).into(holder.doctor);
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

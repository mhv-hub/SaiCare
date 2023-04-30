package com.sai.app.saicare;


import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ServicesFragment extends Fragment {

    View view;
    ImageView it,it1,it2,it3,it4,it5,it6,it7,it8,it9,it10;
    Drawable d;

    public ServicesFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_services, container, false);

        it = (ImageView) view.findViewById(R.id.imgt);
        it1 = (ImageView) view.findViewById(R.id.imgt1);
        it2 = (ImageView) view.findViewById(R.id.imgt2);
        it3 = (ImageView) view.findViewById(R.id.imgt3);
        it4 = (ImageView) view.findViewById(R.id.imgt4);
        it5 = (ImageView) view.findViewById(R.id.imgt5);
        it6 = (ImageView) view.findViewById(R.id.imgt6);
        it7 = (ImageView) view.findViewById(R.id.imgt7);
        it8 = (ImageView) view.findViewById(R.id.imgt8);
        it9 = (ImageView) view.findViewById(R.id.imgt9);
        it10 = (ImageView) view.findViewById(R.id.imgt10);

        d = getResources().getDrawable(R.drawable.one);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.six);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it1.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.two);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it2.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.three);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it3.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.four);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it4.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.five);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it5.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.six);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it6.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.one);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it7.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.two);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it8.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.three);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it9.setImageDrawable(d);

        d = getResources().getDrawable(R.drawable.four);
        d.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP));
        it10.setImageDrawable(d);

        return view;
    }

}

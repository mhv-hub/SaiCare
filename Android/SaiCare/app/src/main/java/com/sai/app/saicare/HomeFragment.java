package com.sai.app.saicare;

import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.w3c.dom.Text;

import java.util.HashMap;

public class HomeFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    View view;
    private SliderLayout imageSlider;
    TextView temp ;
    Button book;
    String preurl = "https://saronic-oscillators.000webhostapp.com/";
    public HomeFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view =  inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = (SliderLayout) view.findViewById(R.id.slider);
        TextView textView = (TextView) view.findViewById(R.id.one);
        textView.setTypeface(Typeface.createFromAsset((getActivity().getAssets()),"fonts/Protos.otf"));
        temp = (TextView) view.findViewById(R.id.temp);

        HashMap<String,String> url_maps = new HashMap<String, String>();

        if(((MainActivity)getActivity()).homelen>0){
            for(int i=0;i<((MainActivity)getActivity()).homelen;i++){
                url_maps.put("img"+i,preurl+"images/"+((MainActivity)getActivity()).home[i]);
            }
        }

        for(String name : url_maps.keySet()){
            DefaultSliderView defaultSliderView = new DefaultSliderView(getActivity());
            defaultSliderView
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            imageSlider.addSlider(defaultSliderView);
        }
        imageSlider.setPresetTransformer(SliderLayout.Transformer.FlipHorizontal);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setDuration(3000);
        imageSlider.addOnPageChangeListener(this);

        book = (Button) view.findViewById(R.id.book);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).openBookAppointment();
            }
        });

        return view;
    }

    @Override
    public void onStop() {
        imageSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {}


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        temp.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}


}

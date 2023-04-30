package com.sai.app.saicare;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.HashMap;

public class GalleryFragment extends Fragment implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener{

    private SliderLayout imageSlider;
    TextView txt;
    View view;
    String preurl = "https://saronic-oscillators.000webhostapp.com/";

    public GalleryFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_gallery, container, false);
        imageSlider = (SliderLayout) view.findViewById(R.id.slider);
        txt = (TextView) view.findViewById(R.id.txt);
        txt.setTypeface(Typeface.createFromAsset((getActivity().getAssets()),"fonts/Protos.otf"));

        HashMap<String,String> url_maps = new HashMap<String, String>();

        if(((MainActivity)getActivity()).gallerylen>0){
            for(int i=0;i<((MainActivity)getActivity()).gallerylen;i++){
                url_maps.put("img"+i,preurl+"images/"+((MainActivity)getActivity()).gallery[i]);
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
        imageSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        imageSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        imageSlider.setDuration(2000);
        imageSlider.addOnPageChangeListener(this);

        return view;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //startActivity(new Intent(getActivity(),ImageViewActivity.class));
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {}

    @Override
    public void onPageScrollStateChanged(int state) {}

}

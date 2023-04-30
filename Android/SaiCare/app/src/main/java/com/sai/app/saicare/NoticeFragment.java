package com.sai.app.saicare;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class NoticeFragment extends Fragment {


    TextView ttt;
    View view;
    ListView listView;
    List<HashMap<String,String>> obj;

    RelativeLayout main,tempp;

    ProgressBar loader;
    String json_array;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProgressDialog p;

    String notice[] = new String[100];
    int ind=0;

    public NoticeFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_notice, container, false);

        ttt = (TextView) view.findViewById(R.id.ttt);
        ttt.setTypeface(Typeface.createFromAsset((getActivity().getAssets()),"fonts/Protos.otf"));

        listView = (ListView) view.findViewById(R.id.notice_list);
        main = (RelativeLayout) view.findViewById(R.id.noticesection);
        tempp = (RelativeLayout) view.findViewById(R.id.ttmp);

        obj = new ArrayList<HashMap<String,String>>();

        GetNotices gn = new GetNotices();
        gn.execute();

        return view;
    }

    public void completed(){
        p.cancel();
        p.hide();
        tempp.setVisibility(View.GONE);
        main.setVisibility(View.VISIBLE);

        for(int i=ind-1;i>=0;i--) {
            HashMap<String, String> hm = new HashMap<String, String>();

            hm.put("msg",notice[i]);
            obj.add(hm);

            String[] from = {"msg"};

            int[] to = {R.id.msg};
            SimpleAdapter adapter = new SimpleAdapter(getActivity(), obj, R.layout.notice_list_layout, from, to);
            listView.setAdapter(adapter);
        }

    }

    class GetNotices extends AsyncTask<Void , Void , String> {

        String insert_url;
        String json_url;
        String JSON_STRING="";

        @Override
        protected void onPreExecute() {
            p  = new ProgressDialog(getActivity());
            p.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            p.setMessage("Loading...Please wait");
            p.setIndeterminate(true);
            p.setCancelable(false);
            p.show();
            insert_url = "https://saronic-oscillators.000webhostapp.com/mahavir/GetNotices.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"UTF-8"));

                StringBuilder stringBuilder = new StringBuilder();
                while((JSON_STRING = bufferedReader.readLine())!=null){
                    stringBuilder.append(JSON_STRING+"\n");
                }

                inputStream.close();

                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();


            } catch (Exception e) {}

            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            json_array = result;
            int flage=0;
            try {
                jsonObject = new JSONObject(json_array);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String temp;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    temp = jo.getString("notice");
                    if(temp.equals("failed")){
                        flage=1;
                        break;
                    }
                    else{
                        notice[ind]=temp;
                        ind++;
                    }
                    count++;
                }
                if(flage==1){
                    p.cancel();
                    p.hide();
                    main.setVisibility(View.GONE);
                    tempp.setVisibility(View.VISIBLE);
                }
                else
                    completed();
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p.cancel();
                        p.hide();
                        Toast.makeText(getActivity(), "Unable to connect, please try again later", Toast.LENGTH_SHORT).show();
                        ((MainActivity)getActivity()).closeFragment();
                    }
                });

            }
        }
    }
}

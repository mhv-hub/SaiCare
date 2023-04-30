package com.sai.app.saicare;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;


public class ContactUsFragment extends Fragment {

    View view;
    EditText name,email,contact,message;
    String sname,semail,scontact,smessage,stype;
    RadioGroup radio;
    Button submit;

    String json_array;
    JSONObject jsonObject;
    JSONArray jsonArray;
    ProgressDialog p;

    public ContactUsFragment() {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contact_us, container, false);

        name = (EditText) view.findViewById(R.id.input_name);
        email = (EditText) view.findViewById(R.id.input_email);
        contact = (EditText) view.findViewById(R.id.input_mobile);
        message = (EditText) view.findViewById(R.id.input_message);

        radio = (RadioGroup) view.findViewById(R.id.radioGroup);

        submit = (Button) view.findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validation();
            }
        });

        return view;
    }

    public void validation(){
        if(name.getText().toString().length()==0){
            Toast.makeText(getActivity(), "Name can not be blank", Toast.LENGTH_LONG).show();
            return;
        }
        if(email.getText().toString().length()==0){
            Toast.makeText(getActivity(), "Email can not be blank", Toast.LENGTH_LONG).show();
            return;
        }
        if(contact.getText().toString().length()==0){
            Toast.makeText(getActivity(), "Contact number can not be blank", Toast.LENGTH_LONG).show();
            return;
        }
        if(message.getText().toString().length()==0){
            Toast.makeText(getActivity(), "message can not be blank", Toast.LENGTH_LONG).show();
            return;
        }
        if(radio.getCheckedRadioButtonId()==-1){
            Toast.makeText(getActivity(), "Select your response type, query or feedback", Toast.LENGTH_LONG).show();
            return;
        }

        sname = name.getText().toString();
        semail = email.getText().toString();
        scontact = contact.getText().toString();
        smessage = message.getText().toString();

        RadioButton radioButton = (RadioButton) view.findViewById(radio.getCheckedRadioButtonId());
        stype = radioButton.getText().toString();

        UpdateFeedback uf = new UpdateFeedback();
        uf.execute();

    }

    public void completed(int resint){
        if(resint==1){
            Toast.makeText(getActivity(), "Response submitted successfuly", Toast.LENGTH_LONG).show();
            ((MainActivity)getActivity()).closeFragment();
        }
        else{
            Toast.makeText(getActivity(), "An error occured, please try agin later", Toast.LENGTH_SHORT).show();
        }
    }


    class UpdateFeedback extends AsyncTask<Void , Void , String> {

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
            insert_url = "https://saronic-oscillators.000webhostapp.com/mahavir/updatefeedback.php";
        }

        @Override
        protected String doInBackground(Void... voids) {

            try {
                URL url = new URL(insert_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);


                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));

                String data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(sname,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(semail,"UTF-8")+"&"+
                        URLEncoder.encode("contact","UTF-8")+"="+URLEncoder.encode(scontact,"UTF-8")+"&"+
                        URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(stype,"UTF-8")+"&"+
                        URLEncoder.encode("message","UTF-8")+"="+URLEncoder.encode(smessage,"UTF-8");



                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();

                outputStream.close();

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
            try {
                jsonObject = new JSONObject(json_array);
                jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                String res;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    res = jo.getString("status");
                    count++;
                    p.cancel();
                    p.hide();
                    int resint = Integer.parseInt(res);
                    completed(resint);
                }
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        p.cancel();
                        p.hide();
                        Toast.makeText(getActivity(), "Unable to submit, please try again later", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }
    }

}

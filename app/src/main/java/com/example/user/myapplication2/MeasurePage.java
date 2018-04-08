package com.example.user.myapplication2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Measure;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MeasurePage extends AppCompatActivity {
    private String NEW_Waist;
    private String New_Arms;
    private String New_Thighs;
    private String New_Hips;
    private String New_Weight;
    private String userid;
    private String deleteReq;
CustomAdapterM yay2;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measure_page);
        userid= getIntent().getStringExtra("userid");

        Button add = (Button) findViewById(R.id.btnMeasure);
        Button cnl = (Button) findViewById(R.id.btnMC);

        cnl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                finish();
            }
        });
add.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View v ) {
        final Dialog dialog = new Dialog(MeasurePage.this);
        dialog.setContentView(R.layout.dialog_add_mmeasure);
        final NumberPicker npWA = (NumberPicker) dialog.findViewById(R.id.npWaist);
        final NumberPicker npWE = (NumberPicker) dialog.findViewById(R.id.npWeight);
        final NumberPicker npT = (NumberPicker) dialog.findViewById(R.id.npThighs);
        final NumberPicker npH = (NumberPicker) dialog.findViewById(R.id.npHips);
        final NumberPicker npA = (NumberPicker) dialog.findViewById(R.id.npArms);

        npWA.setMinValue(0);
        npWA.setMaxValue(200);
        npWE.setMinValue(0);
        npWE.setMaxValue(200);
        npT.setMinValue(0);
        npT.setMaxValue(200);
        npH.setMinValue(0);
        npH.setMaxValue(200);
        npA.setMinValue(0);
        npA.setMaxValue(200);

        Button btnC = (Button) dialog.findViewById(R.id.btnMAC);
        Button btnS = (Button) dialog.findViewById(R.id.btnMAS);

        btnS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {

                New_Arms=String.valueOf(npA.getValue());
                New_Hips=String.valueOf(npH.getValue());
                New_Thighs=String.valueOf(npT.getValue());
                NEW_Waist=String.valueOf(npWA.getValue());
                New_Weight=String.valueOf(npWE.getValue());

                bgTask4 bgTask4 = new bgTask4(MeasurePage.this);
                bgTask4.execute();
                bgTask bgTask = new bgTask(MeasurePage.this);
                bgTask.execute();
                dialog.dismiss();
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                dialog.cancel();
            }
        });
        dialog.create();
        dialog.show();
    }
});



        bgTask bgTask = new bgTask(MeasurePage.this);
        bgTask.execute();    }
//get data
     class bgTask extends AsyncTask<Measure, Void, ArrayList<Measurement>> {

         Context ctx;
         Activity activity;
         ArrayList<Measurement> arrayList = new ArrayList<>();
         ProgressDialog mProgress;

         public bgTask( Context ctx ) {
             this.ctx = ctx;
// activity = (Activity)ctx;
         }

         String json_string = "http://msgpelleg.com/Gym_PullDataMeasure.php";


         @Override
         protected ArrayList<Measurement> doInBackground( Measure... measures ) {
             try {
                 URL url = new URL(json_string);
                 HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                 InputStream inputStream = httpURLConnection.getInputStream();
                 BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                 StringBuilder stringBuilder = new StringBuilder();
                 String line;

                 while ((line = bufferedReader.readLine()) != null) {
                     stringBuilder.append(line + "\n");

                 }

                 httpURLConnection.disconnect();
                 String json_string = stringBuilder.toString().trim();
                 JSONObject jsonObject = new JSONObject(json_string);
                 JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                 int count = 0;
                 while (count < jsonArray.length()) {
                     JSONObject JO = jsonArray.getJSONObject(count);
                     count++;
                     Measurement shift = new Measurement(JO.getInt("arms"), JO.getInt("hips"), JO.getInt("thighs"), JO.getInt("waist"), JO.getInt("weight"), JO.getString("date"));
                     arrayList.add(shift);
                 }

                 Log.d("JSON-STRING", json_string);


             } catch (MalformedURLException e) {
                 e.printStackTrace();
             } catch (IOException e) {
                 e.printStackTrace();
             } catch (JSONException e) {
                 e.printStackTrace();
             }


             return null;
         }

         @Override
         public void onPreExecute() {
             super.onPreExecute();
             mProgress = new ProgressDialog(ctx);
             mProgress.setMessage("Please yay...");
             mProgress.show();
         }

         @Override
         protected void onPostExecute( ArrayList<Measurement> aVoid ) {

             mProgress.dismiss();
             yay2 = new CustomAdapterM(ctx,arrayList);
             ListView listView = (ListView) findViewById(R.id.lvMeasure);

             listView.setAdapter(yay2);
listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
        final Measurement item = (Measurement) yay2.getItem(position);
        final Dialog dialog = new Dialog(MeasurePage.this);
        dialog.setTitle("Choose what to change:");
        dialog.setContentView(R.layout.dialog_delete_mmeasure);
        final Button Yes2Delete = (Button) dialog.findViewById(R.id.btnYes);
        final Button No2Delete = (Button) dialog.findViewById(R.id.btnNO);
Yes2Delete.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View v ) {
        deleteReq= userid+item.getDate();
        bgTask2 bgTask2 = new bgTask2(MeasurePage.this);
        bgTask2.execute();
        bgTask bgTask = new bgTask(MeasurePage.this);
        bgTask.execute();
        dialog.dismiss();
    }
});
      No2Delete.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick( View v ) {
              dialog.cancel();
          }
      });
        dialog.create();
        dialog.show();
    }
});
             super.onPostExecute(aVoid);

         }


     }
//add data
      class bgTask4 extends AsyncTask<CustomAdapterM, Void, Void> {

    Context ctx;
    Activity activity;
    ProgressDialog mProgress;

    public bgTask4(Context ctx){
        this.ctx =ctx;
// activity = (Activity)ctx;
    }
    String json_string ="http://msgpelleg.com/Gym_measureADD.php";

    @Override
    protected void onPostExecute( Void aVoid ) {
        mProgress.dismiss();
        New_Arms="";
        New_Hips="";
        New_Thighs="";
        NEW_Waist="";
        New_Weight="";
    }

    @Override
    public void onPreExecute() {
        super.onPreExecute();
        mProgress = new ProgressDialog(ctx);
        mProgress.setMessage("Adding Data...");
        mProgress.show();
    }


    @Override
    protected Void doInBackground( CustomAdapterM... customAdapters ) {
        try {

            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date dateFromatted= new Date();


            URL url = new URL(json_string);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setDoOutput(true);
            OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
            String Data = URLEncoder.encode("date","UTF-8")+ "=" +URLEncoder.encode((dateFormat.format(dateFromatted)).toString());
            Data += "&"  + URLEncoder.encode("personid","UTF-8")+ "=" +URLEncoder.encode(userid.toString());
            Data += "&"  + URLEncoder.encode("waist","UTF-8")+ "=" +URLEncoder.encode(NEW_Waist.toString());
            Data += "&"  + URLEncoder.encode("arms","UTF-8")+ "=" +URLEncoder.encode(New_Arms.toString());
            Data += "&"  + URLEncoder.encode("thighs","UTF-8")+ "=" +URLEncoder.encode(New_Thighs.toString());
            Data += "&"  + URLEncoder.encode("hips","UTF-8")+ "=" +URLEncoder.encode(New_Hips.toString());
            Data += "&"  + URLEncoder.encode("weight","UTF-8")+ "=" +URLEncoder.encode(New_Weight.toString());
            Data += "&"  + URLEncoder.encode("measureid","UTF-8")+ "=" +URLEncoder.encode((userid+dateFormat.format(dateFromatted)).toString());
            we.write(Data);
            we.flush();


            BufferedReader reader = null;

            reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) !=null){sb.append(line+"\n");}



            httpURLConnection.disconnect();







        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }



}
//delete data
     class bgTask2 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask2(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_measureDelete.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
            deleteReq="";
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Deleting Data...");
            mProgress.show();
        }


        @Override
        protected Void doInBackground( CustomAdapter... customAdapters ) {
            try {



                URL url = new URL(json_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                String Data = URLEncoder.encode("personid","UTF-8")+ "=" +URLEncoder.encode(userid);
                Data += "&"  + URLEncoder.encode("measureid","UTF-8")+ "=" +URLEncoder.encode(deleteReq);


                we.write(Data);
                we.flush();

                BufferedReader reader = null;

                reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) !=null){sb.append(line+"\n");}


//                    we.writeBytes("set1="+toEnter.getSet1());
//                    we.writeBytes("set2="+toEnter.getSet2());
//                    we.writeBytes("set3="+toEnter.getSet3());
//                    we.writeBytes("weight="+toEnter.getWeight());
//                    we.writeBytes("exercise="+toEnter.getExercise());

                we.flush();
                //we.close();

                httpURLConnection.disconnect();







            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }



    }

}

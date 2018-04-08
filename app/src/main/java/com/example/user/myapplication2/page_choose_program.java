package com.example.user.myapplication2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

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
import java.util.ArrayList;

public class page_choose_program extends AppCompatActivity {
String newPlanName="";
String userid="";
String deleteReq="";
ArrayAdapter<String> yay2;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_choose_program);
        userid= getIntent().getStringExtra("userid");

        Button add = (Button) findViewById(R.id.btnBPA);
        Button cancel = (Button) findViewById(R.id.btnBPC);
         final EditText planame = (EditText) findViewById(R.id.etBPP);
        ListView listView = (ListView) findViewById(R.id.lvBPL);

        bgTask bgTask = new bgTask(page_choose_program.this);
        bgTask.execute();




        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                finish();
            }
        });


    }

    //receive workouts
    public class bgTask extends AsyncTask<Exercise, Void, ArrayList<String>> {

        Context ctx;
        Activity activity;
        ArrayList<String>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_BuildPrgoram_Receive.php";



        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Please Wait...");
            mProgress.show();
        }

        @Override
        protected void onPostExecute( ArrayList<String> aVoid ) {

            mProgress.dismiss();



            yay2= new ArrayAdapter<String>(ctx, R.layout.row_buildp, R.id.tvRBPH, arrayList);
            ListView listView = (ListView) findViewById(R.id.lvBPL);
            listView.setAdapter(yay2);
//clicking on an item list
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                    final String item = (String) yay2.getItem(position);
                    Intent intent = new Intent(page_choose_program.this,StartWork.class);

                    intent.putExtra("userid",userid);
                    intent.putExtra("workplan",item);
                    page_choose_program.this.startActivity(intent);

                }});
            super.onPostExecute(aVoid);

        }



        @Override
        protected ArrayList<String> doInBackground(Exercise... shifts) {
            try {
                URL url = new URL(json_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                String Data = URLEncoder.encode("id","UTF-8")+ "=" +URLEncoder.encode(userid);



                we.write(Data);
                we.flush();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line+"\n");

                }

                httpURLConnection.disconnect();
                String json_string = stringBuilder.toString().trim();
                JSONObject jsonObject = new JSONObject(json_string);
                JSONArray jsonArray = jsonObject.getJSONArray("server_response");
                int count = 0;
                while (count <jsonArray.length()){
                    JSONObject JO =jsonArray.getJSONObject(count);
                    count++;
                    String shift = new String(JO.getString("Program"));
                    arrayList.add(shift);
                }

                Log.d("JSON-STRING",json_string);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return arrayList;
        }

    }

}

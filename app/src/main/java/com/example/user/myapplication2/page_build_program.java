package com.example.user.myapplication2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

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
import java.util.List;

public class page_build_program extends AppCompatActivity {
String newPlanName="";
String userid="";
String deleteReq="";
    ArrayList<String>BarrayList = new ArrayList<>();

ArrayAdapter<String> yay2;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_build_program);
        userid= getIntent().getStringExtra("userid");

        Button add = (Button) findViewById(R.id.btnBPA);
        Button cancel = (Button) findViewById(R.id.btnBPC);
         final EditText planame = (EditText) findViewById(R.id.etBPP);
        ListView listView = (ListView) findViewById(R.id.lvBPL);

        bgTask bgTask = new bgTask(page_build_program.this);
        bgTask.execute();

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                if (!(planame.getText().toString().matches(""))) {
                newPlanName = planame.getText().toString();
                    for (String item: BarrayList) {
                        if(item.matches(newPlanName)){
                            newPlanName = "Error";
                            break;
                        }}

                    if (!(newPlanName.matches("Error")) ) {
                        bgTask4 bgTask4 = new bgTask4(page_build_program.this);
                        bgTask4.execute();
                        planame.setText("");

                    }else{
                        Toast errorToast = Toast.makeText(page_build_program.this, "ERROR! the name of the plan already exists! please choose another", Toast.LENGTH_SHORT);
                        errorToast.show();
                    }


                }
            }
        });


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
                    final Dialog dialog = new Dialog(page_build_program.this);
                    dialog.setTitle("Choose what to change:");
                    dialog.setContentView(R.layout.dialog_delete_program);


                    Button btnS = (Button) dialog.findViewById(R.id.btnopen);
                    Button btnD = (Button) dialog.findViewById(R.id.btndelete);


                    btnD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {
                            deleteReq= item;
                            bgTask2 bgTask2 = new bgTask2(page_build_program.this);
                            bgTask2.execute();
                            bgTask bgTask = new bgTask(page_build_program.this);
                            bgTask.execute();
                            dialog.dismiss();
                        }
                    });

                    btnS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {
                            dialog.dismiss();
                            Intent intent = new Intent(page_build_program.this,EditWorkPlan.class);

                            intent.putExtra("userid",userid);
                            intent.putExtra("workplan",item);
                            page_build_program.this.startActivity(intent);
finish();

                        }
                    });


                    dialog.create();
                    dialog.show();
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
BarrayList= arrayList;
            return arrayList;
        }

    }

    //delete workout step1
    public class bgTask2 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask2(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_BuildProgram_Delete.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
            bgTask2_2 bgTask2_2 = new bgTask2_2(page_build_program.this);
            bgTask2_2.execute();        }

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
                String Data = URLEncoder.encode("ppid","UTF-8")+ "=" +URLEncoder.encode((userid + deleteReq).replaceAll("\\s+", ""));


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

    //delete workout step2
    public class bgTask2_2 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask2_2(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_BuildProgram_DeleteStep2.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
            deleteReq="";
            bgTask bgTask = new bgTask(page_build_program.this);
            bgTask.execute();
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
                String Data = URLEncoder.encode("userid","UTF-8")+ "=" +URLEncoder.encode(userid);
                Data += "&"  + URLEncoder.encode("program","UTF-8")+ "=" +URLEncoder.encode(deleteReq);

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

    //add workout
    public class bgTask4 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask4(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_BuildProgram_Add.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
             newPlanName="";
            bgTask bgTask = new bgTask(page_build_program.this);
            bgTask.execute();
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Adding Data...");
            mProgress.show();

        }


        @Override
        protected Void doInBackground( CustomAdapter... customAdapters ) {
            if (newPlanName != "Error") {
                try {


                    URL url = new URL(json_string);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    String Data = URLEncoder.encode("personid", "UTF-8") + "=" + URLEncoder.encode(userid);
                    Data += "&" + URLEncoder.encode("ppid", "UTF-8") + "=" + URLEncoder.encode((userid + newPlanName).replaceAll("\\s+", ""));
                    Data += "&" + URLEncoder.encode("program", "UTF-8") + "=" + URLEncoder.encode(newPlanName);


                    we.write(Data);
                    we.flush();

                    BufferedReader reader = null;

                    reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line + "\n");
                    }


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
            return null;

        }

    }
}

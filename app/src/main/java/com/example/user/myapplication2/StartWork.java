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
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
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

public class StartWork extends AppCompatActivity {
    CustomAdapter yay2;
String userid="1";
String workplan="BASIC";
int TimerMin=0;
int TimerSec=0;
     TimerHandler handler=null;
    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_work);
        userid= getIntent().getStringExtra("userid");
        workplan= getIntent().getStringExtra("workplan");

        bgTask bgTask = new bgTask(StartWork.this);
        bgTask.execute();
        Button CancelNExit = (Button) findViewById(R.id.btnSWC);
        final Button Timer = (Button) findViewById(R.id.btnSWT);


        Timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                final Dialog dialog = new Dialog(StartWork.this);
                dialog.setTitle("Choose what to change:");
                dialog.setContentView(R.layout.timer);
                dialog.create();

               final Button btnstart = (Button) dialog.findViewById(R.id.btnStart);
                final Button btnclose = (Button) dialog.findViewById(R.id.btnClose);
              //  final Button btnreset = (Button) dialog.findViewById(R.id.btnReset);
                final TextView dotdot= (TextView) dialog.findViewById(R.id.dotdot);
                final NumberPicker min = (NumberPicker) dialog.findViewById(R.id.npMinute) ;
                final NumberPicker sec = (NumberPicker) dialog.findViewById(R.id.npSECOND) ;
min.setMaxValue(5);
min.setMinValue(0);
sec.setMinValue(0);
sec.setMaxValue(59);
min.setValue(TimerMin);
                sec.setValue(TimerSec);
                dialog.show();
                if (handler != null) {
                if (handler.timerRuning) {
                    btnstart.setText("Stop");

                   // btnreset.setVisibility(View.INVISIBLE);
                    min.setVisibility(View.INVISIBLE);
                    sec.setVisibility(View.INVISIBLE);
                    dotdot.setVisibility(View.INVISIBLE);

                }
                else {
                    btnstart.setText("Start");

                  //  btnreset.setVisibility(View.VISIBLE);
                    min.setVisibility(View.VISIBLE);
                    sec.setVisibility(View.VISIBLE);
                    dotdot.setVisibility(View.VISIBLE);

                }}

               btnstart.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick( View v ) {

                       if (handler == null) {
                           handler = new TimerHandler(btnstart, Timer);

                       }else{


                       }

                       if (btnstart.getText().toString().matches("Stop")) {
                           btnstart.setText("Start");

                           min.setVisibility(View.VISIBLE);
                           sec.setVisibility(View.VISIBLE);
                         //  btnreset.setVisibility(View.VISIBLE);
                           dotdot.setVisibility(View.VISIBLE);
                           handler.RunTimer();
                       }
                       else{

                           TimerMin=min.getValue();
                           TimerSec=sec.getValue();
                           if (min.getValue()  + sec.getValue() == 0){
                               Toast errorToast = Toast.makeText(StartWork.this, "cant run TIMER on 00:00", Toast.LENGTH_SHORT);
                               errorToast.show();
                           }
                           else {
                               handler.RunTimer(min, sec);
                               btnstart.setText("Stop");

                               min.setVisibility(View.INVISIBLE);
                               sec.setVisibility(View.INVISIBLE);
                               //  btnreset.setVisibility(View.INVISIBLE);
                               dotdot.setVisibility(View.INVISIBLE);

                               dialog.dismiss();
                           }
                       }
                   }
               });

//               btnreset.setOnClickListener(new View.OnClickListener() {
//                   @Override
//                   public void onClick( View v ) {
//                       if (!(handler.timerRuning))
//                       handler.resetTimer();
//                   }
//               });

               btnclose.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick( View v ) {

                       dialog.cancel();
                   }
               });


            }
        });

CancelNExit.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View v ) {
      finish();
    }
});


    }

//edit data
    public class bgTask2 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask2(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_EditUpdate.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Uploading Data...");
            mProgress.show();
        }


        @Override
        protected Void doInBackground( CustomAdapter... customAdapters ) {
            try {


                for (int i =0; i < yay2.getCount()-1 ; i++){
                    URL url = new URL(json_string);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    Exercise toEnter = yay2.getItem(i);
                    String Data = URLEncoder.encode("set1","UTF-8")+ "=" +URLEncoder.encode(String.valueOf(toEnter.getSet1()));
                    Data += "&"  + URLEncoder.encode("set2","UTF-8")+ "=" +URLEncoder.encode(String.valueOf(toEnter.getSet2()));
                    Data += "&"  + URLEncoder.encode("set3","UTF-8")+ "=" +URLEncoder.encode(String.valueOf(toEnter.getSet3()));
                    Data += "&"  + URLEncoder.encode("weight","UTF-8")+ "=" +URLEncoder.encode(String.valueOf(toEnter.getWeight()));
                    Data += "&"  + URLEncoder.encode("exercise","UTF-8")+ "=" +URLEncoder.encode(toEnter.getExercise());
                    Data += "&"  + URLEncoder.encode("toughness","UTF-8")+ "=" +URLEncoder.encode(toEnter.getToughness());



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



                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }



        }

//recieve data
    public class bgTask extends AsyncTask<Exercise, Void, ArrayList<Exercise>> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_PullData.php";



        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Please yay...");
            mProgress.show();
        }

        @Override
        protected void onPostExecute( ArrayList<Exercise> aVoid ) {

            mProgress.dismiss();

              //  arrayList.add(0,new Exercise("extra4header",1,1,1,1,1,"NULL","NULL","NuLL"));
if (arrayList.size() != 0) {
    Exercise yay = arrayList.get(0);
    String checkmuscle = "none";
    Exercise header;
    ArrayList<Exercise> FixedarrayList = new ArrayList<>();
    List<Integer> myList = new ArrayList<Integer>();
    List<String> myList2 = new ArrayList<String>();
//myList.add(0);
//myList2.add(checkmuscle);
    int counter=0;
    if(arrayList.size()==1)counter=0;
    for (Exercise item : arrayList) {
        yay = arrayList.get(counter);
        String isittherightmuscle = yay.getMusclegroup();
        if (!(isittherightmuscle.equals(checkmuscle))) {

            isittherightmuscle = yay.getMusclegroup();
            myList2.add(isittherightmuscle);
            // header = new Exercise("0", 0, 0, 0, 0, 0, isittherightmuscle, String.valueOf(poscount), "0");
            //  FixedarrayList.add(counter, header);
            myList.add(counter);
            checkmuscle = isittherightmuscle;
            counter++;
        } else { counter++;}
    }
    int newcount=0;
    for (int item:myList) {


        header = new Exercise("header", 0, 0, 0, 0, 0, myList2.get(newcount), String.valueOf(newcount), "0");
        arrayList.add(item+newcount, header);
        newcount++;
    }


    yay2 = new CustomAdapter(ctx, arrayList);
    ListView listView = (ListView) findViewById(R.id.lvSW);

    listView.setAdapter(yay2);

    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
            final Exercise item = (Exercise) yay2.getItem(position);
            if (!(item.getExercise() == "header")) {
                final Dialog dialog = new Dialog(StartWork.this);
                dialog.setTitle("Choose what to change:");
                dialog.setContentView(R.layout.exerciseedit);
                final NumberPicker np = (NumberPicker) dialog.findViewById(R.id.npEES1);
                final NumberPicker np2 = (NumberPicker) dialog.findViewById(R.id.npEES2);
                final NumberPicker np3 = (NumberPicker) dialog.findViewById(R.id.npEES3);
                final NumberPicker npw = (NumberPicker) dialog.findViewById(R.id.npEEW);
                final NumberPicker npt = (NumberPicker) dialog.findViewById(R.id.npEET);

                Button save = (Button) dialog.findViewById(R.id.btnEES);
                Button cancel = (Button) dialog.findViewById(R.id.btnEEC);
                np.setMaxValue(200);
                np.setMinValue(0);
                npt.setMaxValue(2);
                npt.setMinValue(0);
                String[] toughnessSET = {"Easy", "Mediocre", "Hard"};
                npt.setDisplayedValues(toughnessSET);

                np2.setMaxValue(200);
                np2.setMinValue(0);
                np3.setMaxValue(200);
                np3.setMinValue(0);
                npw.setMaxValue(200);
                npw.setMinValue(0);
                np.setValue(item.getSet1());
                np2.setValue(item.getSet2());
                np3.setValue(item.getSet3());
                npw.setValue(item.getWeight());
                npt.setValue(ToughnessDECONVERTER(item.getToughness()));
                dialog.create();
                dialog.show();
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {
                        dialog.cancel();
                    }
                });
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {
                        item.setSet1((np.getValue()));
                        item.setSet2((np2.getValue()));
                        item.setSet3((np3.getValue()));
                        item.setWeight((npw.getValue()));
                        item.setToughness(ToughnessCONVERTER(npt.getValue()));
                        bgTask2 bgTask2 = new bgTask2(StartWork.this);
                        bgTask2.execute();
                        yay2.notifyDataSetChanged();


                        dialog.dismiss();
                    }
                });
                //arrayList.set(position-1,item);
            }

        }
    });
}
            super.onPostExecute(aVoid);

        }



        @Override
        protected ArrayList<Exercise> doInBackground(Exercise... shifts) {
            try {
                URL url = new URL(json_string);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                String Data = URLEncoder.encode("id","UTF-8")+ "=" +URLEncoder.encode(userid);
                Data += "&"  + URLEncoder.encode("wp","UTF-8")+ "=" +URLEncoder.encode(workplan);

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
                    Exercise shift = new Exercise(JO.getString("Exercise"), JO.getInt("SET1"),JO.getInt("SET2"),JO.getInt("SET3"),JO.getInt("Weight"),JO.getInt("Target"),JO.getString("MuscleGroup"),JO.getString("POS"),JO.getString("Toughness"));
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

    public String ToughnessCONVERTER(int picker){
        String pick="null";
        switch (picker)
        {
            case 0:
                pick="Easy";
                break;
            case 1:
                pick="Mediocre";
                break;
            case 2:
                pick="Hard";
                break;


        }
        return pick;
    }
    public int ToughnessDECONVERTER(String picker){
        int pick=0;
        switch (picker)
        {
            case "Easy":
                pick=0;
                break;
            case "E":
                pick=0;
                break;
            case "Mediocre":
                pick=1;
                break;
            case "M":
                pick=1;
                break;
            case "Hard":
                pick=2;
                break;
            case "H":
                pick=2;
                break;


        }
        return pick;
    }

}

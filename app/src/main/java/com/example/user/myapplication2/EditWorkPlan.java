package com.example.user.myapplication2;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
import java.util.Iterator;
import java.util.List;

public class EditWorkPlan extends AppCompatActivity {
    CustomAdapterE yay2;
    ArrayList<Exercise>arrayList= new ArrayList<>();
    ArrayList<Exercise>BarrayList= new ArrayList<>();
    String deleteReq="";
    String userid="";
    String NEWtarget="";
    String NewWeight="";
    String NewMuscle="";
    String NewExercise="";
    String exercise2change="";
    String musclegroup="";
    String pos="0";
    String workplan="";
    String toughness="H";
    String[] MuscleGroup =  {"Biceps","Abdominals","Obliques","Chest","Shoulders","Traps","Triceps","Lats","Erector Spinae","Glutes","Hamstrings","Quadriceps","Forearms","Calves","Abs","Neck"};
    String[] toughnessSET={"Easy","Mediocre","Hard"};

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_work_plan);
        userid= getIntent().getStringExtra("userid");
        workplan= getIntent().getStringExtra("workplan");
        bgTask bgTask = new bgTask(EditWorkPlan.this);
        bgTask.execute();
        Button btnC = (Button) findViewById(R.id.btnEWPC);
btnC.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick( View v ) {
        finish();
    }
});
        Button btnAdd = (Button) findViewById(R.id.btnEWPNE);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v ) {
                final Dialog dialog = new Dialog(EditWorkPlan.this);
                dialog.setTitle("Choose what to Add:");
                dialog.setContentView(R.layout.dialog_epedit);
                final NumberPicker npt = (NumberPicker) dialog.findViewById(R.id.npDEWPT);
                final NumberPicker npw = (NumberPicker) dialog.findViewById(R.id.npDEWPW);
                npt.setMinValue(0);
                npt.setMaxValue(200);
                npw.setMinValue(0);
                npw.setMaxValue(200);
                final NumberPicker npm = (NumberPicker) dialog.findViewById(R.id.npDEWPM);
                npm.setMinValue(0);
                npm.setMaxValue(15);
                npm.setDisplayedValues(MuscleGroup);

                final EditText ete = (EditText) dialog.findViewById(R.id.etDEWE);
                Button btnS = (Button) dialog.findViewById(R.id.btnDEWPS);
                Button btnC = (Button) dialog.findViewById(R.id.btnDEWPC);
                Button btnD = (Button) dialog.findViewById(R.id.btnDEWPD);
                btnD.setVisibility(View.INVISIBLE);

                btnD.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {
                        deleteReq= ete.getText().toString();
                        bgTask2 bgTask2 = new bgTask2(EditWorkPlan.this);
                        bgTask2.execute();

                        dialog.dismiss();
                    }
                });

                btnS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick( View v ) {

                        NewExercise=ete.getText().toString();
                        NEWtarget=String.valueOf(npt.getValue());
                        NewWeight=String.valueOf(npw.getValue());
                        musclegroup=String.valueOf(musclegroupCONVERTER(npm.getValue()));

                        for (Exercise item: BarrayList) {
                            if(item.getExercise().toString().matches(NewExercise)){
                                NewExercise = "Error";
                                break;
                            }}

                            if (!(NewExercise.matches("Error")) ) {
                                bgTask4 bgTask4 = new bgTask4(EditWorkPlan.this);
                                bgTask4.execute();

                            }else{
                                Toast errorToast = Toast.makeText(EditWorkPlan.this, "ERROR! the name of the exercise already exists! please choose another", Toast.LENGTH_SHORT);
                                errorToast.show();
                            }
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

    }
//receive exercises
    public class bgTask extends AsyncTask<Exercise, Void, ArrayList<Exercise>> {

        Context ctx;
        Activity activity;
        ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_PlanReceive.php";



        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Please Wait...");
            mProgress.show();
        }

        @Override
        protected void onPostExecute( ArrayList<Exercise> aVoid ) {

            mProgress.dismiss();
    TextView empty = (TextView) findViewById(R.id.emptymsg);

if (arrayList.size() != 0){
    empty.setVisibility(View.INVISIBLE);
            Exercise yay = arrayList.get(0);
            String checkmuscle = "none";
            Exercise header;
            ArrayList<Exercise> FixedarrayList = new ArrayList<>();
            List<Integer> myList = new ArrayList<Integer>();
            List<String> myList2 = new ArrayList<String>();

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

            yay2 = new CustomAdapterE(ctx,arrayList);
            ListView listView = (ListView) findViewById(R.id.lvEWP);
            listView.setAdapter(yay2);
//clicking on an item list
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick( AdapterView<?> parent, View view, int position, long id ) {
                    final Exercise item = (Exercise) yay2.getItem(position );
                    final Dialog dialog = new Dialog(EditWorkPlan.this);
                    dialog.setTitle("Choose what to change:");
                    dialog.setContentView(R.layout.dialog_epedit);
                    final NumberPicker npt = (NumberPicker) dialog.findViewById(R.id.npDEWPT);
                    final NumberPicker npw = (NumberPicker) dialog.findViewById(R.id.npDEWPW);
                    final NumberPicker npm = (NumberPicker) dialog.findViewById(R.id.npDEWPM);
                    npt.setMinValue(0);
                    npt.setMaxValue(200);
                    npw.setMinValue(0);
                    npw.setMaxValue(200);
                    npm.setMinValue(0);
                    npm.setMaxValue(15);
                    npm.setDisplayedValues(MuscleGroup);
                    npm.setValue(musclegroupDECONVERTER(item.getMusclegroup()));
                    final EditText ete = (EditText) dialog.findViewById(R.id.etDEWE);
                    ete.setText(item.getExercise().toString());
                    exercise2change=item.getExercise().toString();

                    Button btnS = (Button) dialog.findViewById(R.id.btnDEWPS);
                    Button btnC = (Button) dialog.findViewById(R.id.btnDEWPC);
                    Button btnD = (Button) dialog.findViewById(R.id.btnDEWPD);

                    btnC.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {
                            exercise2change="";
                            dialog.cancel();
                        }
                    });

                    btnD.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {
                            deleteReq= ete.getText().toString();
                            bgTask2 bgTask2 = new bgTask2(EditWorkPlan.this);
                            bgTask2.execute();

                            dialog.dismiss();
                        }
                    });

                    btnS.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick( View v ) {

                            NewExercise=ete.getText().toString();
                            NEWtarget=String.valueOf(npt.getValue());
                            NewWeight=String.valueOf(npw.getValue());
                            NewMuscle = String.valueOf(musclegroupCONVERTER(npm.getValue()));
                            for (Exercise target: BarrayList) {
                                if(target.getExercise().toString().equals(NewExercise) && !(target.equals(item))){
                                    break;
                                }}

                            if (!(NewExercise.equals("Error"))) {
                                bgTask3 bgTask3 = new bgTask3(EditWorkPlan.this);
                                bgTask3.execute();
                            }else{

                                Toast errorToast = Toast.makeText(EditWorkPlan.this, "ERROR! the name of the exercise already exists! please choose another", Toast.LENGTH_SHORT);
                                errorToast.show();


                            }
                            dialog.dismiss();
                        }
                    });


                    dialog.create();
                    dialog.show();
                }});
            }else {
    empty.setVisibility(View.VISIBLE);
    yay2 = new CustomAdapterE(ctx,arrayList);
    ListView listView = (ListView) findViewById(R.id.lvEWP);
    listView.setAdapter(yay2);

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
                    Exercise shift = new Exercise(JO.getString("Exercise"), 1,1,1,JO.getInt("Weight"), JO.getInt("Target"),JO.getString("MuscleGroup"),JO.getString("POS"),"H");
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
BarrayList = arrayList;
            return arrayList;
        }

    }
//delete exercises
    public class bgTask2 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        ProgressDialog mProgress;

        public bgTask2(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_PlanDelete.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            bgTask bgTask = new bgTask(EditWorkPlan.this);
            bgTask.execute();
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
                    Data += "&"  + URLEncoder.encode("exercise","UTF-8")+ "=" +URLEncoder.encode(deleteReq);


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
//edit exercises
    public class bgTask3 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
    // ArrayList<Exercise>arrayList = new ArrayList<>();

    ProgressDialog mProgress;

        public bgTask3(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_PlanEditor.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
            deleteReq="";
            NewExercise="";
            exercise2change="";
            NEWtarget="";
            NewMuscle="";
            NewWeight="";
            bgTask bgTask = new bgTask(EditWorkPlan.this);
            bgTask.execute();
        }

        @Override
        public void onPreExecute() {
            super.onPreExecute();
            mProgress = new ProgressDialog(ctx);
            mProgress.setMessage("Editing Data...");
            mProgress.show();

        }


        @Override
        protected Void doInBackground( CustomAdapter... customAdapters ) {
            if (!(NewExercise.matches("Error"))) {
                try {


                    URL url = new URL(json_string);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setDoOutput(true);
                    OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                    String Data = URLEncoder.encode("personid", "UTF-8") + "=" + URLEncoder.encode(userid);
                    Data += "&" + URLEncoder.encode("exercise", "UTF-8") + "=" + URLEncoder.encode(exercise2change);
                    Data += "&" + URLEncoder.encode("exerciseNEW", "UTF-8") + "=" + URLEncoder.encode(NewExercise);
                    Data += "&" + URLEncoder.encode("target", "UTF-8") + "=" + URLEncoder.encode(NEWtarget);
                    Data += "&" + URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(NewWeight);
                    Data += "&" + URLEncoder.encode("muscle", "UTF-8") + "=" + URLEncoder.encode(NewMuscle);
                    Data += "&" + URLEncoder.encode("repid", "UTF-8") + "=" + URLEncoder.encode((userid + NewExercise + workplan).replaceAll("\\s+", ""));
                    Data += "&" + URLEncoder.encode("pos", "UTF-8") + "=" + URLEncoder.encode(pos);

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
            }else {
mProgress.dismiss();
                mProgress = new ProgressDialog(ctx);
                mProgress.setMessage("Error! Input already exists");
                mProgress.show();


            }
            return null;
        }



    }
 //add exercise
    public class bgTask4 extends AsyncTask<CustomAdapter, Void, Void> {

        Context ctx;
        Activity activity;
       // ArrayList<Exercise>arrayList = new ArrayList<>();
        ProgressDialog mProgress;

        public bgTask4(Context ctx){
            this.ctx =ctx;
// activity = (Activity)ctx;
        }
        String json_string ="http://msgpelleg.com/Gym_PlanAdd.php";

        @Override
        protected void onPostExecute( Void aVoid ) {
            mProgress.dismiss();
            deleteReq="";
            NewExercise="";
            exercise2change="";
            NEWtarget="";
            NewWeight="";
             musclegroup="";
            bgTask bgTask = new bgTask(EditWorkPlan.this);
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
           if (!(NewExercise.matches("Error"))) {
               try {
                   URL url = new URL(json_string);
                   HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                   httpURLConnection.setDoOutput(true);
                   OutputStreamWriter we = new OutputStreamWriter(httpURLConnection.getOutputStream());
                   String Data = URLEncoder.encode("personid", "UTF-8") + "=" + URLEncoder.encode(userid);
                   Data += "&" + URLEncoder.encode("exerciseNEW", "UTF-8") + "=" + URLEncoder.encode(NewExercise);
                   Data += "&" + URLEncoder.encode("target", "UTF-8") + "=" + URLEncoder.encode(NEWtarget);
                   Data += "&" + URLEncoder.encode("weight", "UTF-8") + "=" + URLEncoder.encode(NewWeight);
                   Data += "&" + URLEncoder.encode("repid", "UTF-8") + "=" + URLEncoder.encode((userid + NewExercise + workplan).replaceAll("\\s+", ""));
                   Data += "&" + URLEncoder.encode("musclegroup", "UTF-8") + "=" + URLEncoder.encode(musclegroup);
                   Data += "&" + URLEncoder.encode("pos", "UTF-8") + "=" + URLEncoder.encode(pos);
                   Data += "&" + URLEncoder.encode("workplan", "UTF-8") + "=" + URLEncoder.encode(workplan);
                   Data += "&" + URLEncoder.encode("toughness", "UTF-8") + "=" + URLEncoder.encode(toughness);


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
           }else{
               mProgress.dismiss();
               mProgress = new ProgressDialog(ctx);
                   mProgress.setMessage("Error! Input already exists");
               mProgress.show();

           }
            return null;
        }



    }

 public String musclegroupCONVERTER(int picker){
        String pick="null";
        switch (picker)
        {
            case 0:
                pick="Biceps";
                break;
            case 1:
                pick="Abdominals";
                break;
            case 2:
                pick="Obliques";
                break;
            case 3:
                pick="Chest";
                break;
            case 4:
                pick="Shoulders";
                break;
            case 5:
                pick="Traps";
                break;
            case 6:
                pick="Triceps";
                break;
            case 7:
                pick="Lats";
                break;
            case 8:
                pick="Erector Spinae";
                break;
            case 9:
                pick="Glutes";
                break;
            case 10:
                pick="Hamstrings";
                break;
            case 11:
                pick="Quadriceps";
                break;
            case 12:
                pick="Forearms";
                break;
            case 13:
                pick="Calves";
                break;
            case 14:
                pick="Abes";
                break;
            case 15:
                pick="Neck";
                break;

        }
  return pick;
 }
    public int musclegroupDECONVERTER(String muscle){
        int pick= 0;
        switch (muscle)
        {
            case "Biceps":
                pick=0;
                break;
            case "Abdominals":
                pick=1;
                break;
            case "Obliques":
                pick=2;
                break;
            case "Chest":
                pick=3;
                break;
            case "Shoulders":
                pick=4;
                break;
            case "Traps":
                pick=5;
                break;
            case "Triceps":
                pick=6;
                break;
            case "Lats":
                pick=7;
                break;
            case "Erector Spinae":
                pick=8;
                break;
            case "Glutes":
                pick=9;
                break;
            case "Hamstrings":
                pick=10;
                break;
            case "Quadriceps":
                pick=11;
                break;
            case "Forearms":
                pick=12;
                break;
            case "Calves":
                pick=13;
                break;
            case "Abes":
                pick=14;
                break;
            case "Neck":
                pick=15;
                break;

        }
        return pick;
    }

}

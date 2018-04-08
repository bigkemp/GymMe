package com.example.user.myapplication2;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
   private EditText user;
   private EditText pass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         user = (EditText) findViewById(R.id.etUsername);
        pass = (EditText) findViewById(R.id.etPassword);
        Button btn = (Button) findViewById(R.id.bButton);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] cradentials = {user.toString(),pass.toString()};
                bgTask bgTask = new bgTask(cradentials);
            }
        });


    }


    public class bgTask extends AsyncTask<String[],Void,Void>{
        Context ctx;
        ProgressDialog mProgress = new ProgressDialog(ctx);
        String json_string = "http://msgpelleg.com/Login_Gym.php";

        public bgTask(String[] cradentials) {

            json_string = json_string + "?user="+cradentials[1] + "&pass="+cradentials[2];
        }

        @Override
        protected void onPreExecute() {
            mProgress.setMessage("Logging In...");
            mProgress.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            mProgress.dismiss();
        }

        @Override
        protected Void doInBackground(String[]... strings) {
            try {

                URL url = new URL(json_string);

                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while   ((line = bufferedReader.readLine()) != null){stringBuilder.append(line+"\n");}

                httpURLConnection.disconnect();
                String json_string = stringBuilder.toString().trim();
                JSONObject jsonObject = new JSONObject(json_string);
                boolean success = jsonObject.getBoolean("syccess");
                if (success){
Intent intent = new Intent(MainActivity.this,MemberPage.class);
MainActivity.this.startActivity(intent);
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setMessage("Failed").setNegativeButton("retry",null).create().show();
                }



            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return null;
        }
    }
}

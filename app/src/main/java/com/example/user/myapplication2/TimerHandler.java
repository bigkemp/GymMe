package com.example.user.myapplication2;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by pelleg on 2/8/2018.
 */

public class TimerHandler {
    private static  long START_TIME_IN_MILLIS= 60000;
    public Button start;
    public Button timer;
    public Button close;
    public Button reset;
private TextView clock;
private CountDownTimer counterDownTimer;
public boolean timerRuning=false;
private long timeLeftInMillis= START_TIME_IN_MILLIS;

//    protected void ) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.timer);
//         start = (Button) findViewById(R.id.btnStart);
//         close = (Button) findViewById(R.id.btnClose);
//         reset = (Button)  findViewById(R.id.btnReset);
//clock = (TextView) findViewById(R.id.clock);
//updateCountDownText();
//    }

    public TimerHandler( Button startD, Button timerD) {
        start= startD;
       // reset= resetD;
        timer=timerD;
        updateCountDownText();
    }

    public void resetTimer(){
     timeLeftInMillis = START_TIME_IN_MILLIS;
     updateCountDownText();
    // reset.setVisibility(View.INVISIBLE);
     start.setVisibility(View.VISIBLE);
    }

    public void RunTimer(NumberPicker min, NumberPicker sec){
int Min2Sec=(min.getValue())*60;
START_TIME_IN_MILLIS = (Min2Sec+sec.getValue())*1000;
        timeLeftInMillis= START_TIME_IN_MILLIS;
            startTimer();



        }

    public void RunTimer(){
            pauseTimer();
    }

    public void startTimer(){

            counterDownTimer = new CountDownTimer(timeLeftInMillis,1000) {
                @Override
                public void onTick( long millisUntilFinished ) {
                    timeLeftInMillis  =millisUntilFinished;
                    updateCountDownText();
                }

                @Override
                public void onFinish() {
timerRuning=false;
start.setText("Start");
timer.setText("Timer");
start.setVisibility(View.INVISIBLE);
//reset.setVisibility(View.VISIBLE);
                }
            }.start();

            timerRuning=true;
            start.setText("Pause");
         //   reset.setVisibility(View.INVISIBLE);

    }

    public void pauseTimer(){


        counterDownTimer.cancel();
        timerRuning=false;
        timer.setText("TIMER");
        start.setText("Start");

      //  reset.setVisibility(View.VISIBLE);
     }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis/1000)/60;
        int seconds = (int) (timeLeftInMillis/1000)%60;
String timeleftformatted= String.format(Locale.getDefault(),"%02d:%02d",minutes,seconds);
timer.setText(timeleftformatted);


    }
}

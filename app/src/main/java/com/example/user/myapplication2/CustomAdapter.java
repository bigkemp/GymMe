package com.example.user.myapplication2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pelleg on 1/31/2018.
 */

public class CustomAdapter extends ArrayAdapter<Exercise> {
    public CustomAdapter( Context context, ArrayList<Exercise> resource ) {
        super(context,R.layout.xrow ,resource);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View Custom;
       // View Custom2 = Inflater.inflate(R.layout.xheader,parent,false);
        Exercise singleItem = getItem(position);
        if (singleItem.getExercise()  == "header"){

            Custom = Inflater.inflate(R.layout.editplan_header, parent,false);
            TextView header3 = (TextView) Custom.findViewById(R.id.tvEPHM);
            header3.setText(singleItem.getMusclegroup());
        }else if(singleItem.getExercise() == "extra4header") {
            Custom = Inflater.inflate(R.layout.xheader,parent,false);
        }
        else   {
             Custom = Inflater.inflate(R.layout.xrow, parent,false);

            TextView exercise = (TextView) Custom.findViewById(R.id.tvExercise);
            TextView set1 = (TextView) Custom.findViewById(R.id.rowSET1);
            TextView set2 = (TextView) Custom.findViewById(R.id.rowSET2);
            TextView set3 = (TextView) Custom.findViewById(R.id.rowSET3);
            TextView weight = (TextView) Custom.findViewById(R.id.rowWeight);
            TextView toughness = (TextView) Custom.findViewById(R.id.EorMorH);


            exercise.setText(singleItem.getExercise());
            set1.setText(String.valueOf(singleItem.getSet1()));
            set2.setText(String.valueOf(singleItem.getSet2()));
            set3.setText(String.valueOf(singleItem.getSet3()));
            weight.setText(String.valueOf(singleItem.getWeight()));
            toughness.setText(singleItem.getToughness());

        }

      //  Exercise singleItem = getItem(position-1);


        return Custom;
    }

    @Override
    public int getPosition( @Nullable Exercise item ) {
        return super.getPosition(item);
    }
    public void yayziz (){}

    @Nullable
    @Override
    public Exercise getItem( int position ) {
        return super.getItem(position);
    }
}

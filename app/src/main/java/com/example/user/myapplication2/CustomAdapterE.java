package com.example.user.myapplication2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by pelleg on 1/31/2018.
 */

public class CustomAdapterE extends ArrayAdapter<Exercise> {
    public CustomAdapterE( Context context, ArrayList<Exercise> resource ) {
        super(context,R.layout.xrow ,resource);
    }
String MG="header";
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        Exercise singleItem = getItem(position);
        View Custom = Inflater.inflate(R.layout.editplan_row, parent,false);

       if (singleItem.getExercise() == MG){
            Custom = Inflater.inflate(R.layout.editplan_header, parent,false);
           TextView header3 = (TextView) Custom.findViewById(R.id.tvEPHM);
           header3.setText(singleItem.getMusclegroup());

       }else {
           TextView exercise = (TextView) Custom.findViewById(R.id.tvEPRE);
           TextView target = (TextView) Custom.findViewById(R.id.tvEPRT);
           TextView weight = (TextView) Custom.findViewById(R.id.tvEPRW);


           exercise.setText(singleItem.getExercise());
           target.setText(String.valueOf(singleItem.getTarget()));
           weight.setText(String.valueOf(singleItem.getWeight()));
       }
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

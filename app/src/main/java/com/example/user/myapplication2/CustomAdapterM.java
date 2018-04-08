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

public class CustomAdapterM extends ArrayAdapter<Measurement> {
    public CustomAdapterM( Context context, ArrayList<Measurement> resource ) {
        super(context,R.layout.measure_row ,resource);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent ) {
        LayoutInflater Inflater = LayoutInflater.from(getContext());
        View Custom = Inflater.inflate(R.layout.measure_row, parent,false);

        Measurement singleItem = getItem(position);

        TextView hips = (TextView) Custom.findViewById(R.id.tvMH);
        TextView thighs = (TextView) Custom.findViewById(R.id.tvMT);
        TextView arms = (TextView) Custom.findViewById(R.id.tvMA);
        TextView weight = (TextView) Custom.findViewById(R.id.tvMWE);
        TextView waist = (TextView) Custom.findViewById(R.id.tvMWA);
        TextView date = (TextView) Custom.findViewById(R.id.tvMWD);


        hips.setText(String.valueOf(singleItem.getHips()));
        thighs.setText(String.valueOf(singleItem.getThighs()));
        arms.setText(String.valueOf(singleItem.getArms()));
        weight.setText(String.valueOf(singleItem.getWeight()));
        waist.setText(String.valueOf(singleItem.getWaist()));
        date.setText((singleItem.getDate()));

        return Custom;
    }

    @Override
    public int getPosition( @Nullable Measurement item ) {
        return super.getPosition(item);
    }

    @Nullable
    @Override
    public Measurement getItem( int position ) {
        return super.getItem(position);
    }
}

package com.example.user.myapplication2;
import android.graphics.Color;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;


import java.util.Calendar;
import java.util.Date;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class JournalPage extends AppCompatActivity {

    private int mNumLabels;
    public JournalPage( int numLabels) {
        mNumLabels = numLabels;
    }
    public JournalPage() {
        mNumLabels = 4;
    }
    private LineGraphSeries<DataPoint> mSeries;

    @Override
    protected void onCreate( Bundle savedInstanceState ) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal_page);


    }






        public void initGraph(GraphView graph) {
            // generate Dates
            Calendar calendar = Calendar.getInstance();
            Date d1 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d2 = calendar.getTime();
            calendar.add(Calendar.DATE, 1);
            Date d3 = calendar.getTime();

            // you can directly pass Date objects to DataPoint-Constructor
            // this will convert the Date to double via Date#getTime()
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {
                    new DataPoint(d1, 1),
                    new DataPoint(d2, 5),
                    new DataPoint(d3, 3)
            });
            graph.addSeries(series);

            // set date label formatter
            graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(graph.getContext()));
            graph.getGridLabelRenderer().setNumHorizontalLabels(mNumLabels);

            // set manual x bounds to have nice steps
            graph.getViewport().setMinX(d1.getTime());
            graph.getViewport().setMaxX(d3.getTime());
            graph.getViewport().setXAxisBoundsManual(true);

            // as we use dates as labels, the human rounding to nice readable numbers
            // is not nessecary
            graph.getGridLabelRenderer().setHumanRounding(false);
        }




}

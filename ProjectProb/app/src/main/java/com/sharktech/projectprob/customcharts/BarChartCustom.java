package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;

import java.util.ArrayList;
import java.util.List;

public class BarChartCustom extends BarChart{

    protected BarChartCustom(Context context) {
        super(context);
    }

    protected BarChart build(DataAnalyse analyse) {
        setLayoutParams(new ViewGroup.LayoutParams(ChartFactory.WIDTH, ChartFactory.HEIGHT));

        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < analyse.size(); i++) {

            BarEntry entry = new BarEntry(i , i, "Data" );
            entry.setVals(new float[]{analyse.getFrequency(i).floatValue()});
            //entry.setData("T : " + analyse.getData(i).getTitle());
            entries.add(entry);
        }
        BarDataSet dataSet = new BarDataSet(entries, "Some Example");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        dataSet.setColor(getResources().getColor(R.color.color_primary_light));
        dataSet.setLabel("Some Bar Label");

        setData(new BarData(dataSet));
        invalidate();
        return this;
    }
}

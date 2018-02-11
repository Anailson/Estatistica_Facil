package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyseValue;
import com.sharktech.projectprob.analyse.SortedGenericList;

import java.util.ArrayList;
import java.util.List;

public class DispersionChart extends BubbleChart{

    protected DispersionChart(Context context){
        super(context);
    }

    protected BubbleChart build(SortedGenericList<DataAnalyseValue> values){
        setLayoutParams(new ViewGroup.LayoutParams(ChartFactory.WIDTH, ChartFactory.HEIGHT));

        List<BubbleEntry> entries = new ArrayList<>();

        for (int i = 0; i < values.asList().size(); i++) {
            DataAnalyseValue value = values.get(i);
            BubbleEntry entry = new BubbleEntry(i , i, 10);
            entry.setData(value.getTitle());
            //entry.setVals(new float[]{value.getFrequency()});
            entry.setData("T : " + value.getTitle());
            entries.add(entry);
        }
        BubbleDataSet dataSet = new BubbleDataSet(entries, "Some Example");

        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        dataSet.setColor(getResources().getColor(R.color.color_primary_light));
        dataSet.setColors(ChartFactory.COLORS);
        dataSet.setLabel("Some Bar Label");

        setData(new BubbleData(dataSet));
        invalidate();

        return this;
    }
}

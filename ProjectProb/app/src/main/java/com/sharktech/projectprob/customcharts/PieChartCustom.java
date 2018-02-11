package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyseValue;
import com.sharktech.projectprob.analyse.SortedGenericList;

import java.util.ArrayList;
import java.util.List;

public class PieChartCustom extends PieChart {

    protected PieChartCustom(Context context) {
        super(context);
    }

    protected PieChart build(SortedGenericList<DataAnalyseValue> values){
        setTransparentCircleRadius(0);
        setLayoutParams(new ViewGroup.LayoutParams(ChartFactory.WIDTH, ChartFactory.HEIGHT));
        setHoleRadius(0);
        setEntryLabelColor(Color.BLACK);

        List<PieEntry> entries = new ArrayList<>();

        for(DataAnalyseValue value : values.asList()){
            entries.add(new PieEntry(value.getFrequency(), "Valor: " + value.getTitle()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Some Example");
        dataSet.setValueTextColor(Color.BLACK);

        dataSet.setColor(getResources().getColor(R.color.color_primary_light));
        dataSet.setValueTextSize(15);
        dataSet.setSliceSpace(2);
        dataSet.setColors(ChartFactory.COLORS);

        setData(new PieData(dataSet));
        invalidate();
        return this;
    }
}

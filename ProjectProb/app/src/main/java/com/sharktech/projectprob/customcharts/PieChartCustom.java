package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.Value;
import com.sharktech.projectprob.customtable.Variable;

import java.util.ArrayList;
import java.util.List;

public class PieChartCustom extends PieChart {

    private Context context;

    protected PieChartCustom(Context context) {
        super(context);
        this.context = context;
    }

    protected PieChart build(Variable variable){
        setTransparentCircleRadius(0);
        setLayoutParams(new ViewGroup.LayoutParams(ChartFactory.WIDTH, ChartFactory.HEIGHT));
        setHoleRadius(0);
        setEntryLabelColor(Color.BLACK);

        List<PieEntry> entries = new ArrayList<>();

        for(Object o: variable.getElements()){
            Value val = (Value) o;
            entries.add(new PieEntry(val.getCount(), val.getData().toString()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Some Example");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColor(context.getResources().getColor(R.color.color_primary_light));
        dataSet.setValueTextSize(15);
        dataSet.setSliceSpace(2);
        dataSet.setColors(ChartFactory.COLORS);

        setData(new PieData(dataSet));
        invalidate();
        return this;
    }
}

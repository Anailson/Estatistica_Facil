package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.Cell;
import com.sharktech.projectprob.customtable.Value;
import com.sharktech.projectprob.customtable.Variable;

import java.util.ArrayList;
import java.util.List;

public class BarChartCustom extends com.github.mikephil.charting.charts.BarChart{

    private Context context;

    protected BarChartCustom(Context context) {
        super(context);
        this.context = context;
    }

    protected BarChart build(Variable.IVariable variable) {
        setLayoutParams(new ViewGroup.LayoutParams(ChartFactory.WIDTH, ChartFactory.HEIGHT));

        List<BarEntry> entries = new ArrayList<>();

        for (int i = 0; i < variable.getElements().size(); i++) {

            Value<Cell.ICell> val = new Value<>(variable.getElements().get(i));

            BarEntry entry = new BarEntry(i, i);
            entry.setVals(new float[]{val.getCount()});
            entry.setData(val);
            entries.add(entry);
        }
        BarDataSet dataSet = new BarDataSet(entries, "Some Example");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        dataSet.setColor(context.getResources().getColor(R.color.color_primary_light));
        dataSet.setLabel("Some Bar Label");

        setData(new BarData(dataSet));
        invalidate();
        return this;
    }
}

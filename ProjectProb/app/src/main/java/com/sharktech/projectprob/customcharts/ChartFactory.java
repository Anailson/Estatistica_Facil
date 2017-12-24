package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.Value;
import com.sharktech.projectprob.customtable.Variable;

import java.util.ArrayList;
import java.util.List;

public class ChartFactory {

    private static final int WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
    private static final int HEIGHT = 800;
    private static final int[] COLORS = new int[]{Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.YELLOW,
                    Color.DKGRAY, Color.RED, Color.MAGENTA, Color.LTGRAY, Color.WHITE};

    public static final int PIE = 1;
    public static final int BAR = 2;
    public static final int DISPERSION = 3;
    public static final int BOX = 4;
    public static final int HISTOGRAM = 5;

    private ChartFactory(){}

    public static PieChart newPieChart(Context context, Variable variable) {

        PieChart chart = new PieChart(context);
        chart.setTransparentCircleRadius(0);
        chart.setLayoutParams(new ViewGroup.LayoutParams(WIDTH, HEIGHT));
        chart.setHoleRadius(0);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setDescription(getDescription(variable.getTitle()));
        configLegend(chart);

        List<PieEntry> entries = new ArrayList<>();

        for(Object o: variable.getElements()){
            Value val = (Value) o;
            entries.add(new PieEntry(val.getCount(), val.getData().toString()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Some Example");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setColor(context.getResources().getColor(R.color.colorPrimaryLight));
        dataSet.setValueTextSize(15);
        dataSet.setSliceSpace(2);
        dataSet.setColors(COLORS);

        chart.setData(new PieData(dataSet));
        chart.invalidate();
        return chart;
    }

    public static BarChart newBarChart(Context context, Variable variable) {
        BarChart chart = new BarChart(context);
        chart.setLayoutParams(new ViewGroup.LayoutParams(WIDTH, HEIGHT));
        chart.setDescription(getDescription(variable.getTitle()));
        configLegend(chart);

        List<BarEntry> entries = new ArrayList<>();

        for(int i = 0; i < variable.getElements().size(); i++){
            Value val = (Value) variable.getElements().get(i);
            BarEntry entry = new BarEntry(i, i);
            entry.setVals(new float[]{val.getCount()});
            entry.setData(val);
            entries.add(entry);
        }
        BarDataSet dataSet = new BarDataSet(entries, "Some Example");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        dataSet.setColor(context.getResources().getColor(R.color.colorPrimaryLight));
        dataSet.setLabel("Some Bar Label");

        chart.setData(new BarData(dataSet));
        chart.invalidate();
        return chart;
    }
    private static void configLegend(Chart chart){
        Legend legend = chart.getLegend();
        legend.setEnabled(true);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        legend.setForm(Legend.LegendForm.SQUARE);
    }

    private static Description getDescription(String text){
        Description description = new Description();
        description.setText(text);
        description.setTextAlign(Paint.Align.CENTER);
        return description;
    }
}
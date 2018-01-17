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
import com.sharktech.projectprob.analyse.DataAnalyse;

public class ChartFactory {

    static final int WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
    static final int HEIGHT = 800;
    static final int[] COLORS = new int[]{Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN, Color.YELLOW,
                    Color.DKGRAY, Color.RED, Color.MAGENTA, Color.LTGRAY, Color.WHITE};

    public static final int PIE = 0;
    public static final int BAR = 1;
    public static final int DISPERSION = 2;
    public static final int BOX = 3;
    public static final int HISTOGRAM = 4;

    private ChartFactory(){}

    public static PieChart newPieChart(Context context, DataAnalyse analyse) {

        PieChartCustom chart = new PieChartCustom(context);
        configLegend(chart);
        chart.setDescription(getDescription(analyse.getTitle()));
        return chart.build(analyse);
    }

    public static BarChart newBarChart(Context context, DataAnalyse analyse) {
        BarChartCustom chart = new BarChartCustom(context);
        configLegend(chart);
        chart.setDescription(getDescription(analyse.getTitle()));
        return chart.build(analyse);
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
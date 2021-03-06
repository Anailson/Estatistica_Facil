package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyseResult;

public class ChartFactory {

    static final int WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
    static final int HEIGHT = 1000;
    static final int[] COLORS = new int[]{Color.BLUE, Color.CYAN, Color.GRAY, Color.GREEN,
            Color.YELLOW,Color.DKGRAY, Color.RED, Color.MAGENTA, Color.LTGRAY, Color.WHITE};

    public static final int PIE = 0;
    public static final int BAR = 1;
    public static final int DISPERSION = 2;
    public static final int BOX = 3;
    public static final int HISTOGRAM = 4;

    private ChartFactory(){}

    public static PieChart newPieChart(Context context, String title, DataAnalyseResult values) {

        PieChartCustom chart = new PieChartCustom(context);
        chart.setDescription(getDescription(context, title));
        return chart.build(values);
    }

    public static BarChart newBarChart(Context context, String title, DataAnalyseResult values) {
        BarChartCustom chart = new BarChartCustom(context);
        chart.setDescription(getDescription(context, title));
        return chart.build(values);
    }

    public static Chart newDispersionChart(Context context, String title, DataAnalyseResult values) {
        DispersionChart chart = new DispersionChart(context);
        chart.setDescription(getDescription(context, title));
        return chart.build(values);
    }

    private static Description getDescription(Context context, String text){
        Description description = new Description();
        description.setText(text);
        description.setTextColor(context.getResources().getColor(R.color.color_default));
        description.setTextAlign(Paint.Align.CENTER);
        return description;
    }
}
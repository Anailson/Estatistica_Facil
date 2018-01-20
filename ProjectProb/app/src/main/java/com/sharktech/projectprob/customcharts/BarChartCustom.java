package com.sharktech.projectprob.customcharts;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
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
        List<LegendEntry> legends = new ArrayList<>();

        for (int i = 0; i < analyse.size(); i++) {
            entries.add(newEntry(i, new float[]{analyse.getFrequency(i).floatValue()}));
            legends.add(newLegend(analyse.getData(i).getTitle()));
        }

        configLegend(legends);
        setBarData(entries);

        invalidate();
        return this;
    }

    @Override
    public void invalidate() {

        setDrawBorders(true);
        setBorderColor(getResources().getColor(R.color.color_primary));
        setBorderWidth(2f);

        getAxisRight().setEnabled(false);
        super.invalidate();
    }

    private void configLegend(List<LegendEntry> legends){

        getLegend().setCustom(legends);
        //getLegend().setEnabled(true);
        getLegend().setDrawInside(false);

        getLegend().setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        getLegend().setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        getLegend().setOrientation(Legend.LegendOrientation.HORIZONTAL);
        getLegend().setForm(Legend.LegendForm.CIRCLE);

        getLegend().setWordWrapEnabled(true);
        getLegend().setTextSize(12f);
        getLegend().setTypeface(Typeface.DEFAULT_BOLD);
        getLegend().setTextColor(getResources().getColor(R.color.color_default));

    }

    private void setBarData(List<BarEntry> entries){
        BarDataSet dataSet = new BarDataSet(entries, "Some Label");
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(15);
        dataSet.setColors(ChartFactory.COLORS);
        dataSet.setDrawValues(true);


        setData(new BarData(dataSet));
    }

    private BarEntry newEntry(float x, float[]values){
        return new BarEntry(x, values);
    }

    private LegendEntry newLegend(String label){
        LegendEntry legend = new LegendEntry();
        legend.label = label;
        //legend.form = Legend.LegendForm.SQUARE;
        //legend.formSize = 50;
        return legend;
    }
}

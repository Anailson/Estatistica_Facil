package com.sharktech.projectprob.controllers;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customcharts.ChartFactory;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.Locale;

public class DataDetailsController {

    private Fragment mFragment;
    private Listeners mListener;
    private DataAnalyse mAnalyse;

    public DataDetailsController(Fragment fragment, TableColumn.IVariable variable) {
        this.mFragment = fragment;
        this.mAnalyse = new DataAnalyse(variable);
        this.mListener = new Listeners();
    }

    public AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return mListener;
    }

    public void changeVariable(TableColumn.IVariable variable) {
        mAnalyse.setVariable(variable);
        mFragment.onResume();
    }

    public void calculate(){

        if(mAnalyse.calculate()) {

            fillText(R.id.txt_arithmetic_avg, mAnalyse.avgArithmetic());
            fillText(R.id.txt_geometric_avg, mAnalyse.avgGeometric());
            fillText(R.id.txt_weighted_avg, mAnalyse.avgWeighted());
            fillText(R.id.txt_quadratic_avg, mAnalyse.avgQuadratic());
            fillText(R.id.txt_mode, mAnalyse.getMode() == null ? " = " : mAnalyse.getMode().getTitle());

            Spinner spnVariable = findViewById(R.id.spn_graphs);
            if(spnVariable != null){
                setGraph(spnVariable.getSelectedItemPosition() - 1);
            }

        } else {

            fillText(R.id.txt_arithmetic_avg, -1d);
            fillText(R.id.txt_geometric_avg, -1d);
            fillText(R.id.txt_weighted_avg, -1d);
            fillText(R.id.txt_quadratic_avg, -1d);
            fillText(R.id.txt_mode, "-");

            removeChartView();
        }
    }

    private <T extends View> T findViewById(int id){
        FragmentActivity view = mFragment.getActivity();
        if(view == null) return null;
        return view.findViewById(id);
    }

    private ViewGroup removeChartView(){
        ViewGroup layout = findViewById(R.id.lyt_charts);
        if(layout == null) return null;

        layout.removeAllViews();
        return layout;
    }

    private void fillText(int id, Double value){
        String text = value > 0
                ? String.format(Locale.getDefault(),"%.6f", value)
                : mFragment.getString(R.string.txt_default);
        fillText(id, text);
    }

    private void fillText(int id, String value){
        TextView view = findViewById(id);
        if(view != null){
            view.setText(value);
        }
    }

    private void setGraph(int chartIndex){

        Spinner spnVariable = findViewById(R.id.spn_variable);

        if(chartIndex >= 0 && spnVariable != null){

            int posVariable = spnVariable.getSelectedItemPosition() - 1;

            if(posVariable >= 0){
                Chart chart = null;
                Log.e("Analyse", String.valueOf(mAnalyse.size()));
                switch (chartIndex) {
                    case ChartFactory.PIE: chart = ChartFactory.newPieChart(mFragment.getContext(), mAnalyse); break;
                    case ChartFactory.BAR: chart = ChartFactory.newBarChart(mFragment.getContext(), mAnalyse); break;
                    case ChartFactory.DISPERSION: chart = ChartFactory.newDispersionChart(mFragment.getContext(), mAnalyse); break;
                    case ChartFactory.BOX: break;
                    case ChartFactory.HISTOGRAM: break;
                }
                ViewGroup chartLayout = removeChartView();
                if (chartLayout != null && chart != null){
                    chartLayout.addView(chart);
                }

            } else {

                Toast.makeText(mFragment.getContext(), R.string.txt_no_variable_selected, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class Listeners implements AdapterView.OnItemSelectedListener{

        //AdapterView.OnItemSelectedListener
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.spn_graphs: setGraph(position - 1); break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}

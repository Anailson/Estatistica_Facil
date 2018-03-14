package com.sharktech.projectprob.controllers;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.mikephil.charting.charts.Chart;
import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.analyse.DataAnalyseResult;
import com.sharktech.projectprob.customcharts.ChartFactory;
import com.sharktech.projectprob.customtable.TableColumn;

public class DataChartController {

    private Fragment mFragment;
    private Listeners mListener;
    private TableColumn.IVariable mVariable;

    public DataChartController(Fragment fragment, TableColumn.IVariable variable) {
        this.mFragment = fragment;
        this.mListener = new Listeners();
        this.mVariable = variable;
    }

    public AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return mListener;
    }

    public void changeVariable(TableColumn.IVariable variable) {
        mVariable = variable;

        Spinner spinner = findViewById(R.id.spn_graphs);
        if(spinner != null){
            int index = spinner.getSelectedItemPosition() - 1;
            setGraph(index);
            mFragment.onResume();
        }
    }

    public void hasNoVariable(){
        removeChartView();
    }

    public void initChart(){
        setGraph(-1);
    }

    private void setGraph(int chartIndex){

        Spinner spnVariable = findViewById(R.id.spn_variable);

        if(chartIndex >= 0 && spnVariable != null){

            int posVariable = spnVariable.getSelectedItemPosition() - 1;

            if(posVariable >= 0){
                Chart chart = null;
                Context context = mFragment.getContext();
                String title = mVariable.getTitle();
                DataAnalyse analyse = new DataAnalyse(mVariable);
                analyse.init();
                DataAnalyseResult values = analyse.getValues();

                switch (chartIndex) {
                    case ChartFactory.PIE: chart = ChartFactory.newPieChart(context, title, values); break;
                    case ChartFactory.BAR: chart = ChartFactory.newBarChart(context, title, values); break;
                    case ChartFactory.DISPERSION: chart = ChartFactory.newDispersionChart(context, title, values); break;
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

    private ViewGroup removeChartView(){
        ViewGroup layout = findViewById(R.id.lyt_charts);
        if(layout == null) return null;

        layout.removeAllViews();
        return layout;
    }

    private <T extends View> T findViewById(int id){
        FragmentActivity view = mFragment.getActivity();
        if(view == null) return null;
        return view.findViewById(id);
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

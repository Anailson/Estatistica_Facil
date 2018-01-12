package com.sharktech.projectprob.controllers;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customcharts.ChartFactory;
import com.sharktech.projectprob.customtable.Variable;

public class DataDetailsController {

    private Fragment mFragment;
    private Listeners mListener;
    private Variable.IVariable mVariable;

    public DataDetailsController(Fragment fragment, Variable.IVariable variable) {
        this.mFragment = fragment;
        this.mListener = new Listeners();
        this.mVariable = variable;
    }

    public AdapterView.OnItemSelectedListener getItemSelectedListener() {
        return mListener;
    }

    public void changeVariable(Variable.IVariable variable) {
        mVariable = variable;
        calculate();
    }

    private View findViewById(int id){
        View view = mFragment.getView();
        if(view != null) {
            return view.findViewById(id);
        }
        return null;
    }

    public void calculate(){

        if(mVariable != null) {
            Variable.Analyses analyses = new Variable<>(mFragment.getContext(), mVariable).calculate();
            fillText(R.id.txt_arithmetic_avg, analyses.avgArithmetic());
            fillText(R.id.txt_geometric_avg, analyses.avgGeometric());
            fillText(R.id.txt_weighted_avg, analyses.avgWeighted());
            fillText(R.id.txt_quadratic_avg, analyses.avgQuadratic());
            fillText(R.id.txt_mode, analyses.mode() == null ? " = " : analyses.mode().toString());

            Spinner spnVariable = (Spinner) findViewById(R.id.spn_graphs);
            if(spnVariable != null){
                setGraph(spnVariable.getSelectedItemPosition());
            }

        } else {

            fillText(R.id.txt_arithmetic_avg, "-");
            fillText(R.id.txt_geometric_avg, "-");
            fillText(R.id.txt_weighted_avg, "-");
            fillText(R.id.txt_quadratic_avg, "-");
            fillText(R.id.txt_mode, "-");

            removeChartView();
        }
    }

    private ViewGroup removeChartView(){
        ViewGroup layout = (ViewGroup) findViewById(R.id.lyt_charts);
        if(layout == null) return null;

        layout.removeAllViews();
        return layout;
    }

    private void fillText(int id, Double value){
        fillText(id, value.toString());
    }

    private void fillText(int id, String value){
        TextView view = (TextView) findViewById(id);
        if(view != null){
            view.setText(value);
        }
    }

    private void setGraph(int chartIndex){
        Activity activity = mFragment.getActivity();
        if(mVariable != null && chartIndex > 0 && activity != null){

            Spinner spnVariable = activity.findViewById(R.id.spn_variable);
            int posVariable = spnVariable.getSelectedItemPosition() - 1;

            ViewGroup chart = removeChartView();
            if(posVariable >= 0 && chart != null){

                switch (chartIndex) {
                    case ChartFactory.PIE:
                        chart.addView(ChartFactory.newPieChart(activity, mVariable));
                        break;
                    case ChartFactory.BAR:
                        chart.addView(ChartFactory.newBarChart(activity, mVariable));
                        break;
                    case ChartFactory.DISPERSION:
                        break;
                    case ChartFactory.BOX:
                        break;
                    case ChartFactory.HISTOGRAM:
                        break;
                }

            } else {
                Toast.makeText(activity, "Nenhuma variável selecionada", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class Listeners implements AdapterView.OnItemSelectedListener{

        //AdapterView.OnItemSelectedListener
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            switch (parent.getId()) {
                case R.id.spn_graphs: setGraph(position); break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}

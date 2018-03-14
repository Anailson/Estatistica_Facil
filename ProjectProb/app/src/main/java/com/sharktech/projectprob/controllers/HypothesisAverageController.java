package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customview.ItemConfidenceInterval;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.Locale;

public class HypothesisAverageController {

    private Fragment mFragment;
    private Listener mListener;

    public HypothesisAverageController(Fragment fragment) {
        mFragment = fragment;
        mListener = new Listener();
    }

    public Listener getListeners(){
        return mListener;
    }

    private void setVarsAsSample(boolean isChecked){
        //setItemValue(R.id.ht_sample_size,"");
        //setItemValue(R.id.ht_sample_avg,"");

        FragmentActivity activity = mFragment.getActivity();

        if (activity != null) {
            int height = isChecked ? LinearLayout.LayoutParams.WRAP_CONTENT : 0;
            //LinearLayout layout = activity.findViewById(R.id.lay_ht_var_sample);
            //layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        }
    }

    private void setVariable(int position){

        String avg = "";
        String size = "";
        if (position >= 0) {
            TableColumn.IVariable variable = VariablePersistence.getInstance().getVariable(position);
            DataAnalyse analyse = new DataAnalyse(variable);
            analyse.init();

            avg = String.format(Locale.getDefault(), "%.6f", analyse.get(DataAnalyse.Average.ARITHMETIC_POUND));
            size = variable.nElements() + "";
        }

        //setItemValue(R.id.ht_sample_avg, avg);
        //setItemValue(R.id.ht_sample_size, size);
    }

    private Double asDouble(String s){
        return Double.valueOf(s.replace(",", "."));
    }

    private void calculate(){
        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {
/*
            ItemConfidenceInterval sampleAvg = activity.findViewById(R.id.ht_sample_avg);
            ItemConfidenceInterval sampleSize = activity.findViewById(R.id.ht_sample_size);
            ItemConfidenceInterval populationSize = activity.findViewById(R.id.ht_population_deviation);
            ItemConfidenceInterval populationDeviation = activity.findViewById(R.id.ht_hypothesis);
            ItemConfidenceInterval confidenceLevel = activity.findViewById(R.id.ht_confidence);

            DataAnalyse.IntervalConfidenceValues values = new DataAnalyse.IntervalConfidenceValues();
            values.setOnResult(getListeners());
            values.setSampleAvg(sampleAvg.isChecked() ? asDouble(sampleAvg.getValue()) : null);
            values.setSampleSize(sampleSize.isChecked() ? asDouble(sampleSize.getValue()) : null);
            values.setPopulationSize(populationSize.isChecked() ? asDouble(populationSize.getValue()) : null);
            values.setDeviation(populationDeviation.isChecked() ? asDouble(populationDeviation.getValue()) : null);
            values.setConfidence(confidenceLevel.isChecked() ? asDouble(confidenceLevel.getValue()) : null);

            DataAnalyse.intervalAverage(values);
            */
        }
    }

    private void setItemValue(int id, String text) {

        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {
            ((ItemConfidenceInterval) activity.findViewById(id))
                    .setValue(text);
        }
    }

    private class Listener implements Switch.OnCheckedChangeListener, AdapterView.OnItemSelectedListener,
            View.OnClickListener, DataAnalyse.IntervalConfidenceResult{

        //Switch.OnCheckedChangeListener
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            setVarsAsSample(isChecked);
        }

        //AdapterView.OnItemSelectedListener
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            setVariable(position - 1);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}

        //View.OnClickListener
        @Override
        public void onClick(View v) {
            calculate();
        }

        //DataAnalyse.IntervalConfidenceResult
        @Override
        public void onSuccess(Double min, Double max, Double error, float z, DataAnalyse.IntervalConfidenceValues values) {
            /*
            String text = "z = " + z + "\n\n";
            text += String.format(Locale.getDefault(), "P = (%s < %s < %s) = %s",
                    format(min), mFragment.getString(R.string.sym_mu), format(max), format(values.getConfidence().intValue() / 100d));
            text += "\nou\n";
            text += String.format(Locale.getDefault(), "IC (%s, %s) = (%s; %s)",
                    mFragment.getString(R.string.sym_mu), asPercent(values.getConfidence()), format(min), format(max));
            text += "\n\n" + String.format(Locale.getDefault(),"Erro de estimação e = %s", format(error));
*/
            FragmentActivity activity = mFragment.getActivity();
            if(activity != null) {
                /*
                TextView valueInterval = activity.findViewById(R.id.txt_ht_value);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) valueInterval.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                valueInterval.setLayoutParams(params);
                valueInterval.setText(text);
                */
            }
        }

        @Override
        public void onError() {

        }

        private String format(Double d){
            return String.format(Locale.getDefault(), "%.2f", d.floatValue());
        }

        private String asPercent(Double d){
            return String.format(Locale.getDefault(), "%.2f%s", d.floatValue(), "%");
        }
    }
}

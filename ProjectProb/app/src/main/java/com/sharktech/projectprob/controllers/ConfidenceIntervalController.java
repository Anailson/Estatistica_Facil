package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customview.ItemConfidenceInterval;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.Locale;

public class ConfidenceIntervalController {

    private Fragment mFragment;
    private Listener mListener;

    public ConfidenceIntervalController(Fragment fragment) {
        mFragment = fragment;
        mListener = new Listener();
    }

    public Listener getListeners() {
        return mListener;
    }

    private void setVarsAsSample(boolean isChecked){
        setItemValue(R.id.ci_sample_avg, false, "");
        setItemValue(R.id.ci_sample_size, false, "");

        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {
            int height = isChecked ? LinearLayout.LayoutParams.WRAP_CONTENT : 0;
            LinearLayout layout = activity.findViewById(R.id.lay_var_sample);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        }
    }

    private void setVariable(int position){
        if (position < 0) {
            setItemValue(R.id.ci_sample_avg, false, "");
            setItemValue(R.id.ci_sample_size, false, "");
        } else {
            TableColumn.IVariable variable = VariablePersistence.getInstance().getVariable(position);
            DataAnalyse analyse = new DataAnalyse(variable);
            analyse.init();

            String avg = String.format(Locale.getDefault(), "%.6f", analyse.get(DataAnalyse.Average.ARITHMETIC_POUND));
            String size = variable.nElements() + "";

            setItemValue(R.id.ci_sample_avg, true, avg);
            setItemValue(R.id.ci_sample_size, true, size);
        }
    }

    private void setItemValue(int id, boolean checked, String text) {

        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {
            ((ItemConfidenceInterval) activity.findViewById(id))
                    .setValue(text)
                    .setChecked(checked);
        }
    }

    private void calculate(){
        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {

            ItemConfidenceInterval sampleAvg = activity.findViewById(R.id.ci_sample_avg);
            ItemConfidenceInterval sampleSize = activity.findViewById(R.id.ci_sample_size);
            ItemConfidenceInterval populationSize = activity.findViewById(R.id.ci_population_size);
            ItemConfidenceInterval populationDeviation = activity.findViewById(R.id.ci_population_deviation);
            ItemConfidenceInterval confidenceLevel = activity.findViewById(R.id.ci_confidence);

            DataAnalyse.IntervalConfidenceValues values = new DataAnalyse.IntervalConfidenceValues();
            values.setOnResult(getListeners());
            values.setSampleAvg(sampleAvg.isChecked() ? Double.valueOf(sampleAvg.getValue()) : null);
            values.setSampleSize(sampleSize.isChecked() ? Double.valueOf(sampleSize.getValue()) : null);
            values.setPopulationSize(populationSize.isChecked() ? Double.valueOf(populationSize.getValue()) : null);
            values.setDeviation(populationDeviation.isChecked() ? Double.valueOf(populationDeviation.getValue()) : null);
            values.setConfidence(confidenceLevel.isChecked() ? Double.valueOf(confidenceLevel.getValue()) : null);

            DataAnalyse.confidenceInterval(values);
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
        public void onSuccess(Double min, Double max) {

        }

        @Override
        public void onError() {

        }
    }
}

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

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.alert.DefaultAlert;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customview.ItemConfidenceInterval;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.Locale;

public class IntervalProportionController {

    private Fragment mFragment;
    private Listener mListener;

    public IntervalProportionController(Fragment fragment) {
        mFragment = fragment;
        mListener = new Listener();
    }

    public Listener getListeners() {
        return mListener;
    }

    private void setVarsAsSample(boolean isChecked){
        setItemValue(R.id.ci_sample_size,"");

        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {
            int height = isChecked ? LinearLayout.LayoutParams.WRAP_CONTENT : 0;
            LinearLayout layout = activity.findViewById(R.id.lay_var_sample);
            layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
        }
    }

    private void setVariable(int position){
        if (position < 0) {
            setItemValue(R.id.ci_sample_size,"");
        } else {
            TableColumn.IVariable variable = VariablePersistence.getInstance().getVariable(position);
            DataAnalyse analyse = new DataAnalyse(variable);
            analyse.init();

            setItemValue(R.id.ci_sample_size, variable.nElements() + "");
        }
    }

    private void setItemValue(int id, String text) {

        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {
            ((ItemConfidenceInterval) activity.findViewById(id)).setValue(text);
        }
    }

    private void calculate(){
        FragmentActivity activity = mFragment.getActivity();
        if (activity != null) {

            ItemConfidenceInterval sampleSuccess = activity.findViewById(R.id.ci_sample_success);
            ItemConfidenceInterval sampleSize = activity.findViewById(R.id.ci_sample_size);
            ItemConfidenceInterval confidenceLevel = activity.findViewById(R.id.ci_confidence);

            DataAnalyse.IntervalConfidenceValues values = new DataAnalyse.IntervalConfidenceValues();
            values.setOnResult(getListeners());
            values.setSuccess(sampleSuccess.isChecked() ? asDouble(sampleSuccess.getValue()) : null);
            values.setSampleSize(sampleSize.isChecked() ? asDouble(sampleSize.getValue()) : null);
            values.setConfidence(confidenceLevel.isChecked() ? asDouble(confidenceLevel.getValue()) : null);

            DataAnalyse.intervalProportion(values);
        }
    }

    private Double asDouble(String s){
        return Double.valueOf(s.replace(",", "."));
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

            String text = "z = " + z + "\n\n";
            text += String.format(Locale.getDefault(), "P = (%s < p < %s) = %s",
                    asPercent(min * 100), asPercent(max * 100), asPercent(values.getConfidence()));
            text += "\nou\n";
            text += String.format(Locale.getDefault(), "IC (p, %s) = (%s; %s)",
                    asPercent(values.getConfidence()), asPercent(min * 100), asPercent(max * 100));
            text += "\n\n" + String.format(Locale.getDefault(),"Erro de estimação e = %s", format(error));

            FragmentActivity activity = mFragment.getActivity();
            if(activity != null) {
                TextView valueInterval = activity.findViewById(R.id.txt_value_ic_prop);

                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) valueInterval.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;

                valueInterval.setLayoutParams(params);
                valueInterval.setText(text);
            }
        }

        @Override
        public void onError() {

            DefaultAlert alert = new DefaultAlert(mFragment.getActivity());
            alert.setTitle("Erro")
                    .setMessage("Não foi possível realizar o cálculo.\nVerifique os valores informados e tente novamente")
                    .setPositiveListener("OK", (dialog, which) -> dialog.dismiss());
            alert.show();
        }

        private String format(Double d){
            return String.format(Locale.getDefault(), "%.2f", d.floatValue());
        }

        private String asPercent(Double d){
            return String.format(Locale.getDefault(), "%.2f%s", d.floatValue(), "%");
        }
    }
}

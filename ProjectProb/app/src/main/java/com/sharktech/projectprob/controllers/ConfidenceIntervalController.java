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

    public Listener getListeners(){
        return mListener;
    }

    private class Listener implements Switch.OnCheckedChangeListener, AdapterView.OnItemSelectedListener{

        private void setItemValue(int id, boolean checked, String text){

            FragmentActivity activity = mFragment.getActivity();
            if(activity != null) {
                ((ItemConfidenceInterval) activity.findViewById(id))
                        .setValue(text)
                        .setChecked(checked);
            }
        }

        //Switch.OnCheckedChangeListener
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            setItemValue(R.id.ci_sample_avg, false, "");
            setItemValue(R.id.ci_sample_size, false, "");

            FragmentActivity activity = mFragment.getActivity();
            if(activity != null){
                int height = isChecked ? LinearLayout.LayoutParams.WRAP_CONTENT : 0;
                LinearLayout layout = activity.findViewById(R.id.lay_var_sample);
                layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height));
            }
        }

        //AdapterView.OnItemSelectedListener
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            if(position == 0){
                setItemValue(R.id.ci_sample_avg, false, "");
                setItemValue(R.id.ci_sample_size, false, "");
            }else{
                TableColumn.IVariable variable = VariablePersistence.getInstance().getVariable(position - 1);
                DataAnalyse analyse = new DataAnalyse(variable);
                analyse.init();

                String avg = String.format(Locale.getDefault(), "%.6f", analyse.get(DataAnalyse.Average.ARITHMETIC_POUND));
                String size = variable.nElements() + "";

                setItemValue(R.id.ci_sample_avg, true, avg);
                setItemValue(R.id.ci_sample_size, true, size);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {}
    }
}

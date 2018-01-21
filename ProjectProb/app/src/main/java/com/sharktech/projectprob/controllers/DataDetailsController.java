package com.sharktech.projectprob.controllers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableColumn;

import java.util.Locale;

public class DataDetailsController {

    private Fragment mFragment;
    private DataAnalyse mAnalyse;

    public DataDetailsController(Fragment fragment, TableColumn.IVariable variable) {
        this.mFragment = fragment;
        this.mAnalyse = new DataAnalyse(variable);
    }

    public void changeVariable(TableColumn.IVariable variable) {
        mAnalyse.setVariable(variable);
        mFragment.onResume();
    }

    public void calculate(){

        if(mAnalyse.calculate()) {

            fillText(R.id.txt_arithmetic_avg, mAnalyse.avgArithmetic());
            fillText(R.id.txt_geometric_avg, mAnalyse.avgPoundGeometric());
            fillText(R.id.txt_weighted_avg, mAnalyse.avgPoundWeighted());
            fillText(R.id.txt_quadratic_avg, mAnalyse.avgQuadratic());
            fillText(R.id.txt_mode, mAnalyse.getMode() == null ? " = " : mAnalyse.getMode().getTitle());
        } else {
            fillText(R.id.txt_arithmetic_avg, -1d);
            fillText(R.id.txt_geometric_avg, -1d);
            fillText(R.id.txt_weighted_avg, -1d);
            fillText(R.id.txt_quadratic_avg, -1d);
            fillText(R.id.txt_mode, "-");
        }
    }

    private <T extends View> T findViewById(int id){
        FragmentActivity view = mFragment.getActivity();
        if(view == null) return null;
        return view.findViewById(id);
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
}

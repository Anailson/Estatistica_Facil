package com.sharktech.projectprob.controllers;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.analyse.DataAnalyseDetails;
import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customview.ItemDataDetail;

import java.util.Locale;

import static com.sharktech.projectprob.analyse.DataAnalyseDetails.AverageKey;

public class DataDetailsController {

    private Fragment mFragment;
    //private DataAnalyse mAnalyse;
    private TableColumn.IVariable mVariable;

    public DataDetailsController(Fragment fragment, TableColumn.IVariable variable) {
        mFragment = fragment;
        //mAnalyse = new DataAnalyse(variable);
        mVariable = variable;

    }

    public void changeVariable(TableColumn.IVariable variable) {
        mVariable = variable;
        calculate();
        mFragment.onResume();
    }

    public void hasNoVariable(){
        defaultValues();
    }

    public void calculate(){

        if(mVariable != null) {

            DataAnalyseDetails details = DataAnalyse.details(mVariable);

            String modeText = mFragment.getString(R.string.txt_default);
            if(details.hasMode()){
                StringBuilder sBuilder = new StringBuilder();
                for(TableCell.ICell cell : details.getModes()){
                    sBuilder.append(cell.getTitle()).append(", ");
                }
                modeText = sBuilder.substring(0, sBuilder.lastIndexOf(", "));
            }

            fillItemDetail(R.id.detail_avg_arithmetic, details.get(AverageKey.ARITHMETIC));
            fillItemDetail(R.id.detail_avg_arithmetic_pound, details.get(AverageKey.POUND_ARITHMETIC));
            fillItemDetail(R.id.detail_avg_geometric, details.get(AverageKey.GEOMETRIC));
            fillItemDetail(R.id.detail_avg_geometric_pound, details.get(AverageKey.POUND_GEOMETRIC));
            fillItemDetail(R.id.detail_avg_weighted, details.get(AverageKey.WEIGHTED));
            fillItemDetail(R.id.detail_avg_weighted_pound, details.get(AverageKey.POUND_WEIGHTED));
            fillItemDetail(R.id.detail_avg_quadratic, details.get(AverageKey.QUADRATIC));
            fillItemDetail(R.id.detail_avg_quadratic_pound, details.get(AverageKey.POUND_QUADRATIC));
            fillText(R.id.txt_mode, modeText);
        } else {
            defaultValues();
        }
    }

    private void defaultValues(){
        fillItemDetail(R.id.detail_avg_arithmetic, -1d);
        fillItemDetail(R.id.detail_avg_arithmetic_pound, -1d);
        fillItemDetail(R.id.detail_avg_geometric, -1d);
        fillItemDetail(R.id.detail_avg_geometric_pound, -1d);
        fillItemDetail(R.id.detail_avg_weighted, -1d);
        fillItemDetail(R.id.detail_avg_weighted_pound, -1d);
        fillItemDetail(R.id.detail_avg_quadratic, -1d);
        fillItemDetail(R.id.detail_avg_quadratic_pound, -1d);
        fillText(R.id.txt_mode, mFragment.getString(R.string.txt_default));
    }

    private void fillItemDetail(int id, Double value){
        String text = value > 0
                ? String.format(Locale.getDefault(),"%.6f", value)
                : mFragment.getString(R.string.txt_default);
        fillItemDetail(id, text);
    }

    private void fillItemDetail(int id, String value){
        Activity activity = mFragment.getActivity();
        if(activity != null) {
            ItemDataDetail view = activity.findViewById(id);
            if(view != null) view.setValueText(value);
        }
    }

    private void fillText(int id, String text){
        Activity activity = mFragment.getActivity();
        if(activity != null) {
            TextView view = activity.findViewById(id);
            if(view != null)  view.setText(text);
        }
    }
}

package com.sharktech.projectprob.controllers;


import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.TextView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.customview.ItemDataDetail;

import java.util.Locale;

public class DataDetailsController {

    private Fragment mFragment;
    private DataAnalyse mAnalyse;

    public DataDetailsController(Fragment fragment, TableColumn.IVariable variable) {
        mFragment = fragment;
        mAnalyse = new DataAnalyse(variable);
    }

    public void changeVariable(TableColumn.IVariable variable) {
        mAnalyse.setVariable(variable);
        calculate();
        mFragment.onResume();
    }

    public void hasNoVariable(){
        defaultValues();
    }

    public void calculate(){

        if(mAnalyse.calculate()) {
            //mAnalyse.initClasses();
            String modeText = mFragment.getString(R.string.txt_default);
            if(mAnalyse.hasMode()){
                StringBuilder sBuilder = new StringBuilder();
                for(TableCell.ICell cell : mAnalyse.getMode()){
                    sBuilder.append(cell.getTitle()).append(", ");
                }
                modeText = sBuilder.substring(0, sBuilder.lastIndexOf(", "));
            }

            fillItemDetail(R.id.detail_avg_arithmetic, mAnalyse.avgArithmetic());
            fillItemDetail(R.id.detail_avg_arithmetic_pound, mAnalyse.avgArithmeticPound());
            fillItemDetail(R.id.detail_avg_geometric, mAnalyse.avgGeometric());
            fillItemDetail(R.id.detail_avg_geometric_pound, mAnalyse.avgGeometricPound());
            fillItemDetail(R.id.detail_avg_weighted, mAnalyse.avgWeighted());
            fillItemDetail(R.id.detail_avg_weighted_pound, mAnalyse.avgWeightedPound());
            fillItemDetail(R.id.detail_avg_quadratic, mAnalyse.avgQuadratic());
            fillItemDetail(R.id.detail_avg_quadratic_pound, mAnalyse.avgQuadratic());
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

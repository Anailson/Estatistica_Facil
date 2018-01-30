package com.sharktech.projectprob.controllers;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.TextView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableCell;
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
        calculate();
        mFragment.onResume();
    }

    public void hasNoVariable(){
        defaultValues();
    }

    public void calculate(){

        if(mAnalyse.calculate()) {

            String modeText;
            if(mAnalyse.hasMode()){
                StringBuilder sBuilder = new StringBuilder();
                for(TableCell.ICell cell : mAnalyse.getMode()){
                    sBuilder.append(cell.getTitle()).append(", ");
                }
                modeText = sBuilder.substring(0, sBuilder.lastIndexOf(", "));
            }else {
                modeText = "Amodal";
            }

            fillText(R.id.txt_avg_arithmetic, mAnalyse.avgArithmetic());
            fillText(R.id.txt_avg_arithmetic_pound, mAnalyse.avgArithmeticPound());
            fillText(R.id.txt_avg_geometric, mAnalyse.avgGeometric());
            fillText(R.id.txt_avg_geometric_pound, mAnalyse.avgGeometricPound());
            fillText(R.id.txt_avg_weighted, mAnalyse.avgWeighted());
            fillText(R.id.txt_avg_weighted_pound, mAnalyse.avgWeightedPound());
            fillText(R.id.txt_avg_quadratic, mAnalyse.avgQuadratic());
            fillText(R.id.txt_avg_quadratic_pound, mAnalyse.avgQuadraticPound());
            fillText(R.id.txt_mode, modeText);
        } else {
            defaultValues();
        }
    }

    private void defaultValues(){

        fillText(R.id.txt_avg_arithmetic, -1d);
        fillText(R.id.txt_avg_arithmetic_pound, -1d);
        fillText(R.id.txt_avg_geometric, -1d);
        fillText(R.id.txt_avg_geometric_pound, -1d);
        fillText(R.id.txt_avg_weighted, -1d);
        fillText(R.id.txt_avg_weighted_pound, -1d);
        fillText(R.id.txt_avg_quadratic, -1d);
        fillText(R.id.txt_avg_quadratic_pound, -1d);
        fillText(R.id.txt_mode, -1d);
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

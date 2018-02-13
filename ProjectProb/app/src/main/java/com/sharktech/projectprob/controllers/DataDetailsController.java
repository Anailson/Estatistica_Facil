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

import static com.sharktech.projectprob.analyse.DataAnalyseDetails.Details;

public class DataDetailsController {

    private Fragment mFragment;
    private TableColumn.IVariable mVariable;

    public DataDetailsController(Fragment fragment, TableColumn.IVariable variable) {
        mFragment = fragment;
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

            fillItemDetail(R.id.detail_avg_arithmetic, details.get(Details.ARITHMETIC));
            fillItemDetail(R.id.detail_avg_arithmetic_pound, details.get(Details.POUND_ARITHMETIC));
            fillItemDetail(R.id.detail_avg_geometric, details.get(Details.GEOMETRIC));
            fillItemDetail(R.id.detail_avg_geometric_pound, details.get(Details.POUND_GEOMETRIC));
            fillItemDetail(R.id.detail_avg_weighted, details.get(Details.WEIGHTED));
            fillItemDetail(R.id.detail_avg_weighted_pound, details.get(Details.POUND_WEIGHTED));
            fillItemDetail(R.id.detail_avg_quadratic, details.get(Details.QUADRATIC));
            fillItemDetail(R.id.detail_avg_quadratic_pound, details.get(Details.POUND_QUADRATIC));
            fillItemDetail(R.id.detail_kurtosis, details.get(Details.KURTOSIS));
            fillItemDetail(R.id.detail_asymmetry, details.get(Details.ASYMMETRY)); //accepts negative value
            fillText(R.id.txt_mode, modeText);
        } else {
            defaultValues();
        }
    }

    private void defaultValues(){
        fillItemDetail(R.id.detail_avg_arithmetic, null);
        fillItemDetail(R.id.detail_avg_arithmetic_pound, null);
        fillItemDetail(R.id.detail_avg_geometric, null);
        fillItemDetail(R.id.detail_avg_geometric_pound, null);
        fillItemDetail(R.id.detail_avg_weighted, null);
        fillItemDetail(R.id.detail_avg_weighted_pound, null);
        fillItemDetail(R.id.detail_avg_quadratic, null);
        fillItemDetail(R.id.detail_avg_quadratic_pound, null);
        fillItemDetail(R.id.detail_kurtosis, null);
        fillItemDetail(R.id.detail_asymmetry, null);
        fillText(R.id.txt_mode, null);
    }

    private void fillItemDetail(int id, Double value){
        String text = value != null
                ? String.format(Locale.getDefault(),"%.6f", value)
                : mFragment.getString(R.string.txt_default);
        fillItemDetailText(id, text);
    }

    private void fillItemDetailText(int id, String value){
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

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

            fillItemDetail(R.id.detail_avg_arithmetic, format(details.get(Details.ARITHMETIC)));
            fillItemDetail(R.id.detail_avg_arithmetic_pound, format(details.get(Details.POUND_ARITHMETIC)));
            fillItemDetail(R.id.detail_avg_geometric, format(details.get(Details.GEOMETRIC)));
            fillItemDetail(R.id.detail_avg_geometric_pound, format(details.get(Details.POUND_GEOMETRIC)));
            fillItemDetail(R.id.detail_avg_weighted, format(details.get(Details.WEIGHTED)));
            fillItemDetail(R.id.detail_avg_weighted_pound, format(details.get(Details.POUND_WEIGHTED)));
            fillItemDetail(R.id.detail_avg_quadratic, format(details.get(Details.QUADRATIC)));
            fillItemDetail(R.id.detail_avg_quadratic_pound, format(details.get(Details.POUND_QUADRATIC)));
            fillItemDetail(R.id.detail_kurtosis, format(details.get(Details.KURTOSIS)));
            fillItemDetail(R.id.detail_asymmetry, format(details.get(Details.ASYMMETRY))); //accepts negative value
            fillText(R.id.txt_mode, modeText);
        } else {
            defaultValues();
        }
    }

    private void defaultValues(){
        String empty = mFragment.getString(R.string.txt_default);
        fillItemDetail(R.id.detail_avg_arithmetic, empty);
        fillItemDetail(R.id.detail_avg_arithmetic_pound, empty);
        fillItemDetail(R.id.detail_avg_geometric, empty);
        fillItemDetail(R.id.detail_avg_geometric_pound, empty);
        fillItemDetail(R.id.detail_avg_weighted, empty);
        fillItemDetail(R.id.detail_avg_weighted_pound, empty);
        fillItemDetail(R.id.detail_avg_quadratic, empty);
        fillItemDetail(R.id.detail_avg_quadratic_pound, empty);
        fillItemDetail(R.id.detail_kurtosis, empty);
        fillItemDetail(R.id.detail_asymmetry, empty);
        fillText(R.id.txt_mode, empty);
    }

    private String format(Double value){
        return value != null
                ? String.format(Locale.getDefault(),"%.6f", value)
                : mFragment.getString(R.string.txt_default);
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

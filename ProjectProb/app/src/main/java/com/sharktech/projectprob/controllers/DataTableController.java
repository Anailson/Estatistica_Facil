package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableObject;

import java.util.ArrayList;

public class DataTableController {

    private Fragment mFragment;
    private TableColumn.IVariable mVariable;
    private CustomTable mTable;

    public DataTableController(Fragment mFragment, TableColumn.IVariable variable) {
        this.mFragment = mFragment;
        this.mVariable = variable;
    }

    public void changeVariable(TableColumn.IVariable variable) {
        this.mVariable = variable;
        mTable.clear();

        View view = mFragment.getView();
        if(view != null){
            ViewGroup contentTable = view.findViewById(R.id.content_data_table);
            contentTable.addView(buildTable());
        }
        mFragment.onResume();
    }

    public void hasNoVariable(){
        mVariable = null;
        mTable.clear();
        mFragment.onResume();
    }

    public ViewGroup buildTable() {
        ArrayList<TableColumn.IVariable> variables = new ArrayList<>();

        if (mVariable != null){
            DataAnalyse analyse = new DataAnalyse(mVariable);
            analyse.calculate();
            variables.add(getVariable(R.string.sum_value, analyse.getData()));
            variables.add(getVariable(R.string.sum_frequency, analyse.getFrequency()));
            variables.add(getVariable(R.string.sum_prod_val_freq, analyse.getProdValFreq()));
            variables.add(getVariable(R.string.prd_value, analyse.getPowVal()));
            variables.add(getVariable(R.string.prd_pwr_val_freq, analyse.getPowValFreq()));
            variables.add(getVariable(R.string.sum_div_val, analyse.getDivVal()));
            variables.add(getVariable(R.string.sum_div_freq_val, analyse.getDivFreqVal()));
            variables.add(getVariable(R.string.sum_sqrt_val, analyse.getSqrtVal()));
            variables.add(getVariable(R.string.sum_prod_sqrt_val_freq, analyse.getProdSqrtValFreq()));
        }

        mTable = new CustomTable(mFragment.getContext());
        mTable.setLineCounter(true);
        mTable.setNoDataFound(mFragment.getString(R.string.txt_no_variable_selected));
        return mTable.build(variables);
    }
    private TableColumn.IVariable getVariable(int title, final ArrayList<TableCell.ICell> cells){
        return getVariable(mFragment.getString(title), cells);
    }

    private TableColumn.IVariable getVariable(String title, final ArrayList<TableCell.ICell> cells){

        VariableObject variable = new VariableObject(title);
        variable.add(cells);
        return variable;
    }
}

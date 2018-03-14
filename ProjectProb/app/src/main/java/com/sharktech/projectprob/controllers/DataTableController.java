package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableCell;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.CellValue;
import com.sharktech.projectprob.models.VariableNumber;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;

import static com.sharktech.projectprob.analyse.DataAnalyse.ValueKey;

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
            analyse.init();

            if (mVariable.isNumber()) {

                variables.add(numericVar(R.string.sum_value, analyse.get(ValueKey.DATA)));
                variables.add(numericVar(R.string.sum_accumulated_frequency, analyse.get(ValueKey.FREQUENCY_ACCUMULATED)));
                variables.add(numericVar(R.string.sum_frequency, analyse.get(ValueKey.FREQUENCY)));

                variables.add(numericVar(R.string.sum_prod_val_freq, analyse.get(ValueKey.PROD_VAL_FREQ)));
                variables.add(numericVar(R.string.prd_value, analyse.get(ValueKey.POW_VAL)));
                variables.add(numericVar(R.string.prd_pwr_val_freq, analyse.get(ValueKey.POW_VAL_FREQ)));
                variables.add(numericVar(R.string.sum_div_val, analyse.get(ValueKey.DIV_BY_VAL)));
                variables.add(numericVar(R.string.sum_div_freq_val, analyse.get(ValueKey.DIV_FREQ_VAL)));
                variables.add(numericVar(R.string.sum_sqrt_val, analyse.get(ValueKey.SQRT_VAL)));
                variables.add(numericVar(R.string.sum_prod_sqrt_val_freq, analyse.get(ValueKey.PROD_SQRT_VAL_FREQ)));
            } else {
                variables.add(stringVar(R.string.sum_value, analyse.get(ValueKey.DATA)));
                variables.add(numericVar(R.string.sum_accumulated_frequency, analyse.get(ValueKey.FREQUENCY_ACCUMULATED)));
                variables.add(numericVar(R.string.sum_frequency, analyse.get(ValueKey.FREQUENCY)));
            }
        }

        mTable = new CustomTable(mFragment.getContext());
        mTable.setLineCounter(true);
        mTable.setNoDataFound(mFragment.getString(R.string.txt_no_variable_selected));
        return mTable.build(variables);
    }

    private VariableString stringVar(int title, final ArrayList<TableCell.ICell> cells){
        VariableString variable = new VariableString(mFragment.getString(title));
        for(TableCell.ICell cell : cells){
            variable.add(new CellValue(cell.getTitle()));
        }
        return variable;
    }

    private VariableNumber numericVar(int title, ArrayList<TableCell.ICell> cells){
        VariableNumber variable = new VariableNumber(mFragment.getString(title));
        for(TableCell.ICell cell : cells){
            CellValue cellValue = new CellValue(cell.getTitle());
            cellValue.setNumber(true);
            variable.add(cellValue);
        }
        return variable;
    }
}

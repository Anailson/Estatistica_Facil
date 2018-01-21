package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
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
        mFragment.onResume();
    }

    public ViewGroup buildTable() {
        ArrayList<TableColumn.IVariable> variables = new ArrayList<>();

        if (mVariable != null){
            DataAnalyse analyse = new DataAnalyse(mVariable);
            analyse.calculate();
            variables.add(getVariable(mVariable.getTitle(), analyse.getData()));
            variables.add(getVariable("Frequencia", analyse.getFrequency()));
            variables.add(getVariable("Variância", analyse.getProdValFreq()));
        }

        mTable = new CustomTable(mFragment.getContext());
        mTable.setLineCounter(true);
        mTable.setNoDataFound(mFragment.getString(R.string.txt_no_variable_selected));
        return mTable.build(variables);
    }

    private TableColumn.IVariable getVariable(final String title, final ArrayList<TableCell.ICell> cells){

        VariableObject variable = new VariableObject(title);
        variable.add(cells);
        return variable;
    }
}

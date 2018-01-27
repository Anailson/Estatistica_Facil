package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableString;

import java.util.ArrayList;

public class DataClassController {
    private Fragment mFragment;
    private TableColumn.IVariable mVariable;
    private CustomTable mTable;

    public DataClassController(Fragment mFragment, TableColumn.IVariable variable) {
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

            VariableString variable = new VariableString("Classes");
            variable.add(new String[]{"oi", "tudo", "bem"});
            variables.add(variable);
        }

        mTable = new CustomTable(mFragment.getContext());
        mTable.setLineCounter(true);
        mTable.setNoDataFound(mFragment.getString(R.string.txt_no_variable_selected));
        return mTable.build(variables);
    }
}

package com.sharktech.projectprob.controllers;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.analyse.DataAnalyse;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableColumn;

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

        View view = mFragment.getView();
        if(view != null){
            ViewGroup mContentTable = view.findViewById(R.id.content_class_table);
            mContentTable.addView(buildTable());
        }
        mFragment.onResume();
    }

    public void hasNoVariable(){
        mVariable = null;
        mTable.clear();
        mFragment.onResume();
    }

    public ViewGroup buildTable() {
        DataAnalyse analyse = new DataAnalyse(mVariable);
        ArrayList<TableColumn.IVariable> variables = analyse.initClasses();

        mTable = new CustomTable(mFragment.getContext());
        mTable.setLineCounter(true);
        mTable.setNoDataFound(mFragment.getString(R.string.txt_no_variable_selected));
        return mTable.build(variables);
    }
}

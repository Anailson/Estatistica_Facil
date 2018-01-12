package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.DataTableController;
import com.sharktech.projectprob.customtable.Variable;

public class DataTableView extends Fragment implements DataAnalyseView.ChangeVariableListener{

    private DataTableController mController;
    private ViewGroup mContentTable;
    private Variable.IVariable mVariable;

    public static DataTableView newInstance(Variable.IVariable variable){
        DataTableView fragment = new DataTableView();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataAnalyseView.ANALYSES, variable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if(bundle != null) mVariable = (Variable.IVariable) bundle.getSerializable(DataAnalyseView.ANALYSES);

        View view = inflater.inflate(R.layout.fragment_data_table, container, false);
        mController = new DataTableController(this, mVariable);
        mContentTable = view.findViewById(R.id.content_data_table);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup table = mController.buildTable();
        mContentTable.addView(table);
    }

    @Override
    public void onChangeVariable(Variable.IVariable variable) {

    }
}

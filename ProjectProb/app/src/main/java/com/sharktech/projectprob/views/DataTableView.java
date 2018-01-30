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
import com.sharktech.projectprob.customtable.TableColumn;

public class DataTableView extends Fragment implements DataAnalyseView.ChangeVariableListener{

    private DataTableController mController;
    private TableColumn.IVariable mVariable;

    public static DataTableView newInstance(TableColumn.IVariable variable){
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
        if(bundle != null) mVariable = (TableColumn.IVariable) bundle.getSerializable(DataAnalyseView.ANALYSES);

        mController = new DataTableController(this, mVariable);
        View view = inflater.inflate(R.layout.fragment_data_table, container, false);
        ViewGroup mContentTable = view.findViewById(R.id.content_data_table);
        mContentTable.addView(mController.buildTable());

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onChangeVariable(TableColumn.IVariable variable) {
        mController.changeVariable(variable);
    }

    @Override
    public void onHasNoVariable() {
        mController.hasNoVariable();
    }
}

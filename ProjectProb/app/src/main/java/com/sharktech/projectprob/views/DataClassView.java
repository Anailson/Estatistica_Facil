package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.DataClassController;
import com.sharktech.projectprob.controllers.DataTableController;
import com.sharktech.projectprob.customtable.TableColumn;

public class DataClassView extends Fragment implements DataAnalyseView.ChangeVariableListener{

    private DataClassController mController;
    private ViewGroup mContentTable;
    private TableColumn.IVariable mVariable;

    public static DataClassView newInstance(TableColumn.IVariable variable){
        DataClassView fragment = new DataClassView();
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

        View view = inflater.inflate(R.layout.fragment_data_class, container, false);
        mController = new DataClassController(this, mVariable);
        mContentTable = view.findViewById(R.id.content_class_table);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        ViewGroup table = mController.buildTable();
        mContentTable.addView(table);
    }

    @Override
    public void onChangeVariable(TableColumn.IVariable variable) {
        mController.changeVariable(variable);
    }
}

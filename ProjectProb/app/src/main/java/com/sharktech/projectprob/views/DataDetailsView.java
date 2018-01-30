package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.DataDetailsController;
import com.sharktech.projectprob.customtable.TableColumn;

public class DataDetailsView extends Fragment implements DataAnalyseView.ChangeVariableListener {

    private DataDetailsController mController;
    private TableColumn.IVariable mVariable;

    public static DataDetailsView newInstance(TableColumn.IVariable variable) {

        DataDetailsView fragment = new DataDetailsView();
        Bundle bundle = new Bundle();
        bundle.putSerializable(DataAnalyseView.ANALYSES, variable);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (bundle != null)
            mVariable = (TableColumn.IVariable) bundle.getSerializable(DataAnalyseView.ANALYSES);

        mController = new DataDetailsController(this, mVariable);
        mController.calculate();
        return inflater.inflate(R.layout.fragment_data_details, container, false);
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
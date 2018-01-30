package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.adapters.SpinAdapter;
import com.sharktech.projectprob.controllers.DataChartController;
import com.sharktech.projectprob.customtable.TableColumn;

public class DataChartView extends Fragment implements DataAnalyseView.ChangeVariableListener{

    private DataChartController mController;
    private TableColumn.IVariable mVariable;

    public static DataChartView newInstance(TableColumn.IVariable variable) {

        DataChartView fragment = new DataChartView();
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

        mController = new DataChartController(this, mVariable);

        View view = inflater.inflate(R.layout.fragment_data_chart, container, false);
        Spinner spnGraphs = view.findViewById(R.id.spn_graphs);
        SpinAdapter adapter = new SpinAdapter(getContext());
        spnGraphs.setAdapter(adapter.getAdapter(R.array.graphs));

        spnGraphs.setOnItemSelectedListener(mController.getItemSelectedListener());

        mController.initChart();
        return view;
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

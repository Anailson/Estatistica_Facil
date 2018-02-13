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
import com.sharktech.projectprob.customview.ItemDataDetail;

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
        if (bundle != null) mVariable = (TableColumn.IVariable) bundle.getSerializable(DataAnalyseView.ANALYSES);

        View view = inflater.inflate(R.layout.fragment_data_details, container, false);
        ItemDataDetail dataArithmetic = view.findViewById(R.id.detail_avg_arithmetic);
        ItemDataDetail dataArithmeticPound = view.findViewById(R.id.detail_avg_arithmetic_pound);
        ItemDataDetail dataGeometric = view.findViewById(R.id.detail_avg_geometric);
        ItemDataDetail dataGeometricPound = view.findViewById(R.id.detail_avg_geometric_pound);
        ItemDataDetail dataWeighted = view.findViewById(R.id.detail_avg_weighted);
        ItemDataDetail dataWeightedPound = view.findViewById(R.id.detail_avg_weighted_pound);
        ItemDataDetail dataQuadratic = view.findViewById(R.id.detail_avg_quadratic);
        ItemDataDetail dataQuadraticPound = view.findViewById(R.id.detail_avg_quadratic_pound);
        ItemDataDetail dataKurtosis = view.findViewById(R.id.detail_kurtosis);
        ItemDataDetail dataAsymmetryQuart = view.findViewById(R.id.detail_asymmetry);

        dataArithmetic.setTexts(R.string.txt_avg_arithmetic, R.string.sym_avg_arithmetic, R.string.txt_default);
        dataArithmeticPound.setTexts(R.string.txt_avg_arithmetic_pound, R.string.sym_avg_arithmetic_pound, R.string.txt_default);
        dataGeometric.setTexts(R.string.txt_avg_geometric, R.string.sym_avg_geometric, R.string.txt_default);
        dataGeometricPound.setTexts(R.string.txt_avg_geometric_pound, R.string.sym_avg_geometric_pound, R.string.txt_default);
        dataWeighted.setTexts(R.string.txt_avg_weighted, R.string.sym_avg_weighted, R.string.txt_default);
        dataWeightedPound.setTexts(R.string.txt_avg_weighted_pound, R.string.sym_avg_weighted_pound, R.string.txt_default);
        dataQuadratic.setTexts(R.string.txt_avg_quadratic, R.string.sym_avg_quadratic, R.string.txt_default);
        dataQuadraticPound.setTexts(R.string.txt_avg_quadratic_pound, R.string.sym_avg_quadratic_pound, R.string.txt_default);
        dataKurtosis.setTexts(R.string.txt_kurtosis, R.string.sym_kurtosis, R.string.txt_default);
        dataAsymmetryQuart.setTexts(R.string.txt_asymmetry, R.string.sym_asymmetry_quart, R.string.txt_default);

        mController = new DataDetailsController(this, mVariable);
        mController.calculate();
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
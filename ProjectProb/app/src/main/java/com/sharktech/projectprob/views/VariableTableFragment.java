package com.sharktech.projectprob.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.MainController;
import com.sharktech.projectprob.controllers.VariableTableController;

public class VariableTableFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vars_table, container, false);

        VariableTableController mController = new VariableTableController(this);
        ViewGroup mParent = view.findViewById(R.id.content_main);
        Button btnCmd = view.findViewById(R.id.btn_cmd);

        btnCmd.setOnClickListener(mController.getClickListener());
        mController.buildTable(mParent, MainController.getVariables(getContext()));

        return view;
    }
}
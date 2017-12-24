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
import com.sharktech.projectprob.customtable.CustomTable;

public class VariableTableFragment extends Fragment {

    private CustomTable customTable;
    private ViewGroup layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_vars_table, container, false);

        VariableTableController controller = new VariableTableController(getActivity());
        Button btnCmd = view.findViewById(R.id.btn_cmd);

        layout = view.findViewById(R.id.content_main);

        btnCmd.setOnClickListener(controller.getClickListener());

        customTable = new CustomTable(getContext());
        customTable.add(MainController.getVariables(getContext()));
        customTable.build();
        layout.addView(customTable);

        return view;
    }
}
package com.sharktech.projectprob.views;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.controllers.VariableTableController;

public class VariableTableFragment extends Fragment {

    private boolean mAttached;
    private VariableTableController mController;
    private ViewGroup mContentTable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mController = new VariableTableController(this);
        View view = inflater.inflate(R.layout.fragment_vars_table, container, false);

        Button btnCmd = view.findViewById(R.id.btn_cmd);
        mContentTable = view.findViewById(R.id.content_table);

        btnCmd.setOnClickListener(mController.getClickListener());
        Log.e("Tag", "createView");

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.e("Tag", "onAttach");
        mAttached = true;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("Tag", "onResume");
        ViewGroup table = mController.buildTable();
        mContentTable.addView(table);

    }
}
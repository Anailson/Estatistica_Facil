package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.parser.VariableParser;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;

import exception.TokenException;


public class VariableTableController {

    private Fragment mFragment;
    private Listeners mListeners;
    private CustomTable mCustomTable;
    private VariableParser mParser;

    public VariableTableController(Fragment fragment) {
        this.mListeners = new Listeners();
        this.mFragment = fragment;
        this.mParser = new VariableParser(fragment.getContext());
    }

    public View.OnClickListener getClickListener() {
        return mListeners;
    }

    public ViewGroup buildTable() {
        mCustomTable = new CustomTable(mFragment.getContext());
        mCustomTable.setLineCounter(false);
        mCustomTable.setNoDataFound(R.string.txt_no_variable_inserted);
        ArrayList<TableColumn.IVariable> vars = VariablePersistence.getInstance().getVariables();

        return mCustomTable.build(vars);
    }

    private void clearTable() {
        View view = mFragment.getView();
        if (view != null) {
            ViewGroup contentTable = view.findViewById(R.id.content_table);
            contentTable.removeAllViews();
            mCustomTable.clear();
        }
    }

    private class Listeners implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Activity activity = mFragment.getActivity();
            if (activity != null) {
                String source = ((EditText) activity.findViewById(R.id.edt_cmd)).getText().toString();
                mParser.setParserResultListener(new OnPostAnalyse());
                mParser.analyse(source);
            }
        }
    }

    private class OnPostAnalyse implements VariableParser.IParserResult {

        @Override
        public void onSuccess() {

            clearTable();
            mFragment.onResume();
        }

        @Override
        public void onError(TokenException e) {
            Toast.makeText(mFragment.getContext(),
                    "Exception " + e.getTokenInfo(),
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
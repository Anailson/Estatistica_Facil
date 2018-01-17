package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.models.VariableString;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;

import exception.TokenException;
import lexer.Parser;
import lexer.Token;
import parser.ParserAdd;


public class VariableTableController {

    private Fragment mFragment;
    private Listeners mListeners;
    private CustomTable mCustomTable;

    public VariableTableController(Fragment fragment){
        this.mListeners = new Listeners();
        this.mFragment = fragment;
    }

    public View.OnClickListener getClickListener(){
        return mListeners;
    }

    public ViewGroup buildTable(){
        mCustomTable = new CustomTable(mFragment.getContext());
        mCustomTable.setLineCounter(false);
        mCustomTable.setNoDataFound(R.string.txt_no_variable_inserted);
        ArrayList<TableColumn.IVariable> vars = VariablePersistence.getInstance().getVariables();
        return mCustomTable.build(vars);
    }

    private void clearTable(){

        Activity activity = mFragment.getActivity();
        if(activity != null){

            ViewGroup contentTable = activity.findViewById(R.id.content_table);
            //contentTable.removeView(mCustomTable);

            mCustomTable.clear();
            mFragment.onResume();
        }
    }

    private void add(ParserAdd parser){

        VariableString objects  = new VariableString("Nova Variavel");
        objects.add(new String[]{ "Valor #1", "Valor #2", "Valor #3", "Valor #4", "Valor #5", "Valor #6"});
        VariablePersistence.getInstance().add(objects);
        clearTable();
    }


    private class Listeners implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Activity activity = mFragment.getActivity();
            if (activity != null) {
                String source = ((EditText) activity.findViewById(R.id.edt_cmd)).getText().toString();

                Parser parser = new Parser();
                parser.register(Token.ADD, new ParserAdd());

                try {
                    Parser.IBaseOperation op = parser.analyse(source + " ");

                    switch (op.id()) {
                        case Token.ADD: add((ParserAdd) op); break;
                    }
                } catch (TokenException e) {
                    Log.e("Exception", e.getMessage());
                }
            }
        }
    }
}
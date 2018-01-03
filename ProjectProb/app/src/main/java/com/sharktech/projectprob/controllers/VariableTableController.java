package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.Variable;

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

    public void buildTable(ViewGroup parent, ArrayList<Variable> vars){
        mCustomTable = new CustomTable(mFragment.getContext());
        parent.addView(mCustomTable.build(vars));
    }

    private void rebuildTable(ViewGroup parent, ArrayList<Variable> vars){
        if (parent.indexOfChild(mCustomTable) > 0){
            parent.removeView(mCustomTable);
        }
        ((ViewGroup) mCustomTable.getParent()).removeView(mCustomTable);

        parent.addView(mCustomTable.rebuild(vars));
        mFragment.onResume();
    }

    private void add(ParserAdd parser){

        Activity activity = mFragment.getActivity();
        if(activity != null) {

            Variable<String> var = new Variable<>(mFragment.getContext(), "Nova Variavel");
            for(int i = 0; i < 5; i++){
                var.add("Nova var #" + i);
            }
            ArrayList<Variable> vars = MainController.getVariables(mFragment.getContext());
            vars.add(var);
            rebuildTable((ViewGroup) activity.findViewById(R.id.content_main), vars);
        }
    }

    private class Listeners implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Activity activity = mFragment.getActivity();
            String source = ((EditText) activity.findViewById(R.id.edt_cmd)).getText().toString() ;

            Parser parser = new Parser();
            parser.register(Token.ADD, new ParserAdd());
            //Log.e("Map", map.toString() + " " + map.size());

            try {
                Parser.IBaseOperation op = parser.analyse(source + " ");

                switch (op.id()){
                    case Token.ADD: add((ParserAdd) op); break;
                }
            } catch (TokenException e) {
                Log.e("Exception", e.getMessage());
            }
        }
    }
}
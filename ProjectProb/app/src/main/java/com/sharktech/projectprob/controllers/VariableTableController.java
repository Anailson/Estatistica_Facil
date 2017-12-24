package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.sharktech.projectprob.R;

import java.util.HashMap;
import java.util.Map;

import exception.TokenException;
import lexer.Parser;
import lexer.Parser.IBaseOperation;
import lexer.SyntacticParser;
import lexer.Token;
import parser.ParserAdd;


public class VariableTableController {

    private Activity activity;
    private Listeners listeners;

    public VariableTableController(Activity activity){
        this.listeners = new Listeners();
        this.activity = activity;
    }

    public View.OnClickListener getClickListener(){
        return listeners;
    }

    private void add(ParserAdd parser){
        Log.e("ADD", parser.getValue(Token.COLUMN) + " " + parser.getValue(Token.VAL));
    }

    private class Listeners implements View.OnClickListener{

        @Override
        public void onClick(View v) {

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
package com.sharktech.projectprob.controllers;

import android.app.Activity;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.alert.DefaultAlert;
import com.sharktech.projectprob.customtable.CustomTable;
import com.sharktech.projectprob.customtable.TableColumn;
import com.sharktech.projectprob.parser.VariableParser;
import com.sharktech.projectprob.persistence.VariablePersistence;

import java.util.ArrayList;
import java.util.Locale;

import lexer.Token;


public class VariableTableController {

    private Fragment mFragment;
    private Listeners mListeners;
    private CustomTable mCustomTable;
    private VariableParser mParser;

    public VariableTableController(Fragment fragment) {
        this.mListeners = new Listeners();
        this.mFragment = fragment;
        this.mParser = new VariableParser();
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

    private class Listeners implements View.OnClickListener, VariableParser.IParserResult {

        //View.OnClickListener
        @Override
        public void onClick(View v) {
            Activity activity = mFragment.getActivity();
            if (activity != null) {
                String source = ((EditText) activity.findViewById(R.id.edt_cmd)).getText().toString();
                mParser.setParserResultListener(this);
                mParser.analyse(source);
            }
        }

        //VariableParser.IParserResult
        @Override
        public void onSuccess() {

            Activity activity = mFragment.getActivity();
            if (activity != null){
                ((EditText) activity.findViewById(R.id.edt_cmd)).setText("");
            }
            clearTable();
            mFragment.onResume();
        }

        @Override
        public void onError(VariableParser.Error error, Token token) {

            int title = R.string.err_general;
            String msg = mFragment.getString(title);
            if (error == VariableParser.Error.ERR_COL_INDEX) {
                title = R.string.err_col_index;
                msg = String.format(Locale.getDefault(), "%s %s %s",
                        "O índice da coluna", token.getText(), "não existe.");
            } else if (error == VariableParser.Error.ERR_ROW_INDEX) {
                title = R.string.err_row_index;
                msg = String.format(Locale.getDefault(), "%s %s %s",
                        "O indice da linha", token.getText(), "não existe.");
            } else if (error == VariableParser.Error.ERR_NUMBER_TEXT) {
                title = R.string.err_text_number;
                msg = String.format(Locale.getDefault(), "%s: %s",
                        "Era esperado um texto e foi encontrado um numero", token.getText());
            } else if (error == VariableParser.Error.ERR_TEXT_NUMBER) {
                title = R.string.err_number_text;
                msg = String.format(Locale.getDefault(), "%s: %s",
                        "Era esperado um numero e foi encontrado um texto", token.getText());
            } else if (error == VariableParser.Error.ERR_UNKNOWN_COMMAND) {
                title = R.string.err_unknown_command;
                msg = String.format(Locale.getDefault(), "%s: %s",
                        "Foi digitado um comando desconhecido: ", token.getText());
            }
            showAlert(mFragment.getString(title), msg);
        }

        private void showAlert(String title, String msg) {

            DefaultAlert alert = new DefaultAlert(mFragment.getContext());
            alert.setPositiveListener(R.string.btn_ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alert.setMessage(msg)
                    .setTitle(title)
                    .show();
        }
    }
}
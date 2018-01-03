package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sharktech.projectprob.R;

import java.util.ArrayList;

public class CustomTable extends HorizontalScrollView {

    private ScrollView mContent;
    //private ArrayList<Variable> mVariables;
    private boolean mLineCounter;

    public CustomTable(Context context) {
        super(context);
        init();
    }

    public CustomTable(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setLineCounter(boolean lineCounter) {
        this.mLineCounter = lineCounter;
    }

    private void init() {

        //mVariables = new ArrayList<>();

        mContent = new ScrollView(getContext());
        mContent.setLayoutParams(getParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContent.setVerticalScrollBarEnabled(true);
        //mContent.setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);

        setLayoutParams(getParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setHorizontalScrollBarEnabled(true);
        //setScrollBarStyle(View.SCROLLBARS_INSIDE_INSET);
        addView(mContent);
    }
/*
    public void add(Variable variable){
        mVariables.add(variable);
    }

    public void add(ArrayList<Variable> variables){
        this.mVariables.addAll(variables);
    }

    public void removeAllVariables(){
        mVariables = new ArrayList<>();
    }
*/
    public ViewGroup build(ArrayList<Variable> variables) {
        LinearLayout layout = getHorizontalLayout();
        mLineCounter = true;
        int nRows = nRows(variables);

        if (hasVariables(variables)) {

            if(mLineCounter){
                layout.addView(addLineCounter(nRows));
            }
            buildColumns(variables, layout, nRows);

        } else {

            Variable title = new Variable(getContext(), "Ainda não foi inserida nenhuma variável.");
            layout.addView(title.build());
        }
        mContent.addView(layout);
        return this;
    }

    public ViewGroup rebuild(ArrayList<Variable> variables) {

        Log.e("rebuild", variables.size() + "");
        removeView(mContent);
        init();
        return build(variables);
    }

    private void buildColumns(ArrayList<Variable> variables, LinearLayout layout, int nRows){

        for(int col = 0; col < variables.size(); col++) {
            Variable column = variables.get(col);
            if(column.nElements() < nRows) {
                fillColumn(nRows, column);
            }
            column.setPosition(col);
            layout.addView(column.build());
        }
    }

    private void fillColumn(int nRows, Variable column){
        for(int i = column.nElements(); i < nRows; i++){
            column.emptyCell();
        }
    }

    private Variable addLineCounter(int nRows){

        Variable<String> column = new Variable<>(getContext(), "#");
        column.setBackgroundColor(getResources().getColor(R.color.color_primary_light));
        for(int i = 0; i < nRows; i++){
            column.add(String.valueOf(i + 1));
        }
        return column.build();
    }

    private LinearLayout getHorizontalLayout(){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

    private boolean hasVariables(ArrayList<Variable> variables) {

        if (variables.size() < 0) {
            return false;
        }
        for (Variable column: variables) {

            if (column.nElements() > 0) {
                return true;
            }
        }
        return false;
    }

    private int nRows(ArrayList<Variable> variables){

        int nRows = 0;
        for (Variable var : variables) {
            nRows = nRows > var.nElements() ? nRows : var.nElements();
        }
        return nRows;
    }

    private ViewGroup.LayoutParams getParams(int width, int height) {
        return new ViewGroup.LayoutParams(width, height);
    }
}
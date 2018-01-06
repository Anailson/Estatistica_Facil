package com.sharktech.projectprob.customtable;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sharktech.projectprob.R;
import com.sharktech.projectprob.customtable.Variable.IVariable;
import com.sharktech.projectprob.models.VariableNumber;

import java.util.ArrayList;

public class CustomTable extends HorizontalScrollView {

    private ScrollView mContent;
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

        mContent = new ScrollView(getContext());
        mContent.setLayoutParams(getParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        mContent.setVerticalScrollBarEnabled(true);

        setLayoutParams(getParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setHorizontalScrollBarEnabled(true);
        addView(mContent);
    }

    public ViewGroup build(ArrayList<IVariable> variables) {
        LinearLayout layout = getHorizontalLayout();
        mLineCounter = true;
        int nRows = nRows(variables);

        if (hasVariables(variables)) {

            if(mLineCounter){
                layout.addView(addLineCounter(nRows));
            }
            buildColumns(variables, layout, nRows);

        } else {

            Variable title = Variable.newVariable(getContext(), getContext().getString(R.string.txt_no_variable));
            layout.addView(title.build());
        }
        mContent.addView(layout);
        return this;
    }

    public void clear() {

        removeView(mContent);
        ViewGroup parentContent = (ViewGroup) mContent.getParent();
        if(parentContent != null) parentContent.removeView(mContent);

        init();
    }

    private void buildColumns(ArrayList<IVariable> variables, LinearLayout layout, int nRows){

        for(int col = 0; col < variables.size(); col++) {

            int nElements = variables.get(col).nElements();
            Variable<IVariable> column = new Variable<>(getContext(), variables.get(col));

            if(nElements < nRows) {
                fillColumn(nRows, column);
            }
            column.setPosition(col);
            removeParent(column);
            layout.addView(column.build());
        }
    }


    private void removeParent(View child){
        ViewGroup parent = (ViewGroup) child.getParent();
        if(parent != null) parent.removeView(child);
    }

    private void fillColumn(int nRows, Variable column){
        for(int i = column.nElements(); i < nRows; i++){
            //column.emptyCell();
        }
    }

    private Variable addLineCounter(int nRows){

        VariableNumber column = new VariableNumber("#");
        for(int i = 0; i < nRows; i++){
            column.add(i + 1);
        }

        Variable<VariableNumber> variable = new Variable<>(getContext(), column);
        variable.setBackgroundColor(getResources().getColor(R.color.color_primary_light));
        return variable.build();
    }

    private LinearLayout getHorizontalLayout(){
        LinearLayout layout = new LinearLayout(getContext());
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        layout.setOrientation(LinearLayout.HORIZONTAL);
        return layout;
    }

    private boolean hasVariables(ArrayList<IVariable> variables) {

        if (variables.size() < 0) {
            return false;
        }
        for (IVariable column: variables) {

            if (column.nElements() > 0) {
                return true;
            }
        }
        return false;
    }

    private int nRows(ArrayList<IVariable> variables){

        int nRows = 0;
        for (IVariable var : variables) {
            nRows = nRows > var.nElements() ? nRows : var.nElements();
        }
        return nRows;
    }

    private ViewGroup.LayoutParams getParams(int width, int height) {
        return new ViewGroup.LayoutParams(width, height);
    }
}